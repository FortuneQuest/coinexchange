package cn.oc.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.oc.domain.SysUser;
import cn.oc.model.R;
import cn.oc.service.SysUserService;
import cn.oc.utils.ArraysUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : SysUserController
 * @Author: oc
 * @Date: 2022/11/25/13:57
 * @Description:
 **/
@Api(tags = "员工管理")
@RequestMapping("/users")
@RestController
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("员工查询")
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "每一页显示的条数")
    })
    @PreAuthorize("hasAuthority('sys_user_query')")
    public R<Page<SysUser>> findByPage(@ApiIgnore Page<SysUser> page ,String mobile ,String fullname) {
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<SysUser> sysUserPage =sysUserService.findByPage(page,mobile,fullname);
        return R.ok(sysUserPage);
    }

    @PostMapping
    @ApiOperation("新增员工")
    @PreAuthorize("hasAuthority('sys_user_create')")
    public R addUser(@RequestBody SysUser sysUser) {
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId =Long.valueOf(user.getUsername()) ;
        sysUser.setCreateBy(userId);
      boolean isOk =  sysUserService.addUser(sysUser);

        if (isOk) {
            return R.ok("新增成功");
        } else {
            return  R.fail("新增失败");
        }
    }

    @PatchMapping
    @ApiOperation("修改员工信息")
    @PreAuthorize("hasAuthority('sys_user_update')")
    public R update(@RequestBody SysUser sysUser) {
        boolean updateUser = sysUserService.updateUser(sysUser);
        if (updateUser) {
            return R.ok("信息编辑成功");
        } else {
          return  R.fail("信息编辑失败");
        }
    }

    @PostMapping("/delete")
    @ApiOperation("删除员工")
    @PreAuthorize("hasAuthority('sys_user_delete')")
    public R deleteUser(@RequestBody Long[] ids) {
        if(ArraysUtil.ArraysIsEmpty(ids)){
            return R.fail("删除时，需要给予id的值");
        }
        boolean b = sysUserService.removeByIds(Arrays.asList(ids));
        if (b) {
            return R.ok("删除成功");
        } else {
            return R.fail("删除失败");
        }
    }


}
