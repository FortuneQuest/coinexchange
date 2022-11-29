package cn.oc.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.oc.domain.User;
import cn.oc.mapper.UserMapper;
import cn.oc.service.UserService;
/**
 * Created with IntelliJ IDEA.
 * @ClassName : UserServiceImpl
 * @Author: oc
 * @Date: 2022/11/30/0:42
 * @Description: 
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}
