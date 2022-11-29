package cn.oc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.oc.domain.SysRole;
import cn.oc.mapper.SysRoleMapper;
import cn.oc.service.SysRoleService;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * @ClassName : SysRoleServiceImpl
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 判断用户是否一个管理员
     * @param userId
     * @return
     */
    @Override
    public boolean isSuperAdmin(Long userId) {
        //当用户的角色code为ROLE_ADMIN是该 用户为超级管理员
        //用户的id查用户的角色->该角色code是否为ROLE_Admin
        String roleCode = sysRoleMapper.getUserRoleCode(userId);
        if (StringUtils.isEmpty(roleCode) && "ROLE_ADMIN".equals(roleCode)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param page  分页数据
     * @param name 角色名称
     * @return
     */
    @Override
    public Page<SysRole> findByPage(Page<SysRole> page, String name) {
        return page(page,new LambdaQueryWrapper<SysRole>().like(!StringUtils.isEmpty(name), SysRole::getName, name));
    }
}
