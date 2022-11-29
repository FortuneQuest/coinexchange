package cn.oc.service.impl;

import cn.oc.constant.LoginConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : UserServiceDetailServiceImpl
 * @Author: oc
 * @Date: 2022/11/08/22:05
 * @Description:
 **/
@Service
public class UserServiceDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //区分是后台人员还是用户
        String login_type = requestAttributes.getRequest().getParameter("login_type");
        if(StringUtils.isEmpty(login_type)){
            throw new AuthenticationServiceException("登陆类型不能为空");
        }
        UserDetails userDetails =null;
        try{
            //得到授权类型
            String grant_type = requestAttributes.getRequest().getParameter("grant_type");
            //如果grant_type等于一个  fresh_token 我们才纠正
            if (LoginConstant.REFRESH_TYPE.equalsIgnoreCase(grant_type)) {
                username =adjustUsername(username,login_type);
            }
            switch (login_type){
                case LoginConstant.ADMIN_TYPE:
                    userDetails=loadSysUserByUsername(username);
                    break;
                case LoginConstant.MEMBER_TYPE:
                    userDetails=loadMemberUserByUsername(username);
                    break;
                default:
                    throw  new AuthenticationServiceException("暂不支持这种登录方式"+login_type);
            }
        }catch (IncorrectResultSizeDataAccessException e){
               //用户不存在
            throw  new UsernameNotFoundException("用户名"+username+"不存在");
        }
            return userDetails;
    }

    /**
     * 纠正用户的名称
     * @param username  用户id
     * @param login_type admin_type，member_type
     * @return
     */
    private String adjustUsername(String username, String login_type) {
        if(LoginConstant.ADMIN_TYPE.equals(login_type)){
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_USER_WITH_ID,String.class,username);
        }
        if(LoginConstant.MEMBER_TYPE.equals(login_type)){
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_USER_WITH_ID,String.class,username);
        }
        return username;
    }

    /**
     * 会员登录
     * @param username
     * @return
     */
    private UserDetails loadMemberUserByUsername(String username) {

        return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_SQL, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            if(rs.wasNull()){
                throw  new UsernameNotFoundException("用户"+username+"不存在");
            }
            long id = rs.getLong("id");  //会员id
            String password = rs.getString("password");  //会员密码
            int status = rs.getInt("status"); //会员状态
            return  new User(
                    String.valueOf(id),
                    password,
                    status==1,
                    true,
                    true,
                    true,
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            }
        },username,username);
    }

    /**
     * 后台人员登录
     * @param username
     * @return
     */
    private UserDetails loadSysUserByUsername(String username) {
        //1、使用用户名查询用户
        return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_SQL, new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                        if (resultSet.wasNull()) {
                            throw new UsernameNotFoundException("用户名" + username + "不存在");
                        }

                        long id = resultSet.getLong("id");  //用户id
                        String password = resultSet.getString("password"); //用户的状态
                        int status = resultSet.getInt("status"); //用户的状态
                        return new User(
                                String.valueOf(id),   //使用userid代替username
                                password,
                                status == 1,
                                true,
                                true,
                                true,
                                getSysUserPermission(id)
                        );
                    }
        },username);
    }

    /**
     * 2、普通用户需要角色查询权限
     * 通过用户id查询用户的权限数据
     * @param id
     * @return
     */
    private Collection<? extends GrantedAuthority> getSysUserPermission(long id) {
        // 1、当用户为超级管理员时，具有所有权限
        String roleCode = jdbcTemplate.queryForObject(LoginConstant.QUERY_ROLE_CODE_SQL, String.class, id);
        //权限名称
        List<String> permissions =null;
        if (LoginConstant.ADMIN_ROLE_CODE.equals(roleCode)) {
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_ALL_PERMISSIONS, String.class);
        } else {
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_PERMISSION_SQL, String.class, id);
        }
        if (permissions ==null||permissions.isEmpty()){
            return Collections.emptySet();
        }
        return permissions.stream()
                .distinct() //去重
                .map(perm->new SimpleGrantedAuthority(perm))
                .collect(Collectors.toSet());

    }
}
