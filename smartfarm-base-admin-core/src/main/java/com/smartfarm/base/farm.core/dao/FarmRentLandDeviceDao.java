package com.smartfarm.base.farm.core.dao;

import com.smartfarm.base.farm.core.entity.FarmRentLandDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FarmRentLandDeviceDao {

    /**
     * 移除地块已配置的设备信息
     * @param rentLandId
     * @return
     */
    public int deleteByLandId(@Param("rentLandId") Long rentLandId);

    /**
     * 批量插入，配置设备
     * @param landDevices
     * @return
     */
    public int insertDevices(List<FarmRentLandDevice> landDevices);

    /**
     * 查询地块设备
     * @param rentLandId
     * @return
     */
    public List<FarmRentLandDevice> getFarmDeviceConfig(@Param("rentLandId") Long rentLandId);

    /**
     * 获取租赁土地的摄像头
     * @param rentLandId
     * @return
     */
    FarmRentLandDevice selectLandCamera(@Param("rentLandId") Long rentLandId);


    int deleteByPrimaryKey(Long id);

    int insert(FarmRentLandDevice record);

    int insertSelective(FarmRentLandDevice record);

    FarmRentLandDevice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FarmRentLandDevice record);

    int updateByPrimaryKey(FarmRentLandDevice record);
}