package com.smartfarm.base.shop.core.entity.vo;

import com.smartfarm.base.shop.core.entity.ShopCart;

public class ShopCartVo extends ShopCart{
	
	public Short productStatus;
	
	public Double productPrice;
	
	public String productName;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Short getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(Short productStatus) {
		this.productStatus = productStatus;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}
	
}
