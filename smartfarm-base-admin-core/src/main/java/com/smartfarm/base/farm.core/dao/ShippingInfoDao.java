package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.ShippingInfo;

public interface ShippingInfoDao {
	
	/**
	 * 新增
	 * @param shippingInfo
	 * @return
	 */
    public int insert(ShippingInfo shippingInfo);

    /**
     * 修改
     * @param shippingInfo
     * @return
     */
    public int updateById(ShippingInfo shippingInfo);
    /**
     * 根据id查询
     * @param shippingInfo
     * @return
     */
    public List<ShippingInfo> selectByRentOrderId(ShippingInfo shippingInfo);   
    /**
     * 批量插入配送信息
     * @param easyTaskStepList
     * @return
     */
    public int batchInsertShippingInfo(List<ShippingInfo> ShippingInfo);
    
    /**
     * 根据订单id查询物流配送信息
     * @param orderId
     * @return
     */
    public List<ShippingInfo> queryShipInfoByOrderId(@Param("orderId") Long orderId);
    
    /**
     * 根据id查询物流详细
     * @param id
     * @return
     */
    public ShippingInfo queryShipDetailById(@Param("id") Long id);
}