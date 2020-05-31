package com.smartfarm.base.farm.core.entity;

public class FarmRentLandOrder {
    /**订单状态：0未支付 1已支付 2超时 3取消*/
    public final static Short STATUS_UNPAY=0;
    public final static Short STATUS_PAYED=1;
    public final static Short STATUS_OVERTIME=2;
    public final static Short STATUS_CANCEL=3;

    /**支付状态：0未支付 1支付中  2已支付  3退款 */
    public final static Short PAYSTATUS_UNPAY=0;
    public final static Short PAYSTATUS_ONGOING=1;
    public final static Short PAYSTATUS_PAYED=2;
    public final static Short PAYSTATUS_REFUND=3;

    /**订单超时的时间，单位分钟**/
    public final static Integer TIME_OUT=10;

    private Long id;

    private Long memberId;

    private String orderCode;

    private String rentDate;

    private String rentTime;

    private Long rentLandId;

    private Double price;

    private Short quantity;

    private Double totalAmount;

    private Double realAmount;

    private Short status;

    private String rentName;

    private String rentMobile;

    private String payTime;

    private String payNo;

    private Short payStatus;

    private Short payType;
    
    private String payEndTime;

    public String getPayEndTime() {
		return payEndTime;
	}

	public void setPayEndTime(String payEndTime) {
		this.payEndTime = payEndTime;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }

    public Long getRentLandId() {
        return rentLandId;
    }

    public void setRentLandId(Long rentLandId) {
        this.rentLandId = rentLandId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Double realAmount) {
        this.realAmount = realAmount;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getRentName() {
        return rentName;
    }

    public void setRentName(String rentName) {
        this.rentName = rentName;
    }

    public String getRentMobile() {
        return rentMobile;
    }

    public void setRentMobile(String rentMobile) {
        this.rentMobile = rentMobile;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public Short getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Short payStatus) {
        this.payStatus = payStatus;
    }

    public Short getPayType() {
        return payType;
    }

    public void setPayType(Short payType) {
        this.payType = payType;
    }


    @Override
    public String toString() {
        return "FarmRentLandOrder{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", orderCode='" + orderCode + '\'' +
                ", rentDate='" + rentDate + '\'' +
                ", rentTime='" + rentTime + '\'' +
                ", rentLandId=" + rentLandId +
                ", price=" + price +
                ", quantity=" + quantity +
                ", totalAmount=" + totalAmount +
                ", realAmount=" + realAmount +
                ", status=" + status +
                ", rentName='" + rentName + '\'' +
                ", rentMobile='" + rentMobile + '\'' +
                ", payTime='" + payTime + '\'' +
                ", payNo='" + payNo + '\'' +
                ", payStatus=" + payStatus +
                ", payType=" + payType +
                '}';
    }
}