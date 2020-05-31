package com.smartfarm.base.admin.core.entity.vo;

import com.smartfarm.base.admin.core.entity.Admin;


public class AdminVo extends Admin {

    private String farmName;

	public String getFarmName() {
		return farmName;
	}

	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}

}