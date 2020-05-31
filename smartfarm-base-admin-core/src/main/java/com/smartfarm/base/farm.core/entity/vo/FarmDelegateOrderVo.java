package com.smartfarm.base.farm.core.entity.vo;

import com.smartfarm.base.farm.core.entity.FarmDelegateOrder;
public class FarmDelegateOrderVo extends FarmDelegateOrder {
	
	private String paramName;
	private String rentLandName;

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}


	public String getRentLandName() {
		return rentLandName;
	}

	public void setRentLandName(String rentLandName) {
		this.rentLandName = rentLandName;
	}
}
