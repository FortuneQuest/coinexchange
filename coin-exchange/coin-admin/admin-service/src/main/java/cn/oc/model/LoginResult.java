package cn.oc.model;

import cn.oc.domain.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : LoginResult
 * @Author: oc
 * @Date: 2022/11/12/18:19
 * @Description:  登录结果
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "登录的结果")
public class LoginResult {
    /**
     * 登录成功的token，来自我们的授权服务器Authorization-server
     */
    @ApiModelProperty(value = "登录成功的token，来自我们的授权服务器Authorization-server")
    private String token;

    /**
     * 该用户的菜单数据
     */
    @ApiModelProperty(value = "菜单数据")
    private List<SysMenu> menus;

    /**
     * 该用户的权限数据
     */
    @ApiModelProperty(value = "菜单权限数据")
    private  List<SimpleGrantedAuthority> authorities;


}


