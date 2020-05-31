package com.smartfarm.base.monitor.core.dao;

import java.util.List;

import com.smartfarm.base.monitor.core.entity.MonitorControlSetNode;

public interface MonitorControlSetNodeDao {

    int insert(MonitorControlSetNode record);

    int updateById(MonitorControlSetNode record);
    
    List<MonitorControlSetNode> queryByControlSetId(Long controlSetId);
}