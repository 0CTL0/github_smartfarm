package com.smartfarm.base.shop.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.shop.core.dao.BaseOrderDao;
import com.smartfarm.base.shop.core.entity.BaseOrder;
import com.smartfarm.base.shop.core.manager.BaseOrderManager;


@Service("baseOrderManager")
public class BaseOrderManagerImpl implements BaseOrderManager {
	@Resource
	private BaseOrderDao baseOrderDao;

	@Override
	public int updateById(BaseOrder baseOrder) {
		return baseOrderDao.updateById(baseOrder);
	}

	@Override
	public BaseOrder queryById(Long id) {
		return baseOrderDao.queryById(id);
	}

	@Override
	public List<Long> queryOrderUnPay() {
		return baseOrderDao.queryOrderUnPay();
	}

	@Override
	public BaseOrder queryByTypeAndRefId(Short type, Long refId) {
		return baseOrderDao.queryByTypeAndRefId(type, refId);
	}

	
}