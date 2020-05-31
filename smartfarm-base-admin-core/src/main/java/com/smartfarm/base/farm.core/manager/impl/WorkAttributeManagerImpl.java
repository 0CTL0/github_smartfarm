package com.smartfarm.base.farm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.dao.WorkAttributeDao;
import com.smartfarm.base.farm.core.entity.WorkAttribute;
import com.smartfarm.base.farm.core.manager.WorkAttributeManager;

@Service("workAttributeManager")
public class WorkAttributeManagerImpl implements WorkAttributeManager{

	@Resource
	private WorkAttributeDao workAttributeDao;
	
	@Override
	public List<WorkAttribute> queryAttributeById(Long id) {
		return workAttributeDao.queryAttributeById(id);
	}

}
