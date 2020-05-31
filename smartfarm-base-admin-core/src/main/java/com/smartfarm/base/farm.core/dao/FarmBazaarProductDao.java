package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.FarmBazaarProduct;

public interface FarmBazaarProductDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(FarmBazaarProduct record);

    FarmBazaarProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FarmBazaarProduct record);

    /**
     * 查询用户土地的所有在售商品
     * @param memberLandId
     * @return
     */
    List<FarmBazaarProduct> selectMemberLandProduct(@Param("memberLandId") Long memberLandId);

    /**
     * 查询全部的在售状态的商品
     * @return
     */
    List<FarmBazaarProduct> selectAllBazaarProduct(@Param("farmId") Long farmId);

    /**
     * 查询所有农场自由销售的在售商品
     * @return
     */
    List<FarmBazaarProduct> selectAllFarmBazaarProduct();
}