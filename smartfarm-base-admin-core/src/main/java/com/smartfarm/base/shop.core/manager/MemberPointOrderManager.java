package com.smartfarm.base.shop.core.manager;

import com.smartfarm.base.shop.core.entity.MemberPointOrder;





public interface MemberPointOrderManager {

	/**
	 * 生成订单
	 * @param memberPointOrder
	 * @return
	 * @throws Exception
	 */
	public Boolean createOrder(MemberPointOrder memberPointOrder) throws Exception;
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public MemberPointOrder queryById(Long id);
	
	/**
	 * 根据id修改
	 * @param memberPointOrder
	 */
	public void updateById(MemberPointOrder memberPointOrder);
	
	/**
	 * 根据订单号查询
	 * @param orderNo
	 * @return
	 */
	public MemberPointOrder queryByOrderNo(String orderNo);
	
	/**
	 * 支付成功
	 * @param memberPointOrder
	 */
	public void orderSucess(MemberPointOrder memberPointOrder);
}
