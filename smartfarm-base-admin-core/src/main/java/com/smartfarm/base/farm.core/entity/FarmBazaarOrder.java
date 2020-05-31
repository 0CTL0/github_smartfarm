package com.smartfarm.base.farm.core.entity;

import java.math.BigDecimal;

public class FarmBazaarOrder {

    /**订单状态：0未支付 1已支付(待发货) 2超时 3取消 4待收货 5确认收货 */
    public final static Short ORDERSTATUS_UNPAY=0;
    public final static Short ORDERSTATUS_PAYED=1;
    public final static Short ORDERSTATUS_OVERTIME=2;
    public final static Short ORDERSTATUS_CANCEL=3;
    public final static Short ORDERSTATUS_SHIPPING=4;
    public final static Short ORDERSTATUS_RECEIVED=5;

    /**支付状态：0未支付 1支付中  2已支付  3退款 */
    public final static Short PAYSTATUS_UNPAY=0;
    public final static Short PAYSTATUS_ONGOING=1;
    public final static Short PAYSTATUS_PAYED=2;
    public final static Short PAYSTATUS_REFUND=3;


    private Long id;

    private Long memberSellerId;

    private Long memberBuyerId;

    private String orderCode;

    private Long bazaarProductId;

    private Integer number;

    private Double amount;

    private Short orderStatus;

    private String receiveName;

    private String receivePhone;

    private String receiveAddress;

    private String payNo;

    private String createTime;

    private String payTime;

    private Short payStatus;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberSellerId() {
        return memberSellerId;
    }

    public void setMemberSellerId(Long memberSellerId) {
        this.memberSellerId = memberSellerId;
    }

    public Long getMemberBuyerId() {
        return memberBuyerId;
    }

    public void setMemberBuyerId(Long memberBuyerId) {
        this.memberBuyerId = memberBuyerId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getBazaarProductId() {
        return bazaarProductId;
    }

    public void setBazaarProductId(Long bazaarProductId) {
        this.bazaarProductId = bazaarProductId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Short getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Short orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public Short getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Short payStatus) {
        this.payStatus = payStatus;
    }

    @Override
    public String toString() {
        return "FarmBazaarOrder{" +
                "id=" + id +
                ", memberSellerId=" + memberSellerId +
                ", memberBuyerId=" + memberBuyerId +
                ", orderCode='" + orderCode + '\'' +
                ", bazaarProductId=" + bazaarProductId +
                ", number=" + number +
                ", amount=" + amount +
                ", orderStatus=" + orderStatus +
                ", receiveName='" + receiveName + '\'' +
                ", receivePhone='" + receivePhone + '\'' +
                ", receiveAddress='" + receiveAddress + '\'' +
                ", payNo='" + payNo + '\'' +
                ", createTime='" + createTime + '\'' +
                ", payTime='" + payTime + '\'' +
                ", payStatus=" + payStatus +
                '}';
    }
}