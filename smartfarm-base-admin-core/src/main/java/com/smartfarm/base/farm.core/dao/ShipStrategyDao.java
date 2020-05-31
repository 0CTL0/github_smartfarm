package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.ShipStrategy;

public interface ShipStrategyDao {
	
	/**
	 * 新增
	 * @param shipStrategy
	 * @return
	 */
    public int insert(ShipStrategy shipStrategy);

    /**
     * 修改
     * @param shipStrategy
     * @return
     */
    public int updateById(ShipStrategy shipStrategy);
    /**
     * 根据土地Id统计配送周期
     * @param landId
     * @return
     */
    public int selectTotalByLandId(@Param("landId") Long landId);
    /**
     * 获取配送周期列表
     * @param landId
     * @return
     */
    public List<ShipStrategy> selectListById(@Param("landId") Long landId);
    /**
     * 根据土地Id删除配送周期
     * @param shipStrategy
     * @return
     */
    public int deleteById(ShipStrategy shipStrategy);
    
    /**
     * 根据土地id获取配送周期信息
     * @param landId
     * @return
     */
    public List<ShipStrategy> queryShipStrategyByLandId(@Param("landId") Long landId);
}