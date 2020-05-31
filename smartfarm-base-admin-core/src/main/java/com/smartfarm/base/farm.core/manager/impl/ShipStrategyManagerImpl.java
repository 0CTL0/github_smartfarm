package com.smartfarm.base.farm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.dao.ShipStrategyDao;
import com.smartfarm.base.farm.core.entity.ShipStrategy;
import com.smartfarm.base.farm.core.manager.ShipStrategyManager;

@Service("shipStrategyManager")
public class ShipStrategyManagerImpl implements ShipStrategyManager{

	@Resource
	private ShipStrategyDao shipStrategyDao;
	
	@Override
	public List<ShipStrategy> queryShipStrategyByLandId(Long landId) {
		return shipStrategyDao.queryShipStrategyByLandId(landId);
	}

}
