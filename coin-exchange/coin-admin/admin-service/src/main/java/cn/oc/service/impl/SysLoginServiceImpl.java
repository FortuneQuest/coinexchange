package cn.oc.service.impl;

import cn.oc.domain.SysMenu;
import cn.oc.feign.JwtToken;
import cn.oc.feign.OAuth2FeignClient;
import cn.oc.model.LoginResult;
import cn.oc.service.SysLoginService;
import cn.oc.service.SysMenuService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.esotericsoftware.minlog.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : SysLoginServiceImpl
 * @Author: oc
 * @Date: 2022/11/12/18:32
 * @Description:  实现登录接口
 **/
@Service
@Slf4j
public class SysLoginServiceImpl implements SysLoginService {

    @Autowired
    OAuth2FeignClient oAuth2FeignClient;

    @Value("${basic.token:Basic Y29pbi1hcGk6Y29pbi1zZWNyZXQ=}")
    private String basicToken;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 登录的接口
     * @param username 用户名
     * @param password 密码
     * @return 登录的结果
     */
    @Override
    public LoginResult login(String username, String password) {
        Log.info("用户{}开始登录",username);
        //1、获取token =，需要远程调用Authorization服务
        ResponseEntity<JwtToken> tokenResponseEntity = oAuth2FeignClient.getToken("password", username, password, "admin_type", basicToken);
        if (tokenResponseEntity.getStatusCode() != HttpStatus.OK) {
            throw  new ApiException(ApiErrorCode.FAILED);
        }
        //body 存的就是jwttoken
        JwtToken jwtToken = tokenResponseEntity.getBody();
        //JSON.toJSONString  此方法将指定的对象序列化为它的等效Json表示形式
        Log.info("远程调用授权服务器成功,获取的token为", JSON.toJSONString(jwtToken,true));
        String token=jwtToken.getAccessToken();

        //2、查询菜单数据
        //将jwt从token中解析出来
        Jwt jwt = JwtHelper.decode(token);
        //从jwt中获取载荷的内容  jwt 三部分组成 标头:存放 令牌的类型，以及使用的签名算法 载荷：存放数据的部分
        // 签名:用于验证消息在此过程中未被更改，并且在使用私钥签名的令牌的情况下，它还可以验证 JWT 的发件人是否是它所说的人。
        String claims = jwt.getClaims();
        //将载荷部分的 json转换为对应的json对象
        JSONObject jwtJson = JSON.parseObject(claims);
        Long userId = Long.valueOf(jwtJson.getString("user_name"));
        List<SysMenu> menus = sysMenuService.getMenusById(userId);

        //3、权限怎么查询 -----不需要查询
        JSONArray authoritiesJsonArray = jwtJson.getJSONArray("authorities");
        //简单的存储返回的权限  ,组装权限数据
        List<SimpleGrantedAuthority> authorities=authoritiesJsonArray.stream()
                .map(authoritiesJson->new SimpleGrantedAuthority(authoritiesJson.toString()))
                .collect(Collectors.toList());
        //将token存在redis，配合网关网关做一个jwt验证操作
        redisTemplate.opsForValue().set(token,"",jwtToken.getExpires_in(),TimeUnit.SECONDS);
        //返回给前端的token +bearer
        return new LoginResult(jwtToken.getTokenType()+" "+token,menus,authorities);
    }
}
