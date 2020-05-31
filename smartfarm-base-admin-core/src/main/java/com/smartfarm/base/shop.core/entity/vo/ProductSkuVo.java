package com.smartfarm.base.shop.core.entity.vo;

import java.util.List;

import com.smartfarm.base.shop.core.entity.ProductSku;
import com.smartfarm.base.shop.core.entity.ProductSkuAttribute;
import com.smartfarm.base.shop.core.entity.ProductSkuCommission;
import com.smartfarm.base.shop.core.entity.ProductSkuImg;

public class ProductSkuVo extends ProductSku {
	private String productName;
	private Short ruleType;
	private String paramValue;
	private String templateName;

	private ProductSkuCommission productSkuCommission;
	
	private List<ProductSkuImg> imgList;
	
	private List<ProductSkuAttribute> attributeList;



	public List<ProductSkuAttribute> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(List<ProductSkuAttribute> attributeList) {
		this.attributeList = attributeList;
	}

	public List<ProductSkuImg> getImgList() {
		return imgList;
	}

	public void setImgList(List<ProductSkuImg> imgList) {
		this.imgList = imgList;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Short getRuleType() {
		return ruleType;
	}

	public void setRuleType(Short ruleType) {
		this.ruleType = ruleType;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public ProductSkuCommission getProductSkuCommission() {
		return productSkuCommission;
	}

	public void setProductSkuCommission(ProductSkuCommission productSkuCommission) {
		this.productSkuCommission = productSkuCommission;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
}
