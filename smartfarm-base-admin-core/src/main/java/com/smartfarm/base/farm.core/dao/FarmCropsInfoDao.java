package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.FarmCropsInfo;

public interface FarmCropsInfoDao {
	
	/**
	 * 获取所有种子信息
	 * @return
	 */
	public List<FarmCropsInfo> selectAllRecordByFarmId(@Param("farmId") Long farmId);

	/**
	 * 根据id查询种子信息
	 * @param id
	 * @return
	 */
	public FarmCropsInfo getCropById(@Param("id") Long id);

	/**
	 * 查询页面列表
	 * @param name
	 * @param status
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<FarmCropsInfo> selectPageList(@Param("farmId") Long farmId, @Param("name") String name, @Param("status") Short status, @Param("start") Integer start, @Param("limit") Integer limit);
	/**
	 * 统计页面列表
	 * @param name
	 * @param status
	 * @return
	 */
	public int selectPageTotal(@Param("farmId") Long farmId, @Param("name") String name, @Param("status") Short status);

	/**
	 * 根据委托单id查询作物信息
	 * @param orderId
	 * @return
	 */
	public FarmCropsInfo getByDelegateOrderId(@Param("orderId") Long orderId);



    int deleteByPrimaryKey(Long id);
    int insertSelective(FarmCropsInfo record);
    FarmCropsInfo selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(FarmCropsInfo record);

    
}
