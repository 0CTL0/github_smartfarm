package com.smartfarm.base.shop.core.manager;

import java.io.IOException;
import java.util.List;

import com.smartfarm.base.shop.core.entity.ProductSku;
import com.smartfarm.base.shop.core.entity.vo.ProductSkuVo;


public interface ProductSkuManager {

	/**
	 * 添加sku
	 * @param productSku
	 * @return
	 */
	public Long insert(ProductSku productSku);
	
	/**
	 * 更新sku
	 * @return
	 */
	public int updateProductSku(ProductSku productSku);
	
	/**
	 * 根据产品Id分页查询所有的sku
	 * @return
	 */
	public List<ProductSkuVo> selectAllProductSku(Long Id, Integer start, Integer limit);
		/**
		 * 统计sku总条数
		 * @return
		 */
	public int countProductSkuList(Long id);
	
	/**
	 * 根据产品Id查找商品信息
	 * @param id
	 * @return
	 */
	public ProductSku selectById(Long id);
	
	
	/**
	 * 根据sku id来更改状态
	 * @param id
	 * @return
	 */
	public int changeStatus(Long id);
	
	/**
	 * 根据产品id查询
	 * @param productId
	 * @return
	 */
	public List<ProductSkuVo> querySkuListByProductId(Long productId);
	
	/**
     * 根据产品id查询可用的规格信息
     * @param productId
     * @param type
     * @return
     */
    public List<ProductSkuVo> querySkuByProductIdAndType(Long productId, Short type);
    /**
     * 根据分类id查看产品sku
     * @param id
     * @return
     */
    public List<ProductSku> querySkuDetailsById(Long id);
    /**
     * 根据batchid查看规则列表
     * @param id
     * @return
     */
    public List<ProductSkuVo> queryProductSkuById(Long id);
    /**
     * 添加产品sku和sku的主图和详情图
     * @param productSku
     * @return
     */
    public Long addProductSku(ProductSku productSku);
   /**
    * 编辑产品SKU和SKU的主图和详情图
    * @param productSku
    * @return
    * @throws IOException
    */
    public int editProductSku(ProductSku productSku);
    /**
     * 查询所有产品sku
     * @return
     */
    public List<ProductSku> queryAllProductSku();
}
