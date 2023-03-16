package cn.oc.controller;

import cn.oc.model.LoginForm;
import cn.oc.model.LoginUser;
import cn.oc.model.R;
import cn.oc.service.impl.LoginServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : LoginController
 * @Author: oc
 * @Date: 2023/03/16/18:26
 * @Description:
 **/
@RestController
@Api(tags = "登录的控制器")
public class LoginController {

    @Autowired
    private LoginServiceImpl loginService;

    @PostMapping("/login")
    @ApiOperation(value = "会员登录")
    public R<LoginUser> login(@RequestBody @Validated LoginForm loginForm){
        LoginUser loginUser = (LoginUser) loginService.login(loginForm);
        return  R.ok(loginUser);
    }
}

