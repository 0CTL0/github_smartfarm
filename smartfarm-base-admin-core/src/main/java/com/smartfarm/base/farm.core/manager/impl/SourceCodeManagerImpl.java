package com.smartfarm.base.farm.core.manager.impl;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.smartfarm.base.util.DateUtil;
import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.dao.SourceCodeDao;
import com.smartfarm.base.farm.core.dao.SourceInfoDao;
import com.smartfarm.base.farm.core.entity.SourceCode;
import com.smartfarm.base.farm.core.entity.SourceInfo;
import com.smartfarm.base.farm.core.entity.vo.SourceCodeVo;
import com.smartfarm.base.farm.core.manager.SourceCodeManager;

@Service("sourceCodeManager")
public class SourceCodeManagerImpl implements SourceCodeManager{

	@Resource
	private SourceCodeDao sourceCodeDao;
	
	@Resource
	private SourceInfoDao sourceInfoDao;
	
	@Override
	public List<SourceCodeVo> querySourceCodeList(Long farmId,String name, String code,
			Short status, Integer start, Integer limit) {
		return sourceCodeDao.querySourceCodeList(farmId,name, code, status, start, limit);
	}

	@Override
	public int queryCountSourceCodeList(Long farmId,String name, String code, Short status) {
		return sourceCodeDao.queryCountSourceCodeList(farmId,name, code, status);
	}

	@Override
	public int insertBatchInfoList(List<SourceCode> list,SourceInfo sourceInfo) {
		int siModify = sourceInfoDao.addCodeStatus(sourceInfo);
		int scInsert = sourceCodeDao.insertBatchInfoList(list);
		return siModify*scInsert;
	}

	@Override
	public SourceCodeVo querySourceCodeWe(String code) {
		SourceCodeVo sourceCodeVo = sourceCodeDao.getSourceInquireWe(code);
		if(sourceCodeVo!=null) {
			SourceCode sourceCode = new SourceCode();
			sourceCode.setId(sourceCodeVo.getId());
			sourceCode.setSearchTimes(sourceCodeVo.getSearchTimes()+1);
			if (sourceCodeVo.getFirstSearchTime()==null) {
				sourceCode.setFirstSearchTime(DateUtil.formatCurrentDateTime());
				sourceCode.setStatus(SourceCode.CODE_STATUS_SOLD);
			}
			sourceCodeDao.updateById(sourceCode);
		}
		return sourceCodeDao.getSourceInquireWe(code);
	}

	@Override
	public int modifyCodeSearchTimeWe(SourceCode sourceCode) {
		return sourceCodeDao.updateById(sourceCode);
	}

	@Override
	public List<SourceCode> queryCodeBySourceId(Long sourceId) {
		return sourceCodeDao.getCodeBySourceId(sourceId);
	}

}
