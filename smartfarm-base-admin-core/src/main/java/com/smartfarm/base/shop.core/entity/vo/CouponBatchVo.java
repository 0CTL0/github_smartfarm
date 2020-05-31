package com.smartfarm.base.shop.core.entity.vo;

import com.smartfarm.base.shop.core.entity.CouponBatch;

public class CouponBatchVo extends CouponBatch{
	private Integer useCount;
	private Integer couponCount;
	public Integer getUseCount() {
		return useCount;
	}
	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}
	public Integer getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}
	
}
