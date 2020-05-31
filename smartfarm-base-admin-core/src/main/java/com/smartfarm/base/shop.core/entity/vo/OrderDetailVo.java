package com.smartfarm.base.shop.core.entity.vo;

import java.util.List;

import com.smartfarm.base.shop.core.entity.OrderDetail;
import com.smartfarm.base.shop.core.entity.OrderProduct;

public class OrderDetailVo extends OrderDetail {

	private List<OrderProduct> productList;

	public List<OrderProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<OrderProduct> productList) {
		this.productList = productList;
	}
}
