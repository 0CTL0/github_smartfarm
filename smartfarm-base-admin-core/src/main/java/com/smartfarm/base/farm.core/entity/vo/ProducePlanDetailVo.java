package com.smartfarm.base.farm.core.entity.vo;

import com.smartfarm.base.farm.core.entity.ProducePlanDetail;

public class ProducePlanDetailVo extends ProducePlanDetail {
	
	private String masterName;//主计划对应商品名

	private String batchCode;//批次号

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}



	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}
	
}
