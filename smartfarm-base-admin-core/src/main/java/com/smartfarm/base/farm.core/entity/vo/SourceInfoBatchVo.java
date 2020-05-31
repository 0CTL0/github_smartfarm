package com.smartfarm.base.farm.core.entity.vo;

import com.smartfarm.base.farm.core.entity.SourceInfo;

public class SourceInfoBatchVo extends SourceInfo {
	
	private Integer yield;// 批次产量
	private Integer packageSpecs;// 包装规格

	public Integer getYield() {
		return yield;
	}

	public void setYield(Integer yield) {
		this.yield = yield;
	}

	public Integer getPackageSpecs() {
		return packageSpecs;
	}

	public void setPackageSpecs(Integer packageSpecs) {
		this.packageSpecs = packageSpecs;
	}

}
