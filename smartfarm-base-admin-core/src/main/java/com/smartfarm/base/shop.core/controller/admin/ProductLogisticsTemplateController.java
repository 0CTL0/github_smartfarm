package com.smartfarm.base.shop.core.controller.admin;

import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.shop.core.entity.ProductLogisticsTemplate;
import com.smartfarm.base.shop.core.entity.ProductTemplateDetail;
import com.smartfarm.base.shop.core.manager.ProductLogisticsTemplateManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/logisticsTemplate")
public class ProductLogisticsTemplateController {
	private Logger log = Logger.getLogger(ProductLogisticsTemplateController.class);
	@Resource
	private ProductLogisticsTemplateManager productLogisticsTemplateManager;

	/**
	 * 查询模板列表
	 * @param request
	 * @param name
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/getPageList")
	@ResponseBody
	public Object queyProductSkuList(HttpServletRequest request, @RequestParam("name")String name, Integer pageSize, Integer pageNumber){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[productSkuController:getPageList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber +",name:" +name+ "}.");
			Long farmId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("logisticsTemplateManagerList", productLogisticsTemplateManager.queryPageList(name, (pageNumber - 1) * pageSize,  pageSize,farmId));
			map.put("total", productLogisticsTemplateManager.queryPageListTotal(name,farmId));
			map.put("success", true);
		}
		catch(Exception e){
			log.error("[productSkuController:getPageList]false", e);
			map.put("success", false);		
		}
		return map;
	}

	/**
	 * 添加模板
	 * @param request
	 * @param productLogisticsTemplate
	 * @return
	 */
	@RequestMapping("/addLogisticsTemplate")
	@ResponseBody
	public Object addLogisticsTemplate(HttpServletRequest request, ProductLogisticsTemplate productLogisticsTemplate){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[productSkuController:addLogisticsTemplate]");
			Long farmId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			productLogisticsTemplate.setFarmId(farmId);
			productLogisticsTemplateManager.addProductLogisticsTemplate(productLogisticsTemplate);
			map.put("success", true);
		}
		catch(Exception e){
			log.error("[productSkuController:addLogisticsTemplate]false", e);
			map.put("success", false);		
		}
		return map;
	}

	/**
	 * 查询模板
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryLogisticsTemplate")
	@ResponseBody
	public Object getLogisticsTemplate(Long  id){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[ProductSkuController:queryLogisticsTemplate]");
			map.put("logisticsTemplate", productLogisticsTemplateManager.queryById(id));
			map.put("success", true);
		}
		catch(Exception e){
			log.error("[productSkuController:queryLogisticsTemplate]false", e);
			map.put("success", false);
		}
		return map;
	}

	/**
	 * 编辑模板
	 * @param productLogisticsTemplate
	 * @return
	 */
	@RequestMapping("/editLogisticsTemplate")
	@ResponseBody
	public Object editLogisticsTemplate(ProductLogisticsTemplate productLogisticsTemplate){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[ProductSkuController:editLogisticsTemplate]");
			map.put("productSku", productLogisticsTemplateManager.editProductLogisticsTemplate(productLogisticsTemplate));
			map.put("success", true);
		}
		catch(Exception e){
			log.error("[productSkuController:editLogisticsTemplate]false", e);
			map.put("success", false);	
		}
		return map;
	}


	/**
	 * 查询模板详情配置列表
	 * @param templateId
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryTemplateDetailList")
	@ResponseBody
	public Object queryTemplateDetailList( @RequestParam("templateId")Long templateId, Integer pageSize, Integer pageNumber){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[productSkuController:queryTemplateDetailList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber +",templateId:" +templateId+ "}.");
			map.put("templateDetailList", productLogisticsTemplateManager.queryTemplateDetailList((pageNumber - 1) * pageSize,  pageSize,templateId));
			map.put("total", productLogisticsTemplateManager.queryTemplateDetailTotal(templateId));
			map.put("success", true);
		}
		catch(Exception e){
			log.error("[productSkuController:queryTemplateDetailList]false", e);
			map.put("success", false);
		}
		return map;
	}


	@RequestMapping("/addTemplateDetail")
	@ResponseBody
	public Object addTemplateDetail(ProductTemplateDetail productTemplateDetail){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[productSkuController:addTemplateDetail]");
			productLogisticsTemplateManager.addProductTemplateDetail(productTemplateDetail);
			map.put("success", true);
		}
		catch(Exception e){
			log.error("[productSkuController:addTemplateDetail]false", e);
			map.put("success", false);
		}
		return map;
	}


	@RequestMapping("/queryProductTemplateDetail")
	@ResponseBody
	public Object queryProductTemplateDetail(Long  id){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[ProductSkuController:queryProductTemplateDetail]");
			map.put("templateDetail", productLogisticsTemplateManager.queryProductTemplateDetail(id));
			map.put("success", true);
		}
		catch(Exception e){
			log.error("[productSkuController:queryProductTemplateDetail]false", e);
			map.put("success", false);
		}
		return map;
	}


	@RequestMapping("/editTemplateDetail")
	@ResponseBody
	public Object editTemplateDetail(ProductTemplateDetail productTemplateDetail){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[ProductSkuController:editTemplateDetail]productTemplateDetail:"+productTemplateDetail);
			productLogisticsTemplateManager.editProductTemplateDetail(productTemplateDetail);
			map.put("success", true);
		}
		catch(Exception e){
			log.error("[productSkuController:editTemplateDetail]false", e);
			map.put("success", false);
		}
		return map;
	}



}
