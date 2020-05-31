package com.smartfarm.base.shop.core.entity;


public class ProductSku {
	
	/** 1 商城: 现金支付  + 邮寄  */
	public final static Short TYPE_SHOPPING = 1; 
	/** 2 积分: 鲜活值     + 邮寄  */
	public final static Short TYPE_INTEGRAL = 2;		
	/** 3 活动: 现金支付  + 自提  */
	public final static Short TYPE_ACTIVITY = 3; 
	
	/** 1 启用  */
	public final static Short STATUS_ENABLE = 1;
	/** 2 禁用  */
	public final static Short STATUS_DISABLE = 2;

    /** 0 不包邮  */
    public final static Short NO_FREE_POSTAGE = 0;
    /** 1 包邮  */
    public final static Short FREE_POSTAGE = 0;

    private Long id;
    /** 所属产品 */
    private Long productId;
    /** 销售价 */
    private Double price;
    /** 库存 */
    private Integer stock;
    /** 创建时间YYYYMMDDHHMMSS */
    private String createTime;
    /** 详细描述 */
    private String details;
    /** 规格 */
    private String norm;
    /** 产品SKU状态（1:'启用',2:'禁用'） */
    private Short status;
    /** 归属：1商城 2积分 3活动 */
    private Short type;
    /** 鲜活值 */
    private Integer point;
    /**重量*/
    private Double weight;
    /**是否包邮*/
    private Short freePostage;
    /**物流模板ID*/
    private Long  logisticsTemplateId;


    public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Short getFreePostage() {
        return freePostage;
    }

    public void setFreePostage(Short freePostage) {
        this.freePostage = freePostage;
    }

    public Long getLogisticsTemplateId() {
        return logisticsTemplateId;
    }

    public void setLogisticsTemplateId(Long logisticsTemplateId) {
        this.logisticsTemplateId = logisticsTemplateId;
    }

    @Override
    public String toString() {
        return "ProductSku{" +
                "id=" + id +
                ", productId=" + productId +
                ", price=" + price +
                ", stock=" + stock +
                ", createTime='" + createTime + '\'' +
                ", details='" + details + '\'' +
                ", norm='" + norm + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", point=" + point +
                ", weight=" + weight +
                ", freePostage=" + freePostage +
                ", logisticsTemplateId=" + logisticsTemplateId +
                '}';
    }
}