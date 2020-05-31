package com.smartfarm.base.shop.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.dao.FarmTunnelInfoDao;
import com.smartfarm.base.shop.core.dao.ActivityInfoDao;
import com.smartfarm.base.shop.core.dao.IndexAdvanceDao;
import com.smartfarm.base.shop.core.dao.ProductInfoDao;
import com.smartfarm.base.shop.core.entity.IndexAdvance;
import com.smartfarm.base.shop.core.entity.vo.IndexAdvanceVo;
import com.smartfarm.base.shop.core.manager.IndexAdvanceManager;

@Service
public class IndexAdvanceManagerImpl implements IndexAdvanceManager {
	@Resource
	private ActivityInfoDao activityInfoDao;
	@Resource
	private FarmTunnelInfoDao farmTunnelInfoDao;
	@Resource
	private ProductInfoDao productInfoDao;
	@Resource
	private IndexAdvanceDao indexAdvanceDao;
	@Override
	public List<IndexAdvanceVo> queryAllList(Long farmId) {
		List<IndexAdvanceVo> list = indexAdvanceDao.queryAllList(farmId);
		for(IndexAdvanceVo vo:list) {
			if(vo.getType() == 1) {
				vo.setProductInfoVo(productInfoDao.queryProductForAdvanceId(vo.getId()));
			}else if(vo.getType() == 2) {
				vo.setFarmTunnelInfoVo(farmTunnelInfoDao.queryTunnelForAdvanceId(vo.getId()));
			}else if(vo.getType() == 3) {
				vo.setActivityInfoVo(activityInfoDao.queryActivityForAdvanceId(vo.getId()));
			}
		}
		return list;
	}
	@Override
	public void insert(IndexAdvance indexAdvance) {
		indexAdvanceDao.insert(indexAdvance);
	}
	@Override
	public void updateById(IndexAdvance indexAdvance) {
		indexAdvanceDao.updateById(indexAdvance);
	}



}
