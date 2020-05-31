package com.smartfarm.base.shop.core.entity;

public class MemberAddress {
    private Long id;

    private String name;

    private Long memberId;

    private String createTime;

    private String mobile;

    private String province;

    private String city;

    private String district;

    private String address;

    private Short status;

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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "MemberAddress [id=" + id + ", name=" + name + ", memberId="
				+ memberId + ", createTime=" + createTime + ", mobile="
				+ mobile + ", province=" + province + ", city=" + city
				+ ", district=" + district + ", address=" + address
				+ ", status=" + status + "]";
	}
    
    
}