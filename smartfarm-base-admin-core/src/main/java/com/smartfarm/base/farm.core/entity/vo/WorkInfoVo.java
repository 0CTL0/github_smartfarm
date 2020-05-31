package com.smartfarm.base.farm.core.entity.vo;

import java.util.List;

import com.smartfarm.base.farm.core.entity.WorkDetail;
import com.smartfarm.base.farm.core.entity.WorkInfo;

public class WorkInfoVo extends WorkInfo{
	private List<WorkDetail> details;

	public List<WorkDetail> getDetails() {
		return details;
	}

	public void setDetails(List<WorkDetail> details) {
		this.details = details;
	}
}
