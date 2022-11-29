package cn.oc.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.oc.mapper.SmsMapper;
import cn.oc.domain.Sms;
import cn.oc.service.SmsService;
/**
 * Created with IntelliJ IDEA.
 * @ClassName : SmsServiceImpl
 * @Author: oc
 * @Date: 2022/11/30/0:42
 * @Description: 
 **/
@Service
public class SmsServiceImpl extends ServiceImpl<SmsMapper, Sms> implements SmsService{

}
