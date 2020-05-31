package com.smartfarm.base.shop.core.entity;


public class MemberOrderCommission {
    private Long id;

    private Long memberId;

    private Long orderId;

    private Long orderMemberId;

    private Double commissionSum;

    private String createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderMemberId() {
        return orderMemberId;
    }

    public void setOrderMemberId(Long orderMemberId) {
        this.orderMemberId = orderMemberId;
    }

    public Double getCommissionSum() {
        return commissionSum;
    }

    public void setCommissionSum(Double commissionSum) {
        this.commissionSum = commissionSum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}