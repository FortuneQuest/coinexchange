package cn.oc.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.oc.mapper.SysUserLogMapper;
import cn.oc.domain.SysUserLog;
import cn.oc.service.SysUserLogService;
/**
 * Created with IntelliJ IDEA.
 * @ClassName : SysUserLogServiceImpl
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
@Service
public class SysUserLogServiceImpl extends ServiceImpl<SysUserLogMapper, SysUserLog> implements SysUserLogService{

}
