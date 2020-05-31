package com.smartfarm.base.shop.core.entity;

public class CouponBatch {
    private Long id;

    private String discretion;

    private Short strategyType;

    private String deadline;

    private Integer dayCount;

    private String displayName;

    private Double reductPrice;

    private Double amount;

    private Short type;

    private Integer discount;
    
    private Long businessId;

    private Integer useTime;

    public Integer getUseTime() {
        return useTime;
    }

    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
    }

    public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscretion() {
        return discretion;
    }

    public void setDiscretion(String discretion) {
        this.discretion = discretion;
    }

    public Short getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(Short strategyType) {
        this.strategyType = strategyType;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Double getReductPrice() {
        return reductPrice;
    }

    public void setReductPrice(Double reductPrice) {
        this.reductPrice = reductPrice;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}