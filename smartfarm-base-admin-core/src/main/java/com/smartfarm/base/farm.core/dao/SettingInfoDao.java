package com.smartfarm.base.farm.core.dao;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.SettingInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface SettingInfoDao {

    /**
     * 根据参数code和farmId获取参数值
     * @param code
     * @param farmId
     * @return
     */
    String selectValuesBycodeAndFarmId(@Param("code") String code, @Param("farmId") Long farmId);

	/**
	 * 新增
	 * @param settingInfo
	 * @return
	 */
    public int insert(SettingInfo settingInfo);

    /**
     * 根据id修改
     * @param settingInfo
     * @return
     */
    int updateById(SettingInfo settingInfo);
    
    /**
     * 根据param_code查找对应的值
     * @param paramCode
     * @return
     */
    public String getCrowdfundingAgreement(@Param("paramCode") String paramCode);

    /**
     * 根据农场id查询所有的记录
     * @param farmId
     * @return
     */
    public List<SettingInfo> getFarmSettingInfo(@Param("farmId") Long farmId);

    /**
     * 保存设置内容
     * @param settingInfoList
     * @return
     */
    public int saveSettingInfo(List<SettingInfo> settingInfoList);


    /**
     * 查询委托类型，浇水除外
     * @return
     */
    public List<SettingInfo> getDelegateTypeInfo(@Param("farmId") Long farmId);

    /**
     * 根据工作类型查询对应信息
     * @param farmId
     * @param paramCode
     * @return
     */
    public SettingInfo getInfoByType(@Param("farmId") Long farmId, @Param("paramCode") String paramCode);

    /**
     * 根据我的租地id查询所属农场id
     * @param landId
     * @return
     */
    public Long getFarmIdByMemberLandId(@Param("landId") Long landId);

    /**
     * 查询农场的委托参数
     * @param farmId
     * @return
     */
    public List<SettingInfo> getDelegateSettingInfo(@Param("farmId") Long farmId);

    /**
     * 统计农场的设置参数类型
     * @param farmId
     * @return
     */
    int countSettingTypeByFarmId(@Param("farmId") Long farmId);
}