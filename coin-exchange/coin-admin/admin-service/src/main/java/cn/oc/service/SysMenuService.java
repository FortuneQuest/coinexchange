package cn.oc.service;

import cn.oc.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @ClassName : SysMenuService
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
public interface SysMenuService extends IService<SysMenu>{

    /**
     * 通过用户的id查询菜单
     * @param userId
     * @return
     */
        List<SysMenu> getMenusById(Long userId);
    }
