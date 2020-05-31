package com.smartfarm.base.farm.core.manager;


import com.smartfarm.base.farm.core.entity.SettingInfo;

import java.util.List;

public interface SettingInfoManager {
	/**
     * 根据param_code查找对应的值
     * @param paramCode
     * @return
     */
    public String getCrowdfundingAgreement(String paramCode);

    /**
     * 查询农场的设置信息
     * @param farmId
     * @return
     */
    public List<SettingInfo> querySettingInfoByFarmId(Long farmId);

    /**
     * 保存，更新农场设置信息
     * @param settingInfoList
     * @return
     */
    public int modifyFarmSettingInfo(List<SettingInfo> settingInfoList);


    /**
     * 查询委托类型
     * @return
     */
    public List<SettingInfo> queryDelegateType(Long farmId);

    /**
     * 根据工作类型查询信息
     * @param farmId
     * @param paramCode
     * @return
     */
    public SettingInfo queryByWorkType(Long farmId, String paramCode);

    /**
     * 查询农场的委托参数设置
     * @param farmId
     * @return
     */
    public List<SettingInfo> queryFarmDelegateSetting(Long farmId);

    List<List> queryFarmSettingLists(Long farmId);
}
