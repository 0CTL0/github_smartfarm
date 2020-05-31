package com.smartfarm.base.monitor.core.manager;

import java.util.List;

import com.smartfarm.base.monitor.core.entity.CameraMessage;

public interface CameraMessageManager {

    /**
     * 农场查询摄像头
     * @param farmId
     * @return
     */
    public List<CameraMessage> queryCamerasByFarmId(Long farmId, Integer start, Integer limit);

    /**
     * 统计农场摄像头数量
     * @param farmId
     * @return
     */
    public int countCamerasTotal(Long farmId);

    /**
     * 农场添加摄像头
     * @param cameraMessage
     * @return
     */
    public int addFarmCamera(CameraMessage cameraMessage);

    /**
     * 根据id查找单个摄像头
     * @param id
     * @return
     */
    public CameraMessage queryCameraById(Long id);

    /**
     * 根据id修改摄像头信息
     * @param cameraMessage
     * @return
     */
    public int modifyCameraInfoById(CameraMessage cameraMessage);

    /**
     * 删除摄像头
     * @param id
     * @return
     */
    public int removeCameraById(Long id);

	/**
     * 根据摄像头id得到播放地址
     * @param id
     * @return
     */
    String queryCameraLiveAddressById(Long id);
    
    /**
     * 抓拍
     */
    void snapPhoto(Long id);
    
    /**
     * 根据id得到摄像头信息
     * @param id
     * @return
     */
    CameraMessage checkCamera(Long id);
    
    /**
     * 得到摄像头信息列表
     * @return
     */
    public List<CameraMessage> queryCameraMessageList();

    /**
     * 查询农场已配置的所有摄像头
     * @param farmId
     * @return
     */
    public List<CameraMessage> queryFarmCameras(Long farmId);
}
