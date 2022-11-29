package cn.oc.aspect;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.oc.domain.SysUserLog;
import cn.oc.model.WebLog;
import cn.oc.service.SysUserLogService;
import com.alibaba.fastjson.JSON;
import com.esotericsoftware.minlog.Log;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;

@Aspect
@Component
@Order(2) //执行顺序 当有多个顺序时，值越低 ，优先值越高
@Slf4j
public class WebLogAdminAspect {

    /**
     * 雪花算法
     * workerId – 终端ID
     * datacenterId – 数据中心ID
     */
    private Snowflake snowflake = new Snowflake(1,1);

    @Autowired
    private SysUserLogService sysUserLogService;

    /**
     * 日志记录;
     * 环绕通知：方法执行之前，之后都能切入
     */


    /**
     * controller 所有类 所有方法都有该切面
     */
    @Pointcut("execution( * cn.oc.controller.*.*(..))")
    public void webLog() {
    }

    /**
     * 记录日志的环绕通知
     */
    @Around("webLog()")
    public Object recodeWebLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        WebLog webLog = new WebLog();
        //执行方法的真实时间 end -start
        long start = System.currentTimeMillis();
        //让目标方法执行
        result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        long end = System.currentTimeMillis();
        //请求该接口花费的时间
        webLog.setSpendTime((int) (end - start));
        //获取当前请求的request对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //获取当前的上下文
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //设置请求的uri
        webLog.setUri(request.getRequestURI());
        //设置请求的url
        String url = request.getRequestURL().toString();
        webLog.setUrl(url);
        //设置请求的basePath
        webLog.setBasePath(StrUtil.removeSuffix(url, URLUtil.url(url).getPath())); //http://ip:port
        //获取当前操作用户  Principal 如果用户名密码登录 就是用户名 id
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        webLog.setUsername(authentication == null ? "anonymous" : userDetails.getUsername());
        //客户端网络地址，或最后的网关请求地址
        webLog.setIp(request.getRemoteAddr());
        //获取方法名称 ，根据方法签名对象
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        //获取类的名称  通过反射  获取这个对象 通过对象获取它的字节对象再获取它的类名
        String targetClassName = proceedingJoinPoint.getTarget().getClass().getName();
        //因为会使用swagger ，必须在方法上添加@ApiOperation(value="{描述}")注解
        //获取注解
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        webLog.setDescription(annotation == null ? "no desc" : annotation.value());
        //cn.oc.controller.xxxController.methodName
        webLog.setMethod(targetClassName + "." + method.getName());
        //{"key_参数名称":"value_方法的值"}
        webLog.setParameter(getMethodParameters(method, proceedingJoinPoint.getArgs()));
        //结果
        webLog.setResult(result);
        SysUserLog sysUserLog = new SysUserLog();
        sysUserLog.setCreated(new Date());
        sysUserLog.setDescription(webLog.getDescription());
        sysUserLog.setGroup(webLog.getDescription());
        if (!"".equals(webLog.getUsername())&&!"anonymousUser".equals(webLog.getUsername())) {
            sysUserLog.setUserId(Long.valueOf(webLog.getUsername()));
        }
        sysUserLog.setMethod(webLog.getMethod());
        sysUserLog.setIp(webLog.getIp());
        //雪花算法
        sysUserLog.setId(snowflake.nextId());
        sysUserLogService.save(sysUserLog);
        return result;
    }

    /**
     * 获取方法的执行参数
     *
     * @param method
     * @param args
     * @return
     */
    private Object getMethodParameters(Method method, Object[] args) {
        HashMap<String, Object> methodParametersWithValue = new HashMap<>();
        //获取方法的一些形参列表
        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer =
                new LocalVariableTableParameterNameDiscoverer();
        //方法的形参名称
        String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);
        for (int i = 0; i < parameterNames.length; i++) {
            if ("password".equals(parameterNames[i]) || "file".equals(parameterNames[i])) {
                methodParametersWithValue.put(parameterNames[i], "受限的支持类型");
            } else {
                methodParametersWithValue.put(parameterNames[i], args[i]);
            }

        }
        return methodParametersWithValue;
    }
}
