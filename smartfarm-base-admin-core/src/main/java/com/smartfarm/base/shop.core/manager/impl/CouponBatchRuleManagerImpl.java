package com.smartfarm.base.shop.core.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.shop.core.dao.CouponBatchRuleDao;
import com.smartfarm.base.shop.core.entity.CouponBatchRule;
import com.smartfarm.base.shop.core.manager.CouponBatchRuleManager;

@Service("/couponBatchRuleManager")
public class CouponBatchRuleManagerImpl implements CouponBatchRuleManager{

	@Resource
	private CouponBatchRuleDao couponBatchRuleDao;

	@Override
	public int add(Long couponBatchId, Short ruleType, String productSkuListJson) {
		// 解析productSkuListJson
		String[] strs = productSkuListJson.split(",");
		for (String s : strs) {
			CouponBatchRule rule = new CouponBatchRule();
			rule.setCouponBatchId(couponBatchId);
			rule.setParamValue(s);
			rule.setRuleType(ruleType);
			couponBatchRuleDao.insert(rule);
		}
		return strs.length;
	}

	@Override
	public int addAllBatchRules(Long couponBatchId, Short ruleType) {
		CouponBatchRule rule = new CouponBatchRule();
		rule.setCouponBatchId(couponBatchId);
		rule.setRuleType(ruleType);
		rule.setParamValue("all");
		return couponBatchRuleDao.insert(rule);
	}
}
