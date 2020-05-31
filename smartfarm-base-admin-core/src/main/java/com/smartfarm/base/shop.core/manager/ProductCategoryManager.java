package com.smartfarm.base.shop.core.manager;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.shop.core.entity.ProductCategory;
import com.smartfarm.base.shop.core.entity.vo.ProductCategoryVo;

public interface ProductCategoryManager {
	
	int insert(ProductCategory record, MultipartFile file) throws IOException;

    int updateById(ProductCategory record, MultipartFile file) throws IOException;
    
    /**
     * 分页查询所有的分类信息
     * @return
     */
    public List<ProductCategoryVo> queryProductCategoryListPage(String name, Integer start, Integer limit, Long businessId);
    
    /**
     * 查询分类的总数 
     * @param name
     * @return
     */
    public int countProductCategoryList(String name, Long businessId);
    
    /**
     * 查询所有分类信息
     * @return
     */
    public List<ProductCategory> queryAllProductCategoryList(Long businessId);
    
    /**
     * 根据id查询分类信息
     * @param id
     * @return
     */
    public ProductCategory queryProductCategoryById(Long id);
    
    /**
	 * 根据等级和状态查询分类
	 * @param level
	 * @param status
	 * @return
	 */
	public List<ProductCategory> queryCategoryByLevelAndStatus(Integer level, Short status, Long businessId);

    /**
     * 改变状态
     * @param categoryId
     * @return
     */
	public int changeStatus(Long categoryId);
	
	public int deleteProductCategoryById(Long id);

    /**
     * 查询两级分类数据
     */
	List<ProductCategoryVo> queryTwoLevelCategory(Short status, Long businessId);
}
