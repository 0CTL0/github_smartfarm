package com.smartfarm.base.shop.core.manager.impl;


import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.UploadUtil;
import com.smartfarm.base.shop.core.dao.ProductCategoryDao;
import com.smartfarm.base.shop.core.entity.ProductCategory;
import com.smartfarm.base.shop.core.entity.vo.ProductCategoryVo;
import com.smartfarm.base.shop.core.manager.ProductCategoryManager;


@Service("productCategoryManager")
public class ProductCategoryManagerImpl implements ProductCategoryManager {
	
	@Resource
	private ProductCategoryDao productCategoryDao;
	@Override
	public int insert(ProductCategory record,MultipartFile file) throws IOException {
		String path = UploadUtil.uploadFile(file, "/file/category/","Category" + DateUtil.formatCurrentDateTime());
		record.setIconUrl(path);
		//设置分类的状态
		Short status = 1;
		record.setStatus(status);
		if(record.getLevel() == null) {
			record.setLevel(1);
		}
		record.setCreateTime(DateUtil.formatCurrentDateTime());
		return productCategoryDao.insert(record);
	}

	@Override
	public int updateById(ProductCategory record,MultipartFile file) throws IOException {
		if (file != null) {
			String path = UploadUtil.uploadFile(file, "/file/category/" , "Category" + DateUtil.formatCurrentDateTime());
			record.setIconUrl(path);
		}
		return productCategoryDao.updateById(record);
	}

	@Override
	public List<ProductCategoryVo> queryProductCategoryListPage(String name, Integer start, Integer limit,Long businessId) {
		List<ProductCategoryVo> categoryList = productCategoryDao.queryProductCategoryListPage(name, start, limit,businessId);
		return categoryList;
	}

	@Override
	public int countProductCategoryList(String name,Long businessId) {
		
		return productCategoryDao.countProductCategoryList(name,businessId);
	}

	@Override
	public List<ProductCategory> queryAllProductCategoryList(Long businessId) {
		return productCategoryDao.queryAllProductCategoryList(businessId);
	}

	@Override
	public ProductCategory queryProductCategoryById(Long id) {
		return productCategoryDao.queryProductCategoryById(id);
	}

	@Override
	public int changeStatus(Long categoryId) {
		ProductCategory category = productCategoryDao.queryProductCategoryById(categoryId);
		Short status = category.getStatus();
		
		if (status == 1) {
			Short s1 = 2;
			category.setStatus(s1);
		}else {
			Short s2 = 1;
			category.setStatus(s2);
		}
		return productCategoryDao.updateById(category);
		
	}

	@Override
	public int deleteProductCategoryById(Long id) {
		return productCategoryDao.deleteProductCategoryById(id);
	}

	@Override
	public List<ProductCategory> queryCategoryByLevelAndStatus(Integer level,
			Short status,Long businessId) {
		return productCategoryDao.queryCategoryByLevelAndStatus(level, status,businessId);
	}

	@Override
	public List<ProductCategoryVo> queryTwoLevelCategory(Short status, Long businessId) {
		List<ProductCategoryVo> productCategories=productCategoryDao.queryCategoryVoByLevelAndStatus(ProductCategory.LEVEL_FIRST, status,businessId);
	for(ProductCategoryVo  productCategory:productCategories){
		productCategory.setChildCategoryList(productCategoryDao.selectByParentId(productCategory.getId()));
		}
		return productCategories;
	}
}