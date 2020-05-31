package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.WorkingLog;
import com.smartfarm.base.farm.core.entity.vo.WorkingLogVo;

public interface WorkingLogDao {
	
	/**
	 * 添加任务日志
	 * @return
	 */
	public int insert(WorkingLog log);
	
	/**
     * 查询可以展示且状态为完成的所有劳动日志
     * @param memberLandId
     * @return
     */
    List<WorkingLogVo> selectByMemberLandId(@Param("memberLandId") Long memberLandId);
	
    /**
	 * 分页查询劳动任务
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<WorkingLogVo> getWorkingLogsPageList(@Param("farmId") Long farmId, @Param("content") String content, @Param("planTime") String planTime, @Param("executeTime") String executeTime, @Param("status") Short status, @Param("start") Integer start, @Param("limit") Integer limit);
	
	/**
	 * 统计劳动任务总数
	 * @return
	 */
	public int getWorkingLogTotal(@Param("farmId") Long farmId, @Param("content") String content, @Param("planTime") String planTime, @Param("executeTime") String executeTime, @Param("status") Short status);

	/**
	 *查询员工全部的委托任务
	 * @param employId
	 * @return
	 */
     List<WorkingLogVo> selectEmployeeTaskList(@Param("employId") Long employId);
	
	/**
	 * 设置任务的展示情况
	 * @param id
	 * @param isShow
	 * @return
	 */
	public int updateShowStatusById(@Param("id") Long id, @Param("isShow") Short isShow);
	/**
	 * 根据id删除任务
	 * @param id
	 * @return
	 */
	public int deleteById(@Param("id") Long id);

	/**
	 * 根据用户id和地块id查询委托记录
	 * @param landId
	 * @param operator
	 * @return
	 */
	public List<WorkingLog> getDelegateLogs(@Param("landId") Long landId, @Param("operator") Long operator);
    
    int deleteByPrimaryKey(Long id);

    int insert2(WorkingLog record);

    WorkingLog selectByPrimaryKey(Long id);

    int updateById(WorkingLog record);

    int updateByPrimaryKey(WorkingLog record);

    
}