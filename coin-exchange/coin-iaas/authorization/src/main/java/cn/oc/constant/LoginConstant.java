package cn.oc.constant;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : loginConstant
 * @Author: oc
 * @Date: 2022/11/08/22:01
 * @Description: 登录的常量
 **/
public class LoginConstant {

    /**
     * 后台管理员
     */
    public static final String ADMIN_TYPE =  "admin_type";

    /**
     * 会员
     */
    public static final String MEMBER_TYPE ="member_type";

    /**
     * 超级管理员角色code
     */
    public static final String ADMIN_ROLE_CODE ="ROLE_ADMIN";

    /**
     *
     */
    public static final String REFRESH_TYPE = "REFRESH_TOKEN";

    /**
     * 使用用户名查询用户
     */
    public static final String QUERY_ADMIN_SQL =
            "SELECT `id` ,`username`, `password`, `status` FROM sys_user WHERE username = ? ";

    /**
     * 查询用户是否为管理员
     */
    public static final String QUERY_ROLE_CODE_SQL =
            "SELECT `code` FROM sys_role LEFT JOIN sys_user_role ON sys_role.id = sys_user_role.role_id WHERE sys_user_role.user_id= ?";

    /**
     * 查询所有权限的名称
     */
    public static final String QUERY_ALL_PERMISSIONS =
            "SELECT `name` FROM sys_privilege";

    /**
     * 查找非管理，先查询角色，再通过角色查询权限；
     */
    public static final String QUERY_PERMISSION_SQL =
            "SELECT `name` FROM sys_privilege LEFT JOIN sys_role_privilege ON" +
                    " sys_role_privilege.privilege_id = sys_privilege.id" +
                    " LEFT JOIN sys_user_role  ON sys_role_privilege.role_id = sys_user_role.role_id WHERE" +
                    " sys_user_role.user_id = ?";

    /**
     * 会员查询sql
     */
    public static final String QUERY_MEMBER_SQL ="SELECT `id`,`password`, `status` FROM `user` WHERE mobile = ? or email = ? ";

    /**
     * 使用后台用户的id 查询用户名称
     */
    public static  final  String QUERY_ADMIN_USER_WITH_ID = "SELECT `username` FROM sys_user where id = ?" ;

    /**
     * 使用用户的id 查询用户手机
     */
    public static  final  String QUERY_MEMBER_USER_WITH_ID = "SELECT `mobile` FROM user where id = ?" ;


}
