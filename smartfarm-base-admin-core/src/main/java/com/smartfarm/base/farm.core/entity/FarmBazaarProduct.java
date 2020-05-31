package com.smartfarm.base.farm.core.entity;

import java.math.BigDecimal;

public class FarmBazaarProduct {

    /**上架状态*/
    public static final Short STATUS_UNABLE=0;
    /**下架状态*/
    public static final Short STATUS_ABLE=1;

    private Long id;

    private Long memberLandId;

    private Long memberCropsId;

    private String name;

    private String cover;

    private Integer stock;

    private Integer unit;

    private BigDecimal price;

    private String endTime;

    private Long memberSellerId;

    private Short status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberCropsId() {
        return memberCropsId;
    }

    public void setMemberCropsId(Long memberCropsId) {
        this.memberCropsId = memberCropsId;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getMemberSellerId() {
        return memberSellerId;
    }

    public void setMemberSellerId(Long memberSellerId) {
        this.memberSellerId = memberSellerId;
    }

    public Long getMemberLandId() {
        return memberLandId;
    }

    public void setMemberLandId(Long memberLandId) {
        this.memberLandId = memberLandId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FarmBazaarProduct{" +
                "id=" + id +
                ", memberLandId=" + memberLandId +
                ", memberCropsId=" + memberCropsId +
                ", name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", stock=" + stock +
                ", unit=" + unit +
                ", price=" + price +
                ", endTime='" + endTime + '\'' +
                ", memberSellerId=" + memberSellerId +
                ", status=" + status +
                '}';
    }
}