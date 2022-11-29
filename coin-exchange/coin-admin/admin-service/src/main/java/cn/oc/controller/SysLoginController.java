package cn.oc.controller;

import cn.oc.model.LoginResult;
import cn.oc.service.SysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : SysLoginController
 * @Author: oc
 * @Date: 2022/11/12/18:25
 * @Description: 登录的控制起
 **/
@RestController
@Api(tags = "登录的控制器")
public class SysLoginController {
    @Autowired
    private SysLoginService sysLoginService;

    @PostMapping("/login")
    @ApiOperation("后台管理人员的登录")
    public LoginResult login(@RequestParam(required = true)String username,
                             @RequestParam(required = true) String password){
        return  sysLoginService.login(username, password);
    }
}
