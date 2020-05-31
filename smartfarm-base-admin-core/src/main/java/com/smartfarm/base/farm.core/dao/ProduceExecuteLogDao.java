package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.ProduceExecuteLog;
import com.smartfarm.base.farm.core.entity.vo.ProduceExecuteLogVo;

public interface ProduceExecuteLogDao {
	
	/**
	 * 添加生产日志
	 * @param executeLog
	 * @return
	 */
	public int insert(ProduceExecuteLog executeLog);

	/**
	 * 分页查询生产日志
	 * @param content
	 * @param planTime
	 * @param executeTime
	 * @param status
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ProduceExecuteLogVo> getExecuteLogsPageList(@Param("farmId") Long farmId, @Param("content") String content, @Param("planTime") String planTime, @Param("executeTime") String executeTime, @Param("status") Short status, @Param("start") Integer start, @Param("limit") Integer limit);

	/**
	 * 统计生产日志总数
	 * @param content
	 * @param planTime
	 * @param executeTime
	 * @param status
	 * @return
	 */
	public int getExecuteLogTotal(@Param("farmId") Long farmId, @Param("content") String content, @Param("planTime") String planTime, @Param("executeTime") String executeTime, @Param("status") Short status);
	
	/**
	 * 设置日志的展示情况
	 * @param id
	 * @param isShow
	 * @return
	 */
	public int updateShowStatusById(@Param("id") Long id, @Param("isShow") Short isShow);
	
	/**
	 * 根据id删除日志
	 * @param id
	 * @return
	 */
	public int deleteById(@Param("id") Long id);
	
    /**
     * 根据员工id查询其所有的任务
     * @param operator
     * @return
     */
	public List<ProduceExecuteLog> getExecuteLogsByOperator(@Param("operator") Long operator);

	/**
	 * 根据id查询日志，用于图片追加
	 * @param id
	 * @return
	 */
	public ProduceExecuteLog getLogById(@Param("id") Long id);

	/**
	 * 小程序回填，完成生产任务
	 * @param executeLog
	 * @return
	 */
	public int completeLog(ProduceExecuteLog executeLog);
	
	/**
	 * 后台查询对应类型的log
	 * @param sourceId
	 * @param taskType
	 * @return
	 */
	public List<ProduceExecuteLogVo> getLogsBySourceIdAndTaskType(@Param("sourceId") Long sourceId, @Param("taskType") Short taskType);
	
	/**
	 * 小程序按类别查询溯源数据
	 * @param sourceCodeId
	 * @param taskType
	 * @return
	 */
	public List<ProduceExecuteLogVo> getLogsBySCodeIdAndTypeWe(@Param("sourceCodeId") Long sourceCodeId, @Param("taskType") Short taskType);
	



    int insert2(ProduceExecuteLog record);

    int updateById(ProduceExecuteLog record);

    int updateByPrimaryKey(ProduceExecuteLog record);
}