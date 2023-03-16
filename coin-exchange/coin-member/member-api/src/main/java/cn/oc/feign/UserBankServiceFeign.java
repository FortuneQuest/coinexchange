package cn.oc.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : UserBankServiceFeign
 * @Author: oc
 * @Date: 2023/03/15/15:08
 * @Description:
 **/

@FeignClient(name = "member-service",contextId = "userBankServiceFeign" ,configuration = OAuth2FeignConfig.class ,path = "/userBanks")
public interface UserBankServiceFeign {

    @GetMapping("/{userId}/info")
    UserBankDto getUserBankInfo(@PathVariable Long userId) ;
}
