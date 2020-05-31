package com.smartfarm.base.monitor.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.monitor.core.dao.MonitorWarningInfoDao;
import com.smartfarm.base.monitor.core.entity.MonitorWarningInfo;
import com.smartfarm.base.monitor.core.entity.vo.MonitorWarningInfoVo;
import com.smartfarm.base.monitor.core.manager.MonitorWarningInfoManager;

@Service("monitorWarningInfoManager")
public class MonitorWarningManagerImpl implements MonitorWarningInfoManager{

	@Resource
	private MonitorWarningInfoDao monitorWarningInfoDao;
	
	@Override
	public int deleteById(Long id) {
		return monitorWarningInfoDao.deleteById(id);
	}

	@Override
	public int insert(MonitorWarningInfo record) {
		return monitorWarningInfoDao.insert(record);
	}

	@Override
	public MonitorWarningInfo selectById(Long id) {
		return monitorWarningInfoDao.selectById(id);
	}

	@Override
	public int updateById(MonitorWarningInfo record) {
		return monitorWarningInfoDao.updateById(record);
	}

	@Override
	public List<MonitorWarningInfoVo> querySoilWarningList(List<Long> ids) {
		return monitorWarningInfoDao.querySoilWarningList(ids);
	}

	@Override
	public List<MonitorWarningInfoVo> queryPageWarningList(Long businessId,
			Integer start, Integer limit) {
		return monitorWarningInfoDao.queryPageWarningList(businessId, start, limit);
	}

	@Override
	public int countPageWarningList(Long businessId) {
		return monitorWarningInfoDao.countPageWarningList(businessId);
	}

}
