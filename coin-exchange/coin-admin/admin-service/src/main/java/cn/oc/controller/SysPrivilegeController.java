package cn.oc.controller;

import cn.oc.domain.SysPrivilege;
import cn.oc.model.R;
import cn.oc.service.SysPrivilegeService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : SysPrivilegeController
 * @Author: oc
 * @Date: 2022/11/19/22:45
 * @Description: 权限管理控制器
 **/
@RestController
@RequestMapping("/privileges")
@Api(tags = "权限管理的控制器")
public class SysPrivilegeController {

    @Autowired
    private SysPrivilegeService sysPrivilegeService;

    /**
     * 权限数据分页查询
     *
     * @param page
     * @return
     */
    @GetMapping()
    @ApiOperation(value = "查询权限配置(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的大小"),
    })
    @PreAuthorize("hasAuthority('sys_privilege_query')")
    public R<Page<SysPrivilege>> findByPage(@ApiIgnore Page<SysPrivilege> page) {
        //查询时，将最近新增的或修改的，优先展示出来  last_update_time
        //添加排序条件，根据列的接口排序
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<SysPrivilege> sysPrivilegePage = sysPrivilegeService.page(page);
        return R.ok(sysPrivilegePage);
    }

    @PostMapping
    @ApiOperation(value = "新增一个权限")
    @PreAuthorize("hasAuthority('sys_privilege_create')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPrivilege", value = "sysPrivilege的json数据")
    })
    public R add(@RequestBody @Validated SysPrivilege sysPrivilege) {
        //新增时需要给新增对象，填充一些属性
//        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String userId = principal.getUsername();
//        sysPrivilege.setCreateBy(Long.valueOf(userId) );
//        sysPrivilege.setCreated(new Date());
//        sysPrivilege.setLastUpdateTime(new Date());

        boolean save = sysPrivilegeService.save(sysPrivilege);
        if (save) {
            return R.ok("新增成功");
        }
        return R.fail("新增失败");
    }

    @PatchMapping
    @ApiOperation(value = "编辑一个权限")
    @PreAuthorize("hasAuthority('sys_privilege_update')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPrivilege", value = "sysPrivilege的json数据")
    })
    public R update(@RequestBody @Validated SysPrivilege sysPrivilege) {
        boolean update = sysPrivilegeService.updateById(sysPrivilege);
        if (update) {
            return R.ok("修改成功");
        }
        return R.fail("修改失败");
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除权限")
    @PreAuthorize("hasAuthority('sys_privilege_delete')")
    public R delete(@RequestBody Long[] ids) {
        if (ArraysUtil.ArraysIsEmpty(ids)) {
            R.fail("删除的id不能为空");
        }
        boolean removeByIds = sysPrivilegeService.removeByIds(Arrays.asList(ids));
        if (removeByIds) {
            return R.ok("删除成功");
        } else {
            return R.fail("删除失败");
        }
    }
}
