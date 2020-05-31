package com.smartfarm.base.farm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.dao.FarmCropsInfoDao;
import com.smartfarm.base.farm.core.entity.FarmCropsInfo;
import com.smartfarm.base.farm.core.manager.FarmCropsInfoManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月13日 14:16:49
 * @version 1.0
 */
@Service("farmCropsInfoManager")
public class FarmCropsInfoManagerImpl implements FarmCropsInfoManager {

	@Resource
	private FarmCropsInfoDao cropsInfoDao;
	
	
	@Override
	public List<FarmCropsInfo> queryCropsInfoList(Long farmId) {
		return cropsInfoDao.selectAllRecordByFarmId(farmId);
	}

	@Override
	public FarmCropsInfo queryCronInfoByOrderId(Long orderId) {
		return cropsInfoDao.getByDelegateOrderId(orderId);
	}

}
