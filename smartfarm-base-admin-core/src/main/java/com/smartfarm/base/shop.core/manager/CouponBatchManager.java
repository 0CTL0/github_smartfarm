package com.smartfarm.base.shop.core.manager;

import java.util.List;

import com.smartfarm.base.shop.core.entity.CouponBatch;
import com.smartfarm.base.shop.core.entity.CouponBatchSend;

public interface CouponBatchManager {
	/**
     * 查询优惠批次列表
     * @return
     */
    public List<CouponBatch> queryCouponBatchList(Integer start, Integer limit, Short type, Long businessId);
    
    /**
     * 查询优惠批次数量
     * @return
     */
    public int queryCountCouponBatchList(Short type, Long businessId);
    /**
	 * 新增
	 * @param couponBatch
	 * @return
	 */
    public int insert(CouponBatch couponBatch);
    /**
     * 根据id查看优惠券详细
     */
    public CouponBatch queryDetailBatchById(Long id);
    
    /**
     * 更新优惠券批次
     * @param couponBatch
     * @return
     */
    public int updateCouponBatch(CouponBatch couponBatch);

}
