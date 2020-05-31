package com.smartfarm.base.farm.core.dao;

import java.util.List;

import com.smartfarm.base.farm.core.entity.LandType;

public interface LandTypeDao {
	
	/**
	 * 新增
	 * @param landType
	 * @return
	 */
    public int insert(LandType landType);

    /**
     * 修改
     * @param landType
     * @return
     */
    public int updateById(LandType landType);
    /**
     * 查询所有土地分类
     * @return
     */
    public List<LandType> selectLandType();
}