package com.smartfarm.base.shop.core.controller.front;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.shop.core.entity.ProductCategory;
import com.smartfarm.base.shop.core.entity.ProductSku;
import com.smartfarm.base.shop.core.manager.ProductCategoryManager;
import com.smartfarm.base.shop.core.manager.ProductInfoManager;
import com.smartfarm.base.shop.core.manager.ProductSkuManager;


@Controller
@RequestMapping("/productF")
public class ProductControllerF {

	private Logger log = Logger.getLogger(ProductControllerF.class);
	@Resource
	private ProductCategoryManager productCategoryManager;
	@Resource
	private ProductInfoManager productInfoManager;
	@Resource
	private ProductSkuManager productSkuManager;
	
	
	/**
	 * 查询产品主页
	 * @return
	 */
	@RequestMapping("/queryShopIndex")
    @ResponseBody
    public Object queryShopIndex(HttpServletRequest request) {

		Short type = ProductSku.TYPE_SHOPPING;
		
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
        	log.info("[ProductController:queryProductIndex]query shop index. type:" + type + ",businessId:" + businessId + ",");
        	
        	map.put("categoryList", productCategoryManager.queryCategoryByLevelAndStatus(ProductCategory.LEVEL_FIRST, ProductCategory.STATUS_ENABLE,businessId));
        	map.put("newList", productInfoManager.queryNewProductList(type,businessId));
        	map.put("hotList", productInfoManager.queryHotProductList(type,businessId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[ProductController:queryProductIndex]false to query shop index.",e);
        }
        return map;
	}

	/**
	 * 查询产品主页
	 * @return
	 */
	@RequestMapping("/queryShopIndexForCaiyuan")
	@ResponseBody
	public Object queryShopIndexForCaiyuan(HttpServletRequest request) {

		Short type = ProductSku.TYPE_SHOPPING;

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			log.info("[ProductController:queryShopIndexForCaiyuan]query shop index. type:" + type + ",businessId:" + businessId + ",");

			map.put("list1", productInfoManager.queryProductListByCategoryId(40L,type));
			map.put("list2", productInfoManager.queryProductListByCategoryId(46L,type));
			map.put("list3", productInfoManager.queryProductListByCategoryId(47L,type));
			map.put("success",true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProductController:queryShopIndexForCaiyuan]false to query shop index.",e);
		}
		return map;
	}

	/**
	 * 查询积分产品主页
	 * @return
	 */
	@RequestMapping("/queryIntegralIndex")
    @ResponseBody
    public Object queryIntegralIndex(HttpServletRequest request) {
		
		Short type = ProductSku.TYPE_INTEGRAL;
				
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[ProductController:queryProductIndex]query Integral index. type =[" + type + "]");
        	Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
        	map.put("integralProductList", productInfoManager.queryIntegralProductList(businessId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[ProductController:queryProductIndex]false to query Integral index.",e);
        }
        return map;
	}

	/**
	 * 查询产品列表
	 * @return
	 */
	@RequestMapping("/productList")
    @ResponseBody
    public Object productList(Long categoryId, Short type) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[ProductController:productList]query product list. categoryId = [" + categoryId + "] type=[" + type + "]");
        	map.put("productList", productInfoManager.queryProductListByCategoryId(categoryId, type));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[ProductController:queryProductIndex]false to query product list.",e);
        }
        return map;
	}
	
	/**
	 * 查询产品列表
	 * @return
	 */
	@RequestMapping("/queryShopCart")
    @ResponseBody
    public Object queryShopCart(Long categoryId) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[ProductController:queryShopCart]query shop cart.");
        	map.put("productList", productInfoManager.queryProductListByCategoryId(categoryId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[ProductController:queryShopCart]false to query shop cart.",e);
        }
        return map;
	}
	
	/**
	 * 查詢产品详细
	 * @param productId
	 * @return
	 */
	@RequestMapping("/queryProductInfo")
    @ResponseBody
    public Object queryProductInfo(Long productId,Short type) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[ProductController:queryProductInfo]query product info.");
        	map.put("productInfo", productInfoManager.selectById(productId));
        	map.put("skuList", productSkuManager.querySkuByProductIdAndType(productId,type));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[ProductController:queryProductInfo]false to query product info.",e);
        }
        return map;
	}



	/**
	 * 查询阳台农夫的商城首页
	 * @return
	 */
	@RequestMapping("/queryYTNFShopIndex")
	@ResponseBody
	public Object queryYTNFShopIndex(HttpServletRequest request) {
		Short type = ProductSku.TYPE_SHOPPING;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			log.info("[ProductController:queryYTNFShopIndex]query shop index. type:" + type + ",businessId:" + businessId + ",");
			map.put("categoryVoList", productCategoryManager.queryTwoLevelCategory(ProductCategory.STATUS_ENABLE,businessId));
			map.put("success",true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProductController:queryYTNFShopIndex]false to query shop index.",e);
		}
		return map;
	}



}
