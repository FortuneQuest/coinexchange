package cn.oc.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : RolePrivilegesParam
 * @Author: oc
 * @Date: 2022/11/24/20:24
 * @Description:
 **/
@Data
@ApiModel("接收角色和权限数据")
public class RolePrivilegesParam {

    @ApiModelProperty("角色的id")
    private Long RoleId;

    @ApiModelProperty("角色的包含的权限")
    private List<Long> privilegeIds= Collections.emptyList();
}
