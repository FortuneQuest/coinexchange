package cn.oc.controller;

import cn.oc.domain.SysUser;
import cn.oc.model.R;
import cn.oc.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : TestController
 * @Author: oc
 * @Date: 2022/11/11/17:22
 * @Description:
 **/
@RestController
@Api(tags = "后台管理系统的测试")
public class TestController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value ="查询用户详情" )
    @GetMapping("/user/info/{id}")
    public R<SysUser> getSysUserInfo(@PathVariable("id") Long id){
        SysUser  sysUser = sysUserService.getById(id);
        return R.ok(sysUser);
    }
}
