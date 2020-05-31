package com.smartfarm.base.farm.core.entity.vo;

import com.smartfarm.base.farm.core.entity.SeedDetail;

public class SeedDetailInfoVo extends SeedDetail{
	private String orderNo;
	private Integer growth;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getGrowth() {
		return growth;
	}
	public void setGrowth(Integer growth) {
		this.growth = growth;
	}
}
