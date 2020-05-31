package com.smartfarm.base.monitor.core.manager;

import java.util.List;

import com.smartfarm.base.monitor.core.entity.MonitorControlSet;
import com.smartfarm.base.monitor.core.entity.MonitorControlSetCondition;
import com.smartfarm.base.monitor.core.entity.MonitorControlSetNode;


public interface MonitorControlSetManager {
	
	/**
	 * 分页查询控制器
	 * @param businessId
	 * @param start
	 * @param limit
	 * @return
	 */
	List<MonitorControlSet> queryPageControlSetList(Long businessId, Integer start, Integer limit);
	
	/**
	 * 
	 * @param businessId
	 * @return
	 */
	Integer countPageControlSetList(Long businessId);
	
	/**
	 * 新增控制器
	 * @param monitorControlSet
	 * @param nodeList
	 * @param conditionList
	 */
	void addControlSet(MonitorControlSet monitorControlSet, List<MonitorControlSetNode> nodeList, List<MonitorControlSetCondition> conditionList);

	/**
	 * 获取所有有效定时控制
	 * @return
	 */
	List<MonitorControlSet> queryAllActiveTimeSet();
	
	void updateStatus(Long id, Short status);
	
	void setControlNode(Long setId, Short status);
}
