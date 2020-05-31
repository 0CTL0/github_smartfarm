package com.smartfarm.base.shop.core.entity;

public class ProductSkuImg {
	/*主图*/
	public final static Short MAINIMG = 1; 
	/*详情图*/
	public final static Short DETAILIMG = 2; 
	
    private Long id;

    //private Long productSkuId;
    private Long productId;
	/*1代表主图  2代表详情图*/
    private Short type;

    private String picUrl;

    private String createTime;
    
    public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}