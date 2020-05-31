package com.smartfarm.base.shop.core.entity;

public class ProductCategory {
	
	/** 等级一  */
	public final static Integer LEVEL_FIRST = 1; 
	/** 等级二  */
	public final static Integer LEVEL_SECOND = 2;	
	
	/** 1 启用  */
	public final static Short STATUS_ENABLE = 1; 
	/** 2 禁用  */
	public final static Short STATUS_DISABLE = 2;	
	
    private Long id;
    /** 产品分类名  */
    private String name;
    /** 上级分类    */
    private Long parentId;
    /** 产品分类图标名  */
    private String iconUrl;
    /** 产品分类等级（支持两级）  */
    private Integer level;
    /** 排序  */
    private Integer sortOrder;
    /** 创建时间  */
    private String createTime;
    /** 状态（1:'启用',2:'禁用'）  */
    private Short status;
    
    private Long businessId;

    public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public void ProductCategory(){

    }
}