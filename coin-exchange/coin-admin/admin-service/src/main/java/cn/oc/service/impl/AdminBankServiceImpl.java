package cn.oc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.oc.mapper.AdminBankMapper;
import cn.oc.domain.AdminBank;
import cn.oc.service.AdminBankService;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * @ClassName : AdminBankServiceImpl
 * @Author: oc
 * @Date: 2022/11/29/1:23
 * @Description: 
 **/
@Service
public class AdminBankServiceImpl extends ServiceImpl<AdminBankMapper, AdminBank> implements AdminBankService{

    @Override
    public Page<AdminBank> findByPage(Page<AdminBank> page, String bankCard) {
        return page(page,new LambdaQueryWrapper<AdminBank>().like(!StringUtils.isEmpty(bankCard)
                ,AdminBank::getBankCard,bankCard)) ;
    }
}
