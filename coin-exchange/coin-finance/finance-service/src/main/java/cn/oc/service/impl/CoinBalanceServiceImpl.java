package cn.oc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.CoinBalance;
import com.bjsxt.mapper.CoinBalanceMapper;
import com.bjsxt.service.CoinBalanceService;
import org.springframework.stereotype.Service;

@Service
public class CoinBalanceServiceImpl extends ServiceImpl<CoinBalanceMapper, CoinBalance> implements CoinBalanceService {

}

