package cn.oc.mapper;

import cn.oc.domain.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @ClassName : SysMenuMapper
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 通过用户的id 查询用户的菜单数据
     * @param userId
     * @return
     */
    List<SysMenu> selectMenusByUserId(Long userId);
}