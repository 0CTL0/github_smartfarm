package com.smartfarm.base.monitor.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.monitor.core.entity.SensorInfo;

public interface SensorInfoDao {
	
    int deleteById(Long id);

    int insert(SensorInfo record);

    SensorInfo selectById(Long id);

    int updateById(SensorInfo record);
    
    /**
     * 分页查询监控传感器
     * @param start
     * @param limit
     * @return
     */
    List<SensorInfo> queryMonitorsensorInfoList(@Param("start") Integer start, @Param("limit") Integer limit);
    
    /**
     * 查询监控传感器数量
     * @return
     */
    int queryCountMonitorsensorInfo();
    
    /**
     * 查询所有传感器信息
     * @return
     */
    List<SensorInfo> queryAllSensorInfo();
    
    /**
     * 根据传感器code值查询其精度
     * @param code
     * @return
     */
    float queryRatioByCode(@Param("code") String code);
}