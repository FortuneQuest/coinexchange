package cn.oc.config.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : JetcacheConfig
 * @Author: oc
 * @Date: 2022/11/09/13:17
 * @Description:
 **/
@Configuration
@EnableCreateCacheAnnotation  //创建缓存的注解
@EnableMethodCache(basePackages = "cn.oc.service.impl") //开启方法级别的缓存
public class JetCacheConfig {
}
