package com.smartfarm.base.monitor.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.monitor.core.dao.ControlNodeDao;
import com.smartfarm.base.monitor.core.dao.GatewayNodeDao;
import com.smartfarm.base.monitor.core.dao.MonitorControlSetConditionDao;
import com.smartfarm.base.monitor.core.dao.MonitorControlSetDao;
import com.smartfarm.base.monitor.core.dao.MonitorControlSetNodeDao;
import com.smartfarm.base.monitor.core.entity.ControlNode;
import com.smartfarm.base.monitor.core.entity.GatewayNode;
import com.smartfarm.base.monitor.core.entity.MonitorControlSet;
import com.smartfarm.base.monitor.core.entity.MonitorControlSetCondition;
import com.smartfarm.base.monitor.core.entity.MonitorControlSetNode;
import com.smartfarm.base.monitor.core.manager.MonitorControlSetManager;
import com.smartfarm.base.monitor.core.service.EventService;

@Service
public class MonitorControlSetManagerImpl implements MonitorControlSetManager {

	@Resource
	private MonitorControlSetDao monitorControlSetDao;
	@Resource
	private MonitorControlSetNodeDao monitorControlSetNodeDao;
	@Resource
	private MonitorControlSetConditionDao monitorControlSetConditionDao;
	@Resource
	private GatewayNodeDao gatewayNodeDao;
	@Resource
	private ControlNodeDao controlNodeDao;
	@Resource
	private EventService eventService;

	@Override
	public List<MonitorControlSet> queryPageControlSetList(Long businessId,
			Integer start, Integer limit) {
		return monitorControlSetDao.queryPageControlSetList(businessId, start, limit);
	}

	@Override
	public Integer countPageControlSetList(Long businessId) {
		return monitorControlSetDao.countPageControlSetList(businessId);
	}

	@Override
	public void addControlSet(MonitorControlSet monitorControlSet,
			List<MonitorControlSetNode> nodeList,
			List<MonitorControlSetCondition> conditionList) {
		monitorControlSet.setCreateTime(DateUtil.formatCurrentDateTime());
		monitorControlSet.setStatus((short)2);
		monitorControlSetDao.insert(monitorControlSet);
		
		for(MonitorControlSetNode monitorControlSetNode:nodeList) {
			monitorControlSetNode.setControlSetId(monitorControlSet.getId());
			monitorControlSetNodeDao.insert(monitorControlSetNode);
		}
		
		for(MonitorControlSetCondition monitorControlSetCondition:conditionList) {
			monitorControlSetCondition.setControlSetId(monitorControlSet.getId());
			monitorControlSetConditionDao.insert(monitorControlSetCondition);
		}
		
		
	}

	@Override
	public List<MonitorControlSet> queryAllActiveTimeSet() {
		return monitorControlSetDao.queryAllActiveTimeSet(DateUtil.formatCurrentDate());
	}

	@Override
	public void updateStatus(Long id, Short status) {
		MonitorControlSet monitorControlSet = new MonitorControlSet();
		monitorControlSet.setId(id);
		monitorControlSet.setStatus(status);
		monitorControlSetDao.updateById(monitorControlSet);
	}

	@Override
	public void setControlNode(Long setId, Short status) {
		List<ControlNode> nodeList = controlNodeDao.queryNodeBySetId(setId);
		for(ControlNode controlNode:nodeList) {
			controlNode.setOperateStatus(status);
			controlNodeDao.updateById(controlNode);
			GatewayNode gatewayNode = gatewayNodeDao.queryGatewayNodeById(controlNode.getGatewayId());
			
			String topic = "stds/down/CT/" + gatewayNode.getProductCode() + "/sLoop";//拼接topic
			String productCode = controlNode.getProductCode();
			String[] codes = productCode.split("-");
			String sensorNodeCode = codes[0];
			String locate = codes[1];
			String json = "{\"id\":"+sensorNodeCode+",\"sid\":" + locate+",\"val\":" + status + ",\"time\":0}";
			eventService.sendProductMsg(topic, json);
		}
		
	}
	

}
