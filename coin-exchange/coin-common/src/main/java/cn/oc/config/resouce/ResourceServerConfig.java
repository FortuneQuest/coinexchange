package cn.oc.config.resouce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.codec.EncodingException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.FileCopyUtils;



/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : ResourceServerConfig
 * @Author: oc
 * @Date: 2022/11/09/13:56
 * @Description:  配置静态资源操作
 **/
@Slf4j
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)  //基于方法注解的权限认证
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * redis的工厂，由redis的Autowirdconfiguration来注入的
     */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // 由于使用的是JWT，我们这里不需要csrf 关闭跨站请求伪造
                .csrf().disable()
                // 基于token，所以不需要session 关闭session
                .sessionManagement().disable()
                .authorizeRequests()
                .antMatchers(
                        "/users/setPassword" ,
                        "/users/register",
                        "/sms/sendTo",
                        "/gt/register" ,
                        "/login" ,
                        "/v2/api-docs",
                        "/swagger-resources/configuration/ui",   //用来获取支持的动作
                        "/swagger-resources",              //用来获取api-docs的URI
                        "/swagger-resources/configuration/security",//安全选项
                        "/swagger-ui.html",
                        "/logout",
                        "/doc.html",
                        "/css/**",
                        "/js/**",
                        "/webjars/**",
                        "/v2/**",
                        "**/favicon.ico",
                        "/swagger-ui.html/**"
                ).permitAll()  //放行
                .antMatchers("/**").authenticated() //必须验证身份权限
                .and().headers().cacheControl();

    }

    /**
     * 设置公钥
     * @param resources configurer for the resource server
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.tokenStore(jwtTokenStore());
        resources.tokenStore(redisTokenStore());
        super.configure(resources);
    }

    //TODO  tokenStore的值是否可以redis？

//    private TokenStore jwtTokenStore() {
//        JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter());
//        return jwtTokenStore;
//    }
public TokenStore redisTokenStore(){
    return  new RedisTokenStore(redisConnectionFactory);
}

    /**
     * 读取公钥
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        //resource验证token(公钥)
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        String s =null;
        try {
            ClassPathResource classPathResource = new ClassPathResource("coinexchange.txt");
            //文件拷贝成一个数组
            byte[] bytes = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
            s = new String(bytes, "UTF-8");
        }catch (Exception e) {
            log.info("读取公钥失败");;
        }
        tokenConverter.setVerifierKey(s);
        return tokenConverter;
    }

}
