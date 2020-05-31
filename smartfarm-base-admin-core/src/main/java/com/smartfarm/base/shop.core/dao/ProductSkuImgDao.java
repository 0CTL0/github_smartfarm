package com.smartfarm.base.shop.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.ProductSkuImg;

public interface ProductSkuImgDao {

    public int insert(ProductSkuImg record);

    public  int updateById(ProductSkuImg record);
    
    /**
     * 根据productId查询
     * @param productId
     * @return
     */
    public List<ProductSkuImg> queryByProductId(Long productId);
    /**
     * 根据流水号来查询
     * @param id
     * @return
     */
    public ProductSkuImg selectById(@Param("id") Long id);
    /**
     * 根据流水号和类型来查询
     * @param productId
     * @param type
     * @return
     */
    public ProductSkuImg selectByProductIdAndType(@Param("productId") Long productId, @Param("type") Short type);
    /**
     * 根据流水号和类型来删除
     * @return
     */
    public int delectByProductIdAndType(@Param("productId") Long productId, @Param("type") Short type);

    /**
     * 根据产品id和类型查询
     * @param productId
     * @param type
     * @return
     */
    public List<ProductSkuImg> queryImgList(@Param("productId") Long productId, @Param("type") Short type);
}