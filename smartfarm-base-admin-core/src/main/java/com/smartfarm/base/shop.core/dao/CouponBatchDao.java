package com.smartfarm.base.shop.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.CouponBatch;

public interface CouponBatchDao {
	
	/**
	 * 新增
	 * @param couponBatch
	 * @return
	 */
    public int insert(CouponBatch couponBatch);

    /**
     * 修改
     * @param couponBatch
     * @return
     */
    public int updateById(CouponBatch couponBatch);
    
    /**
     * 查询优惠批次列表
     * @return
     */
    public List<CouponBatch> queryCouponBatchList(@Param("start") Integer start,
                                                  @Param("limit") Integer limit,
                                                  @Param("type") Short type,
                                                  @Param("businessId") Long businessId);
    
    /**
     * 查询优惠批次数量
     * @return
     */
    public int queryCountCouponBatchList(@Param("type") Short type, @Param("businessId") Long businessId);
    
    /**
     * 根据id查看优惠券详细
     */
    public CouponBatch queryDetailBatchById(@Param("id") Long id);
    
    /**
	 * 查询奖品种类
	 * @return
	 */
	public List<CouponBatch> getPrizes(Long businessId);
}