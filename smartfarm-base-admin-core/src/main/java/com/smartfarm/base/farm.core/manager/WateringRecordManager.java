package com.smartfarm.base.farm.core.manager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月13日 15:43:48
 * @version 1.0
 */
public interface WateringRecordManager {

	/**
	 * 查询浇水次数
	 * @param memberLandId
	 * @param wateringDate
	 * @return
	 */
	public int queryWateringTimes(Long memberLandId, String wateringDate);
	
	
}
