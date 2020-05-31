package com.smartfarm.base.shop.core.entity.vo;

import java.util.List;

import com.smartfarm.base.shop.core.entity.OrderInfo;
import com.smartfarm.base.shop.core.entity.OrderRefund;

public class OrderInfoVo extends OrderInfo{
	private String nick_name;//用户名
	private List<OrderDetailVo> orderDetailVoList;

	private OrderRefund orderRefund;

	public List<OrderDetailVo> getOrderDetailVoList() {
		return orderDetailVoList;
	}

	public void setOrderDetailVoList(List<OrderDetailVo> orderDetailVoList) {
		this.orderDetailVoList = orderDetailVoList;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public OrderRefund getOrderRefund() {
		return orderRefund;
	}

	public void setOrderRefund(OrderRefund orderRefund) {
		this.orderRefund = orderRefund;
	}
}
