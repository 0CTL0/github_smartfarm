package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.AcreageInfo;

public interface AcreageInfoDao {
	
	/**
	 * 新增
	 * @param acreageInfo
	 * @return
	 */
    public int insert(AcreageInfo acreageInfo);

    /**
     * 修改
     * @param acreageInfo
     * @return
     */
    public int updateById(AcreageInfo acreageInfo);
    /**
     * 根据土地ID统计土块
     * @param landId
     * @return
     */
    public int selectTotalByLandId(@Param("landId") Long landId);
    /**
     * 根据土地id获取土块列表
     * @param landId
     * @return
     */
    public List<AcreageInfo> selectListById(@Param("landId") Long landId);
    /**
     * 根据id删除土块
     * @param acreageInfo
     * @return
     */
    public int deleteById(AcreageInfo acreageInfo);
    
    /**
     * 根据id查询土地信息
     * @param id
     * @return
     */
    public AcreageInfo selectAcreageInfoById(@Param("id") Long id);
}