package com.smartfarm.base.shop.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.ProductSku;
import com.smartfarm.base.shop.core.entity.vo.ProductSkuVo;

public interface ProductSkuDao {

	/**
	 * 添加sku
	 * @param productSku
	 * @return
	 */
    public Long insert(ProductSku productSku);

    /**
     * 根据id修改sku
     * @param productSku
     * @return
     */
    public int updateById(ProductSku productSku);
       
    /**
     * 通过id查找sku
     * @return
     */
    public ProductSku selectById(Long id);
       
    /**
     * 通过产品Id获得SKU列表
     * @param productInfoId
     * @param start
     * @param limit
     * @return
     */
    public List<ProductSkuVo> selectSkuList(@Param("productInfoId") Long productInfoId, @Param("start") Integer start, @Param("limit") Integer limit);
        
   /**
    * 通过产品Id统计其sku 
    * @param productInfoId
    * @return
    */
     public int  countSkuList(@Param("productInfoId") Long productInfoId);
     
     /**
      * 根据产品id查询可用的规格信息
      * @param productId
      * @return
      */
     public List<ProductSkuVo> querySkuByProductId(Long productId);
     
     /**
      * 根据id查询
      * @param id
      * @return
      */
     public ProductSku queryById(Long id);
     
     /**
      * 减库存
      * @param id
      * @param stock
      * @return
      */
     public int subSkuStock(@Param("id") Long id, @Param("stock") Integer stock);
     
     /**
      * 添加库存
      * @param id
      * @param stock
      * @return
      */
     public int addSkuStock(@Param("id") Long id, @Param("stock") Integer stock);
     
     /**
      * 根据产品id查询可用的规格信息
      * @param productId
      * @param type
      * @return
      */
     public List<ProductSkuVo> querySkuByProductIdAndType(@Param("productId") Long productId, @Param("type") Short type);
     
     /**
      * 根据分类id查看产品sku
      * @param id
      * @return
      */
     public List<ProductSku> querySkuDetailsById(@Param("id") Long id);
     
     /**
      * 根据batchid查看规则列表
      * @param id
      * @return
      */
     public List<ProductSkuVo> queryProductSkuById(@Param("id") Long id);
     
     /**
      * 查询所有产品sku
      * @return
      */
     public List<ProductSku> queryAllProductSku();

    /**
     * 查询农场所有产品sku佣金列表
     * @return
     */
    List<ProductSkuVo> selectSkuCommissionList(@Param("farmId") Long farmId, @Param("name") String name, @Param("start") Integer start,
                                               @Param("limit") Integer limit);
    /**
     * 统计农场所有产品sku佣金列表
     * @return
     */
    int selectSkuCommissionTotal(@Param("farmId") Long farmId, @Param("name") String name);


}