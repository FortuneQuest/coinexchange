package cn.oc.service;

import cn.oc.domain.WorkIssue;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * Created with IntelliJ IDEA.
 * @ClassName : WorkIssueService
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
public interface WorkIssueService extends IService<WorkIssue>{


        /**
         * 分页查询工单
         * @param page 分页参数
         * @param status 状态
         * @param startTime 卡死hi时间
         * @param endTime 结束时间
         * @return
         */
        Page<WorkIssue> findByPage(Page<WorkIssue> page, Integer status, String startTime, String endTime);
    }
