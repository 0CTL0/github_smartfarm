package com.smartfarm.base.farm.core.manager;

import com.smartfarm.base.farm.core.entity.FarmTunnelCamera;

import java.util.List;

public interface FarmTunnelCameraManager {

    /**
     * 设置地块摄像头
     * @param farmId
     * @param tunnelId
     * @param cameras
     * @return
     */
    public int addTunnelCameraBatch(Long farmId, Long tunnelId, Long[] cameras);

    /**
     * 查询地块的设备
     * @param tunnelId
     * @return
     */
    public List<FarmTunnelCamera> queryFarmCameraConfigByTunnelId(Long tunnelId);
    
    /**
     * 根据基地id查询公有视频
     * @param baseId
     * @return
     */
    public List<FarmTunnelCamera> getFarmCameraByBaseId(Long baseId);
}
