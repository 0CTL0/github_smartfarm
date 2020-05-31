package com.smartfarm.base.monitor.core.controller.front;

import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.monitor.core.entity.GatewayNode;
import com.smartfarm.base.monitor.core.manager.GatewayNodeManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监控-网关，控制层。
 *
 * @author HongSF
 * @version v1.0.0
 * @date 2020/4/1 10:11
 * @see com.smartfarm.base.monitor.core.controller.front
 **/
@RestController
@RequestMapping("gateway")
public class GatewayNodeControllerF {
    private Logger log = Logger.getLogger(MonitorRealDataControllerF.class);
    @Resource
    private GatewayNodeManager gatewayNodeManager;

    /**
     * 根据农场ID查询网关集合
     * 条件1:农场ID  条件2:状态为联机(status=2)
     *
     * @param request {@link HttpServletRequest}
     * @return {@link Object}
     */
    @RequestMapping("/queryGatewayListByFarm")
    public Object queryGatewayListByFarm(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //1.获取农场ID
            Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            //2.获取该农场所有网关
            List<GatewayNode> gatewayNodeList = this.gatewayNodeManager.listGatewayByBusinessId(businessId);
            //3.封装返回数据
            map.put("gwList", gatewayNodeList);
            map.put("success", true);
            log.info("[MonitorRealDataController:queryGatewayListByFarm]{farmId:" + businessId + "}query " +
                    "GatewayList.");
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MonitorRealDataController:queryGatewayListByFarm]false to query GatewayList.", e);
        }
        return map;
    }

}
