package com.smartfarm.base.farm.core.entity;

public class FarmDelegatePlant {
    private Long id;

    private Long cropsId;

    private Integer plantArea;

    private Long orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCropsId() {
        return cropsId;
    }

    public void setCropsId(Long cropsId) {
        this.cropsId = cropsId;
    }

    public Integer getPlantArea() {
        return plantArea;
    }

    public void setPlantArea(Integer plantArea) {
        this.plantArea = plantArea;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}