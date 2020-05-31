package com.smartfarm.base.shop.core.dao;

import java.util.List;

import com.smartfarm.base.shop.core.entity.CouponBatchRule;
import com.smartfarm.base.shop.core.entity.vo.CouponBatchRuleVo;

public interface CouponBatchRuleDao {

	/**
	 * 新增
	 * @param couponBatchRule
	 * @return
	 */
    public int insert(CouponBatchRule couponBatchRule);

    /**
     * 修改
     * @param couponBatchRule
     * @return
     */
    public int updateById(CouponBatchRule couponBatchRule);
    
    /**
     * 根据批次查询规则
     * @param couponBatchId
     * @return
     */
    public List<CouponBatchRule> queryByBatchId(Long couponBatchId);
    
    /**
     * 查询规则
     * @param couponBatchId
     * @return
     */
    public List<CouponBatchRuleVo> queryVoByBatchId(Long couponBatchId);

}