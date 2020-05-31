package com.smartfarm.base.shop.core.entity.vo;

import com.smartfarm.base.farm.core.entity.vo.FarmTunnelInfoVo;
import com.smartfarm.base.shop.core.entity.IndexAdvance;


public class IndexAdvanceVo extends IndexAdvance {
	private ProductInfoVo productInfoVo;
	
	private FarmTunnelInfoVo farmTunnelInfoVo;
	
	private ActivityInfoVo activityInfoVo;

	public ActivityInfoVo getActivityInfoVo() {
		return activityInfoVo;
	}

	public void setActivityInfoVo(ActivityInfoVo activityInfoVo) {
		this.activityInfoVo = activityInfoVo;
	}

	public FarmTunnelInfoVo getFarmTunnelInfoVo() {
		return farmTunnelInfoVo;
	}

	public void setFarmTunnelInfoVo(FarmTunnelInfoVo farmTunnelInfoVo) {
		this.farmTunnelInfoVo = farmTunnelInfoVo;
	}

	public ProductInfoVo getProductInfoVo() {
		return productInfoVo;
	}

	public void setProductInfoVo(ProductInfoVo productInfoVo) {
		this.productInfoVo = productInfoVo;
	}
	
}

