package cn.oc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.oc.domain.SysUserRole;
import cn.oc.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.oc.domain.SysUser;
import cn.oc.mapper.SysUserMapper;
import cn.oc.service.SysUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : SysUserServiceImpl
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description:
 **/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;


    @Override
    public Page<SysUser> findByPage(Page<SysUser> page, String mobile, String fullname) {
        Page<SysUser> pageData = page(page,
                new LambdaQueryWrapper<SysUser>()
                        .like(!StringUtils.isEmpty(mobile), SysUser::getMobile, mobile)
                        .like(!StringUtils.isEmpty(fullname), SysUser::getFullname, fullname)
        );
        List<SysUser> records = pageData.getRecords();
        if (CollectionUtil.isNotEmpty(records)) {
            for (SysUser record : records) {
                List<SysUserRole> userRoles = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getUserId, record.getId()));
                if (CollectionUtil.isNotEmpty(userRoles)) {
                    record.setRole_Strings(userRoles.stream()
                            .map(sysUserRole -> sysUserRole.getRoleId().toString())
                            .collect(Collectors.joining(",")));
                }
            }
        }
        return pageData;
    }





    @Override
    @Transactional()
    public boolean addUser(SysUser sysUser) {
        //用户的密码
        String password = sysUser.getPassword();
        //角色ids
        String role_strings = sysUser.getRole_Strings();
        //加密密码
        String encode = getBCryptPasswordEncoder().encode(password);
        sysUser.setPassword(encode);
        boolean save = super.save(sysUser);
        if (save) {
            //给用户新增权限数据
            if (!StringUtils.isEmpty(role_strings)) {
                String[] roleIds = role_strings.split(",");
                //保持list的大小和ids长度保持一致
                List<SysUserRole> sysUserRoles = new ArrayList<>(roleIds.length);
                for (String roleId : roleIds) {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setRoleId(Long.valueOf(roleId));
                    //TODO 该id并未获取到
                    sysUserRole.setUserId(sysUser.getId());
                    sysUserRoles.add(sysUserRole);
                }
                //批量操作
                sysUserRoleService.saveBatch(sysUserRoles);
            }
        }
        return save;
    }

    @Override
    public boolean updateUser(SysUser sysUser) {
        String password = sysUser.getPassword();
        String encode = getBCryptPasswordEncoder().encode(password);
        sysUser.setPassword(encode);
        boolean update = super.updateById(sysUser);
        return update;
    }

    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    @Transactional
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        boolean b = super.removeByIds(idList);
        sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId,idList));
        return b;
    }
}
