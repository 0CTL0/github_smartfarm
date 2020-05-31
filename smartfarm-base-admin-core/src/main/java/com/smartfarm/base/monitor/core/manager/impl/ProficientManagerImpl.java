package com.smartfarm.base.monitor.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.monitor.core.dao.ProficientDao;
import com.smartfarm.base.monitor.core.entity.Proficient;
import com.smartfarm.base.monitor.core.manager.ProficientManager;

@Service("proficlientManagr")
public class ProficientManagerImpl implements ProficientManager {

	@Resource
	private ProficientDao proficientDao;
	
	
	@Override
	public List<Proficient> getPageList(String mobile, Integer start, Integer limit) {
		return proficientDao.getPageList(mobile, start, limit);
	}

	@Override
	public int countProficlientPageList(String mobile) {
		return proficientDao.getPageTotal(mobile);
	}

	@Override
	public int addProficlient(Proficient proficient) {
		return proficientDao.insert(proficient);
	}

	@Override
	public Proficient selectProficientById(Long id) {
		return proficientDao.selectById(id);
	}

	@Override
	public int updateProficient(Proficient proficient) {
		return proficientDao.updateById(proficient);
	}

	@Override
	public int deleteProficient(Long id) {
		return proficientDao.deleteById(id);
	}

	@Override
	public List<Proficient> queryProficientList() {
		return proficientDao.queryProficientList();
	}

}
