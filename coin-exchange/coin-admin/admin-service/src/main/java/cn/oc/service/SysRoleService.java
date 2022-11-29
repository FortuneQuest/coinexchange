package cn.oc.service;

import cn.oc.domain.SysRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * Created with IntelliJ IDEA.
 * @ClassName : SysRoleService
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
public interface SysRoleService extends IService<SysRole>{

        /**
         * 判断一个用户是否为超级管理员
         * @param userId
         * @return
         */
        boolean isSuperAdmin(Long userId);

        /**
         * 根据使用角色的名称模糊分页查询角色
         * @param page  分页数据
         * @param name 角色名称
         * @return
         */
        Page<SysRole> findByPage(Page<SysRole> page, String name);
    }
