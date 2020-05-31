package com.smartfarm.base.farm.core.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.dao.SeedDetailDao;
import com.smartfarm.base.farm.core.entity.vo.SeedDetailInfoVo;
import com.smartfarm.base.farm.core.manager.SeedDetailManager;

@Service("seedDetailManager")
public class SeedDetailManagerImpl implements SeedDetailManager{

	@Resource
	private SeedDetailDao seedDetailDao;
	
	@Override
	public SeedDetailInfoVo querySeedDetailById(Long detailId) {
		return seedDetailDao.querySeedDetailById(detailId);
	}

}
