package cn.oc;

import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : CoinCommon
 * @Author: oc
 * @Date: 2022/11/10/16:10
 * @Description:
 **/
@SpringBootApplication
@EnableDiscoveryClient //开启客户端服务发现
@EnableSwagger2
public class CoinCommonApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoinCommonApplication.class,args);
    }
}
