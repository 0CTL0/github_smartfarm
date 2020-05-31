package com.smartfarm.base.farm.core.dao;

import com.smartfarm.base.farm.core.entity.FarmTunnelCamera;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FarmTunnelCameraDao {

    /**
     * 移除地块已配置的摄像头
     * @param tunnelId
     * @return
     */
    public int deleteByTunnelId(@Param("tunnelId") Long tunnelId);

    /**
     * 批量插入，配置设备
     * @param tunnelCameras
     * @return
     */
    public int insertCameras(List<FarmTunnelCamera> tunnelCameras);

    /**
     * 查询地块设备
     * @param tunnelId
     * @return
     */
    public List<FarmTunnelCamera> getFarmCameraConfig(@Param("tunnelId") Long tunnelId);


    /**
     * 根据基地id查询公有视频
     * @param baseId
     * @return
     */
    public List<FarmTunnelCamera> getFarmCameraByBaseId(Long baseId);


    int deleteByPrimaryKey(Long id);

    int insert(FarmTunnelCamera record);

    int insertSelective(FarmTunnelCamera record);

    FarmTunnelCamera selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FarmTunnelCamera record);

    int updateByPrimaryKey(FarmTunnelCamera record);
}