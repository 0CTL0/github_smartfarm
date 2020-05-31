package com.smartfarm.base.farm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.dao.GradeDao;
import com.smartfarm.base.farm.core.entity.Grade;
import com.smartfarm.base.farm.core.manager.GradeManager;

@Service("/gradeManager")
public class GradeManagerImpl implements GradeManager{

	@Resource
	private GradeDao gradeDao;
	
	@Override
	public int insertBatch(List<Grade> list) {
		return gradeDao.insertBatch(list);
	}

	@Override
	public int deleteGroupById(Long id) {
		return gradeDao.deleteGroupById(id);
	}

}
