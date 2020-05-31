package com.smartfarm.base.shop.core.entity;


public class ProductTemplateDetail {
    private Long id;

    private Long logisticsTemplateId;

    private String shipArea;

    private Double firstHeavy;

    private Integer firstPiece;

    private Double firstFreight;

    private Double continueHeavy;

    private Integer continuePiece;

    private Double continueFreight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLogisticsTemplateId() {
        return logisticsTemplateId;
    }

    public void setLogisticsTemplateId(Long logisticsTemplateId) {
        this.logisticsTemplateId = logisticsTemplateId;
    }

    public String getShipArea() {
        return shipArea;
    }

    public void setShipArea(String shipArea) {
        this.shipArea = shipArea;
    }

    public Double getFirstHeavy() {
        return firstHeavy;
    }

    public void setFirstHeavy(Double firstHeavy) {
        this.firstHeavy = firstHeavy;
    }

    public Integer getFirstPiece() {
        return firstPiece;
    }

    public void setFirstPiece(Integer firstPiece) {
        this.firstPiece = firstPiece;
    }

    public Double getFirstFreight() {
        return firstFreight;
    }

    public void setFirstFreight(Double firstFreight) {
        this.firstFreight = firstFreight;
    }

    public Double getContinueHeavy() {
        return continueHeavy;
    }

    public void setContinueHeavy(Double continueHeavy) {
        this.continueHeavy = continueHeavy;
    }

    public Double getContinueFreight() {
        return continueFreight;
    }

    public void setContinueFreight(Double continueFreight) {
        this.continueFreight = continueFreight;
    }

    public Integer getContinuePiece() {
        return continuePiece;
    }

    public void setContinuePiece(Integer continuePiece) {
        this.continuePiece = continuePiece;
    }

    @Override
    public String toString() {
        return "ProductTemplateDetail{" +
                "id=" + id +
                ", logisticsTemplateId=" + logisticsTemplateId +
                ", shipArea='" + shipArea + '\'' +
                ", firstHeavy=" + firstHeavy +
                ", firstPiece=" + firstPiece +
                ", firstFreight=" + firstFreight +
                ", continueHeavy=" + continueHeavy +
                ", continuePiece=" + continuePiece +
                ", continueFreight=" + continueFreight +
                '}';
    }
}