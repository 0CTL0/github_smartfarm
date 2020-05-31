package com.smartfarm.base.monitor.core.entity.vo;

import com.smartfarm.base.monitor.core.entity.MonitorWarningInfo;

public class MonitorWarningInfoVo extends MonitorWarningInfo{
	private String nodeName;
	
	private String gatewayName;

	public String getGatewayName() {
		return gatewayName;
	}

	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
}
