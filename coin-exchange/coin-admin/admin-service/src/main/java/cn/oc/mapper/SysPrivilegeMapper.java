package cn.oc.mapper;

import cn.oc.domain.SysPrivilege;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * @ClassName : SysPrivilegeMapper
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
@Mapper
public interface SysPrivilegeMapper extends BaseMapper<SysPrivilege> {
    /**
     * 使用角色id查询权限
     * @param roleId
     * @return
     */
    List<SysPrivilege> selectById(Long roleId);

    /**
     * 使用角色id查询权限的id
     * @param roleId
     * @return
     */
    Set<Long> getPrivilegesByRoleId(Long roleId);
}