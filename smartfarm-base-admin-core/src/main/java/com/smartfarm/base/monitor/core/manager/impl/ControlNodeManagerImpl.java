package com.smartfarm.base.monitor.core.manager.impl;

import com.smartfarm.base.monitor.core.dao.ControlNodeDao;
import com.smartfarm.base.monitor.core.dao.GatewayNodeDao;
import com.smartfarm.base.monitor.core.entity.ControlNode;
import com.smartfarm.base.monitor.core.entity.ControlNodeDetails;
import com.smartfarm.base.monitor.core.entity.GatewayNode;
import com.smartfarm.base.monitor.core.entity.vo.WeChatControlNodeVo;
import com.smartfarm.base.monitor.core.manager.ControlNodeManager;
import com.smartfarm.base.monitor.core.service.EventService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("controlNodeManager")
public class ControlNodeManagerImpl implements ControlNodeManager {

    @Resource
    private ControlNodeDao controlNodeDao;
    @Resource
    private EventService eventService;
    @Resource
    private GatewayNodeDao gatewayNodeDao;

    @Override
    public List<ControlNode> getControlNodes() {
        return controlNodeDao.getControlNodes();
    }

	@Override
	public List<ControlNodeDetails> getControlNodesDetails() {
		return controlNodeDao.getControlNodesDetails();
	}

	@Override
	public ControlNode getSingleControlNode(Long id) {
		return controlNodeDao.getById(id);
	}
	
	@Override
	public int updateControlNodeById(ControlNode controlNode) {
		return controlNodeDao.updateById(controlNode);
	}

    @Override
    public List<ControlNode> queryControllerNodeList() {
        return controlNodeDao.queryControllerNodeList();
    }

    @Override
    public void publishMessage(String gatewayCode, String productCode, Short operateStatus) {
        String operate;
        if (operateStatus == 1) {
            operate = "1";
        } else {
            operate = "0";
        }
        String topic = "stds/down/CT/" + gatewayCode + "/sLoop";//拼接topic
        String[] codes = productCode.split("-");
        String sensorNodeCode = codes[0];
        String locate = codes[1];
        String json = "{\"id\":" + sensorNodeCode + ",\"sid\":" + locate + ",\"val\":" + operate + ",\"time\":0}";
        eventService.sendProductMsg(topic, json);
    }

    @Override
    public int addControlNode(ControlNode controlNode) {
        return controlNodeDao.add(controlNode);
    }

    @Override
    public int deleteControlNodeById(Long id) {
        return controlNodeDao.deleteById(id);
    }

    @Override
    public List<ControlNode> queryFarmerControlNodes(List<GatewayNode> gatewayNodes) {
        return controlNodeDao.getFarmerControlNodes(gatewayNodes);
    }

    @Override
    public void controlNodeOperate(Long id, Short status) {
        ControlNode controlNode = controlNodeDao.getById(id);
        controlNode.setOperateStatus(status);
        controlNodeDao.updateById(controlNode);
        GatewayNode gatewayNode = gatewayNodeDao.queryGatewayNodeById(controlNode.getGatewayId());

        String topic = "stds/down/CT/" + gatewayNode.getProductCode() + "/sLoop";//拼接topic
        String productCode = controlNode.getProductCode();
        String[] codes = productCode.split("-");
        String sensorNodeCode = codes[0];
        String locate = codes[1];
        String json = "{\"id\":" + sensorNodeCode + ",\"sid\":" + locate + ",\"val\":" + status + ",\"time\":0}";
        eventService.sendProductMsg(topic, json);

    }

    @Override
    public List<ControlNode> queryFarmControlNodes(Long farmId) {
        return controlNodeDao.getFarmControlNodes(farmId);
    }

    /**
     * 根据网关ID获取其对应下的控制器及定时策略数量
     * 条件1:网关ID  条件2:控制器节点状态联机(status=2)
     *
     * @param gatewayId {@link Long} 网关ID
     * @return @return {@link Object}
     */
    @Override
    public List<WeChatControlNodeVo> listControllerNodeAndSetCountWithOnline(Long gatewayId) {
        return this.controlNodeDao.listControllerNodeAndSetCountWithOnline(gatewayId);
    }
}
