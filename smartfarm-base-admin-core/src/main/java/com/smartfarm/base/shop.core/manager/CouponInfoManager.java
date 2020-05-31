package com.smartfarm.base.shop.core.manager;

import java.util.List;

import com.smartfarm.base.shop.core.entity.CouponBatch;
import com.smartfarm.base.shop.core.entity.CouponBatchSend;
import com.smartfarm.base.shop.core.entity.CouponInfo;
import com.smartfarm.base.shop.core.entity.vo.CouponBatchSendVo;
import com.smartfarm.base.shop.core.entity.vo.CouponInfoVo;
import org.apache.ibatis.annotations.Param;

public interface CouponInfoManager {
	/**
	 * 根据优惠券批次id插入num条优惠券信息
	 * @param id
	 * @param num
	 * @return
	 */
	public int insertCouponInfoList(Long id, Integer num);
	
	/**
	 * 查询所有优惠券
	 * @param status
	 * @param nickName
	 * @param couponName
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<CouponInfoVo> queryCouponInfoList(Short status, String nickName, String couponName, Integer start, Integer limit,
                                                  Long businessId);
	
	/**
	 * 查询所有优惠券数量
	 * @param status
	 * @param nickName
	 * @param couponName
	 * @return
	 */
	public int queryCountCouponInfoList(Short status, String nickName, String couponName, Long businessId);
	
	/**
	 * 根据订单查询用户可用优惠券
	 * @param orderId
	 * @param memberId
	 * @return
	 */
	public List<CouponInfoVo> queryCouponListByOrderId(Long orderId, Long memberId);
	
	/**
     * 查询用户可用优惠券
     * @param memberId
     * @param nowTime
     * @return
     */
    public List<CouponInfoVo> queryUseableByMemberId(Long memberId, String nowTime);
    
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
    public List<CouponInfoVo> queryExpiredByMemberId(Long memberId, String nowTime);
    
    /**
     * 生成优惠券
     * @param memberId
     * @param couponBatchId
     * @param num
     * @return
     */
    public List<CouponInfo> createCouponForMember(Long memberId, Long couponBatchId, int num);
    
    /**
     * 更新优惠券
     * @param couponInfo
     */
    public void updateCouponInfo(CouponInfo couponInfo);

	/**
	 * 根据优惠码查询
	 * @param couponNo
	 * @return
	 */
	public CouponInfo queryCouponByCode(@Param("couponNo") String couponNo);

	/**
	 * 查询可用优惠券赠送活动
	 * @param businessId
	 * @return
	 */
	public List<CouponBatchSendVo> queryUseableCouponSend(Long businessId);

	/**
	 * 统计用户某个批次的优惠券
	 * @param couponBatchId
	 * @param memberId
	 * @return
	 */
	public Integer countCouponByUserIdAndBatchId(Long couponBatchId, Long memberId);


	/**
	 * 库存减一
	 * @param id
	 * @return
	 */
	public int updateRemainNumSend(Long id);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public CouponBatchSend querySendById(Long id);

	/**
	 * 分页查询
	 * @param businessId
	 * @return
	 */
	public List<CouponBatchSendVo> queryCouponSendPage(Long businessId, Integer start, Integer limit);

	/**
	 * 分页查询
	 * @param businessId
	 * @return
	 */
	public int countCouponSendPage(Long businessId);

	/**
	 * 根据批次id查询
	 * @param id
	 * @return
	 */
	public CouponBatchSend querySendByBatchId(Long id);

	/**
	 * 新增
	 * @param couponBatchSend
	 */
	public void insertCouponSend(CouponBatchSend couponBatchSend);
	/**
	 * 修改
	 * @param couponBatchSend
	 */
	public void updateCouponSendById(CouponBatchSend couponBatchSend);

	/**
	 * 查询奖品种类
	 * @return
	 */
	public List<CouponBatch> getPrizes(Long businessId);
}
