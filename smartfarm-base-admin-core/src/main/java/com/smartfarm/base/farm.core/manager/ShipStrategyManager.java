package com.smartfarm.base.farm.core.manager;

import java.util.List;

import com.smartfarm.base.farm.core.entity.ShipStrategy;

public interface ShipStrategyManager {
	/**
     * 根据土地id获取配送周期信息
     * @param landId
     * @return
     */
    public List<ShipStrategy> queryShipStrategyByLandId(Long landId);
}
