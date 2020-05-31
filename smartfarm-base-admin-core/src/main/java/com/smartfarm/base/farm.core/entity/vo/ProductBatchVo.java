package com.smartfarm.base.farm.core.entity.vo;

import com.smartfarm.base.farm.core.entity.ProductBatch;

public class ProductBatchVo extends ProductBatch {

	private Long masterId;//主计划id
	private String name;// 名称
	private Integer plantArea;// 种植面积
	private String farmName;// 农场名
	private String baseName;// 基地名
	private String tunnelName;// 地块名
	private String startDate;// 开始日期
	private String endDate;// 结束日期


	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPlantArea() {
		return plantArea;
	}

	public void setPlantArea(Integer plantArea) {
		this.plantArea = plantArea;
	}

	public String getFarmName() {
		return farmName;
	}

	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public String getTunnelName() {
		return tunnelName;
	}

	public void setTunnelName(String tunnelName) {
		this.tunnelName = tunnelName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
