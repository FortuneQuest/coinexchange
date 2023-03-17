package cn.oc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.CoinServer;
import com.bjsxt.mapper.CoinServerMapper;
import com.bjsxt.service.CoinServerService;
import org.springframework.stereotype.Service;

@Service
public class CoinServerServiceImpl extends ServiceImpl<CoinServerMapper, CoinServer> implements CoinServerService {

}

