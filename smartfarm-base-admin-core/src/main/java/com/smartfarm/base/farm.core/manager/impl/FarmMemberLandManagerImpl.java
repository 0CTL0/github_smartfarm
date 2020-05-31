package com.smartfarm.base.farm.core.manager.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.farm.core.dao.*;
import com.smartfarm.base.farm.core.entity.*;
import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.entity.vo.FarmMemberLandVo;
import com.smartfarm.base.farm.core.entity.vo.WorkingLogVo;
import com.smartfarm.base.farm.core.manager.FarmMemberLandManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月18日 16:51:25
 * @version 1.0
 */
@Service("farmMemberLandManager")
public class FarmMemberLandManagerImpl implements FarmMemberLandManager {

	@Resource
	private FarmMemberLandDao memberLandDao;
	@Resource
	private WorkingLogDao workingLogDao;
	@Resource
	private FarmRentLandInfoDao farmRentLandInfoDao;
	@Resource
	private FarmCropsInfoDao farmCropsInfoDao;
	@Resource
	private FarmMemberCropsDao farmMemberCropsDao;

	@Override
	public List<FarmMemberLandVo> queryLandsByMemberId(Long memberId) {
		return memberLandDao.getLandsByMemberId(memberId);
	}
	@Override
	public List<WorkingLogVo> getWorkingLogVoList(Long memberLandId) {
		List<WorkingLogVo> logList=workingLogDao.selectByMemberLandId(memberLandId);
		for(WorkingLogVo log:logList){
			if(log.getPic()!=null){
				String picStrList[]=log.getPic().split(";");
				List<String> contentPicList = new ArrayList<String>(Arrays.asList(picStrList));
				log.setPicList(contentPicList);
			}
		}
		return logList;
	}
	@Override
	public FarmMemberLand queryMemberLandById(Long id) {
		return memberLandDao.getById(id);
	}
	@Override
	public int modifyLandAliasById(FarmMemberLand memberLand) {
		return memberLandDao.updateById(memberLand);
	}

	@Override
	public void updateOverdueLand() throws ParseException {
		//租赁土地到期后更新状态，恢复库存
		List<FarmMemberLand> memberLandList=memberLandDao.selectAllMemberLand();
		if(memberLandList!=null){
			for(FarmMemberLand farmMemberLand:memberLandList){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String newTimes = sdf.format(new Date());
				Date newTime = sdf.parse(newTimes);
				Date expirationTime = sdf.parse(farmMemberLand.getExpiration());
				if(newTime.getTime()>expirationTime.getTime()){
					farmMemberLand.setStatus(FarmMemberLand.STATUS_INVALID);
					memberLandDao.updateByPrimaryKey(farmMemberLand);
					FarmRentLandInfo farmRentLandInfo=farmRentLandInfoDao.selectByPrimaryKey(farmMemberLand.getRentLandId());
					farmRentLandInfo.setStatus(FarmRentLandInfo.STATUS_ABLE);
					farmRentLandInfoDao.updateByPrimaryKeySelective(farmRentLandInfo);
				}
			}
		}
	}

	@Override
	public Long selectFarmId(Long rentLandId) {
		return farmRentLandInfoDao.selectFarmId(rentLandId);
	}

	@Override
	public Long selectFarmTunnelId(Long memberLandId) {
		return memberLandDao.selectFarmTunnelId(memberLandId);
	}

	@Override
	public int getCountDelegatePlantArea(Long memberLandId) {
		return memberLandDao.countDelegatePlantArea(memberLandId);
	}

	@Override
	public void plantRegister(FarmMemberCrops farmMemberCrops) {
		FarmCropsInfo cropsInfo = farmCropsInfoDao.getCropById(farmMemberCrops.getCropsId());
		farmMemberCrops.setStatus(FarmMemberCrops.STATUS_PLANTING);
		WorkingLog workingLog=new WorkingLog();
		workingLog.setMemberLandId(farmMemberCrops.getMemberLandId());
		workingLog.setStatus(WorkingLog.STATUS_ABLED);
		workingLog.setActualExecuteTime(DateUtil.formatCurrentDateTime());
		workingLog.setContent("您种植了"+cropsInfo.getName()+farmMemberCrops.getArea());
		workingLog.setTaskType("Planting");
		workingLog.setIsShow(WorkingLog.SHOW_ABLED);
		farmMemberCropsDao.insert(farmMemberCrops);
		workingLogDao.insert(workingLog);
	}
}
