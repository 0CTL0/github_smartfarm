package com.smartfarm.base.shop.core.entity.vo;

import com.smartfarm.base.shop.core.entity.ActivityRegistration;

public class ActivityRegistrationVo extends ActivityRegistration{
	
	private String priceName; 
	private Double price;
	
	private String nickName;
	
	private String picUrl;
	
	private String activityName;
	
	private String address;
	
	private String activityTime;
	private String activityDeadline;
	
	public String getActivityTime() {
		return activityTime;
	}
	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getPriceName() {
		return priceName;
	}
	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public String getActivityDeadline() {
		return activityDeadline;
	}

	public void setActivityDeadline(String activityDeadline) {
		this.activityDeadline = activityDeadline;
	}
}
