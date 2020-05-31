package com.smartfarm.base.farm.core.manager;

import java.util.List;

import com.smartfarm.base.farm.core.entity.FarmDelegateExpress;
import com.smartfarm.base.farm.core.entity.WorkingLog;
import com.smartfarm.base.farm.core.entity.vo.WorkingLogVo;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月23日 17:21:58
 * @version 1.0
 */
public interface FarmWorkingLogManager {

	/**
	 * 添加劳动任务
	 * @param log
	 * @return
	 */
	public int addWorkingLog(WorkingLog log);
	
	/**
	 * 查询任务
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<WorkingLogVo> queryWorkingLogs(Long farmId, String content, String planTime, String executeTime, Short status, Integer start, Integer limit);
	
	/**
	 * 统计任务总数
	 * @return
	 */
	public int queryWorkingLogsTotal(Long farmId, String content, String planTime, String executeTime, Short status);

	/**
	 * 获取员工的全部的委托任务
	 * @param id
	 * @return
	 */
	List<WorkingLogVo> getEmployeeTaskList(Long id);

	/**
	 * 获取员工的委托任务
	 * @param id
	 * @return
	 */
	WorkingLog getWorkingLog(Long id);

	/**
	 * 编辑委托任务
	 * @param workingLog
	 * @return
	 */
	int editWorkingLog(WorkingLog workingLog, FarmDelegateExpress delegateExpress, String taskType, Short weight, Integer index);

	/**
	 * 设置任务是否展示
	 * @param id
	 * @param isShow
	 * @return
	 */
	public int modifyLogShowStatus(Long id, Short isShow);

	/**
	 * 删除任务，并更新计划发布状态
	 * @param id
	 * @param detailId
	 * @return
	 */
	public int deleteWorkingLog(Long id, Long detailId);

	/**
	 * 查询地块的委托记录
	 * @param landId
	 * @param operator
	 * @return
	 */
	public List<WorkingLog> queryLandDelegateLogs(Long landId, Long operator);
}
