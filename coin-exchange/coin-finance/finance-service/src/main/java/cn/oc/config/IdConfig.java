package cn.oc.config;

import cn.hutool.core.lang.Snowflake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : IdConfig
 * @Author: oc
 * @Date: 2023/03/17/12:15
 * @Description:
 **/
@Configuration
public class IdConfig {

    @Value("${snow.app.id:1}")
    private Integer appId;

    @Value("${snow.data.id:1}")
    private Integer dataId;


    @Bean
    public Snowflake snowflake() {
        Snowflake snowflake = new Snowflake(appId, dataId);
        return snowflake;
    }

}
