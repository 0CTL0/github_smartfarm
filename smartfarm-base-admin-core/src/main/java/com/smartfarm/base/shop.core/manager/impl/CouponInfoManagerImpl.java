package com.smartfarm.base.shop.core.manager.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.smartfarm.base.shop.core.dao.*;
import com.smartfarm.base.shop.core.entity.*;
import com.smartfarm.base.shop.core.entity.vo.CouponBatchSendVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.util.SerialNumberUtil;
import com.smartfarm.base.shop.core.entity.vo.CouponInfoVo;
import com.smartfarm.base.shop.core.manager.CouponInfoManager;

@Service("couponInfoManager")
public class CouponInfoManagerImpl implements CouponInfoManager{
	
	@Resource
	private CouponBatchDao couponBatchDao;
	@Resource
	private CouponInfoDao couponInfoDao;
	@Resource
	private MemberInfoDao memberInfoDao;
	@Resource
	private OrderInfoDao orderInfoDao;
	@Resource
	private OrderDetailDao orderDetailDao;
	@Resource
	private OrderProductDao orderProductDao;
	@Resource
	private CouponBatchRuleDao couponBatchRuleDao;
	@Resource
	private CouponBatchSendDao couponBatchSendDao;
	
	
	@Override
	public int insertCouponInfoList(Long id, Integer num) {
		CouponBatch batch = couponBatchDao.queryDetailBatchById(id);
//		List<CouponInfo> infos = new ArrayList<CouponInfo>();
		for(int i=0;i<num;i++){
			CouponInfo couponInfo = new CouponInfo();
			couponInfo.setCouponBatchId(id);
			couponInfo.setCouponName(batch.getDisplayName());
			couponInfo.setDescription(batch.getDiscretion());
			couponInfo.setStatus((short)0);
			if(batch.getAmount()!=null){
				couponInfo.setAmount(batch.getAmount());
			}
			
			if(batch.getReductPrice()!=null){
				couponInfo.setReductPrice(batch.getReductPrice());
			}
			if(batch.getDiscount()!=null){
				couponInfo.setDiscount(batch.getDiscount());
			}
			couponInfo.setCreateTime(DateUtil.formatCurrentDateTime());
			if(batch.getStrategyType() == 1) {
				try {
					couponInfo.setEndTime(DateUtil.formatAddDate(DateUtil.formatCurrentDate(), batch.getDayCount()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else if(batch.getStrategyType() == 2) {
				couponInfo.setEndTime(batch.getDeadline());
			}
			couponInfo.setType(batch.getType());
			couponInfo.setUseTime(batch.getUseTime());
			
			couponInfoDao.insert(couponInfo);
			couponInfo.setCouponNo(SerialNumberUtil.toSerialNumber(couponInfo.getId(), 6));
			couponInfoDao.updateById(couponInfo);
		}
		return num;
	}

	@Override
	public List<CouponInfoVo> queryCouponInfoList(Short status,
			String nickName, String couponName, Integer start, Integer limit,Long businessId) {
		MemberInfo info = memberInfoDao.queryByNickName(nickName,businessId);
		Long memberId = null;
		if(info != null){
			memberId = info.getId();
		}
		return couponInfoDao.queryCouponInfoList(status, memberId, couponName, start, limit, businessId);
	}

	@Override
	public int queryCountCouponInfoList(Short status, String nickName,
			String couponName,Long businessId) {
		MemberInfo info = memberInfoDao.queryByNickName(nickName,businessId);
		Long memberId = null;
		if(info != null){
			memberId = info.getId();
		}
		return couponInfoDao.queryCountCouponInfoList(status, memberId, couponName, businessId);
	}

	@Override
	public List<CouponInfoVo> queryCouponListByOrderId(Long orderId, Long memberId) {
		OrderInfo orderInfo = orderInfoDao.queryById(orderId);
		List<OrderProduct> productList = orderProductDao.queryByOrderId(orderId);
		List<CouponInfoVo> list = new ArrayList<CouponInfoVo>();
		//查询满足产品的优惠券
		List<Long> ids = new ArrayList<Long>();
		for(OrderProduct orderProduct:productList) {
			ids.add(orderProduct.getProductSkuId());
		}
		List<CouponInfoVo> couponList = couponInfoDao.queryByOrderInfo(memberId, orderInfo.getTotalPrice(), ids,DateUtil.formatCurrentDateTime());
		for(CouponInfoVo vo : couponList) {
			List<CouponBatchRule> ruleList = couponBatchRuleDao.queryByBatchId(vo.getCouponBatchId());
			vo.setRuleList(ruleList);
			Boolean allFlag = false;//标记是否全局产品
			List<Long> skuIdList = new ArrayList<Long>(); //记录优惠产品id
			for(CouponBatchRule couponBatchRule:ruleList) {
				if(couponBatchRule.getRuleType() == 1) {//sku
					if(couponBatchRule.getParamValue().equals("all")) {
						allFlag = true;
					}else {
						skuIdList.add(Long.valueOf(couponBatchRule.getParamValue()));
					}
				}
			}
			if(allFlag) {
				list.add(vo);
			} else if(checkOrderCoupon(skuIdList, vo, orderInfo)) {
				list.add(vo);
			}
		}
		return list;
	}

	private Boolean checkOrderCoupon(List<Long> skuIdList, CouponInfo couponInfo, OrderInfo orderInfo) {
		Double allPrice = 0D;
		List<OrderProduct> productList = orderProductDao.queryByOrderId(orderInfo.getId());
		for(OrderProduct orderProduct:productList) {
			if(skuIdList.contains(orderProduct.getProductSkuId())) {
				allPrice = allPrice + orderProduct.getPrice() * orderProduct.getQuantity();
			}
		}
		if(allPrice >= couponInfo.getReductPrice()) {
			return true;
		}
		return false;
	}

	@Override
	public List<CouponInfoVo> queryUseableByMemberId(Long memberId,
			String nowTime) {
		List<CouponInfoVo> list = couponInfoDao.queryUseableByMemberId(memberId, nowTime);
		for(CouponInfoVo vo : list) {
			vo.setRuleVoList(couponBatchRuleDao.queryVoByBatchId(vo.getCouponBatchId()));
		}
		return list;
	}

	@Override
	public List<CouponInfoVo> queryUsedByMemberId(Long memberId) {
		List<CouponInfoVo> list = couponInfoDao.queryUsedByMemberId(memberId);
		for(CouponInfoVo vo : list) {
			vo.setRuleVoList(couponBatchRuleDao.queryVoByBatchId(vo.getCouponBatchId()));
		}
		return list;
	}

	@Override
	public List<CouponInfoVo> queryExpiredByMemberId(Long memberId,
			String nowTime) {
		List<CouponInfoVo> list = couponInfoDao.queryExpiredByMemberId(memberId, nowTime);
		for(CouponInfoVo vo : list) {
			vo.setRuleVoList(couponBatchRuleDao.queryVoByBatchId(vo.getCouponBatchId()));
		}
		return list;
	}

	@Override
	public List<CouponInfo> createCouponForMember(Long memberId,
			Long couponBatchId, int num) {
		List<CouponInfo> list = new ArrayList<CouponInfo>();
		CouponBatch couponBatch = couponBatchDao.queryDetailBatchById(couponBatchId);
		
		for(int i = 0;i < num;i++) {
			CouponInfo couponInfo = new CouponInfo();
			couponInfo.setAmount(couponBatch.getAmount());
			couponInfo.setCouponBatchId(couponBatchId);
			couponInfo.setCouponName(couponBatch.getDisplayName());
			couponInfo.setCouponNo(RandomUtil.randomStr(6));
			couponInfo.setCreateTime(DateUtil.formatCurrentDateTime());
			couponInfo.setDescription(couponBatch.getDiscretion());
			couponInfo.setDiscount(couponBatch.getDiscount());
			if(couponBatch.getStrategyType() == 1) {
				couponInfo.setEndTime(DateUtil.formatAddDateTime(couponBatch.getDayCount()));
			}else {
				couponInfo.setEndTime(couponBatch.getDeadline());
			}
			couponInfo.setMemberId(memberId);
			couponInfo.setReductPrice(couponBatch.getReductPrice());
			couponInfo.setStatus((short)1);
			couponInfo.setType(couponBatch.getType());
			couponInfoDao.insert(couponInfo);
			list.add(couponInfo);
		}
		return list;
	}

	@Override
	public void updateCouponInfo(CouponInfo couponInfo) {
		couponInfoDao.updateById(couponInfo);
	}

	/**
	 * 根据优惠码查询
	 * @param couponNo
	 * @return
	 */
	public CouponInfo queryCouponByCode(@Param("couponNo")String couponNo) {
		return couponInfoDao.queryCouponByCode(couponNo);
	}

	public List<CouponBatchSendVo> queryUseableCouponSend(Long businessId) {
		return couponBatchSendDao.queryUseableSend(businessId, DateUtil.formatCurrentDateTime());
	}

	public Integer countCouponByUserIdAndBatchId(Long couponBatchId, Long memberId) {
		return couponInfoDao.countCouponByUserIdAndBatchId(couponBatchId,memberId);
	}

	public int updateRemainNumSend(Long id) {
		return couponBatchSendDao.updateRemainNumSend(id);
	}

	public CouponBatchSend querySendById(Long id) {
		return couponBatchSendDao.querySendById(id);
	}

	public List<CouponBatchSendVo> queryCouponSendPage(Long businessId,Integer start,Integer limit) {
		return couponBatchSendDao.queryCouponSendPage(businessId, start, limit);
	}

	public int countCouponSendPage(Long businessId) {
		return couponBatchSendDao.countCouponSendPage(businessId);
	}

	public CouponBatchSend querySendByBatchId(Long id) {
		return couponBatchSendDao.querySendByBatchId(id);
	}

	public void insertCouponSend(CouponBatchSend couponBatchSend) {
		couponBatchSendDao.insert(couponBatchSend);
	}

	public void updateCouponSendById(CouponBatchSend couponBatchSend) {
		couponBatchSendDao.updateById(couponBatchSend);
	}


	public List<CouponBatch> getPrizes(Long businessId) {
		return couponBatchDao.getPrizes(businessId);
	}
}
