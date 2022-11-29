package cn.oc.controller;

import cn.oc.domain.SysUserLog;
import cn.oc.model.R;
import cn.oc.service.SysUserLogService;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : SysUserLogController
 * @Author: oc
 * @Date: 2022/11/26/16:23
 * @Description:
 **/
@RestController
@Api(tags = "用户的操作记录查询")
@RequestMapping("/sysUserLog")
public class SysUserLogController {

    @Autowired
    private SysUserLogService sysUserLogService;

    @GetMapping
    @ApiOperation("分页查询用户的操作记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size",value = "分页查询的大小"),
            @ApiImplicitParam(name = "current",value = "当前页")
    })
    @PreAuthorize("hasAuthority('sys_user_log_query')")
    public R<Page<SysUserLog>> findByPage(@ApiIgnore Page<SysUserLog> page) {
        page.addOrder(OrderItem.desc("created"));
         return  R.ok(sysUserLogService.page(page));
    }
}
