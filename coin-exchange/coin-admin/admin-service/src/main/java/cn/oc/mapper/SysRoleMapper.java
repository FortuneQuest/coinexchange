package cn.oc.mapper;

import cn.oc.domain.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.
 * @ClassName : SysRoleMapper
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 获取用户角色code
     * @param userId
     * @return
     */
    String getUserRoleCode(Long userId);
}