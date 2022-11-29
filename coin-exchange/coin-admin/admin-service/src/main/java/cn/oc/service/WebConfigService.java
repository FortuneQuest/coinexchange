package cn.oc.service;

import cn.oc.domain.WebConfig;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * Created with IntelliJ IDEA.
 * @ClassName : WebConfigService
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
public interface WebConfigService extends IService<WebConfig>{


        /**
         * 条件分页查询
         * @param page 分页参数
         * @param name 名称
         * @param type 类型
         * @return
         */
        Page<WebConfig> findByPage(Page page, String name, String type);
    }
