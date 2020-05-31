package com.smartfarm.base.shop.core.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.shop.core.manager.ProductLogisticsTemplateManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.shop.core.entity.ProductSku;
import com.smartfarm.base.shop.core.manager.ProductSkuManager;


@Controller
@RequestMapping("/productSku")
public class ProductSkuController {
	private Logger log = Logger.getLogger(ProductSkuController.class);
	@Resource
	private ProductSkuManager productSkuManager;
	@Resource
	private ProductLogisticsTemplateManager productLogisticsTemplateManager;
	
	@RequestMapping("/queyProductSkuList")
	@ResponseBody
	public Object queyProductSkuList(HttpServletRequest request, @RequestParam("productId")Long productId, Integer pageSize, Integer pageNumber){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[productSkuController:queyProductSkuList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber +",productId:" +productId+ "}.");
			Long farmId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("productSkuList", productSkuManager.selectAllProductSku(productId, (pageNumber - 1) * pageSize,  pageSize));
			map.put("templates",productLogisticsTemplateManager.queryAllTemplate(farmId));
			log.info("[productSkuController:queyProductSkuList] enter"+"name:"+productId+"pageSize:"+pageSize+"pageNumber:"+pageNumber);
			
			map.put("total", productSkuManager.countProductSkuList(productId));
			
			log.info("[productSkuProductController:queyProductSkuList] enter"+"name:"+productId+"pageSize:"+pageSize+"pageNumber:"+pageNumber);
			map.put("success", true);
		}
		catch(Exception e){
			log.info("[productSkuController:queyProductSkuList] false");		
			map.put("success", false);		
		}
		return map;
	}

	@RequestMapping("/addProductSku")
	@ResponseBody
	public Object addProductSku(HttpServletRequest request, ProductSku productSku){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[productSkuController:addProductSku]{productSku:" + productSku.toString() + "}.");
			
			productSkuManager.addProductSku(productSku);
			map.put("success", true);
		}
		catch(Exception e){
			log.info("[productSkuController:addProductSku] false");		
			map.put("success", false);		
		}
		return map;
	}
	
	
	@RequestMapping("/queryProductSkuById")
	@ResponseBody
	public Object queryProductInfoById(Long id){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[ProductSkuController:queryProductSkuById]{queryProductSkuById}"+id);
			
			map.put("productSku", productSkuManager.selectById(id));
			
			log.info("[ProductSkuController:queryProductSkuById]{queryProductSkuById} get!");
			map.put("success", true);
		}
		catch(Exception e){
			log.info("[ProductSkuController:queryProductSkuById]{queryProductSkuById} false!");
			map.put("success", false);	
		}
		return map;
	}
		
	@RequestMapping("/editProductSku")
	@ResponseBody
	public Object editProductSku(HttpServletRequest request, ProductSku productSku){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[productSkuController:addProductSku]{productSku:" + productSku.toString() + "}.");
			productSkuManager.editProductSku(productSku);
			map.put("success", true);		
		}
		catch(Exception e){
			map.put("success", false);
		}
		return map;
	}
	
	@RequestMapping("/changeStatus")
	@ResponseBody
	public Object changeNew(@RequestParam("productSkuId")Long id){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			productSkuManager.changeStatus(id);
			map.put("success", true);
		}
		catch(Exception e){
			map.put("success", false);
		
		}
		return map;
	}
}
