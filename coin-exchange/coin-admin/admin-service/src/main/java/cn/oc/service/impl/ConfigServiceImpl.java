package cn.oc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.oc.domain.Config;
import cn.oc.mapper.ConfigMapper;
import cn.oc.service.ConfigService;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * @ClassName : ConfigServiceImpl
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService{

    @Override
    public Page<Config> findByPage(Page<Config> page, String type, String code, String name) {
        return page(page,new LambdaQueryWrapper<Config>()
                .like(!StringUtils.isEmpty(type),Config::getType,type)
                .like(!StringUtils.isEmpty(name),Config::getName,name)
                .like(!StringUtils.isEmpty(code),Config::getCode,code));
    }
}
