package cn.oc.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : UserInfoController
 * @Author: oc
 * @Date: 2022/11/08/0:35
 * @Description:
 **/
@RestController
public class UserInfoController {

    /**
     * 使用token换取回来的用户的信息
     * @param principal 一个装载信息的载体
     * @return  principal 使用token换取回来的用户的信息
     */
    @GetMapping("/user/info")
    public Principal userInfo(Principal principal){
        //具体实现是通过ThreadLocal实现的
        SecurityContextHolder.getContext().getAuthentication();
        return principal;
    }


}

