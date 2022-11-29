package cn.oc.service;

import cn.oc.domain.Notice;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * Created with IntelliJ IDEA.
 * @ClassName : NoticeService
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
public interface NoticeService extends IService<Notice>{


        /**
         *
         * @param page 分页参数
         * @param tile 公告的标签
         * @param startTime 公告创建的起始时间
         * @param endTime 公告创建的结束时间
         * @param status 公告状态
         * @return
         */
        Page<Notice> findByPage(Page<Notice> page, String tile, String startTime, String endTime, Integer status);
    }
