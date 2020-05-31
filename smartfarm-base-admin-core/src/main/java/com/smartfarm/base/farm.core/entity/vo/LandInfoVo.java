package com.smartfarm.base.farm.core.entity.vo;

import java.util.List;

import com.smartfarm.base.farm.core.entity.AcreageInfo;
import com.smartfarm.base.farm.core.entity.LandInfo;
import com.smartfarm.base.farm.core.entity.SeedInfo;

public class LandInfoVo extends LandInfo {
	private String landTypeName;	
	
	//租赁土地最低档次的价格
	private Double minRentPrice; 
	private List<AcreageInfo> areaList;
	private List<SeedInfo> seedList;

	
	public String getLandTypeName() {
		return landTypeName;
	}

	public void setLandTypeName(String landTypeName) {
		this.landTypeName = landTypeName;
	}

	public List<AcreageInfo> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<AcreageInfo> areaList) {
		this.areaList = areaList;
	}

	public List<SeedInfo> getSeedList() {
		return seedList;
	}

	public void setSeedList(List<SeedInfo> seedList) {
		this.seedList = seedList;
	}

	public Double getMinRentPrice() {
		return minRentPrice;
	}

	public void setMinRentPrice(Double minRentPrice) {
		this.minRentPrice = minRentPrice;
	}


}
