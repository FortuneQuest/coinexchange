package cn.oc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : AuthorizationConfig
 * @Author: oc
 * @Date: 2022/11/07/21:11
 * @Description:
 **/
@EnableAuthorizationServer  //开启服务授权功能
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 密钥加密器
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 验证管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 保存用户的信息
     */
    @Qualifier("userServiceDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    /**
     * redis的工厂，由redis的Autowirdconfiguration来注入的
     */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 添加第三方客户端
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //存在内存中
        clients.inMemory()
                //第三方客户端的名称
                .withClient("coin-api")
                //第三方客户端的密钥
                .secret(passwordEncoder.encode("coin-secret"))
                //第三方客户端授权范围
                .scopes("all")
                //授权类型
                .authorizedGrantTypes("password","refresh_token")
                //获取token的有效期
                .accessTokenValiditySeconds(7*24*3600)
                //刷新token的时间
                .refreshTokenValiditySeconds(30*24*3600)
                //添加一组配置
                .and()
                .withClient("inside-app")
                .secret(passwordEncoder.encode("inside-secret"))
                .authorizedGrantTypes("client_credentials")
                .scopes("all")
                .accessTokenValiditySeconds(7*24*3600);

        super.configure(clients);
        /**
         *  1、token未到期，前端可正常请求。
         *  2、token过期，后端会返回与前端约定好的相关参数(比如，响应码401)，前端在返回拦截器中判断拦截，
         *  并将refresh_token替换掉已经失效的token去调用api_refresh_token的接口请求。后端则判断refresh_token是否过期。
         *         （1）refresh_token未过期，后端重新生成token和refresh_token返给前端。
         *         前端接收到后，缓存token，并重新执行之前失败的接口。
         *         （2）refresh_token过期，后端返回token失效，前端跳转到登录页。
         */

    }
    /**
     * 配置验证管理器,UserDetailService
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //验证管理器
        endpoints.authenticationManager(authenticationManager)
//                .tokenStore(jwtTokenStore())
                .tokenEnhancer(jwtAccessTokenConverter() )
                .tokenStore(redisTokenStore()) //tokenStore 存放token
                .userDetailsService(userDetailsService);
    }

    private TokenStore jwtTokenStore(){
        JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter());
        return jwtTokenStore;
    }

    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //加载私钥
        ClassPathResource classPathResource = new ClassPathResource("coinexchange.jks");
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, "coinexchange".toCharArray());
        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("coinexchange","coinexchange".toCharArray()));
        return jwtAccessTokenConverter;
    }

    public TokenStore redisTokenStore(){
        return  new RedisTokenStore(redisConnectionFactory);
    }
}
