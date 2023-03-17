package cn.oc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : FinanceServiceApplication
 * @Author: oc
 * @Date: 2023/03/17/12:14
 * @Description:
 **/
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class FinanceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanceServiceApplication.class, args);
    }
}
