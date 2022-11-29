package cn.oc.config.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : MybatisPlusConfig
 * @Author: oc
 * @Date: 2022/11/09/13:20
 * @Description:
 **/
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页操作
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //配置方言
        paginationInterceptor.setDbType(DbType.MYSQL);
        return paginationInterceptor;
    }

    /**
     * 乐观锁 删除时使用的
     * @Version  使用乐观锁
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        OptimisticLockerInterceptor optimisticLockerInterceptor = new OptimisticLockerInterceptor();
        return  optimisticLockerInterceptor;
    }

    /**
     * 主键序列的生成
     * ID_WORK 对数字类型 默认 mybatis——plus
     * ID_WORK_STR 对字符串类型
     */
    @Bean
    public IKeyGenerator iKeyGenerator(){
        H2KeyGenerator h2KeyGenerator = new H2KeyGenerator();
        return h2KeyGenerator ;
    }


}
