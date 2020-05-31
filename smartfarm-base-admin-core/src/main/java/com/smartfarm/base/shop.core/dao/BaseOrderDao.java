package com.smartfarm.base.shop.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.BaseOrder;

public interface BaseOrderDao {

	/**
	 * 新增
	 * @param baseOrder
	 * @return
	 */
    public int insert(BaseOrder baseOrder);
    
    /**
     * 根据id修改
     * @param id
     * @return
     */
    public int updateById(BaseOrder baseOrder);
    
    /**
     * 根据类型和id查询
     * @param type
     * @param refId
     * @return
     */
    public BaseOrder queryByTypeAndRefId(@Param("type") Short type, @Param("refId") Long refId);
    
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
}