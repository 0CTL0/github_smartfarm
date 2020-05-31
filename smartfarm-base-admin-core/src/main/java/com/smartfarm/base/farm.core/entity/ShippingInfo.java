package com.smartfarm.base.farm.core.entity;

public class ShippingInfo {
	//未发货
	public final static Short UNSHIP = 1; 
	//已发货
	public final static Short SHIPPING = 2; 
	//确认收货
	public final static Short RECEIVED= 3; 
	
    private Long id;

    private Long orderId;

    private Integer serial;

    private String shipName;

    private String shipNo;

    private String shipTime;

    private String address;

    private Short status;

    private Long adminUserId;

    private String updateTime;

    private String receiveName;

    private String receiveMobile;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipNo() {
        return shipNo;
    }

    public void setShipNo(String shipNo) {
        this.shipNo = shipNo;
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime;
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

    public Long getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveMobile() {
        return receiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

	@Override
	public String toString() {
		return "ShippingInfo [id=" + id + ", orderId=" + orderId + ", serial="
				+ serial + ", shipName=" + shipName + ", shipNo=" + shipNo
				+ ", shipTime=" + shipTime + ", address=" + address
				+ ", status=" + status + ", adminUserId=" + adminUserId
				+ ", updateTime=" + updateTime + ", receiveName=" + receiveName
				+ ", receiveMobile=" + receiveMobile + "]";
	}
    
    
    
}