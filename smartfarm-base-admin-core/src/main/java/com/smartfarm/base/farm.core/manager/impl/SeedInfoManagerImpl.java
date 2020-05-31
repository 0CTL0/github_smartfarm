package com.smartfarm.base.farm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.dao.SeedInfoDao;
import com.smartfarm.base.farm.core.entity.SeedInfo;
import com.smartfarm.base.farm.core.manager.SeedInfoManager;

@Service("seedInfoManagr")
public class SeedInfoManagerImpl implements SeedInfoManager {
	@Resource
	private SeedInfoDao seedInfoDao;
	
	@Override
	public List<SeedInfo> getSeedInfoPageList(String name, Short status,
			Integer start, Integer limit) {
		return seedInfoDao.selectPageList(name, status, start, limit);
	}

	@Override
	public int countSeedInfoPageList(String name, Short status) {
		return seedInfoDao.selectPageTotal(name, status);
	}

	@Override
	public int editSeedInfo(SeedInfo seedInfo) {
		return seedInfoDao.updateById(seedInfo);
	}

	@Override
	public SeedInfo getSeedInfo(Long id) {
		return seedInfoDao.selectSeedInfoById(id);
	}

	@Override
	public int addSeedInfo(SeedInfo seedInfo) {		
		return seedInfoDao.insert(seedInfo);
	}

	@Override
	public List<SeedInfo> querySeedInfoByLandId(Long landId) {
		return seedInfoDao.querySeedInfoByLandId(landId);
	}

}
