package com.smartfarm.base.farm.core.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.dao.WateringRecordDao;
import com.smartfarm.base.farm.core.manager.WateringRecordManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月13日 15:45:19
 * @version 1.0
 */
@Service("wateringRecordManager")
public class WateringRecordManagerImpl implements WateringRecordManager {

	@Resource
	private WateringRecordDao wateringRecordDao;
	
	
	@Override
	public int queryWateringTimes(Long memberLandId, String wateringDate) {
		return wateringRecordDao.getTotalByLandIdAndDate(memberLandId, wateringDate);
	}

}
