package com.smartfarm.base.shop.core.entity.vo;

import com.smartfarm.base.shop.core.entity.ProductCategory;

import java.util.List;

public class ProductCategoryVo extends ProductCategory{
	
	private String parentName;
	private List<ProductCategory> childCategoryList;	//二级子分类

	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<ProductCategory> getChildCategoryList() {
		return childCategoryList;
	}

	public void setChildCategoryList(List<ProductCategory> childCategoryList) {
		this.childCategoryList = childCategoryList;
	}

}
