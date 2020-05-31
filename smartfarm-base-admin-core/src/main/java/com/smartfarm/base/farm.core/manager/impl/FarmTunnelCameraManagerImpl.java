package com.smartfarm.base.farm.core.manager.impl;

import com.smartfarm.base.farm.core.dao.FarmTunnelCameraDao;
import com.smartfarm.base.farm.core.entity.FarmTunnelCamera;
import com.smartfarm.base.farm.core.manager.FarmTunnelCameraManager;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

@Service("farmTunnelCameraManager")
public class FarmTunnelCameraManagerImpl implements FarmTunnelCameraManager {

    @Resource
    private FarmTunnelCameraDao tunnelCameraDao;


    @Override
    public int addTunnelCameraBatch(Long farmId, Long tunnelId, Long[] cameras) {
        List<FarmTunnelCamera> tunnelCameras = new ArrayList<FarmTunnelCamera>();
        if (cameras!=null){
            for (int i=0;i<cameras.length;i++){
                FarmTunnelCamera camera = new FarmTunnelCamera();
                camera.setCameraId(cameras[i]);
                tunnelCameras.add(camera);
            }
        }
        for (FarmTunnelCamera tunnelCamera : tunnelCameras){
            tunnelCamera.setTunnelId(tunnelId);
            tunnelCamera.setFarmId(farmId);
        }
        int delete = tunnelCameraDao.deleteByTunnelId(tunnelId);
        int insert = 1;
        if (tunnelCameras.size()>0){
            tunnelCameraDao.insertCameras(tunnelCameras);
        }
        return delete*insert;
    }

    @Override
    public List<FarmTunnelCamera> queryFarmCameraConfigByTunnelId(Long tunnelId) {
        return tunnelCameraDao.getFarmCameraConfig(tunnelId);
    }

	@Override
	public List<FarmTunnelCamera> getFarmCameraByBaseId(Long baseId) {
		return tunnelCameraDao.getFarmCameraByBaseId(baseId);
	}
}
