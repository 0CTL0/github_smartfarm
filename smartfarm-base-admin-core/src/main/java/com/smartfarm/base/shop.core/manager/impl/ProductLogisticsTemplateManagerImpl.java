package com.smartfarm.base.shop.core.manager.impl;

import com.smartfarm.base.shop.core.dao.ProductLogisticsTemplateDao;
import com.smartfarm.base.shop.core.dao.ProductTemplateDetailDao;
import com.smartfarm.base.shop.core.entity.ProductLogisticsTemplate;
import com.smartfarm.base.shop.core.entity.ProductTemplateDetail;
import com.smartfarm.base.shop.core.manager.ProductLogisticsTemplateManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductLogisticsTemplateManagerImpl implements ProductLogisticsTemplateManager {
    @Resource
    private ProductLogisticsTemplateDao productLogisticsTemplateDao;
    @Resource
    private ProductTemplateDetailDao productTemplateDetailDao;

    @Override
    public List<ProductLogisticsTemplate> queryPageList(String name, Integer start, Integer limit, Long farmId) {
        return productLogisticsTemplateDao.selectPageList(name,start,limit,farmId);
    }

    @Override
    public int queryPageListTotal(String name, Long farmId) {
        return productLogisticsTemplateDao.countPageList(name,farmId);
    }

    @Override
    public ProductLogisticsTemplate queryById(Long id) {
        return productLogisticsTemplateDao.selectByPrimaryKey(id);
    }

    @Override
    public int addProductLogisticsTemplate(ProductLogisticsTemplate productLogisticsTemplate) {
        return productLogisticsTemplateDao.insertSelective(productLogisticsTemplate);
    }

    @Override
    public int editProductLogisticsTemplate(ProductLogisticsTemplate productLogisticsTemplate) {
        return productLogisticsTemplateDao.updateByPrimaryKeySelective(productLogisticsTemplate);
    }


    @Override
    public List<ProductTemplateDetail> queryTemplateDetailList(Integer start, Integer limit, Long templateId) {
        return productTemplateDetailDao.selectPageList(start,limit,templateId);
    }

    @Override
    public int queryTemplateDetailTotal(Long templateId) {
        return productTemplateDetailDao.selectPageTotal(templateId);
    }

    @Override
    public int addProductTemplateDetail(ProductTemplateDetail productTemplateDetail) {
        return productTemplateDetailDao.insertSelective(productTemplateDetail);
    }

    @Override
    public ProductTemplateDetail queryProductTemplateDetail(Long id) {
        return productTemplateDetailDao.selectByPrimaryKey(id);
    }

    @Override
    public int editProductTemplateDetail(ProductTemplateDetail productTemplateDetail) {
        return productTemplateDetailDao.updateByPrimaryKeySelective(productTemplateDetail);
    }

    @Override
    public List<ProductLogisticsTemplate> queryAllTemplate(Long farmId) {
        return productLogisticsTemplateDao.selectAllTemplate(farmId);
    }
}
