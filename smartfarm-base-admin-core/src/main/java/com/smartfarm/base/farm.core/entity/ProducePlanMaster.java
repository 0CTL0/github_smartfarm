package com.smartfarm.base.farm.core.entity;

public class ProducePlanMaster {

	private Long id;// 流水号id
	private Long batchId;// 批次号
	private Integer plantArea;// 种植面积
	private Long tunnelId;// 基地地块
	private String startDate;// 开始日期
	private String endDate;// 结束日期
	private String createTime;// 创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Integer getPlantArea() {
        return plantArea;
    }

    public void setPlantArea(Integer plantArea) {
        this.plantArea = plantArea;
    }

    public Long getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(Long tunnelId) {
        this.tunnelId = tunnelId;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}