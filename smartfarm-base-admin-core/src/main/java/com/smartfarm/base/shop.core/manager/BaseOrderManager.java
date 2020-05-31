package com.smartfarm.base.shop.core.manager;

import java.util.List;

import com.smartfarm.base.shop.core.entity.BaseOrder;





public interface BaseOrderManager {
	
	/**
     * 根据id修改
     * @param id
     * @return
     */
    public int updateById(BaseOrder baseOrder);
    
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public BaseOrder queryById(Long id);
    
    /**
     * 查询未处理的订单
     * @return
     */
    public List<Long> queryOrderUnPay();
    
    /**
     * 根据类型和id查询
     * @param type
     * @param refId
     * @return
     */
    public BaseOrder queryByTypeAndRefId(Short type, Long refId);
}
