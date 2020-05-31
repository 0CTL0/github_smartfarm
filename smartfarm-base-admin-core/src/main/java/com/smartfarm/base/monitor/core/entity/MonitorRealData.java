package com.smartfarm.base.monitor.core.entity;

public class MonitorRealData {
    private Long id;

    private Long sersorNodeId;

    private String transferDate;

    private String transferTime;

    private double realValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSersorNodeId() {
        return sersorNodeId;
    }

    public void setSersorNodeId(Long sersorNodeId) {
        this.sersorNodeId = sersorNodeId;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(String transferTime) {
        this.transferTime = transferTime;
    }

    public double getRealValue() {
        return realValue;
    }

    public void setRealValue(double realValue) {
        this.realValue = realValue;
    }

}