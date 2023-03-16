package cn.oc.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : IdAutoConfiguration
 * @Author: oc
 * @Date: 2023/03/15/17:03
 * @Description:
 **/

@Configuration
@EnableConfigurationProperties(IdProperties.class)
public class IdAutoConfiguration {
    private  static IdProperties idProperties;

    /**
     * 发送请求的工具
     */
    private  static RestTemplate restTemplate =new RestTemplate();

    public IdAutoConfiguration(IdProperties idProperties){
        IdAutoConfiguration.idProperties =idProperties;
    }

    public  static  boolean check(String realName ,String carNum){
        /**
         * AppCode的形式验证: Authorization:APPCODE 你自己的AppCode
         *  -H Authorization:APPCODE 你自己的AppCode
         */
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization","APPCODE "+idProperties.getAppCode());
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                String.format(idProperties.getUrl(), carNum, realName),
                HttpMethod.GET,
                new HttpEntity<>(null, httpHeaders),
                String.class
        );
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String body = responseEntity.getBody();
            JSONObject jsonObject = JSON.parseObject(body);
            String status = jsonObject.getString("status");
            if("01".equals(status)){
                return  true;
            }
        }
        return  false;
    }
}
