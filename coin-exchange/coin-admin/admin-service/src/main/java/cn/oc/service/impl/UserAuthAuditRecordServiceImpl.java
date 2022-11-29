package cn.oc.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.oc.mapper.UserAuthAuditRecordMapper;
import cn.oc.domain.UserAuthAuditRecord;
import cn.oc.service.UserAuthAuditRecordService;
/**
 * Created with IntelliJ IDEA.
 * @ClassName : UserAuthAuditRecordServiceImpl
 * @Author: oc
 * @Date: 2022/11/30/0:42
 * @Description: 
 **/
@Service
public class UserAuthAuditRecordServiceImpl extends ServiceImpl<UserAuthAuditRecordMapper, UserAuthAuditRecord> implements UserAuthAuditRecordService{

}
