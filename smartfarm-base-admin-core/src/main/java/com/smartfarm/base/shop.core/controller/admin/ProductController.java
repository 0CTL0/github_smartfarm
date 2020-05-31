package com.smartfarm.base.shop.core.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.shop.core.manager.ProductCategoryManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.shop.core.entity.ProductInfo;
import com.smartfarm.base.shop.core.manager.ProductInfoManager;


@Controller
@RequestMapping("/product")
public class ProductController {
	
	private Logger log = Logger.getLogger(ProductController.class);
	@Resource
	private ProductInfoManager productInfoManager;
	@Resource
	private ProductCategoryManager productCategoryManager;
	
	/**
	 * 分页查询商品列表
	 * @return
	 */
	@RequestMapping("/queyProductInfoList")
	@ResponseBody
	public Object queyProductInfoList(HttpServletRequest request, String name,Long categoryId, Integer pageSize, Integer pageNumber){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[ProductController:queyProductInfoList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber +",name:" + name +",categoryId:" + categoryId + "}.");
			Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("productList", productInfoManager.selectAllProductInfo(name,categoryId, (pageNumber - 1) * pageSize,  pageSize, businessId));
			map.put("total", productInfoManager.countProductInfoList(name,categoryId, businessId));
			map.put("categoryList", productCategoryManager.queryAllProductCategoryList( businessId));
			map.put("success", true);
		}
		catch(Exception e){
			log.error("[ProductController:queyProductInfoList] false",e);
			map.put("success", false);
		}
		return map;
	}
	
	/**
	 * 根据id查询商品
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryProductInfoById")
	@ResponseBody
	public Object queryProductInfoById(Long id){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[ProductController:addproductInfo]{queryProductInfoById}"+id);
			map.put("productInfo", productInfoManager.selectVoById(id));
			map.put("success", true);
		}
		catch(Exception e){
			log.info("[ProductController:queryProductInfoById]{queryProductInfoById} false!");
			map.put("success", false);	
		}
		return map;
	}

	/**
	 * 添加商品
	 * @param productInfo
	 * @return
	 */
	@RequestMapping("/addProductInfo")
	@ResponseBody
	public Object addProductInfo(HttpServletRequest request, ProductInfo productInfo, 
			@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam(value = "file2", required = false) MultipartFile file2,
			@RequestParam(value = "mainImgs", required = false) MultipartFile[] mainImgs,@RequestParam(value = "detailImgs", required = false) MultipartFile[] detailImgs){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			map.put("add", true);
			
			log.info("[ProductController:addproductInfo]{name:" + productInfo.getName()  + "}");
			Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			productInfo.setBusinessId(businessId);
			productInfoManager.insert(productInfo, file,file2,mainImgs,detailImgs);		
			
			map.put("success", true);
		}catch(Exception e){
			log.error("[ProductController:addproductInfo]false to add productInfo.", e);
			map.put("success", false);
		}
		
		return map;
	}
	
	/**
	 * 删除商品
	 * @param id
	 * @return
	 */
	@RequestMapping("/removeProductInfo")
	@ResponseBody
	public Object removeProductInof(Long id){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			productInfoManager.delectProductInfo(id);
			map.put("success", true);
		}
		catch(Exception e){
			map.put("success", false);
		
		}
		return map;
	}
	
	/**
	 * 编辑商品
	 * @param productInfo
	 * @return
	 */
	@RequestMapping("/editProductInfo")
	@ResponseBody
	public Object editProductInfo(HttpServletRequest request, ProductInfo productInfo,
			@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam(value = "file2", required = false) MultipartFile file2,
			@RequestParam(value = "mainImgs", required = false) MultipartFile[] mainImgs,@RequestParam(value = "detailImgs", required = false) MultipartFile[] detailImgs){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[ProductCategoryController:editProductInfo]{id:" + productInfo.getId()  +",name:" + productInfo.getName()  +  ",categoryId:" + productInfo.getCategoryId() + "}");
		
			productInfoManager.updateProductInfo(productInfo, file,file2,mainImgs,detailImgs);
			map.put("success", true);		
		}
		catch(Exception e){
			map.put("success", false);
		}
		return map;
	}
	
	/**
	 * 改变商品的状态
	 * @param id
	 * @return
	 */
	@RequestMapping("/changeStatus")
	@ResponseBody
	public Object changeStatus(@RequestParam("productInfoId")Long id){
		log.info("[ProductInfoController:changeStatus]id="+id);
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			productInfoManager.changeStatus(id);
			map.put("success", true);
		}
		catch(Exception e){
			map.put("success", false);
		
		}
		return map;
	}
	
	/**
	 * 获得所有的商家信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAllProductBusinessList")
	@ResponseBody
	public Object queryAllProductCategoryList(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			log.info("[ProductInfoController:queryAllProductInfoList]");
			map.put("AllBusiness", productInfoManager.queryAllProductBusinessList());
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[ProductCategoryController:queryProductInfoList]false to query productInfo list.", e);
		}
		return map;
	}
	
	/**
	 * 更改商品的新品状态
	 * @param id
	 * @return
	 */
	@RequestMapping("/changeNew")
	@ResponseBody
	public Object changeNew(@RequestParam("productId")Long id){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			productInfoManager.changeNew(id);
			map.put("success", true);
		}
		catch(Exception e){
			map.put("success", false);
		
		}
		return map;
	}
	
	/**
	 * 更改商品的新人气状态
	 * @param id
	 * @return
	 */
	@RequestMapping("/changeHot")
	@ResponseBody
	public Object changeHot(@RequestParam("productId")Long id){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			productInfoManager.changeHot(id);
			map.put("success", true);
		}
		catch(Exception e){
			map.put("success", false);
		
		}
		return map;
	}
}
