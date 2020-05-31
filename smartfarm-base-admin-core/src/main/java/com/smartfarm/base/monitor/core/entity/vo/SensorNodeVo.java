package com.smartfarm.base.monitor.core.entity.vo;

import com.smartfarm.base.monitor.core.entity.SensorNode;

public class SensorNodeVo extends SensorNode{
	private String gateNodeName;//网关名称
	private String name;//传感器名
	
	private String unit;
	private String pCode;
	private double realValue;
	
	private double avgValue;
	
	private String codeList;
	
	private Long sersorNodeId;
	
	
	
	public String getGateNodeName() {
		return gateNodeName;
	}
	public void setGateNodeName(String gateNodeName) {
		this.gateNodeName = gateNodeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public double getRealValue() {
		return realValue;
	}
	public void setRealValue(double realValue) {
		this.realValue = realValue;
	}
	public double getAvgValue() {
		return avgValue;
	}
	public void setAvgValue(double avgValue) {
		this.avgValue = avgValue;
	}
	public String getCodeList() {
		return codeList;
	}
	public void setCodeList(String codeList) {
		this.codeList = codeList;
	}
	public Long getSersorNodeId() {
		return sersorNodeId;
	}
	public void setSersorNodeId(Long sersorNodeId) {
		this.sersorNodeId = sersorNodeId;
	}
	public String getpCode() {
		return pCode;
	}
	public void setpCode(String pCode) {
		this.pCode = pCode;
	}
	
}
