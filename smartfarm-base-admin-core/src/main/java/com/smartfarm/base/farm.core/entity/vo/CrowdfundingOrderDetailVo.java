package com.smartfarm.base.farm.core.entity.vo;

import com.smartfarm.base.farm.core.entity.CrowdfundingOrder;


public class CrowdfundingOrderDetailVo extends CrowdfundingOrder{

	private Short profitType;
	private Double bonusRate;
	private String cover;
	private String provideTime;
	private Long crowdId;//众筹id

	public Short getProfitType() {
		return profitType;
	}
	public void setProfitType(Short profitType) {
		this.profitType = profitType;
	}
	public Double getBonusRate() {
		return bonusRate;
	}
	public void setBonusRate(Double bonusRate) {
		this.bonusRate = bonusRate;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getProvideTime() {
		return provideTime;
	}
	public void setProvideTime(String provideTime) {
		this.provideTime = provideTime;
	}
	public Long getCrowdId() {
		return crowdId;
	}
	public void setCrowdId(Long crowdId) {
		this.crowdId = crowdId;
	}

}
