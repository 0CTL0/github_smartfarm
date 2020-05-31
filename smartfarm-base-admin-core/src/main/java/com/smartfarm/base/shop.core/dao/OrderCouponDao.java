package com.smartfarm.base.shop.core.dao;

import com.smartfarm.base.shop.core.entity.OrderCoupon;

public interface OrderCouponDao {
	
	/**
	 * 新增
	 * @param orderCoupon
	 * @return
	 */
    int insert(OrderCoupon orderCoupon);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateById(OrderCoupon record);
    
    /**
     * 根据订单id查询
     * @param orderId
     * @return
     */
    public OrderCoupon queryUsingByOrderId(Long orderId);
}