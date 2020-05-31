package com.smartfarm.base.farm.core.dao;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.SeedProductInfo;

public interface SeedProductInfoDao {
	
	/**
	 * 根据种子id和产品id查找匹配信息是否存在
	 * @param seedId
	 * @param productId
	 * @return
	 */
	public SeedProductInfo getBySidAndPid(@Param("seedId") Long seedId, @Param("productId") Long productId);
	
	/**
	 * 添加种子-产品匹配对
	 * @param seedProductInfo
	 * @return
	 */
	public int insert(SeedProductInfo seedProductInfo);
	
	
	
	
    int deleteByPrimaryKey(Long id);

    int insert2(SeedProductInfo record);

    

    SeedProductInfo selectByPrimaryKey(Long id);

    int updateById(SeedProductInfo record);

    int updateByPrimaryKey(SeedProductInfo record);
}