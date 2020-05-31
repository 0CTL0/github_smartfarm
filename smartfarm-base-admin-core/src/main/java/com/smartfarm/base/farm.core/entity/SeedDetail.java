package com.smartfarm.base.farm.core.entity;

public class SeedDetail {
	
	//待种植
	public final static Short UNPLANTED = 1; 
	//种植中
	public final static Short PLANTING = 2;
	//成熟
	public final static Short MATURE = 3; 
	
    private Long id;

    private Long orderId;

    private String name;

    private Double price;

    private Integer area;

    private Double totalAmount;

    private Long seedId;

    private Short plantStatus;

    private String plantTime;

    private String matureTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getSeedId() {
        return seedId;
    }

    public void setSeedId(Long seedId) {
        this.seedId = seedId;
    }

    public Short getPlantStatus() {
        return plantStatus;
    }

    public void setPlantStatus(Short plantStatus) {
        this.plantStatus = plantStatus;
    }

    public String getPlantTime() {
        return plantTime;
    }

    public void setPlantTime(String plantTime) {
        this.plantTime = plantTime;
    }

    public String getMatureTime() {
        return matureTime;
    }

    public void setMatureTime(String matureTime) {
        this.matureTime = matureTime;
    }
}