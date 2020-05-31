package com.smartfarm.base.farm.core.entity;


public class RentOrder {
	//订单状态：未支付、支付超时、支付失败、支付成功（待种植）、已种植、已完成
	public final static Short STATUS_UNPAID=1;
	public final static Short STATUS_PAYOVER=2;
	public final static Short STATUS_REFUNDED=3;
	public final static Short STATUS_SUCCESS=4;
	public final static Short STATUS_PLANTING=5;
	public final static Short STATUS_END=6;
	
    private Long id;

    private String orderNo;

    private Long acreageId;

    private String name;

    private String code;

    private Double areageCost;

    private Double seedCost;

    private String orderTime;

    private Short status;

    private Short payStatus;

    private String payTime;

    private Double discount;

    private Double totalAmount;

    private Double realAmount;

    private String rentName;

    private String rentMobile;

    private Long memberId;    

    private String receiveName;

    private String receiveMobile;

    private String address;

    private String expireTime;
    
    private String payNo;
    
    private Short payType;
    
    private Integer period;
    
    private Short shipTimes;
    
    private Long strategyId;
  
    public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Short getShipTimes() {
		return shipTimes;
	}

	public void setShipTimes(Short shipTimes) {
		this.shipTimes = shipTimes;
	}

	

    public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getAcreageId() {
        return acreageId;
    }

    public void setAcreageId(Long acreageId) {
        this.acreageId = acreageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getAreageCost() {
        return areageCost;
    }

    public void setAreageCost(Double areageCost) {
        this.areageCost = areageCost;
    }

    public Double getSeedCost() {
        return seedCost;
    }

    public void setSeedCost(Double seedCost) {
        this.seedCost = seedCost;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Short payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Double realAmount) {
        this.realAmount = realAmount;
    }

    public String getRentName() {
        return rentName;
    }

    public void setRentName(String rentName) {
        this.rentName = rentName;
    }

    public String getRentMobile() {
        return rentMobile;
    }

    public void setRentMobile(String rentMobile) {
        this.rentMobile = rentMobile;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveMobile() {
        return receiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

	public Short getPayType() {
		return payType;
	}

	public void setPayType(Short payType) {
		this.payType = payType;
	}

	public Long getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(Long strategyId) {
		this.strategyId = strategyId;
	}
}