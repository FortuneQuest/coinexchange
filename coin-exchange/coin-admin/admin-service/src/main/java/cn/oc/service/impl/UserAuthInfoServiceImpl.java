package cn.oc.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.oc.domain.UserAuthInfo;
import cn.oc.mapper.UserAuthInfoMapper;
import cn.oc.service.UserAuthInfoService;
/**
 * Created with IntelliJ IDEA.
 * @ClassName : UserAuthInfoServiceImpl
 * @Author: oc
 * @Date: 2022/11/30/0:42
 * @Description: 
 **/
@Service
public class UserAuthInfoServiceImpl extends ServiceImpl<UserAuthInfoMapper, UserAuthInfo> implements UserAuthInfoService{

}
