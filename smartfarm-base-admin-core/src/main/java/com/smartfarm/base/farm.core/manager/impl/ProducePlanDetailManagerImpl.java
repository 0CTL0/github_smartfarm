package com.smartfarm.base.farm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.dao.ProducePlanDetailDao;
import com.smartfarm.base.farm.core.entity.ProducePlanDetail;
import com.smartfarm.base.farm.core.entity.vo.ProducePlanDetailVo;
import com.smartfarm.base.farm.core.manager.ProducePlanDetailManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年11月26日 09:56:26
 * @version 1.0
 */
@Service("producePlanDetailManager")
public class ProducePlanDetailManagerImpl implements ProducePlanDetailManager {

	@Resource
	private ProducePlanDetailDao producePlanDetailDao;

	@Override
	public List<ProducePlanDetail> queryPageListByMasterId(Long masterId,Integer start,Integer limit) {
		return producePlanDetailDao.getAllPageListByMasterId(masterId,start,limit);
	}

	@Override
	public int queryPageTotalByMasterId(Long masterId) {
		return producePlanDetailDao.getPageTotalByMasterId(masterId);
	}

	@Override
	public ProducePlanDetail querySingleDetailById(Long id) {
		return producePlanDetailDao.getDetailById(id);
	}

	@Override
	public int modifyDetailById(ProducePlanDetail planDetail) {
		return producePlanDetailDao.updateById(planDetail);
	}

	@Override
	public int addPlanDetail(ProducePlanDetail planDetail) {
		return producePlanDetailDao.insert(planDetail);
	}

	@Override
	public int deleteDetailById(Long id) {
		return producePlanDetailDao.deleteById(id);
	}

	@Override
	public List<ProducePlanDetailVo> queryAllDetailsPageList(Long farmId,String batchCode,String name, String startDate, String endDate, Integer start,
			Integer limit) {
		return producePlanDetailDao.getAllDetailsPageList(farmId,batchCode,name, startDate, endDate, start, limit);
	}

	@Override
	public int queryAllDetailsTotal(Long farmId,String name, String startDate, String endDate) {
		return producePlanDetailDao.getAllDetailsTotal(farmId,name, startDate, endDate);
	}

}
