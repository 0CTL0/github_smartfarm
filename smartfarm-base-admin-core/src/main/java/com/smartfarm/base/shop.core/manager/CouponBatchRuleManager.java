package com.smartfarm.base.shop.core.manager;


public interface CouponBatchRuleManager {
	
	/**
	 * 新增
	 * @param couponBatchRule
	 * @return
	 */
	public int add(Long couponBatchId, Short ruleType, String productSkuListJson);

	public int addAllBatchRules(Long couponBatchId, Short ruleType);
}
