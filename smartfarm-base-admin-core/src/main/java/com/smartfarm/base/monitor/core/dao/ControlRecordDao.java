package com.smartfarm.base.monitor.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.monitor.core.entity.ControlRecord;

public interface ControlRecordDao {

	/**
	 * 根据节点设备的id来查询对应的控制记录
	 * @param id
	 * @return
	 */
	public List<ControlRecord> getByNodeIdPageList(@Param("nodeId") Long nodeId, @Param("start") Integer startInteger, @Param("limit") Integer limit);
	
	/**
	 * 根据节点设备id统计其操作记录数
	 * @param nodeId
	 * @return
	 */
	public int getByNodeIdPageTotal(@Param("nodeId") Long nodeId);
	
	/**
	 * 插入操作记录（开启）
	 * @param controlRecord
	 * @return
	 */
	public int addCtrlRecord(ControlRecord controlRecord);
	
	/**
	 * 根据node_id来查找最新的一条记录的id
	 * @param controlRecord
	 * @return
	 */
	public ControlRecord getLastByNodeId(ControlRecord controlRecord);
	
	/**
	 * 更新操作记录（关闭）
	 * @param controlRecord
	 * @return
	 */
	public int updateCtrlRcd(ControlRecord controlRecord);
}
