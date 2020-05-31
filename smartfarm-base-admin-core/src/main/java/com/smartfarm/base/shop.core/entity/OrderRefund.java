package com.smartfarm.base.shop.core.entity;

public class OrderRefund {
    /**请求处理中*/
    public static final Short STATUS_Request=0;
    /**已回复*/
    public static final Short STATUS_REPLIED=1;

    private Long id;

    private Long businessId;

    private Long orderId;

    private String refundNo;

    private Short status;

    private String transactionId;

    private String refundId;

    private String refundFee;

    private String successTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
        this.refundFee = refundFee;
    }

    public String getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(String successTime) {
        this.successTime = successTime;
    }

    @Override
    public String toString() {
        return "OrderRefund{" +
                "id=" + id +
                ", businessId=" + businessId +
                ", orderId=" + orderId +
                ", refundNo='" + refundNo + '\'' +
                ", status=" + status +
                ", transactionId='" + transactionId + '\'' +
                ", refundId='" + refundId + '\'' +
                ", refundFee='" + refundFee + '\'' +
                ", successTime='" + successTime + '\'' +
                '}';
    }
}