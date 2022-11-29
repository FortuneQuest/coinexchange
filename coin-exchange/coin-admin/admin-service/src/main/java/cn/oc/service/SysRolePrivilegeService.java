package cn.oc.service;

import cn.oc.domain.SysMenu;
import cn.oc.domain.SysRolePrivilege;
import cn.oc.model.RolePrivilegesParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : SysRolePrivilegeService
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description:
 **/
public interface SysRolePrivilegeService extends IService<SysRolePrivilege> {


    /**
     * 查询角色的权限
     *
     * @param roleId
     * @return
     */
    List<SysMenu> findSysMenuAndPrivileges(Long roleId);

    /**给角色授予权限
     * @param rolePrivilegesParam
     */
    Boolean grant_privileges(RolePrivilegesParam rolePrivilegesParam);
}
