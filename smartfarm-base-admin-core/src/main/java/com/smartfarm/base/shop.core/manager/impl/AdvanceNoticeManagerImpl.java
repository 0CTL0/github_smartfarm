package com.smartfarm.base.shop.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.shop.core.dao.AdvanceNoticeDao;
import com.smartfarm.base.shop.core.entity.AdvanceNotice;
import com.smartfarm.base.shop.core.manager.AdvanceNoticeManager;

@Service
public class AdvanceNoticeManagerImpl implements AdvanceNoticeManager {
	@Resource
	private AdvanceNoticeDao advanceNoticeDao;
	
	@Override
	public List<AdvanceNotice> queryUseableAdvanceList(Short type,Long farmId) {
		return advanceNoticeDao.queryUseableAdvanceList(type,farmId);
	}

	@Override
	public int insert(AdvanceNotice advanceNotice) {
		return advanceNoticeDao.insert(advanceNotice);
	}

	@Override
	public int updateById(AdvanceNotice advanceNotice) {
		return advanceNoticeDao.updateById(advanceNotice);
	}

	@Override
	public List<AdvanceNotice> queryAdvanceList(Long farmId) {
		return advanceNoticeDao.queryAdvanceList(farmId);
	}


}
