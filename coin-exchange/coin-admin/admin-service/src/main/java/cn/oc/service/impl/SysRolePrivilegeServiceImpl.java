package cn.oc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.oc.domain.SysMenu;
import cn.oc.domain.SysPrivilege;
import cn.oc.model.RolePrivilegesParam;
import cn.oc.service.SysMenuService;
import cn.oc.service.SysPrivilegeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.oc.domain.SysRolePrivilege;
import cn.oc.mapper.SysRolePrivilegeMapper;
import cn.oc.service.SysRolePrivilegeService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * @ClassName : SysRolePrivilegeServiceImpl
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
@Service
public class SysRolePrivilegeServiceImpl extends ServiceImpl<SysRolePrivilegeMapper, SysRolePrivilege> implements SysRolePrivilegeService{

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysPrivilegeService sysPrivilegeService;

    @Autowired
    private SysRolePrivilegeService sysRolePrivilegeService;

    @Override
    public List<SysMenu> findSysMenuAndPrivileges(Long roleId) {
        List<SysMenu> list = sysMenuService.list();
        if (CollectionUtil.isEmpty(list)) {
            return  Collections.emptyList();
        }
        //一级菜单
        List<SysMenu> rootMenus = list.stream().filter(
                sysMenu -> sysMenu.getParentId() == null)
                .collect(Collectors.toList());
        if (CollectionUtil.isEmpty(rootMenus)) {
            return  Collections.emptyList();
        }
        //2级菜单
        List<SysMenu> subMenus =new ArrayList<>();
        for (SysMenu rootMenu : rootMenus) {
            subMenus.addAll(getChildMenus(rootMenu.getId(),roleId,list));
        }
        return subMenus;
    }

    @Override
    @Transactional
    public Boolean grant_privileges(RolePrivilegesParam rolePrivilegesParam) {
        Long roleId = rolePrivilegesParam.getRoleId();
        //先删除之前该角色的权限
        sysRolePrivilegeService.remove(new LambdaQueryWrapper<SysRolePrivilege>().
                eq(SysRolePrivilege::getRoleId, roleId));
            //新增该角色的权限
            List<Long> privilegeIds = rolePrivilegesParam.getPrivilegeIds();
            if (CollectionUtil.isNotEmpty(privilegeIds)) {
                ArrayList<SysRolePrivilege> sysPrivileges = new ArrayList<>();
                for (Long privilegeId : privilegeIds) {
                    SysRolePrivilege sysRolePrivilege = new SysRolePrivilege();
                    sysRolePrivilege.setRoleId(roleId);
                    sysRolePrivilege.setPrivilegeId(privilegeId);
                    sysPrivileges.add(sysRolePrivilege);

                boolean b = sysRolePrivilegeService.saveBatch(sysPrivileges);
                return b;
            }
            return true;

        }
        return null;
    }

    /**
     * 查询菜单的子菜单
     * @return
     */
    private List<SysMenu> getChildMenus(Long parentId,Long roleId,List<SysMenu> sources) {
        ArrayList<SysMenu> childs = new ArrayList<>();
        for (SysMenu source : sources) {
            if (Objects.equals(source.getParentId(), parentId)) {
                childs.add(source);
                //给该儿子菜单设置儿子菜单  递归
                source.setChilds(getChildMenus(source.getId(),roleId,sources));
               List<SysPrivilege> sysPrivileges = sysPrivilegeService.getAllSysPrivileges(roleId,source.getId());
                //儿子菜单可能包含权限
                source.setPrivileges(sysPrivileges);
            }
        }
        return  childs;
    }
}
