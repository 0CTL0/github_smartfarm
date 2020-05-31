package com.smartfarm.base.monitor.core.controller.front;


import com.smartfarm.base.monitor.core.entity.ControlNode;
import com.smartfarm.base.monitor.core.manager.ControlNodeManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 监控模块-控制器，对应monitor_control_node表，控制层。
 *
 * @author HongSF
 * @version v1.0.0
 * @date 2020/4/2
 **/
@RestController
@RequestMapping("/controlNode")
public class ControlNodeControllerF {
    private Logger log = Logger.getLogger(ControlNodeControllerF.class);

    @Resource
    private ControlNodeManager controlNodeManager;

    /**
     * 根据控制器设备ID更新控制器操作状态
     *
     * @param id     {@link Long} 控制器设备ID
     * @param status {@link Short} 操作状态（1、开 2、关 3、暂停）
     * @return {@link Object}
     */
    @RequestMapping("/controlNodeOperate")
    public Object controlNodeOperate(Long id, Short status) {
        Map<String, Object> map = new HashMap<String, Object>(1);
        try {
            log.info("[ControlNodeController:controlNodeOperate]");
            controlNodeManager.controlNodeOperate(id, status);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[ControlNodeController:controlNodeOperate]false to query controlNode list.", e);
        }
        return map;
    }

    /**
     * 根据网关ID获取其对应下的控制器及定时策略数量
     * 条件1:网关ID  条件2:控制器节点状态联机(status=2)
     *
     * @param gatewayId {@link Long} 网关ID
     * @return {@link Object}
     */
    @RequestMapping("/controlNodeAndSetCount")
    public Object listControllerNodeAndSetCount(Long gatewayId) {
        Map<String, Object> map = new HashMap<String, Object>(2);
        try {
            log.info("[ControlNodeController:listControllerNodeAndSetCount]");
            map.put("cnList", this.controlNodeManager.listControllerNodeAndSetCountWithOnline(gatewayId));
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[ControlNodeController:listControllerNodeAndSetCount]false to query controllerNodeAndSetCount " +
                    "list.", e);
        }
        return map;
    }


    /**
     * 更新控制器节点
     * Restful API
     *
     * @param controlNode {@link Long} 控制器设备对象
     * @return {@link Object}
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Object updateControlNode(@RequestBody ControlNode controlNode) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[ControlNodeController:updateControlNodeInfo] controlNode : " + controlNode + ".");
            controlNodeManager.updateControlNodeById(controlNode);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[ControlNodeController:updateControlNodeInfo]fail to update controlNode.", e);
        }
        return map;
    }


}
