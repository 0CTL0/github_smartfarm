package com.smartfarm.base.monitor.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.monitor.core.dao.ControlRecordDao;
import com.smartfarm.base.monitor.core.entity.ControlRecord;
import com.smartfarm.base.monitor.core.manager.ControlRecordManager;

@Service("controlRecordManager")
public class ControlRecordManagerImpl implements ControlRecordManager {

	@Resource
	private ControlRecordDao controlRecordDao;

	@Override
	public List<ControlRecord> getControlRecordsByNodeIdPageList(Long nodeId,Integer start,Integer limit) {
		return controlRecordDao.getByNodeIdPageList(nodeId, start, limit);
	}

	@Override
	public int countCtrlRecordsByNodeIdPageList(Long nodeId) {
		return controlRecordDao.getByNodeIdPageTotal(nodeId);
	}
	
	@Override
	public int addControlRecord(ControlRecord controlRecord) {
		return controlRecordDao.addCtrlRecord(controlRecord);
	}

	@Override
	public ControlRecord getLastRcdByNodeId(ControlRecord controlRecord) {
		return controlRecordDao.getLastByNodeId(controlRecord);
	}

	@Override
	public int updateCtrlRecordById(ControlRecord controlRecord) {
		return controlRecordDao.updateCtrlRcd(controlRecord);
	}

	
	
}
