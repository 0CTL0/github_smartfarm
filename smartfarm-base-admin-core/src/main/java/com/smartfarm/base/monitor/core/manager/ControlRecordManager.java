package com.smartfarm.base.monitor.core.manager;

import java.util.List;

import com.smartfarm.base.monitor.core.entity.ControlRecord;

public interface ControlRecordManager {

	/**
	 * 根据设备id查询对应的记录
	 * @param id
	 * @return
	 */
	public List<ControlRecord> getControlRecordsByNodeIdPageList(Long nodeId, Integer start, Integer limit);
	
	/**
	 * 根据设备id统计操作记录数
	 * @param nodeId
	 * @return
	 */
	public int countCtrlRecordsByNodeIdPageList(Long nodeId);
	
	/**
	 * 插入操作记录
	 * @param controlRecord
	 * @return
	 */
	public int addControlRecord(ControlRecord controlRecord);
	
	/**
	 * 根据node_id查找节点的最新一条记录的id
	 * @param controlRecord
	 * @return
	 */
	public ControlRecord getLastRcdByNodeId(ControlRecord controlRecord);
	
	/**
	 * 根据id修改记录
	 * @param controlRecord
	 * @return
	 */
	public int updateCtrlRecordById(ControlRecord controlRecord);
}
