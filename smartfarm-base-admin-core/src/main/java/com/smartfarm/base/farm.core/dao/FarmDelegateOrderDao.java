package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.FarmDelegateOrder;
import com.smartfarm.base.farm.core.entity.vo.FarmDelegateOrderVo;

public interface FarmDelegateOrderDao {
	
	/**
	 * 添加种植委托申请单
	 * @param
	 * @return
	 */
	public int insert(FarmDelegateOrder delegateOrder);
	
	/**
	 * 根据申请单号查询申请单的流水号id
	 * @param applyNo
	 * @return
	 */
	public Long getIdByApplyNo(@Param("applyNo") String applyNo);
	
	/**
	 * 按条件，分页查询委托
	 * @param
	 * @param startDate
	 * @param endDate
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<FarmDelegateOrderVo> getDelegateOrderPageList(@Param("farmId") Long farmId, @Param("workType") String workType, @Param("status") Short status, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("start") Integer start, @Param("limit") Integer limit);
	
	/**
	 * 按条件查询委托总数
	 * @param
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int getDelegateOrderTotal(@Param("farmId") Long farmId, @Param("workType") String paramCode, @Param("status") Short status, @Param("startDate") String startDate, @Param("endDate") String endDate);
	
	/**
	 * 发布委托任务，修改委托状态
	 * @param delegateOrder
	 * @return
	 */
	public int updateStatusById(FarmDelegateOrder delegateOrder);


	
	
	
	
    int deleteByPrimaryKey(Long id);

    int insert2(FarmDelegateOrder record);

    

    FarmDelegateOrder selectByPrimaryKey(Long id);

    int updateById(FarmDelegateOrder record);

    int updateByPrimaryKey(FarmDelegateOrder record);
}