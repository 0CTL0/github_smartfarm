package com.smartfarm.base.monitor.core.entity;

public class ControlRecord {
	
	private Long id;
	private Long nodeId;
	private String startTime;
	private String endTime;
	private String duration;
	private Short operateType;
	private Short switchType;
	private Long operator;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Short getOperateType() {
		return operateType;
	}
	public void setOperateType(Short operateType) {
		this.operateType = operateType;
	}
	public Short getSwitchType() {
		return switchType;
	}
	public void setSwitchType(Short switchType) {
		this.switchType = switchType;
	}
	public Long getOperator() {
		return operator;
	}
	public void setOperator(Long operator) {
		this.operator = operator;
	}
	
}
