package com.smartfarm.base.farm.core.manager;

import java.util.List;

import com.smartfarm.base.farm.core.entity.ShippingInfo;

public interface ShipInfoManager {
	/**
     * 根据订单id查询物流配送信息
     * @param orderId
     * @return
     */
    public List<ShippingInfo> queryShipInfoByOrderId(Long orderId);
    
    /**
     * 根据id查询物流详细
     * @param id
     * @return
     */
    public ShippingInfo queryShipDetailById(Long id);
}
