package com.smartfarm.base.farm.core.entity;

public class ProducePlanDetail {
	
	private Long id;
	private String name;
	private String brief;
	private String stage;
	private String taskDate;
	private Long plantId;//应该是批次表id
	private Short logStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(String taskDate) {
		this.taskDate = taskDate;
	}

	public Long getPlantId() {
		return plantId;
	}

	public void setPlantId(Long plantId) {
		this.plantId = plantId;
	}

	public Short getLogStatus() {
		return logStatus;
	}

	public void setLogStatus(Short logStatus) {
		this.logStatus = logStatus;
	}
	
}