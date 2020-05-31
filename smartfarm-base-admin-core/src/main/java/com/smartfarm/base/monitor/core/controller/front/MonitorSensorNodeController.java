package com.smartfarm.base.monitor.core.controller.front;

import com.smartfarm.base.monitor.core.entity.vo.WeChatMonitorSensorVo;
import com.smartfarm.base.monitor.core.manager.SensorNodeManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监控-传感器，控制层。
 *
 * @author HongSF
 * @version v1.0.0
 * @date 2020/4/1 11:49
 * @see com.smartfarm.base.monitor.core.controller.front
 **/
@RestController
@RequestMapping("sensorNode")
public class MonitorSensorNodeController {
    private Logger log = Logger.getLogger(MonitorRealDataControllerF.class);
    @Resource
    private SensorNodeManager sensorNodeManager;

    /**
     * 根据网关ID查询传感器及今天最新的实时数据
     * 条件1:网关ID  条件2:状态在线(status=2)  条件3:今天最新
     *
     * @param gatewayId 网关ID
     * @return {@link List}
     */
    @RequestMapping("/sensorNodeAndTodayNewestRealData")
    public Object querySensorNodeAndTodayNewestRealDataByGatewayId(Long gatewayId) {
        Map<String, Object> map = new HashMap<String, Object>(2);
        try {
            //1.查询数据
            //TODO:备注:因为数据库中没有实时数据,所以就先用固定日期,后期需要修改
            //TODO:"20191126"=>DateUtil.formatCurrentDate()
            List<WeChatMonitorSensorVo> sensorNodeVoList =
                    this.sensorNodeManager.listSensorNodeAndTodayNewestRealDataByGatewayId("20191126", gatewayId);
            //2.封装返回数据
            map.put("snList", sensorNodeVoList);
            map.put("success", true);
            log.info("[MonitorSensorNodeController:querySensorNodeVoListByGatewayId]{gatewayId:" + gatewayId +
                    "}query SensorNodeVoList.");
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MonitorSensorNodeController:querySensorNodeVoListByGatewayId]false to query SensorNodeVoList" +
                    ".", e);
        }
        return map;
    }
}
