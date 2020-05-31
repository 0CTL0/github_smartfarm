package com.smartfarm.base.shop.core.entity;


public class ProductInfo {
	
	/** 1 启用  */
	public final static Short STATUS_ENABLE = 1; 
	/** 2 禁用  */
	public final static Short STATUS_DISABLE = 2;		
	
	
    private Long id;
    /** 产品名称 */
    private String name;
    /** 产品代码 */
    private String goodsSn;
    /** 所属类别 */
    private Long categoryId;
    /** 所属商家 */
    private Long businessId;
    /** 产品示例图 */
    private String picUrl;
    /** 产品简介 */
    private String brief;
    /** 产品状态（1:'启用',2:'禁用'） */
    private Short status;
    /** 排序 */
    private Integer sortOrder;
    /** 分享图 */
    private String shareUrl;
    /** 新品上架 */
    private Boolean isNew;
    /** 精品推荐 */
    private Boolean isHot;
    /** 单位 */
    private String unit;
    /** 创建时间 */
    private String createTime;
    /** 展示价格 */
    private Double showPrice;
    /** 展示鲜活值  */
    private Integer point;

    public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Boolean getIsHot() {
        return isHot;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Double getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(Double showPrice) {
        this.showPrice = showPrice;
    }
}