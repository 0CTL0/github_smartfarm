package com.smartfarm.base.shop.core.dao;

import java.util.List;

import com.smartfarm.base.shop.core.entity.ProductSkuAttribute;

public interface ProductSkuAttributeDao {
	/**
	 * 添加
	 * @param productSkuAttribute
	 * @return
	 */
    int insert(ProductSkuAttribute productSkuAttribute);

    /**
     * 根据id修改
     * @param productSkuAttribute
     * @return
     */
    int updateByPrimaryKeySelective(ProductSkuAttribute productSkuAttribute);
    
    /**
     * 查询产品属性
     * @param productSkuId
     * @return
     */
    public List<ProductSkuAttribute> queryByProductSkuId(Long productSkuId);
}