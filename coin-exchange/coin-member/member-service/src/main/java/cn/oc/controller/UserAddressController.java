package cn.oc.controller;

import cn.oc.domain.User;
import cn.oc.domain.UserAddress;
import cn.oc.model.R;
import cn.oc.service.UserAddressService;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : UserAddressController
 * @Author: oc
 * @Date: 2023/03/16/21:25
 * @Description:
 **/
@RestController
@Api(tags = "用户钱包地址")
@RequestMapping("/userAddress")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping
    @ApiOperation(value = "查阅用户的钱包地址")
    public R<Page<UserAddress>> findByPage(@ApiIgnore Page<UserAddress> page, Long userId) {
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<UserAddress> userAddressServiceByPage = userAddressService.findByPage(page, userId);
        return  R.ok(userAddressServiceByPage);
    }

    @GetMapping("/current")
    public R getCurrentUserAddress(){
        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        List<UserAddress> userAddressList = userAddressService.getUserAddressByUserId(Long.valueOf(userId)) ;
        return R.ok(userAddressList) ;
    }

    @GetMapping("/getCoinAddress/{coinId}")
    @ApiOperation(value = "查询用户某种币的钱包地址")
    public R<String> getCoinAddress(@PathVariable("coinId") Long coinId){
        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        UserAddress userAddress = userAddressService.getUserAddressByUserIdAndCoinId(userId,coinId) ;
        return R.ok(userAddress.getAddress()) ;

}
