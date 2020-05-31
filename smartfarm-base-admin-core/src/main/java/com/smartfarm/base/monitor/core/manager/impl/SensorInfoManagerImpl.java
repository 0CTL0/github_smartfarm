package com.smartfarm.base.monitor.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.monitor.core.dao.SensorInfoDao;
import com.smartfarm.base.monitor.core.entity.SensorInfo;
import com.smartfarm.base.monitor.core.manager.SensorInfoManager;

@Service("sensorInfoManager")
public class SensorInfoManagerImpl implements SensorInfoManager{

	@Resource
	private SensorInfoDao sensorInfoDao;
	
	@Override
	public List<SensorInfo> queryMonitorsensorInfoList(Integer start,
			Integer limit) {
		return sensorInfoDao.queryMonitorsensorInfoList(start, limit);
	}

	@Override
	public int queryCountMonitorsensorInfo() {
		return sensorInfoDao.queryCountMonitorsensorInfo();
	}

	@Override
	public int insert(SensorInfo info) {
		return sensorInfoDao.insert(info);
	}

	@Override
	public SensorInfo querySensorInfoById(Long id) {
		return sensorInfoDao.selectById(id);
	}

	@Override
	public int eidtSensorInfoById(SensorInfo info) {
		return sensorInfoDao.updateById(info);
	}

	@Override
	public int deleteSensorInfoById(Long id) {
		return sensorInfoDao.deleteById(id);
	}

	@Override
	public List<SensorInfo> queryAllSensorInfo() {
		return sensorInfoDao.queryAllSensorInfo();
	}

}
