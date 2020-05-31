package com.smartfarm.base.farm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.dao.WorkTypeDao;
import com.smartfarm.base.farm.core.entity.WorkType;
import com.smartfarm.base.farm.core.manager.WorkTypeManager;

@Service("workTypeManager")
public class WorkTypeManagerImpl implements WorkTypeManager{

	@Resource
	private WorkTypeDao workTypeDao;
	
	@Override
	public List<WorkType> queryWorkTypeList() {
		return workTypeDao.queryWorkTypeList();
	}

	@Override
	public WorkType queryWorkTypeById(Long id) {
		return workTypeDao.queryWorkTypeById(id);
	}

}
