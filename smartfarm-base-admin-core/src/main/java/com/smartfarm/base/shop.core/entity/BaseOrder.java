package com.smartfarm.base.shop.core.entity;

public class BaseOrder {
	
	/** 商城订单类型  */
	public static short BASE_ORDER_TYPE_ORDER =  1;
	/** 活动订单类型 */
	public static short BASE_ORDER_TYPE_ACTIVITY =  2;
	/** 地块租赁类型 */
	public static short BASE_ORDER_TYPE_RENT =  3;
	/** 自由贸易类型 */
	public static short BASE_ORDER_TYPE_FARMBAZAAR =  4;
	/** 会员积分充值类型 */
	public static short BASE_ORDER_TYPE_POINT =  5;

	public static short BASE_ORDER_TYPE_CROW = 6;
	
	/** 初始状态 */
	public static short BASE_ORDER_STATUS_INIT = 1;
	/** 失效状态 */
	public static short BASE_ORDER_STATUS_UNLOCK = 2;
	
    private Long id;

    private Short type;

    private Long refId;
    
    private Short status;

    public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
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

    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }
}