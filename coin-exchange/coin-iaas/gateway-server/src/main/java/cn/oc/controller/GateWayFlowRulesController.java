package cn.oc.controller;

import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : GateWayFlowRulesController
 * @Author: oc
 * @Date: 2022/10/13/20:28
 * @Description:
 **/
@RestController
public class GateWayFlowRulesController {

    /**
     * 获取当前系统的限流策略
     */
    @GetMapping("/gw/flow/rules")
    public Set<GatewayFlowRule> getCurrentGateWayFlowRules(){
        return GatewayRuleManager.getRules();
    }

    /**
     * 获取我们定义的ipi分组
     */
    @GetMapping("/gw/api/groups")
    public Set<ApiDefinition> getApiGroups(){
        return GatewayApiDefinitionManager.getApiDefinitions();
    }
}
