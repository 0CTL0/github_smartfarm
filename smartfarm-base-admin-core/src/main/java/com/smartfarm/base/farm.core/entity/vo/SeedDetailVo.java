package com.smartfarm.base.farm.core.entity.vo;

import com.smartfarm.base.farm.core.entity.SeedDetail;

public class SeedDetailVo extends SeedDetail{
	private String cover;
	private Long sInfoId;

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Long getsInfoId() {
		return sInfoId;
	}

	public void setsInfoId(Long sInfoId) {
		this.sInfoId = sInfoId;
	}
}
