package cn.oc.service;

import cn.oc.model.LoginResult;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : SysLoginService
 * @Author: oc
 * @Date: 2022/11/12/18:30
 * @Description:  登录的接口
 **/
public interface SysLoginService {
    /**
     * 登录的接口
     * @param username 用户名
     * @param password 密码
     * @return 登录的结果
     */
    LoginResult login(String username,String password);
}
