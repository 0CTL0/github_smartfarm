package com.smartfarm.base.farm.core.entity.vo;

import java.util.List;

import com.smartfarm.base.farm.core.entity.RentOrder;

public class RentOrderDetailVo extends RentOrder{
	private String nickName;
	private int area;
	private String landName;
	private String aName;
	private Integer leastShipTimes;
	private Integer landPeriod;
	private String overdueTimeString;
	private String cover;
	private Integer unDeliverCount;
	private List<SeedDetailVo> seedDetails;
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
	public List<SeedDetailVo> getSeedDetails() {
		return seedDetails;
	}
	public void setSeedDetails(List<SeedDetailVo> seedDetails) {
		this.seedDetails = seedDetails;
	}
	public Integer getUnDeliverCount() {
		return unDeliverCount;
	}
	public void setUnDeliverCount(Integer unDeliverCount) {
		this.unDeliverCount = unDeliverCount;
	}
}
