package com.smartfarm.base.farm.core.entity;

public class FarmDelegateExpress {

    /**未邮寄*/
    public static final Short STATUS_UNSHIPPED=0;
    /**已邮寄*/
    public static final Short STATUS_SHIPPED=1;

    public static final short STATUS_TO_BE_POSTAL = 0;
    public static final short STATUS_POSTALED = 1;

    private Long id;
    private Long delegateOrderId;
    private Long memberCropsId;
    private Short weight;
    private String shopName;
    private String shopNo;
    private String shopTime;
    private String address;
    private String receiveName;
    private String receiveMobile;
    private Short status;

    private Long bazaarOrderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDelegateOrderId() {
        return delegateOrderId;
    }

    public void setDelegateOrderId(Long delegateOrderId) {
        this.delegateOrderId = delegateOrderId;
    }

    public Long getMemberCropsId() {
        return memberCropsId;
    }

    public void setMemberCropsId(Long memberCropsId) {
        this.memberCropsId = memberCropsId;
    }

    public Short getWeight() {
        return weight;
    }

    public void setWeight(Short weight) {
        this.weight = weight;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getShopTime() {
        return shopTime;
    }

    public void setShopTime(String shopTime) {
        this.shopTime = shopTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Long getBazaarOrderId() {
        return bazaarOrderId;
    }

    public void setBazaarOrderId(Long bazaarOrderId) {
        this.bazaarOrderId = bazaarOrderId;
    }
}