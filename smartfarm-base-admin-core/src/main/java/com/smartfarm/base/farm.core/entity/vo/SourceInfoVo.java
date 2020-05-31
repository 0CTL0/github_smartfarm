package com.smartfarm.base.farm.core.entity.vo;

import java.util.List;

import com.smartfarm.base.farm.core.entity.SourceInfo;

public class SourceInfoVo extends SourceInfo{
	private List<WorkInfoVo> works;

	public List<WorkInfoVo> getWorks() {
		return works;
	}

	public void setWorks(List<WorkInfoVo> works) {
		this.works = works;
	}
}
