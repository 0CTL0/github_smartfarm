package com.smartfarm.base.admin.core.entity;

public class FarmInfo {

	private Long id;// 流水号
	private String name;// 农场名
	private String address;// 地址
	private Long longitude;// 经度
	private Long latitude;// 纬度
	private String brief;// 简介
	private String pic;// 图片
	private Short status;//状态
	private String contractUrl;//农场租赁协议图

	/**启用*/
	public static final Short STATUS_ABLE=1;
	/**禁用*/
	public static final Short STATUS_ENALBE=2;

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getLongitude() {
		return longitude;
	}

	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}

	public Long getLatitude() {
		return latitude;
	}

	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getContractUrl() {
		return contractUrl;
	}

	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}

	@Override
	public String toString() {
		return "FarmInfo{" +
				"id=" + id +
				", name='" + name + '\'' +
				", address='" + address + '\'' +
				", longitude=" + longitude +
				", latitude=" + latitude +
				", brief='" + brief + '\'' +
				", pic='" + pic + '\'' +
				", status=" + status +
				", contractUrl='" + contractUrl + '\'' +
				'}';
	}
}