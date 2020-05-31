package com.smartfarm.base.farm.core.entity.vo;

import java.math.BigDecimal;
import java.util.List;

import com.smartfarm.base.farm.core.entity.Crowdfunding;
import com.smartfarm.base.farm.core.entity.Grade;

public class CrowdFundingVo extends Crowdfunding{

	private List<Grade> gradeList;
	private BigDecimal minGradePrice;
	private Integer orderQuantities;
	private Double totalAmount;
	private BigDecimal completionRate;

	public List<Grade> getGradeList() {
		return gradeList;
	}

	public void setGradeList(List<Grade> gradeList) {
		this.gradeList = gradeList;
	}

	public BigDecimal getMinGradePrice() {
		return minGradePrice;
	}

	public void setMinGradePrice(BigDecimal minGradePrice) {
		this.minGradePrice = minGradePrice;
	}

	public Integer getOrderQuantities() {
		return orderQuantities;
	}

	public void setOrderQuantities(Integer orderQuantities) {
		this.orderQuantities = orderQuantities;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getCompletionRate() {
		return completionRate;
	}

	public void setCompletionRate(BigDecimal completionRate) {
		this.completionRate = completionRate;
	}
	
}
