package cn.oc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.oc.mapper.NoticeMapper;
import cn.oc.domain.Notice;
import cn.oc.service.NoticeService;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * @ClassName : NoticeServiceImpl
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService{


    @Override
    public Page<Notice> findByPage(Page<Notice> page, String tile, String startTime, String endTime, Integer status) {

        return page(page,new LambdaQueryWrapper<Notice>()
                .like(!StringUtils.isEmpty(tile),Notice::getTitle,tile)
                //查询创建时间在起始时间和结束时间范围
                .between(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime),
                        Notice::getCreated,startTime,endTime+" 23:59:59")
                .eq(status!=null,Notice::getStatus,status));
    }
}
