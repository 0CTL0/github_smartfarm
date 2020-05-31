package com.smartfarm.base.shop.core.dao;

import com.smartfarm.base.shop.core.entity.ProductLogisticsTemplate;
import com.smartfarm.base.shop.core.entity.ProductTemplateDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductTemplateDetailDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(ProductTemplateDetail record);

    ProductTemplateDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductTemplateDetail record);


    List<ProductTemplateDetail> selectPageList(@Param("start") Integer start, @Param("limit") Integer limit, @Param("templateId") Long templateId);
    int selectPageTotal(@Param("templateId") Long templateId);

    List<ProductTemplateDetail> selectByTemplateId(@Param("templateId") Long templateId);


}