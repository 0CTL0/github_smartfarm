package com.smartfarm.base.farm.core.dao;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.ProducePlanMaster;

public interface ProducePlanMasterDao {
	
	/**
	 * 根据batch_id查找master的id
	 * @param batchId
	 * @return
	 */
	public Long getMasterIdByBatchId(@Param("batchId") Long batchId);
	
    int deleteByPrimaryKey(Long id);

    int insert2(ProducePlanMaster record);

    int insert(ProducePlanMaster record);

    ProducePlanMaster selectByPrimaryKey(Long id);

    int updateById(ProducePlanMaster record);

    int updateByPrimaryKey(ProducePlanMaster record);
}