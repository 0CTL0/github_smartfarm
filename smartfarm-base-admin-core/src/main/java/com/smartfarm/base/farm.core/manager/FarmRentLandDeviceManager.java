package com.smartfarm.base.farm.core.manager;

import com.smartfarm.base.farm.core.entity.FarmRentLandDevice;

import java.util.List;

public interface FarmRentLandDeviceManager {

    /**
     * 设置地块设备
     * @param farmId
     * @param rentLandId
     * @param cameras
     * @param controlNodes
     * @param sensorNodes
     * @return
     */
    public int addLandDeviceBatch(Long farmId, Long rentLandId, Long[] cameras, Long[] controlNodes, Long[] sensorNodes);

    /**
     * 查询地块的设备
     * @param rentLandId
     * @return
     */
    public List<FarmRentLandDevice> queryFarmDeviceConfigByLandId(Long rentLandId);

    /**
     * 获取租赁土地的摄像头
     * @param rentLandId
     * @return
     */
   FarmRentLandDevice getLandCamera(Long rentLandId);
}
