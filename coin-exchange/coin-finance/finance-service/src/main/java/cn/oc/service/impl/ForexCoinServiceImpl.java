package cn.oc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.ForexCoin;
import com.bjsxt.mapper.ForexCoinMapper;
import com.bjsxt.service.ForexCoinService;
import org.springframework.stereotype.Service;

@Service
public class ForexCoinServiceImpl extends ServiceImpl<ForexCoinMapper, ForexCoin> implements ForexCoinService {

}

