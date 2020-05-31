package com.smartfarm.base.shop.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.admin.core.entity.FarmInfo;
import com.smartfarm.base.shop.core.entity.ProductCategory;
import com.smartfarm.base.shop.core.entity.ProductInfo;
import com.smartfarm.base.shop.core.entity.vo.ProductInfoVo;


public interface ProductInfoDao {

	/**
	 * 添加
	 * @param productInfo
	 * @return
	 */
    int insert(ProductInfo productInfo);
    
    /**
     * 删除
     * @param id
     * @return
     */
    int delete(Long id);
    
    /**
     * 修改
     * @param productInfo
     * @return
     */
    int updateById(ProductInfo productInfo);                          
    
    /**
   	 * 	查询商品的总数
   	 * @param businessId
   	 * @return
   	 */
   	public int countProductInfoList(@Param("name") String name, @Param("categoryId") Long categoryId, @Param("businessId") Long businessId);
   	
    /**
	 * 分页查询商品信息
	 * @param businessId
	 * @return
	 */
	public List<ProductInfoVo> selectProductInfoList(@Param("name") String name, @Param("categoryId") Long categoryId, @Param("start") Integer start,
                                                     @Param("limit") Integer limit, @Param("businessId") Long businessId);
	
	/**
	 * 根据id查询商品信息
	 * @param id
	 * @return
	 */
	public ProductInfo selectById(Long id);

	/**
	 * 获取所有的商家
	 * @return
	 */
	public List<FarmInfo> queryAllProductBusinessList();
	
	/**
	 * 查询新品
	 * @return
	 */
	public List<ProductInfoVo> queryNewProductList(Long businessId);

	/**
	 * 查询新品
	 * @return
	 */
	public List<ProductInfoVo> queryNewProductListByType(@Param("type") Short type, @Param("businessId") Long businessId);
	
	/**
	 * 查询热销产品
	 * @return
	 */
	public List<ProductInfoVo> queryHotProductList(Long businessId);
	
	/**
	 * 查询热销产品
	 * @return
	 */
	public List<ProductInfoVo> queryHotProductListByType(@Param("type") Short type, @Param("businessId") Long businessId);
	
	public List<ProductInfoVo> queryIntegralProductList(Long businessId);

	/**
	 * 根据分类查询产品
	 * @param categoryId
	 * @return
	 */
	public List<ProductInfoVo> queryProductListByCategoryId(Long categoryId);
	
	/**
	 * 根据分类查询产品
	 * @param categoryId
	 * @return
	 */
	public List<ProductInfoVo> queryProductListByCategory(@Param("category_id") Long categoryId, @Param("type") Short type);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ProductInfo queryById(Long id);
	
	/**
	 * 查询推荐产品
	 * @param farmId
	 * @return
	 */
	public List<ProductInfoVo> queryProductForAdvance(Long farmId);
	
	ProductInfoVo queryProductForAdvanceId(Long advanceId);
	
	/**
	 * 查询可用商城产品
	 * @param businessId
	 * @return
	 */
	List<ProductInfoVo> queryProductForAdvanceAdd(Long businessId);

	ProductInfoVo selectVoByProductId(Long id);
}