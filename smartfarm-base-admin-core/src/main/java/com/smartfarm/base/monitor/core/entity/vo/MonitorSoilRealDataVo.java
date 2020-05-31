package com.smartfarm.base.monitor.core.entity.vo;

import com.smartfarm.base.monitor.core.entity.MonitorRealData;

/**
 * 
 * @author lyq
 * 封装土壤数据
 *
 */
public class MonitorSoilRealDataVo extends MonitorRealData{
	private String name;
	private String unit;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
