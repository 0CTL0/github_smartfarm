package com.smartfarm.base.farm.core.dao;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.WateringRecord;

public interface WateringRecordDao {
	
	/**
	 * 统计目前为止，当天的浇水次数
	 * @param memberLandId
	 * @param wateringDate
	 * @return
	 */
	public int getTotalByLandIdAndDate(@Param("memberLandId") Long memberLandId, @Param("wateringDate") String wateringDate);
	
	/**
	 * 添加浇水记录
	 * @param record
	 * @return
	 */
	public int insert(WateringRecord record);
	
	
	
	
	
    int deleteByPrimaryKey(Long id);

    int insert2(WateringRecord record);

    WateringRecord selectByPrimaryKey(Long id);

    int updateById(WateringRecord record);

    int updateByPrimaryKey(WateringRecord record);
}