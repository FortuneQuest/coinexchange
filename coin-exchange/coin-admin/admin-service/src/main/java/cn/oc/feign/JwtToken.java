package cn.oc.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : JwTToken
 * @Author: oc
 * @Date: 2022/11/12/19:01
 * @Description:
 **/
@Data
public class JwtToken {

    /**
     * access_token   sonProperty 此注解用于属性上，作用是把该属性的名称序列化为另外一个名称，
     * 如把trueName属性序列化为name，@JsonProperty ("name")。
     */
    @JsonProperty("access_token")
    private  String accessToken;

    /**
     *token_type  token类型
     */
    @JsonProperty("token_type")
    private  String tokenType;

    /**
     * refresh_token 刷新的token
     */
    @JsonProperty("refresh_token")
    private String refresh_token;

    /**
     * expires_in 过期时间
     */
    @JsonProperty("expires_in")
    private Long expires_in;

    /**
     * scope 范围
     */
    private String scope;

    /**
     * 颁发行政
     */
    private String jti;
}
