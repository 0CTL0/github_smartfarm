package com.smartfarm.base.farm.core.entity.vo;

import com.smartfarm.base.farm.core.entity.SeedInfo;

public class SeedInfoVo extends SeedInfo{
	private Long landId;
	private boolean flag=false;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Long getLandId() {
		return landId;
	}

	public void setLandId(Long landId) {
		this.landId = landId;
	}
}
