package cn.oc.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : RedisConfig
 * @Author: oc
 * @Date: 2022/11/09/13:27
 * @Description:
 **/
@Configuration
public class RedisConfig {

    /**
     *
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();
        //redis key 的序列化
        redisTemplate.setKeySerializer(keySerializer);
        //redis value 的序列化
        redisTemplate.setValueSerializer(valueSerializer);
        //redis hash key 的序列化
        redisTemplate.setHashKeySerializer(keySerializer);
        //redis hash value 的序列化
        redisTemplate.setHashValueSerializer(valueSerializer);
        return redisTemplate;
    }
}
