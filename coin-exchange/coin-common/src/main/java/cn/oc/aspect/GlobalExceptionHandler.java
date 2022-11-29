package cn.oc.aspect;

import cn.oc.model.R;
import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : GlobalExceptionHandler
 * @Author: oc
 * @Date: 2022/11/10/0:28
 * @Description:
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 内部API调用异常出口   mybatis_plus
     */
    @ExceptionHandler(value = ApiException.class)  //绑定异常
    public R handlerApiException(ApiException exception){
        //获取异常代码
        IErrorCode errorCode = exception.getErrorCode();
        if(errorCode!=null){
            return  R.fail(errorCode);
        }
        //若无错误代码，则返回信息
        return  R.fail(exception.getMessage());
    }

    /**
     * 方法参数校验失败异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)  //绑定方法参数未校验异常
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        //获取参数校验失败的结果
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        //判断是否有错误
        if (bindingResult.hasErrors()) {
            //获取错误的对象
            FieldError fieldError = bindingResult.getFieldError();
            //判断错误是否为空
            if(fieldError!=null){
                return  R.fail(fieldError.getField()+fieldError.getDefaultMessage());
            }
        }
        return  R.fail(methodArgumentNotValidException.getMessage());
    }

    /**
     * 对象内部使用Validate 没有校验成功的异常
     */
    @ExceptionHandler(BindException.class)  //绑定异常 方法参数或者对象参数没有被校验成功的异常
    public  R handlerBindException(BindException bindException) {
        BindingResult bindingResult = bindException.getBindingResult();
        //判断是否有错误结果
        if (bindingResult.hasErrors()) {
            //获取错误的对象
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                return  R.fail(fieldError.getField()+fieldError.getDefaultMessage());
            }
        }
        //直接返回该错误的诊断信息
        return R.fail(bindException.getMessage());
    }

}
