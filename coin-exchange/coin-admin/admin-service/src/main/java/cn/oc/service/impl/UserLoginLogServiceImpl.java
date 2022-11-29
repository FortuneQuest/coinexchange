package cn.oc.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.oc.domain.UserLoginLog;
import cn.oc.mapper.UserLoginLogMapper;
import cn.oc.service.UserLoginLogService;
/**
 * Created with IntelliJ IDEA.
 * @ClassName : UserLoginLogServiceImpl
 * @Author: oc
 * @Date: 2022/11/30/0:42
 * @Description: 
 **/
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog> implements UserLoginLogService{

}
