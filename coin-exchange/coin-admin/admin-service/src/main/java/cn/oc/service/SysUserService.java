package cn.oc.service;

import cn.oc.domain.SysUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * Created with IntelliJ IDEA.
 * @ClassName : SysUserService
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
public interface SysUserService extends IService<SysUser>{


        /**
         * 分页查询员工
         * @param page
         * @param mobile  员工手机
         * @param fullname 员工全名称
         * @return
         */
        Page<SysUser> findByPage(Page<SysUser> page, String mobile, String fullname);

        /**
         * 新增一个员工
         * @param sysUser
         * @return
         */
        boolean addUser(SysUser sysUser);

        /**
         * 更新一个员工的信息
         * @param sysUser
         */
        boolean updateUser(SysUser sysUser);
    }
