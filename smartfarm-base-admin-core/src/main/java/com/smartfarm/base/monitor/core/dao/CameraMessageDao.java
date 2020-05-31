package com.smartfarm.base.monitor.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.monitor.core.entity.CameraMessage;

public interface CameraMessageDao {

    /**
     * 查询摄像头
     * @param farmId
     * @param start
     * @param limit
     * @return
     */
    public List<CameraMessage> getFarmCamerasPageList(@Param("farmId") Long farmId, @Param("start") Integer start, @Param("limit") Integer limit);

    /**
     * 统计农场摄像头总数
     * @param farmId
     * @return
     */
    public int getFarmCamerasTotal(@Param("farmId") Long farmId);
	/**
	 * 新增
	 * @param cameraMessage
	 * @return
	 */
    public int insert(CameraMessage cameraMessage);

    /**
     * 查询单个摄像头
     * @param id
     * @return
     */
    public CameraMessage getSingleCamera(@Param("id") Long id);

    /**
     * 根据id修改
     * @param cameraMessage
     * @return
     */
    public int updateById(CameraMessage cameraMessage);

    /**
     * 删除摄像头
     * @param id
     * @return
     */
    public int deleteById(@Param("id") Long id);
    
    /**
     * 
     * 根据id得到摄像头信息
     * @param id
     * @return
     */
    public CameraMessage queryCameraMessageById(@Param("id") Long id);
    
    /**
     * 得到摄像头信息列表
     * @return
     */
    public List<CameraMessage> queryCameraMessageList();

    /**
     * 查询农场的所有摄像头
     * @param farmId
     * @return
     */
    public List<CameraMessage> getFarmCameras(@Param("farmId") Long farmId);
}
