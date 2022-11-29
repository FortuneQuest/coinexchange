package cn.oc.controller;

import cn.oc.domain.SysRole;
import cn.oc.model.R;
import cn.oc.service.SysRoleService;
import cn.oc.utils.ArraysUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : SysRoleControlle
 * @Author: oc
 * @Date: 2022/11/20/22:17
 * @Description:  角色管理
 **/
@RestController
@RequestMapping("/roles")
@Api(tags = "角色管理")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping
    @ApiOperation(value = "条件分页查询")
    @PreAuthorize("hasAuthority('sys_role_query')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value ="当前页"),
            @ApiImplicitParam(name = "size",value ="每页显示的大小"),
            @ApiImplicitParam(name = "name",value ="角色的名称",paramType = "query")
    })
    public R<Page<SysRole>> findByPage(String name, @ApiIgnore Page<SysRole> page) throws UnsupportedEncodingException {
        page.addOrder(OrderItem.desc("last_update_time"));
        System.out.println(name);
        return  R.ok(sysRoleService.findByPage(page,name));
    }

    @PostMapping("")
    @ApiOperation("新增一个角色")
    @PreAuthorize("hasAuthority('sys_role_create')")
    //RequestBody 意味着传过去的是一个json
    public R add(@RequestBody @Validated SysRole sysRole){
        boolean save = sysRoleService.save(sysRole);
        if (save) {
            return R.ok("新增成功");
        }
           return R.fail("新增失败");
    }


    @PostMapping("/delete")
    @ApiOperation(value = "删除一个角色数据")
    @PreAuthorize("hasAuthority('sys_role_delete')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "删除角色的id集合",dataType ="Long")
    })
    public R delete(@RequestBody Long[] ids){
        if(ArraysUtil.ArraysIsEmpty(ids)){
            return  R.fail("要删除的数据不能为空");
        }
        boolean remove = sysRoleService.removeByIds(Arrays.asList(ids));
        if (remove) {
            return R.ok("删除成功");
        } else {
            return R.fail("删除失败");
        }
    }
}
