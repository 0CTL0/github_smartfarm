package com.smartfarm.base.shop.core.entity;

public class CouponBatchRule {
    private Long id;

    private Short ruleType;

    private Long couponBatchId;

    private String paramValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getRuleType() {
        return ruleType;
    }

    public void setRuleType(Short ruleType) {
        this.ruleType = ruleType;
    }

    public Long getCouponBatchId() {
        return couponBatchId;
    }

    public void setCouponBatchId(Long couponBatchId) {
        this.couponBatchId = couponBatchId;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}