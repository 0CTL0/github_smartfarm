package com.smartfarm.base.farm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.farm.core.dao.*;
import com.smartfarm.base.farm.core.entity.*;
import com.smartfarm.base.shop.core.entity.MemberInfo;
import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.entity.vo.WorkingLogVo;
import com.smartfarm.base.farm.core.manager.FarmWorkingLogManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月23日 17:23:10
 * @version 1.0
 */
@Service("farmWorkingLogManager")
public class FarmWorkingLogManagerImpl implements FarmWorkingLogManager {

	@Resource
	private WorkingLogDao workingLogDao;
	@Resource
	private FarmDelegateOrderDao delegateOrderDao;
	@Resource
	private FarmDelegateExpressDao delegateExpressDao;
	@Resource
	private FarmBazaarOrderDao farmBazaarOrderDao;
	@Resource
	private FarmMemberCropsDao memberCropsDao;
	@Resource
	private FarmCropsInfoDao cropsInfoDao;
	@Resource
	private FarmDelegatePlantDao delegatePlantDao;
	@Resource
	private FarmMemberLandDao farmMemberLandDao;
	
	
	@Override
	public int addWorkingLog(WorkingLog log) {
		int addWL = workingLogDao.insert(log);
		FarmDelegateOrder delegateOrder = new FarmDelegateOrder();
		delegateOrder.setId(log.getPlanDetailId());
		delegateOrder.setStatus((short) 2);
		int update = delegateOrderDao.updateStatusById(delegateOrder);
		return addWL*update;
	}

	@Override
	public List<WorkingLogVo> queryWorkingLogs(Long farmId,String content,String planTime,String executeTime,Short status, Integer start, Integer limit) {
		return workingLogDao.getWorkingLogsPageList(farmId,content,planTime,executeTime,status, start, limit);
	}

	@Override
	public int queryWorkingLogsTotal(Long farmId,String content,String planTime,String executeTime,Short status) {
		return workingLogDao.getWorkingLogTotal(farmId,content,planTime,executeTime,status);
	}

	@Override
	public List<WorkingLogVo> getEmployeeTaskList(Long id) {
		return workingLogDao.selectEmployeeTaskList(id);
	}

	@Override
	public WorkingLog getWorkingLog(Long id) {
		return workingLogDao.selectByPrimaryKey(id);
	}

	@Override
	public int editWorkingLog(WorkingLog workingLog, FarmDelegateExpress delegateExpress,String taskType,Short weight,Integer index) {
		if (index==0){//上传第一张图片时才添加，避免重复追加回填内容
			if (taskType.equals("Picking")){
				workingLog.setContent(workingLog.getContent() + "\n\n实际收成: " + weight + "kg");
			}
			if (taskType.equals("Posting")){
				workingLog.setContent(workingLog.getContent() + "\n\n快递公司: " + delegateExpress.getShopName() + "\n快递单号: " + delegateExpress.getShopNo());
			}
		}
		if (taskType.equals("Planting")){
			FarmMemberCrops crops = new FarmMemberCrops();
			FarmDelegatePlant delegatePlant=delegatePlantDao.getPlantDelegateInfo(workingLog.getPlanDetailId());
			crops.setMemberLandId(workingLog.getMemberLandId());
			crops.setCropsId(delegatePlant.getCropsId());
			crops.setStatus((short) 1);
			crops.setArea(delegatePlant.getPlantArea());
			memberCropsDao.insert(crops);
		}
		int updateMC = 1;
		if (taskType.equals("Picking")){
			FarmCropsInfo cropsInfo = cropsInfoDao.getByDelegateOrderId(workingLog.getPlanDetailId());
			FarmMemberCrops memberCrops = memberCropsDao.getByDelegateOrderId(workingLog.getPlanDetailId());
			//memberCrops.setStatus(FarmMemberCrops.STATUS_REAPED);
			memberCrops.setWeight(weight);
			memberCrops.setInventory(weight);
			memberCrops.setPickTime(workingLog.getActualExecuteTime());
			try {
				memberCrops.setFreshTime(DateUtil.formatAddDateTime(memberCrops.getPickTime(),cropsInfo.getFreshDay()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			updateMC = memberCropsDao.updateAfterPick(memberCrops);
		}
		int updateDE = 1;
		if (taskType.equals("Posting")){
			long expressId = delegateExpressDao.getExpressIdByLogId(workingLog.getId());
			delegateExpress.setId(expressId);
			delegateExpress.setStatus(FarmDelegateExpress.STATUS_POSTALED);
			updateDE = delegateExpressDao.updateById(delegateExpress);
			//发货后，自由销售订单状态置为待收货
			FarmDelegateExpress farmDelegateExpress=delegateExpressDao.selectByPrimaryKey(expressId);
			if(farmDelegateExpress.getBazaarOrderId()!=null){
				FarmBazaarOrder farmBazaarOrder=farmBazaarOrderDao.selectByPrimaryKey(farmDelegateExpress.getBazaarOrderId());
				farmBazaarOrder.setOrderStatus(FarmBazaarOrder.ORDERSTATUS_SHIPPING);
				farmBazaarOrderDao.updateByPrimaryKeySelective(farmBazaarOrder);
			}
		}
		if (taskType.equals("SoilPreparate")){
			FarmMemberCrops memberCrops=memberCropsDao.getById(delegateExpress.getMemberCropsId());
			memberCrops.setStatus(FarmMemberCrops.STATUS_RESOIL);
			memberCropsDao.updateById(memberCrops);
			FarmMemberLand farmMemberLand=farmMemberLandDao.getById(workingLog.getMemberLandId());
			farmMemberLand.setAres(farmMemberLand.getAres()+memberCrops.getArea());
			farmMemberLandDao.updateById(farmMemberLand);
		}
		int updateWL = workingLogDao.updateById(workingLog);
		return updateWL*updateDE*updateMC;
	}

	@Override
	public int modifyLogShowStatus(Long id, Short isShow) {
		return workingLogDao.updateShowStatusById(id, isShow);
	}

	@Override
	public int deleteWorkingLog(Long id, Long detailId) {
		int delete = workingLogDao.deleteById(id);
		FarmDelegateOrder delegateOrder = new FarmDelegateOrder();
		delegateOrder.setId(detailId);
		delegateOrder.setStatus((short) 0);
		int update = delegateOrderDao.updateStatusById(delegateOrder);
		return delete*update;
	}

	@Override
	public List<WorkingLog> queryLandDelegateLogs(Long landId, Long operator) {
		return workingLogDao.getDelegateLogs(landId, operator);
	}
}
