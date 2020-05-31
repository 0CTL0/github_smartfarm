package com.smartfarm.base.shop.core.manager;

import com.smartfarm.base.shop.core.entity.ProductLogisticsTemplate;
import com.smartfarm.base.shop.core.entity.ProductTemplateDetail;

import java.util.List;

public interface ProductLogisticsTemplateManager {
    List<ProductLogisticsTemplate> queryPageList(String name, Integer start, Integer limit, Long farmId);
    int queryPageListTotal(String name, Long farmId);
    ProductLogisticsTemplate queryById(Long id);
    int addProductLogisticsTemplate(ProductLogisticsTemplate productLogisticsTemplate);
    int editProductLogisticsTemplate(ProductLogisticsTemplate productLogisticsTemplate);
    List<ProductLogisticsTemplate> queryAllTemplate(Long farmId);

    List<ProductTemplateDetail> queryTemplateDetailList(Integer start, Integer limit, Long templateId);
    int queryTemplateDetailTotal(Long templateId);
    int addProductTemplateDetail(ProductTemplateDetail productTemplateDetail);
    ProductTemplateDetail queryProductTemplateDetail(Long id);
    int editProductTemplateDetail(ProductTemplateDetail productTemplateDetail);

}
