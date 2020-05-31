package com.smartfarm.base.monitor.core.manager;

import java.util.List;

import com.smartfarm.base.monitor.core.entity.SensorInfo;

public interface SensorInfoManager {
	/**
     * 分页查询监控传感器
     * @param start
     * @param limit
     * @return
     */
    List<SensorInfo> queryMonitorsensorInfoList(Integer start, Integer limit);
    
    /**
     * 查询监控传感器数量
     * @return
     */
    int queryCountMonitorsensorInfo();
    /**
     * 新增
     * @param info
     * @return
     */
    int insert(SensorInfo info);
    /**
     * 根据id查看监控传感器
     * @param id
     * @return
     */
    SensorInfo querySensorInfoById(Long id);
    /**
     * 根据id更新监控传感器
     * @param id
     * @return
     */
    int eidtSensorInfoById(SensorInfo info);
    
    /**
     * 根据id删除监控传感器
     * @param id
     * @return
     */
    int deleteSensorInfoById(Long id);
    /**
     * 查询所有传感器信息
     * @return
     */
    List<SensorInfo> queryAllSensorInfo();
}
