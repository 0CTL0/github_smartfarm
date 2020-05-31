package com.smartfarm.base.shop.core.entity.vo;

import java.util.List;

import com.smartfarm.base.shop.core.entity.ProductInfo;
import com.smartfarm.base.shop.core.entity.ProductSkuImg;

public class ProductInfoVo extends ProductInfo{
	private String categoryName;
	private String businessName;
	private Double minSkuPrice;
	private List<ProductSkuVo> productSkuList;
	private List<ProductSkuImg> mainPicList;
	private List<ProductSkuImg> detailPicList;


	public List<ProductSkuVo> getProductSkuList() {
		return productSkuList;
	}

	public void setProductSkuList(List<ProductSkuVo> productSkuList) {
		this.productSkuList = productSkuList;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public List<ProductSkuImg> getMainPicList() {
		return mainPicList;
	}

	public void setMainPicList(List<ProductSkuImg> mainPicList) {
		this.mainPicList = mainPicList;
	}

	public List<ProductSkuImg> getDetailPicList() {
		return detailPicList;
	}

	public void setDetailPicList(List<ProductSkuImg> detailPicList) {
		this.detailPicList = detailPicList;
	}

	public Double getMinSkuPrice() {
		return minSkuPrice;
	}

	public void setMinSkuPrice(Double minSkuPrice) {
		this.minSkuPrice = minSkuPrice;
	}
}
