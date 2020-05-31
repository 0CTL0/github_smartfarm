package com.smartfarm.base.farm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.dao.SourceCodeDao;
import com.smartfarm.base.farm.core.dao.SourceInfoDao;
import com.smartfarm.base.farm.core.entity.SourceInfo;
import com.smartfarm.base.farm.core.entity.vo.SourceInfoVo;
import com.smartfarm.base.farm.core.manager.SourceInfoManager;

@Service("sourceInfoManager")
public class SourceInfoManagerImpl implements SourceInfoManager{

	@Resource
	private SourceInfoDao sourceInfoDao;
	
	@Resource
	private SourceCodeDao sourceCodeDao;
	
	@Override
	public List<SourceInfo> qurySourceInfoList(Long farmId,String name,String prefix,Integer start, Integer limit) {
		return sourceInfoDao.qurySourceInfoList(farmId,name,prefix,start, limit);
	}

	@Override
	public int quryCountSourceInfoList(Long farmId) {
		return sourceInfoDao.quryCountSourceInfoList(farmId);
	}

	@Override
	public int addSourceInfo(SourceInfo sourceInfo) {
		return sourceInfoDao.insert(sourceInfo);
	}

	@Override
	public SourceInfoVo queryAttributeDataById(Long id) {
		return sourceInfoDao.queryAttributeDataById(id);
	}

}
