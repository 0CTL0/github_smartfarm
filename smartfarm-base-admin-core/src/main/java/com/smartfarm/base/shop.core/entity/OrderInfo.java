package com.smartfarm.base.shop.core.entity;

public class OrderInfo {

    /**status的值：-2超时 -1 取消 0未支付 1订单已付款 -3退款申请 -4退款中 -5已退款 -6拒绝退款*/
    private Long id;

    private Long memberId;

    private String orderNo;

    private Short status;

    private Double totalPrice;

    private Double freightPrice;

    private Double couponPrice;

    private Double integralPrice;

    private Double groupPrice;

    private Double orderPrice;

    private Double realPrice;

    private String payNo;

    private String createTime;

    private String payTime;

    private Short payStatus;
    
    private String endTime;
    
    private Short type;
    
    private Long businessId;

    private String refundMsg;


    public String getRefundMsg() {
        return refundMsg;
    }

    public void setRefundMsg(String refundMsg) {
        this.refundMsg = refundMsg;
    }

    public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(Double freightPrice) {
        this.freightPrice = freightPrice;
    }

    public Double getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(Double couponPrice) {
        this.couponPrice = couponPrice;
    }

    public Double getIntegralPrice() {
        return integralPrice;
    }

    public void setIntegralPrice(Double integralPrice) {
        this.integralPrice = integralPrice;
    }

    public Double getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(Double groupPrice) {
        this.groupPrice = groupPrice;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Double getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Double realPrice) {
        this.realPrice = realPrice;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public Short getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Short payStatus) {
        this.payStatus = payStatus;
    }


    @Override
    public String toString() {
        return "OrderInfo{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", orderNo='" + orderNo + '\'' +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", freightPrice=" + freightPrice +
                ", couponPrice=" + couponPrice +
                ", integralPrice=" + integralPrice +
                ", groupPrice=" + groupPrice +
                ", orderPrice=" + orderPrice +
                ", realPrice=" + realPrice +
                ", payNo='" + payNo + '\'' +
                ", createTime='" + createTime + '\'' +
                ", payTime='" + payTime + '\'' +
                ", payStatus=" + payStatus +
                ", endTime='" + endTime + '\'' +
                ", type=" + type +
                ", businessId=" + businessId +
                ", refundMsg='" + refundMsg + '\'' +
                '}';
    }


}