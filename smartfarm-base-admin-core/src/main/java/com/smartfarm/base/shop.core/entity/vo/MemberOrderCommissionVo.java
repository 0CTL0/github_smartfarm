package com.smartfarm.base.shop.core.entity.vo;

import com.smartfarm.base.shop.core.entity.MemberInfo;
import com.smartfarm.base.shop.core.entity.MemberOrderCommission;
import com.smartfarm.base.shop.core.entity.OrderInfo;

public class MemberOrderCommissionVo extends MemberOrderCommission {
    private OrderInfo orderInfo;             //分销订单
    private MemberInfo orderMember;         //下单用户

    private String orderMemberName;          //下单用户昵称
    private String commissionMemberName;    //佣金用户昵称
    private String orderNo;                 //订单号
    private Double totalPrice;              //订单金额


    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public MemberInfo getOrderMember() {
        return orderMember;
    }

    public void setOrderMember(MemberInfo orderMember) {
        this.orderMember = orderMember;
    }

    public String getOrderMemberName() {
        return orderMemberName;
    }

    public void setOrderMemberName(String orderMemberName) {
        this.orderMemberName = orderMemberName;
    }

    public String getCommissionMemberName() {
        return commissionMemberName;
    }

    public void setCommissionMemberName(String commissionMemberName) {
        this.commissionMemberName = commissionMemberName;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
