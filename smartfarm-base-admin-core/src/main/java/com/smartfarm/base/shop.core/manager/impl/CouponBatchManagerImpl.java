package com.smartfarm.base.shop.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import com.smartfarm.base.shop.core.entity.CouponBatchSend;
import org.springframework.stereotype.Service;

import com.smartfarm.base.shop.core.dao.CouponBatchDao;
import com.smartfarm.base.shop.core.entity.CouponBatch;
import com.smartfarm.base.shop.core.manager.CouponBatchManager;

@Service("couponBatchManager")
public class CouponBatchManagerImpl implements CouponBatchManager{

	@Resource
	private CouponBatchDao couponBatchDao;
	
	@Override
	public List<CouponBatch> queryCouponBatchList(Integer start,Integer limit,Short type, Long businessId) {
		return couponBatchDao.queryCouponBatchList(start,limit,type,businessId);
	}

	@Override
	public int queryCountCouponBatchList(Short type, Long businessId) {
		return couponBatchDao.queryCountCouponBatchList(type,businessId);
	}

	@Override
	public int insert(CouponBatch couponBatch) {
		return couponBatchDao.insert(couponBatch);
	}

	@Override
	public CouponBatch queryDetailBatchById(Long id) {
		return couponBatchDao.queryDetailBatchById(id);
	}

	@Override
	public int updateCouponBatch(CouponBatch couponBatch) {
		return couponBatchDao.updateById(couponBatch);
	}

}
