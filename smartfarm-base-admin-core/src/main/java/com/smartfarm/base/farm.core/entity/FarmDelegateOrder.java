package com.smartfarm.base.farm.core.entity;

public class FarmDelegateOrder {
    private Long id;

    private String applyNo;

    private String applyDate;

    private String workDate;

    private String workType;

    private Long memberLandId;

    private String remark;

    private Long integral;

    private Short status;
    
    private Long memberCropsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public Long getMemberLandId() {
        return memberLandId;
    }

    public void setMemberLandId(Long memberLandId) {
        this.memberLandId = memberLandId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

	public Long getMemberCropsId() {
		return memberCropsId;
	}

	public void setMemberCropsId(Long memberCropsId) {
		this.memberCropsId = memberCropsId;
	}
    
}