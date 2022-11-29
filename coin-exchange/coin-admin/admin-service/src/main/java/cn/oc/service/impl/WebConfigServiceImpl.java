package cn.oc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.oc.mapper.WebConfigMapper;
import cn.oc.domain.WebConfig;
import cn.oc.service.WebConfigService;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * @ClassName : WebConfigServiceImpl
 * @Author: oc
 * @Date: 2022/11/11/15:26
 * @Description: 
 **/
@Service
public class WebConfigServiceImpl extends ServiceImpl<WebConfigMapper, WebConfig> implements WebConfigService{

    @Override
    public Page<WebConfig> findByPage(Page page, String name, String type) {
        return page(page,new LambdaQueryWrapper<WebConfig>().like(!StringUtils.isEmpty(name),WebConfig::getName,name)
                .eq(!StringUtils.isEmpty(type),WebConfig::getType,type));
    }
}
