package com.smartfarm.base.shop.core.entity.vo;

import java.util.List;

import com.smartfarm.base.shop.core.entity.CouponBatchRule;
import com.smartfarm.base.shop.core.entity.CouponInfo;

public class CouponInfoVo extends CouponInfo{
	private String nickName;
	
	private List<CouponBatchRule> ruleList;
	
	private List<CouponBatchRuleVo> ruleVoList;

	public List<CouponBatchRuleVo> getRuleVoList() {
		return ruleVoList;
	}

	public void setRuleVoList(List<CouponBatchRuleVo> ruleVoList) {
		this.ruleVoList = ruleVoList;
	}

	public List<CouponBatchRule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<CouponBatchRule> ruleList) {
		this.ruleList = ruleList;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
