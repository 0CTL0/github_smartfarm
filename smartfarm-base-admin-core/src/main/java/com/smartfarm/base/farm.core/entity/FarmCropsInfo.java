package com.smartfarm.base.farm.core.entity;

import java.math.BigDecimal;

public class FarmCropsInfo {
    /**禁用*/
    public static final Short STATUS_UNABLE=0;
    /**启用*/
    public static final Short STATUS_ABLE=1;

    private Long id;

    private String name;

    private Double price;

    private Integer area;

    private String cover;

    private String carousel;

    private Integer harvest;

    private String effect;

    private String plantingTime;

    private Integer growth;

    private Short status;

    private Integer orderNum;

    private Integer freshDay;

    private Long farmId;

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

    public Integer getFreshDay() {
        return freshDay;
    }

    public void setFreshDay(Integer freshDay) {
        this.freshDay = freshDay;
    }

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

    @Override
    public String toString() {
        return "FarmCropsInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", area=" + area +
                ", cover='" + cover + '\'' +
                ", carousel='" + carousel + '\'' +
                ", harvest=" + harvest +
                ", effect='" + effect + '\'' +
                ", plantingTime='" + plantingTime + '\'' +
                ", growth=" + growth +
                ", status=" + status +
                ", orderNum=" + orderNum +
                ", freshDay=" + freshDay +
                ", farmId=" + farmId +
                '}';
    }
}