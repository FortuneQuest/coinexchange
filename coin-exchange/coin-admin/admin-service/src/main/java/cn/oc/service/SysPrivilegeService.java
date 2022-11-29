package cn.oc.service;

import cn.oc.domain.SysPrivilege;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : SysPrivilegeService
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description:
 **/
public interface SysPrivilegeService extends IService<SysPrivilege> {


    /**
     * 获取该菜单下该角色的所有权限数据
     * @param roleId
     * @return
     */
    List<SysPrivilege> getAllSysPrivileges(Long roleId,Long menuId);
}
