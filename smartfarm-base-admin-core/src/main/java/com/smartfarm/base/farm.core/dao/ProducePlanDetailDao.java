package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.ProducePlanDetail;
import com.smartfarm.base.farm.core.entity.vo.ProducePlanDetailVo;

public interface ProducePlanDetailDao {
	
	/**
	 * 根据master_id分页查询所有的计划明细
	 * @return
	 */
	public List<ProducePlanDetail> getAllPageListByMasterId(@Param("masterId") Long masterId, @Param("start") Integer start, @Param("limit") Integer limit);
	
	/**
	 * 根据master_id查询单个主计划的计划明细总数
	 * @param masterId
	 * @return
	 */
	public int getPageTotalByMasterId(@Param("masterId") Long masterId);
	
	/**
	 * 根据id查找单个计划
	 * @param id
	 * @return
	 */
	public ProducePlanDetail getDetailById(@Param("id") Long id);
	
	/**
	 * 根据id修改计划
	 * @param planDetail
	 * @return
	 */
	public int updateById(ProducePlanDetail planDetail);

	/**
	 * 添加计划
	 * @param planDetail
	 * @return
	 */
	public int insert(ProducePlanDetail planDetail);
	
	/**
	 * 批量添加计划明细
	 * @param detailList
	 * @return
	 */
	public int insertDetailList(@Param("detailList") List<ProducePlanDetail> detailList);
	
	/**
	 * 根据id删除计划
	 * @param id
	 * @return
	 */
    public int deleteById(@Param("id") Long id);

    /**
     * 分页查询未来一周内的所有生产计划明细,默认一周
     * @param start
     * @param limit
     * @return
     */
    public List<ProducePlanDetailVo> getAllDetailsPageList(@Param("farmId") Long farmId, @Param("batchCode") String batchCode, @Param("name") String name, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("start") Integer start, @Param("limit") Integer limit);
    
    /**
     * 查询未来一周的生产计划总数,默认一周
     * @param startDate
     * @param endDate
     * @return
     */
    public int getAllDetailsTotal(@Param("farmId") Long farmId, @Param("name") String name, @Param("startDate") String startDate, @Param("endDate") String endDate);
    
    /**
     * 根据id修改计划的log_status的值，发布任务时调用
     * @param id
     * @param logStatus
     * @return
     */
    public int updateLogStatusById(@Param("id") Long id, @Param("logStatus") Short logStatus);
    
    
    
    int insert2(ProducePlanDetail record);
    int updateByPrimaryKey(ProducePlanDetail record);
}