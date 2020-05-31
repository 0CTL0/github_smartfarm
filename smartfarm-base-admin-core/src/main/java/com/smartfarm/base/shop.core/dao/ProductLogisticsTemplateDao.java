package com.smartfarm.base.shop.core.dao;

import com.smartfarm.base.shop.core.entity.ProductLogisticsTemplate;
import com.smartfarm.base.shop.core.entity.vo.ProductInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductLogisticsTemplateDao {

    int deleteByPrimaryKey(Long id);
    int insertSelective(ProductLogisticsTemplate record);
    ProductLogisticsTemplate selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(ProductLogisticsTemplate record);

    List<ProductLogisticsTemplate> selectPageList(@Param("name") String name, @Param("start") Integer start, @Param("limit") Integer limit, @Param("farmId") Long farmId);
    int countPageList(@Param("name") String name, @Param("farmId") Long farmId);

    List<ProductLogisticsTemplate> selectAllTemplate(@Param("farmId") Long farmId);

}