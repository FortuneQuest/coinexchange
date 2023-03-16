package cn.oc.controller;

import cn.oc.domain.UserBank;
import cn.oc.dto.UserBankDto;
import cn.oc.feign.UserBankServiceFeign;
import cn.oc.mappers.UserBankDtoMapper;
import cn.oc.model.R;
import cn.oc.service.UserBankService;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : UserBankController
 * @Author: oc
 * @Date: 2023/03/16/21:38
 * @Description:
 **/
@RequestMapping("/userBanks")
@RestController
public class UserBankController implements UserBankServiceFeign {

    @Override
    public UserBankDto getUserBankInfo(Long userId) {
        UserBank currentUserBank = userBankService.getCurrentUserBank(userId);
        UserBankDto userBankDto = UserBankDtoMapper.INSTANCE.toConvertDto(currentUserBank);
        return userBankDto ;
    }

    @Autowired
    private  UserBankService userBankService;
    @GetMapping
    @ApiOperation(value = "分页查询用户的银行卡")
    @PreAuthorize("hasAuthority('user_bank_query')")
    public R<Page<UserBank>> findByPage(Page<UserBank> page , Long usrId) {
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<UserBank> userBankPage = userBankService.findByPage(page, usrId);
        return R.ok(userBankPage);
    }

    @PostMapping("/status")
    @ApiOperation(value = "修改银行卡的状态")
    public R updateStatus(Long id ,Byte status){
        UserBank userBank = new UserBank();
        userBank.setId(id);
        userBank.setStatus(status);
        boolean updateById = userBankService.updateById(userBank);
        if(updateById){
            return R.ok() ;
        }
        return R.fail("银行卡状态修改失败") ;
    }

    @PatchMapping
    @ApiOperation(value = "修改银行卡")
    public R updateStatus(@RequestBody @Validated UserBank userBank){
        boolean updateById = userBankService.updateById(userBank);
        if(updateById){
            return R.ok() ;
        }
        return R.fail("银行卡状态修改失败") ;
    }

    @GetMapping("/current")
    @ApiOperation(value = "查询当前用户的银行卡")
    public R<UserBank> getCurrentUserBank(){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        UserBank userBank =  userBankService.getCurrentUserBank(userId) ;
        return R.ok(userBank) ;
    }
    @PostMapping("/bind")
    @ApiOperation(value = "绑定银行卡")
    public  R bindBank(@RequestBody @Validated UserBank userBank){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        boolean isOk = userBankService.bindBank(userId,userBank) ;
        if(isOk){
            return R.ok() ;
        }
        return R.fail("绑定失败") ;
    }


}
