package com.smartfarm.base.farm.core.manager.impl;

import javax.annotation.Resource;

import com.smartfarm.base.farm.core.entity.SettingInfo;
import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.dao.SettingInfoDao;
import com.smartfarm.base.farm.core.manager.SettingInfoManager;

import java.util.ArrayList;
import java.util.List;

@Service("settingInfoManager")
public class SettingInfoManagerImpl implements SettingInfoManager{

	@Resource
	private SettingInfoDao settingInfoDao;
	
	@Override
	public String getCrowdfundingAgreement(String paramCode) {
		return settingInfoDao.getCrowdfundingAgreement(paramCode);
	}

	@Override
	public List<SettingInfo> querySettingInfoByFarmId(Long farmId) {
		return settingInfoDao.getFarmSettingInfo(farmId);
	}

	@Override
	public List<List> queryFarmSettingLists(Long farmId) {
		List<SettingInfo> settingInfos=settingInfoDao.getFarmSettingInfo(farmId);
		List<List> settingLists=new ArrayList<List>();
		int typeNum=settingInfoDao.countSettingTypeByFarmId(farmId);
		for(int i=1;i<=typeNum;i++){
			List<SettingInfo> list=new ArrayList<SettingInfo>();
			for(int j=0,len=settingInfos.size();j<len;j++){
				if(settingInfos.get(j).getSettingType()==i){
					list.add(settingInfos.get(j));
				}
			}
			settingLists.add(list);
		}
		return settingLists;
	}

	@Override
	public int modifyFarmSettingInfo(List<SettingInfo> settingInfoList) {
		for(SettingInfo settingInfo:settingInfoList){
			settingInfoDao.updateById(settingInfo);
		}
//		return settingInfoDao.saveSettingInfo(settingInfoList);
		return 0;
	}


	@Override
	public List<SettingInfo> queryDelegateType(Long farmId) {
		return settingInfoDao.getDelegateTypeInfo(farmId);
	}

	@Override
	public SettingInfo queryByWorkType(Long farmId,String paramCode) {
		return settingInfoDao.getInfoByType(farmId, paramCode);
	}

	@Override
	public List<SettingInfo> queryFarmDelegateSetting(Long farmId) {
		return settingInfoDao.getDelegateSettingInfo(farmId);
	}

}
