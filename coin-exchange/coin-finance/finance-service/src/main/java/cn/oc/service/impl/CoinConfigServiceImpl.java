package cn.oc.service.impl;

import cn.oc.domain.Coin;
import cn.oc.domain.CoinConfig;
import cn.oc.mapper.CoinConfigMapper;
import cn.oc.service.CoinConfigService;
import cn.oc.service.CoinService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinConfigServiceImpl extends ServiceImpl<CoinConfigMapper, CoinConfig> implements CoinConfigService {

    @Autowired
    private CoinService coinService;

    /**
     * 通过币种的id 查询币种的配置信息
     *
     * @param coinId 币种的id
     * @return 币种的配置信息
     */
    @Override
    public CoinConfig findByCoinId(Long coinId) {
        // coinConfig的id 和Coin的id 值是相同的
        return getOne(new LambdaQueryWrapper<CoinConfig>().eq(CoinConfig::getId, coinId));
    }

    /**
     * 新增或修改币种配置
     *
     * @param coinConfig
     * @return
     */
    @Override
    public boolean updateOrSave(CoinConfig coinConfig) {
        //
        Coin coin = coinService.getById(coinConfig.getId());
        if (coin == null) {
            throw new IllegalArgumentException("coin-Id不存在");
        }
        coinConfig.setCoinType(coin.getType());
        coinConfig.setName(coin.getName());
        // 如何是新增/修改呢?
        CoinConfig config = getById(coinConfig.getId());
        if (config == null) { // 新增操作
            return save(coinConfig);
        } else { // 修改操作
            return updateById(coinConfig);
        }
    }
}

