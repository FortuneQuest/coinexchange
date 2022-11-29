package cn.oc.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : OAuth2FeignClient
 * @Author: oc
 * @Date: 2022/11/12/18:52
 * @Description:
 **/
@FeignClient("authorization-server")  //服务前缀

public interface OAuth2FeignClient {

    @PostMapping("/oauth/token")
    ResponseEntity<JwtToken> getToken(
            //授权类型
            @RequestParam("grant_type") String grantType,
            //用户名
            @RequestParam("username") String username,
            //用户密码
            @RequestParam("password") String password,
            //用户登录类型
            @RequestParam("login_type") String loginType,
            //第三方客户端token
            @RequestHeader("Authorization") String basicToken
    );


}
