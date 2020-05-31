package com.smartfarm.base.shop.core.entity.vo;

import java.util.List;

import com.smartfarm.base.shop.core.entity.ActivityInfo;
import com.smartfarm.base.shop.core.entity.ActivityPrice;


public class ActivityInfoVo extends ActivityInfo {

	private Double price;
	
	private List<ActivityPrice> priceList;
	
	private List<ActivityRegistrationVo> registrationList;

	public List<ActivityPrice> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<ActivityPrice> priceList) {
		this.priceList = priceList;
	}

	public List<ActivityRegistrationVo> getRegistrationList() {
		return registrationList;
	}

	public void setRegistrationList(List<ActivityRegistrationVo> registrationList) {
		this.registrationList = registrationList;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}

