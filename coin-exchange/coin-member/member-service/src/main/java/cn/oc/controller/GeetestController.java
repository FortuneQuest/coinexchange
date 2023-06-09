package cn.oc.controller;

import cn.oc.geetest.GeetestLib;
import cn.oc.geetest.GeetestLibResult;
import cn.oc.model.R;
import cn.oc.util.IpUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : GeetestController
 * @Author: oc
 * @Date: 2023/03/15/20:55
 * @Description:
 **/
@RestController
@RequestMapping("/get")
public class GeetestController {

    @Autowired
    private GeetestLib geetestLib;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("regiser")
    @ApiOperation("获得第一次数据包")
    public R<String>  register(String uuid){
        String digestmod ="md5";
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("digestmod", digestmod);
        paramMap.put("user_id", uuid);
        paramMap.put("client_type", "web");
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        paramMap.put("ip_address", IpUtil.getIpAddr(requestAttributes.getRequest()));

        GeetestLibResult result = geetestLib.register(digestmod, paramMap); // 极验服务器交互

        // 将结果状态写到session中，此处register接口存入session，后续validate接口会取出使用
        // 注意，此demo应用的session是单机模式，格外注意分布式环境下session的应用
        redisTemplate.opsForValue().set(GeetestLib.GEETEST_SERVER_STATUS_SESSION_KEY, result.getStatus(), 180, TimeUnit.SECONDS);
//        request.getSession().setAttribute( result.getStatus());
        redisTemplate.opsForValue().set(GeetestLib.GEETEST_SERVER_USER_KEY + ":" + uuid, uuid, 180, TimeUnit.SECONDS);
//        request.getSession().setAttribute("userId", userId);
        // 注意，不要更改返回的结构和值类型
        return R.ok(result.getData());
    }
}
