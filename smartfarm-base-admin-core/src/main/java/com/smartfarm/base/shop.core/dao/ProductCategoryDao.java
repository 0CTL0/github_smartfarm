package com.smartfarm.base.shop.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.ProductCategory;
import com.smartfarm.base.shop.core.entity.vo.ProductCategoryVo;


public interface ProductCategoryDao {

	/**
	 * 添加
	 * @param productCategory
	 * @return
	 */
    public int insert(ProductCategory productCategory);

    /**
     * 根据id修改
     * @param productCategory
     * @return
     */
    public int updateById(ProductCategory productCategory);

	public int countProductCategoryList(@Param("name") String name, @Param("businessId") Long businessId);

	/**
	 * 查询所有分类
	 * @return
	 */
	public List<ProductCategory> queryAllProductCategoryList(Long businessId);

	public ProductCategory queryProductCategoryById(Long id);
	
	/**
	 * 分页查询分类
	 * @param name
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ProductCategoryVo> queryProductCategoryListPage(@Param("name") String name, @Param("start") Integer start,
                                                                @Param("limit") Integer limit, @Param("businessId") Long businessId);
	
	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	public int deleteProductCategoryById(@Param("id") Long id);
	
	/**
	 * 根据等级和状态查询分类
	 * @param level
	 * @param status
	 * @return
	 */
	public List<ProductCategory> queryCategoryByLevelAndStatus(@Param("level") Integer level,
                                                               @Param("status") Short status, @Param("businessId") Long businessId);

	/**
	 * 查询子分类
	 * @param parentId
	 * @return
	 */
	List<ProductCategory> selectByParentId(@Param("parentId") Long parentId);

	List<ProductCategoryVo> queryCategoryVoByLevelAndStatus(@Param("level") Integer level, @Param("status") Short status, @Param("businessId") Long businessId);
}