package cn.oc.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.oc.mapper.UserBankMapper;
import cn.oc.domain.UserBank;
import cn.oc.service.UserBankService;
/**
 * Created with IntelliJ IDEA.
 * @ClassName : UserBankServiceImpl
 * @Author: oc
 * @Date: 2022/11/30/0:42
 * @Description: 
 **/
@Service
public class UserBankServiceImpl extends ServiceImpl<UserBankMapper, UserBank> implements UserBankService{

}
