package cn.oc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : ResourceServerConfig
 * @Author: oc
 * @Date: 2022/11/08/0:48
 * @Description: 将当前服务变成资源服务器
 **/
@EnableResourceServer  //开启资源服务器  将服务变成资源服务器
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

}
