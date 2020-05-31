package com.smartfarm.base.farm.core.manager;

import java.util.List;

import com.smartfarm.base.farm.core.entity.ProduceExecuteLog;
import com.smartfarm.base.farm.core.entity.vo.ProduceExecuteLogVo;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年11月27日 15:45:06
 * @version 1.0
 */
public interface ProduceExecuteLogManager {

	/**
	 * 添加生产日志
	 * @param executeLog
	 * @return
	 */
	public int addExecuteLog(ProduceExecuteLog executeLog);

	/**
	 * 查询日志
	 * @param farmId
	 * @param content
	 * @param planTime
	 * @param executeTime
	 * @param status
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ProduceExecuteLogVo> queryExecuteLogs(Long farmId, String content, String planTime, String executeTime, Short status, Integer start, Integer limit);

	/**
	 * 统计日志总数
	 * @param farmId
	 * @param content
	 * @param planTime
	 * @param executeTime
	 * @param status
	 * @return
	 */
	public int queryExecuteLogsTotal(Long farmId, String content, String planTime, String executeTime, Short status);
	
	/**
	 * 设置日志是否展示
	 * @param id
	 * @param isShow
	 * @return
	 */
	public int modifyLogShowStatus(Long id, Short isShow);
	
	/**
	 * 删除日志，并更新计划发布状态
	 * @param id
	 * @param detailId
	 * @return
	 */
	public int deleteExecuteLog(Long id, Long detailId);
	
	/**
	 * 根据员工id查询其任务
	 * @param operator
	 * @return
	 */
	public List<ProduceExecuteLog> queryLogsByOperator(Long operator);
	
	/**
	 * 完成生产任务，数据回填
	 * @param executeLog
	 * @return
	 */
	public int WeChatPerformLog(ProduceExecuteLog executeLog);
	
	/**
	 * 后台分类别查询溯源信息
	 * @param sourceId
	 * @param taskType
	 * @return
	 */
	public List<ProduceExecuteLogVo> queryLogsByTypeAndDetail(Long sourceId, Short taskType);

	/**
	 * 小程序按类别查询溯源信息
	 * @param sourceCodeId
	 * @param taskType
	 * @return
	 */
	public List<ProduceExecuteLogVo> queryLogsByCodeIdAndTypeWe(Long sourceCodeId, Short taskType);
}
