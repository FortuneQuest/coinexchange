package cn.oc;

import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : AdminServiceApplication
 * @Author: oc
 * @Date: 2022/11/11/15:39
 * @Description:
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//@MapperScans("cn.oc.mapper")
public class AdminServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class,args);
    }
}
