package com.smartfarm.base.farm.core.entity;

public class LandInfo {
	
	 /** 上架  */
	public final static Short STATUS_PUTAWAY = 1; 
	 /** 下架  */
	public final static Short STATUS_SOLDOUT = 2;
	
    private Long id;

    private String name;

    private String cover;

    private String brief;

    private String description;

    private Long typeId;

    private Short status;

    private String seedIds;

    private Integer period;

    private Integer shipTimes;

    private Integer sort;

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getSeedIds() {
        return seedIds;
    }

    public void setSeedIds(String seedIds) {
        this.seedIds = seedIds;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getShipTimes() {
        return shipTimes;
    }

    public void setShipTimes(Integer shipTimes) {
        this.shipTimes = shipTimes;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}