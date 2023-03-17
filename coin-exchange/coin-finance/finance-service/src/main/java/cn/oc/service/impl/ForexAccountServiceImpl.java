package cn.oc.service.impl;


import cn.oc.domain.ForexAccount;
import cn.oc.mapper.ForexAccountMapper;
import cn.oc.service.ForexAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ForexAccountServiceImpl extends ServiceImpl<ForexAccountMapper, ForexAccount> implements ForexAccountService {

}

