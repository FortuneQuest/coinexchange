package cn.oc.service;

import cn.oc.domain.Config;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * Created with IntelliJ IDEA.
 * @ClassName : ConfigService
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
public interface ConfigService extends IService<Config>{

        /**
         *
         * @param page
         * @param type
         * @param code
         * @param name
         * @return
         */
        Page<Config> findByPage(Page<Config> page, String type, String code, String name);
    }
