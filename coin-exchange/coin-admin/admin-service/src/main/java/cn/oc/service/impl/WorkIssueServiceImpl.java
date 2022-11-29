package cn.oc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.oc.domain.WorkIssue;
import cn.oc.mapper.WorkIssueMapper;
import cn.oc.service.WorkIssueService;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * @ClassName : WorkIssueServiceImpl
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
@Service
public class WorkIssueServiceImpl extends ServiceImpl<WorkIssueMapper, WorkIssue> implements WorkIssueService{

    @Override
    public Page<WorkIssue> findByPage(Page<WorkIssue> page, Integer status, String startTime, String endTime) {
        return page(page,new LambdaQueryWrapper<WorkIssue>()
                .eq(status!=null,WorkIssue::getStatus,status)
                .between(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)
                        ,WorkIssue::getCreated,startTime,endTime+" 23:59:59"));
    }
}
