package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.LandSeed;

public interface LandSeedDao {
	
	/**
	 * 添加
	 * @param landSeed
	 * @return
	 */
    public int insert(LandSeed landSeed);

    /**
     * 根据id修改
     * @param landSeed
     * @return
     */
    public int updateById(LandSeed landSeed);
    /**
     * 根据id查询
     * @param landSeed
     * @return
     */
    public List<LandSeed> selectByLandId(@Param("id") Long id);
    /**
     * 根据Id删除
     * @param id
     * @return
     */
    public int deleteById(@Param("id") Long id);
}