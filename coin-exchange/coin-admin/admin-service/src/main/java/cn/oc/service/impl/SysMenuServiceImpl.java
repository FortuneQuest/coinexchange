package cn.oc.service.impl;

import cn.oc.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.oc.mapper.SysMenuMapper;
import cn.oc.domain.SysMenu;
import cn.oc.service.SysMenuService;
/**
 * Created with IntelliJ IDEA.
 * @ClassName : SysMenuServiceImpl
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService{

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 通过用户的id查询菜单
     * @param userId
     * @return
     */
    @Override
    public List<SysMenu> getMenusById(Long userId) {
            //1、如果该用户是一个超级管理员，那就拥有所有的菜单
        if (sysRoleService.isSuperAdmin(userId)) {
            return list();
        } else {
            //2、不是超级管理员，---》查询角色--通过角色查询菜单
            return sysMenuMapper.selectMenusByUserId(userId);
        }
    }
}
