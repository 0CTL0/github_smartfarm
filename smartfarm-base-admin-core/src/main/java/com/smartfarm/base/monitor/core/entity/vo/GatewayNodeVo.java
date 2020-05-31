package com.smartfarm.base.monitor.core.entity.vo;

import java.util.List;

import com.smartfarm.base.monitor.core.entity.ControlNode;
import com.smartfarm.base.monitor.core.entity.GatewayNode;
import com.smartfarm.base.monitor.core.entity.SensorNode;

public class GatewayNodeVo extends GatewayNode {

	private List<ControlNode> controlNodeList;
	
	private List<SensorNode> sensorNodeList; 

	public List<SensorNode> getSensorNodeList() {
		return sensorNodeList;
	}

	public void setSensorNodeList(List<SensorNode> sensorNodeList) {
		this.sensorNodeList = sensorNodeList;
	}

	public List<ControlNode> getControlNodeList() {
		return controlNodeList;
	}

	public void setControlNodeList(List<ControlNode> controlNodeList) {
		this.controlNodeList = controlNodeList;
	}
	
}
