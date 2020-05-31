package com.smartfarm.base.monitor.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.monitor.core.entity.MonitorControlSetCondition;

public interface MonitorControlSetConditionDao {

    int insert(MonitorControlSetCondition record);

    int updateById(MonitorControlSetCondition record);
    
    List<MonitorControlSetCondition> querySetConditionByvalue(@Param("nowTime") String nowTime, @Param("dataValue") Double dataValue,
                                                              @Param("controlNodeId") Long controlNodeId);
}