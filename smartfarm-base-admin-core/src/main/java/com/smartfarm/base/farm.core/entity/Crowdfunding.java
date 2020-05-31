package com.smartfarm.base.farm.core.entity;

import java.math.BigDecimal;

/**众筹活动*/
public class Crowdfunding {
    /** 流水号 */
    private Long id;
    /** 众筹活动名称 */
    private String name;
    /** 众筹简介  */
    private String brief;
    /** 众筹详情（图片） */
    private String description;
    /** 众筹封面  */
    private String cover;
    /** 目标金额 */
    private BigDecimal targerAmount;
    /** 项目发起人 */
    private String sponsor;
    /** 开始时间 */
    private String startTime;
    /** 结束时间 */
    private String endTime;
    /** 收益类型（1、分红 2、实物 3、分红+实物） */
    private Short profitType;
    /** 收益发放时间 */
    private String provideTime;
    /** 是不是热筹 */
    private Boolean isHot;
    /** 排序 */
    private Integer orderNum;
    /** 状态（-1初稿、1上架、2众筹、3众筹失败、4众筹成功、5项目进展中、6发放收益、7结束） */
    private Short status;
    /** 项目周期 */
    private Integer period;
    /** 众筹最少量（del） */
    private Double miniQuantity;
    /** 预期分红率（del？） */
    private Double bonusRate;
    /** 收益方式说明 */
    private String profitInfo;
    /** 是否展示 */
    private Boolean isShow;
    /** 所属农场 */
    private Long farmId;

    public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Double getMiniQuantity() {
		return miniQuantity;
	}

	public void setMiniQuantity(Double miniQuantity) {
		this.miniQuantity = miniQuantity;
	}

	public Double getBonusRate() {
		return bonusRate;
	}

	public void setBonusRate(Double bonusRate) {
		this.bonusRate = bonusRate;
	}

	public String getProfitInfo() {
		return profitInfo;
	}

	public void setProfitInfo(String profitInfo) {
		this.profitInfo = profitInfo;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public BigDecimal getTargerAmount() {
        return targerAmount;
    }

    public void setTargerAmount(BigDecimal targerAmount) {
        this.targerAmount = targerAmount;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
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

    public Short getProfitType() {
        return profitType;
    }

    public void setProfitType(Short profitType) {
        this.profitType = profitType;
    }

    public String getProvideTime() {
        return provideTime;
    }

    public void setProvideTime(String provideTime) {
        this.provideTime = provideTime;
    }

    public Boolean getIsHot() {
        return isHot;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }
}