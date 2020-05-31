package com.smartfarm.base.farm.core.dao;

import com.smartfarm.base.farm.core.entity.FarmDelegatePlant;
import com.smartfarm.base.farm.core.entity.vo.FarmDelegatePlantVo;
import org.apache.ibatis.annotations.Param;

public interface FarmDelegatePlantDao {
	
	/**
	 * 添加委托种植记录
	 * @param record
	 * @return
	 */
	public int insert(FarmDelegatePlant delegatePlant);

	/**
	 * 查询种植委托内容
	 * @param orderId
	 * @return
	 */
	public FarmDelegatePlantVo getPlantDelegateInfo(@Param("orderId") Long orderId);
	
	
	
	
    int deleteByPrimaryKey(Long id);

    int insert2(FarmDelegatePlant record);

    

    FarmDelegatePlant selectByPrimaryKey(Long id);

    int updateById(FarmDelegatePlant record);

    int updateByPrimaryKey(FarmDelegatePlant record);
}