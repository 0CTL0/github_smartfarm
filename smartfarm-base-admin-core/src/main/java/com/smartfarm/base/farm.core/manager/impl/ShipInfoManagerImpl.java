package com.smartfarm.base.farm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.dao.ShippingInfoDao;
import com.smartfarm.base.farm.core.entity.ShippingInfo;
import com.smartfarm.base.farm.core.manager.ShipInfoManager;

@Service("shipInfoManager")
public class ShipInfoManagerImpl implements ShipInfoManager{

	@Resource
	private ShippingInfoDao shippingInfoDao;
	
	@Override
	public List<ShippingInfo> queryShipInfoByOrderId(Long orderId) {
		return shippingInfoDao.queryShipInfoByOrderId(orderId);
	}

	@Override
	public ShippingInfo queryShipDetailById(Long id) {
		return shippingInfoDao.queryShipDetailById(id);
	}

}
