package com.smartfarm.base.shop.core.service;

import java.util.Map;



public interface OrderService {
	
	/**
	 * 生成订单
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> makeOrder(Long id) throws Exception;
	
	/**
	 * 支付成功
	 * @param id
	 */
	public void paySuccess(Long id);
	
	/**
	 * 主动取消订单
	 * @param id
	 */
	public void cleanOrder(Long id);
}
