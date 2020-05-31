package com.smartfarm.base.farm.core.entity;

import java.math.BigDecimal;

public class SeedInfo {
    private Long id;

    private String name;

    private BigDecimal price;

    private Integer area;

    private String cover;

    private String carousel;

    private Integer harvest;

    private String unit;

    private String effect;

    private String plantingTime;

    private Integer growth;

    private Short status;

    private Integer orderNum;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCarousel() {
        return carousel;
    }

    public void setCarousel(String carousel) {
        this.carousel = carousel;
    }

    public Integer getHarvest() {
        return harvest;
    }

    public void setHarvest(Integer harvest) {
        this.harvest = harvest;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getPlantingTime() {
        return plantingTime;
    }

    public void setPlantingTime(String plantingTime) {
        this.plantingTime = plantingTime;
    }

    public Integer getGrowth() {
        return growth;
    }

    public void setGrowth(Integer growth) {
        this.growth = growth;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

	@Override
	public String toString() {
		return "SeedInfo [id=" + id + ", name=" + name + ", price=" + price
				+ ", area=" + area + ", cover=" + cover + ", carousel="
				+ carousel + ", harvest=" + harvest + ", unit=" + unit
				+ ", effect=" + effect + ", plantingTime=" + plantingTime
				+ ", growth=" + growth + ", status=" + status + ", orderNum="
				+ orderNum + "]";
	}
    
    
}