package com.smartfarm.base.shop.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.CouponInfo;
import com.smartfarm.base.shop.core.entity.vo.CouponInfoVo;

public interface CouponInfoDao {
	
	/**
	 * 新增
	 * @param couponInfo
	 * @return
	 */
    public int insert(CouponInfo couponInfo);

    /**
     * 修改
     * @param couponInfo
     * @return
     */
    public int updateById(CouponInfo couponInfo);
    
    /**
     * 批量插入优惠券信息
     * @param list
     * @return
     */
    public int insertBatchInfoList(List<CouponInfo> list);
    
    /**
     * 查询所有优惠券
     * @param status
     * @param member_id
     * @param couponName
     * @param start
     * @param limit
     * @return
     */
    public List<CouponInfoVo> queryCouponInfoList(@Param("status") Short status, @Param("memberId") Long member_id,
                                                  @Param("couponName") String couponName, @Param("start") Integer start, @Param("limit") Integer limit,
                                                  @Param("businessId") Long businessId);
    
    /**
     * 查询所有优惠券数量
     * @param status
     * @param member_id
     * @param couponName
     * @return
     */
    public int queryCountCouponInfoList(@Param("status") Short status, @Param("memberId") Long member_id,
                                        @Param("couponName") String couponName, @Param("businessId") Long businessId);

    /**
     * 根据订单信息查询优惠券
     * @param memberId
     * @param price
     * @param ids
     * @return
     */
    public List<CouponInfoVo> queryByOrderInfo(@Param("memberId") Long memberId, @Param("price") Double price,
                                               @Param("ids") List<Long> ids, @Param("nowTime") String nowTime);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public CouponInfo queryById(Long id);
    
    /**
     * 查询用户可用优惠券
     * @param memberId
     * @param nowTime
     * @return
     */
    public List<CouponInfoVo> queryUseableByMemberId(@Param("memberId") Long memberId, @Param("nowTime") String nowTime);
    
    /**
     * 查询用户已用优惠券
     * @param memberId
     * @return
     */
    public List<CouponInfoVo> queryUsedByMemberId(Long memberId);
    
    /**
     * 查询用户过期优惠券
     * @param memberId
     * @param nowTime
     * @return
     */
    public List<CouponInfoVo> queryExpiredByMemberId(@Param("memberId") Long memberId, @Param("nowTime") String nowTime);

    /**
     * 根据优惠码查询
     * @param couponNo
     * @return
     */
    public CouponInfo queryCouponByCode(@Param("couponNo") String couponNo);

    /**
     * 统计用户某个批次的优惠券
     * @param couponBatchId
     * @param memberId
     * @return
     */
    public Integer countCouponByUserIdAndBatchId(@Param("couponBatchId") Long couponBatchId, @Param("memberId") Long memberId);
}