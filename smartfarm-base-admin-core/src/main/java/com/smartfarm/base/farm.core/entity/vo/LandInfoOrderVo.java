package com.smartfarm.base.farm.core.entity.vo;

import com.smartfarm.base.farm.core.entity.LandInfo;

public class LandInfoOrderVo extends LandInfo{
	private String areaName;
	private String code;
	private Integer area;
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
}
