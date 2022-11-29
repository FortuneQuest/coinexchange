package cn.oc.controller;

import cn.oc.domain.SysMenu;
import cn.oc.model.R;
import cn.oc.model.RolePrivilegesParam;
import cn.oc.service.SysRolePrivilegeService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : SysRolePrivilegeController
 * @Author: oc
 * @Date: 2022/11/24/17:15
 * @Description:
 **/
@Api(tags = "角色权限的配置")
@RestController
public class SysRolePrivilegeController {

    @Autowired
    private SysRolePrivilegeService sysRolePrivilegeService;

    @GetMapping("/role_privileges")
    @ApiOperation(value = "查询角色的权限列表")
    public R<List<SysMenu>> findSysMenuAndPrivileges(Long roleId) {
       List<SysMenu> sysMenus= sysRolePrivilegeService.findSysMenuAndPrivileges(roleId);
    return  R.ok(sysMenus);}


    @PostMapping("grant_privileges")
    @ApiOperation("授予角色某种权限")

    public R grant_privileges(@RequestBody RolePrivilegesParam rolePrivilegesParam) {
       boolean isOk= sysRolePrivilegeService.grant_privileges(rolePrivilegesParam);
        if (isOk) {
            return R.ok("授予成功");
        }
        return  R.fail("授予失败");
    }
}
