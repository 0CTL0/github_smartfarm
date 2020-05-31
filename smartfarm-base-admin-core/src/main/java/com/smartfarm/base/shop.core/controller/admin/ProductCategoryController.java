package com.smartfarm.base.shop.core.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.shop.core.entity.ProductCategory;
import com.smartfarm.base.shop.core.entity.vo.ProductCategoryVo;
import com.smartfarm.base.shop.core.manager.ProductCategoryManager;


@Controller
@RequestMapping("/productCategory")
public class ProductCategoryController {
	private Logger log = Logger.getLogger(ProductCategoryController.class);
	@Resource
	private ProductCategoryManager productCategoryManager;
	
	/**
	 * 查询分类信息
	 */
	@RequestMapping("/queryProductCategoryList")
	@ResponseBody
	public Object queryProductCategoryList(HttpServletRequest request, String name, Integer pageSize, Integer pageNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("123");
		try{
			log.info("[ProductCategoryController:queryProductCategoryList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}query community info for page.");
			Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			List<ProductCategoryVo> productCategoryList = productCategoryManager.queryProductCategoryListPage(name, (pageNumber - 1) * pageSize, pageSize,businessId);
			
			map.put("productCategoryList", productCategoryList);
			map.put("total", productCategoryManager.countProductCategoryList(name,businessId));
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[ProductCategoryController:queryProductCategoryList]false to query productCategory list.", e);
		}
		return map;
	}
	
	@RequestMapping("/queryAllProductCategoryList")
	@ResponseBody
	public Object queryAllProductCategoryList(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			log.info("[ProductCategoryController:queryAllProductCategoryList]");
			Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("AllCategories", productCategoryManager.queryAllProductCategoryList(businessId));
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[ProductCategoryController:queryProductCategoryList]false to query productCategory list.", e);
		}
		return map;
	}
	
	@RequestMapping("/addProductCategory")
	@ResponseBody
	public Object addProductCategory(HttpServletRequest request,ProductCategory category,@RequestParam(value = "file", required = false) MultipartFile file){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			map.put("add", true);
			Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			log.info("[ProductCategoryController:addProductCategoryInfo]{name:" + category.getName()  +  ",parentId:" + category.getParentId()
			+  ",sortOrder:" + category.getSortOrder() + "，businessId:" + businessId + "}");
			
			category.setBusinessId(businessId);
			productCategoryManager.insert(category, file);
			map.put("success", true);
			
		}catch(Exception e){
			map.put("success", false);
			log.error("[ProductCategoryController:addProductCategoryInfo]false to add category.", e);
		}
		return map;
		
	}
	
	@RequestMapping("/queryProductCategoryById")
	@ResponseBody
	public Object queryProductCategoryById(HttpServletRequest request,@RequestParam("categoryId") Long categoryId){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			ProductCategory category = productCategoryManager.queryProductCategoryById(categoryId);
			Long parentId = category.getParentId();
			ProductCategory parentCategory = productCategoryManager.queryProductCategoryById(parentId);
			map.put("category", category);
			map.put("parentCategory", parentCategory);
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[ProductCategoryController:queryProductCategoryInfo]false to add category.", e);
		}
		return map;
	}
	
	//updateStatus
	@RequestMapping("/updateStatus")
	@ResponseBody
	public Object updateStatusById(HttpServletRequest request,@RequestParam("categoryId") Long categoryId){
		Map<String, Object> map = new HashMap<String, Object>();
		try{			
			productCategoryManager.changeStatus(categoryId);
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
		}
		return map;
		
	}
	//editProductCategory
	@RequestMapping("/editProductCategory")
	@ResponseBody
	public Object editProductCategory(HttpServletRequest request,ProductCategory category,@RequestParam(value = "file", required = false) MultipartFile file){
		Map<String, Object> map = new HashMap<String,Object>();
		try{
			map.put("edit", true);
			log.info("[ProductCategoryController:editProductCategoryInfo]{name:" + category.getName()  +  ",parentId:" + category.getParentId()
			+  ",sortOrder:" + category.getSortOrder() + "}");
			productCategoryManager.updateById(category,file);
			map.put("success", true);
			
		}catch(Exception e){
			map.put("success", false);
			log.error("[ProductCategoryController:addProductCategoryInfo]false to add category.", e);
		}
		return map;
	}


}
