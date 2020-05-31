package com.smartfarm.base.shop.core.entity;

public class ActivityInfo {
    private Long id;

    private String name;

    private String picUrl;

    private Integer joinNum;

    private String joinTime;
    
    private String activityTime;

    private String address;

    private Double longitude;

    private Double latitude;

    private String mobile;

    private Long businessId;

    private Boolean needName;

    private Boolean needMobile;

    private Boolean needIdcard;

    private String detail;

    private Boolean isShow;

    private Short status;
    
    private String joinDeadline;
    
    //排序
    private Integer orderNum;

    private String createTime;
    
    private Integer remain;

    private String activityDeadline;

    public Integer getRemain() {
		return remain;
	}

	public void setRemain(Integer remain) {
		this.remain = remain;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(Integer joinNum) {
        this.joinNum = joinNum;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Boolean getNeedName() {
        return needName;
    }

    public void setNeedName(Boolean needName) {
        this.needName = needName;
    }

    public Boolean getNeedMobile() {
        return needMobile;
    }

    public void setNeedMobile(Boolean needMobile) {
        this.needMobile = needMobile;
    }

    public Boolean getNeedIdcard() {
        return needIdcard;
    }

    public void setNeedIdcard(Boolean needIdcard) {
        this.needIdcard = needIdcard;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

	public String getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}

	public String getJoinDeadline() {
		return joinDeadline;
	}

	public void setJoinDeadline(String joinDeadline) {
		this.joinDeadline = joinDeadline;
	}

    public String getActivityDeadline() {
        return activityDeadline;
    }

    public void setActivityDeadline(String activityDeadline) {
        this.activityDeadline = activityDeadline;
    }
}