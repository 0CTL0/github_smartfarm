package com.smartfarm.base.monitor.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.monitor.core.entity.MonitorControlSet;

public interface MonitorControlSetDao {

    int insert(MonitorControlSet record);

    int updateById(MonitorControlSet record);
    
    /**
	 * 分页查询控制器
	 * @param businessId
	 * @param start
	 * @param limit
	 * @return
	 */
	List<MonitorControlSet> queryPageControlSetList(@Param("businessId") Long businessId, @Param("start") Integer start, @Param("limit") Integer limit);
	
	/**
	 * 分页统计控制器
	 * @param businessId
	 * @return
	 */
	Integer countPageControlSetList(Long businessId);
	
	/**
	 * 获取所有有效定时控制
	 * @param nowDate
	 * @return
	 */
	List<MonitorControlSet> queryAllActiveTimeSet(@Param("nowDate") String nowDate);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	MonitorControlSet queryById(Long id);
}