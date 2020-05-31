package com.smartfarm.base.shop.core.entity;

public class ProductSkuAttribute {
	
    private Long id;
    /** 属性名称  */
    private String attribute;
    /** 属性值  */
    private String attValue;
    /** 所属SKU  */
    private Long productSkuId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttValue() {
        return attValue;
    }

    public void setAttValue(String attValue) {
        this.attValue = attValue;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }
}