package com.smartfarm.base.farm.core.entity.vo;

import java.util.List;

import com.smartfarm.base.farm.core.entity.RentOrder;
import com.smartfarm.base.farm.core.entity.SeedDetail;

public class RentOrderVo extends RentOrder {
	private String nickName;
	private int area;
	private String landName;
	private String aName;
	private Integer leastShipTimes;
	private Integer landPeriod;
	private String overdueTimeString;
	private String cover;
	private Integer deliverCount;
	private String nextDeliveryTime;
	private List<SeedDetail> seedDetails;
	

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public String getLandName() {
		return landName;
	}

	public void setLandName(String landName) {
		this.landName = landName;
	}

	public String getaName() {
		return aName;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}

	public Integer getLeastShipTimes() {
		return leastShipTimes;
	}

	public void setLeastShipTimes(Integer leastShipTimes) {
		this.leastShipTimes = leastShipTimes;
	}

	public Integer getLandPeriod() {
		return landPeriod;
	}

	public void setLandPeriod(Integer landPeriod) {
		this.landPeriod = landPeriod;
	}

	public List<SeedDetail> getSeedDetails() {
		return seedDetails;
	}

	public void setSeedDetails(List<SeedDetail> seedDetails) {
		this.seedDetails = seedDetails;
	}

	public String getOverdueTimeString() {
		return overdueTimeString;
	}

	public void setOverdueTimeString(String overdueTimeString) {
		this.overdueTimeString = overdueTimeString;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Integer getDeliverCount() {
		return deliverCount;
	}

	public void setDeliverCount(Integer deliverCount) {
		this.deliverCount = deliverCount;
	}

	public String getNextDeliveryTime() {
		return nextDeliveryTime;
	}

	public void setNextDeliveryTime(String nextDeliveryTime) {
		this.nextDeliveryTime = nextDeliveryTime;
	}

}
