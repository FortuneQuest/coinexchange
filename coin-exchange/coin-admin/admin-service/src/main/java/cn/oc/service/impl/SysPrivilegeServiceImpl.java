package cn.oc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.oc.mapper.SysPrivilegeMapper;
import cn.oc.domain.SysPrivilege;
import cn.oc.service.SysPrivilegeService;
/**
 * Created with IntelliJ IDEA.
 * @ClassName : SysPrivilegeServiceImpl
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
@Service
public class SysPrivilegeServiceImpl extends ServiceImpl<SysPrivilegeMapper, SysPrivilege> implements SysPrivilegeService{

    @Autowired
    private SysPrivilegeMapper sysPrivilegeMapper;

    @Override
    public List<SysPrivilege> getAllSysPrivileges(Long roleId, Long menuId) {
        //查询所有的该菜单下的权限
        List<SysPrivilege> sysPrivileges = list(new LambdaQueryWrapper<SysPrivilege>().eq(SysPrivilege::getMenuId, menuId));
        if (CollectionUtil.isEmpty(sysPrivileges)) {
            return Collections.emptyList();
        }
        Set<Long> currentRoleSysPrivileges =sysPrivilegeMapper.getPrivilegesByRoleId(roleId);
        //当前传递的橘色是否包含该权限的信息
        for (SysPrivilege sysPrivilege : sysPrivileges) {
            if (currentRoleSysPrivileges.contains(sysPrivilege.getId())) {
                //当前角色是否有该权限
                sysPrivilege.setOwn(1);
            }

        }
        return sysPrivileges;
    }





}
