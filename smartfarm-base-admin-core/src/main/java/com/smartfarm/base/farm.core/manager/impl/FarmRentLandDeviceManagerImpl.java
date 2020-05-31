package com.smartfarm.base.farm.core.manager.impl;

import com.smartfarm.base.farm.core.dao.FarmRentLandDeviceDao;
import com.smartfarm.base.farm.core.entity.FarmRentLandDevice;
import com.smartfarm.base.farm.core.manager.FarmRentLandDeviceManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("farmRentLandDeviceManager")
public class FarmRentLandDeviceManagerImpl implements FarmRentLandDeviceManager {

    @Resource
    private FarmRentLandDeviceDao rentLandDeviceDao;


    @Override
    public int addLandDeviceBatch(Long farmId, Long rentLandId, Long[] cameras, Long[] controlNodes, Long[] sensorNodes) {
        List<FarmRentLandDevice> landDevices = new ArrayList<FarmRentLandDevice>();
        if (cameras!=null){
            for (int i=0;i<cameras.length;i++){
                FarmRentLandDevice device = new FarmRentLandDevice();
                device.setDeviceType(FarmRentLandDevice.DEVICE_CAMERA);
                device.setDeviceId(cameras[i]);
                landDevices.add(device);
            }
        }
        if (controlNodes!=null){
            for (int i=0;i<controlNodes.length;i++){
                FarmRentLandDevice device = new FarmRentLandDevice();
                device.setDeviceType(FarmRentLandDevice.DEVICE_CONTROL_NODE);
                device.setDeviceId(controlNodes[i]);
                landDevices.add(device);
            }
        }
        if (sensorNodes!=null){
            for (int i=0;i<sensorNodes.length;i++){
                FarmRentLandDevice device = new FarmRentLandDevice();
                device.setDeviceType(FarmRentLandDevice.DEVICE_SENSOR_NODE);
                device.setDeviceId(sensorNodes[i]);
                landDevices.add(device);
            }
        }
        for (FarmRentLandDevice landDevice : landDevices){
            landDevice.setRentLandId(rentLandId);
            landDevice.setFarmId(farmId);
        }
        int delete = rentLandDeviceDao.deleteByLandId(rentLandId);
        int insert = 1;
        if (landDevices.size()>0){
            rentLandDeviceDao.insertDevices(landDevices);
        }
        return delete*insert;
    }

    @Override
    public List<FarmRentLandDevice> queryFarmDeviceConfigByLandId(Long rentLandId) {
        return rentLandDeviceDao.getFarmDeviceConfig(rentLandId);
    }

    @Override
    public FarmRentLandDevice getLandCamera(Long rentLandId) {
        return rentLandDeviceDao.selectLandCamera(rentLandId);
    }
}
