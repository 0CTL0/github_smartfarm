package com.smartfarm.base.shop.core.manager;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.shop.core.entity.ProductInfo;
import com.smartfarm.base.shop.core.entity.vo.ProductInfoVo;


public interface ProductInfoManager {
	/**
	 * 添加商品信息
	 * @param productInfo
	 * @return
	 */
	public int insert(ProductInfo productInfo, MultipartFile file, MultipartFile file2, MultipartFile[] mainImgs, MultipartFile[] detailImgs)throws IOException;
	/**
	 * 更新商品信息
	 * @param productInfo
	 * @return
	 */
	public int updateProductInfo(ProductInfo productInfo, MultipartFile file, MultipartFile file2, MultipartFile[] mainImgs, MultipartFile[] detailImgs) throws IOException;
	
	/**
	 * 分类查询所有的商品
	 * @return
	 */
	public List<ProductInfoVo> selectAllProductInfo(String name, Long categoryId, Integer start, Integer limit, Long businessId);
		/**
		 * 	查询商品总条数
		 * @param businessId
		 * @return
		 */
	public int countProductInfoList(String name, Long categoryId, Long businessId);
	
	/**
	 * 根据Id查找商品信息
	 * @param id
	 * @return
	 */
	public ProductInfo selectById(Long id);
	
	/**
	 * 删除商品信息
	 * @param id
	 * @return
	 */
	public int delectProductInfo(Long id);
	
	/**
	 * 更改商品的新品状态
	 * @param id
	 * @return
	 */
	public int changeNew(Long id);
	/**
	 * 更改商品的人气状态
	 * @param id
	 * @return
	 */
	public int changeHot(Long id);
	
	/**
	 * 改变商品的状态
	 */
	public int changeStatus(Long id);
	
	/**
	 * 查询所有的商家信息
	 * @return
	 */
	public Object queryAllProductBusinessList();
	
	/**
	 * 查询新品
	 * @return
	 */
	public List<ProductInfoVo> queryNewProductList(Long businessId);
	
	/**
	 * 小程序查询新品上市的产品
	 * @param type 归属：1商城 2积分 3活动
	 * @return
	 */
	public List<ProductInfoVo> queryNewProductList(Short type, Long businessId);
	/**
	 * 查询热销产品
	 * @return
	 */
	public List<ProductInfoVo> queryHotProductList(Long businessId);
	/**
	 * 小程序查询精品推荐的产品
	 * @param type 归属：1商城 2积分 3活动
	 * @return
	 */
	public List<ProductInfoVo> queryHotProductList(Short type, Long businessId);
	/**
	 * 小程序查询积分产品
	 * @return  按照积分降序排列
	 */
	public List<ProductInfoVo> queryIntegralProductList(Long businessId);
	
	/**
	 * 根据分类查询产品
	 * @param categoryId
	 * @return
	 */
	public List<ProductInfoVo> queryProductListByCategoryId(Long categoryId);
	/**
	 * 小程序查询精品推荐的产品
	 * @param categoryId 产品分类 
	 * @param type 归属：1商城 2积分 3活动
	 * @return
	 */
	public List<ProductInfoVo> queryProductListByCategoryId(Long categoryId, Short type);
	
	/**
	 * 首页推荐产品
	 * @return
	 */
	public List<ProductInfoVo> queryIndexProduct(Long farmId);
	
	/**
	 * 查询可用商城产品
	 * @param businessId
	 * @return
	 */
	List<ProductInfoVo> queryProductForAdvanceAdd(Long businessId);

	public ProductInfoVo selectVoById(Long id);
}
