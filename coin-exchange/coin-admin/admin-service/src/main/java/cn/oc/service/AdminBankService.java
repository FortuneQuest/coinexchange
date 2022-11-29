package cn.oc.service;

import cn.oc.domain.AdminBank;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * Created with IntelliJ IDEA.
 * @ClassName : AdminBankService
 * @Author: oc
 * @Date: 2022/11/29/1:23
 * @Description: 
 **/
public interface AdminBankService extends IService<AdminBank>{


        /**
         * 条件查找
         * @param page
         * @param bankCard
         * @return
         */
        Page<AdminBank> findByPage(Page<AdminBank> page, String bankCard);
    }
