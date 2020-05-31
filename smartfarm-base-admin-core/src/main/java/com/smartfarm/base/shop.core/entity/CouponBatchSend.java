package com.smartfarm.base.shop.core.entity;

public class CouponBatchSend {
    private Long id;

    private Long couponBatchId;

    private String startTime;

    private String endTime;

    private Short status;

    private Integer timeForUser;

    private Integer totalNum;

    private Integer remainNum;

    public Integer getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(Integer remainNum) {
        this.remainNum = remainNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCouponBatchId() {
        return couponBatchId;
    }

    public void setCouponBatchId(Long couponBatchId) {
        this.couponBatchId = couponBatchId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getTimeForUser() {
        return timeForUser;
    }

    public void setTimeForUser(Integer timeForUser) {
        this.timeForUser = timeForUser;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }
}