package com.smartfarm.base.shop.core.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.admin.core.manager.FarmManager;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.shop.core.entity.IndexAdvance;
import com.smartfarm.base.shop.core.manager.ActivityManager;
import com.smartfarm.base.shop.core.manager.IndexAdvanceManager;
import com.smartfarm.base.shop.core.manager.ProductInfoManager;

@Controller
@RequestMapping("/indexAdvance")
public class IndexAdvanceController {
	private Logger log = Logger.getLogger(IndexAdvanceController.class);
	@Resource
	private IndexAdvanceManager indexAdvanceManager;
	@Resource
	private ProductInfoManager productInfoManager;
	@Resource
	private FarmManager farmManager;
	@Resource
	private ActivityManager activityManager;
	/**
	 * 查询列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryList")
	@ResponseBody
	public Object queryList(HttpServletRequest request){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[IndexAdvanceController:queryList]query advanceNotice list.");
			Long farmId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("list",indexAdvanceManager.queryAllList(farmId));
			map.put("success", true);
		}
		catch(Exception e){
			log.error("[IndexAdvanceController:queryList]false to query advanceNotice list.",e);
			
			map.put("success", false);
			
		}
		return map;
	}
	
	/**
	 * 添加广告栏
	 * @param request
	 * @return
	 */
	@RequestMapping("/addAdvance")
	@ResponseBody
	public Object addAdvance(HttpServletRequest request, IndexAdvance indexAdvance){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[IndexAdvanceController:addAdvance]add advance notice.");
			Long farmId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			indexAdvance.setFarmId(farmId);
			indexAdvance.setStatus((short)1);
			indexAdvanceManager.insert(indexAdvance);
			map.put("success", true);
		}catch(Exception e){
			log.error("[IndexAdvanceController:addAdvance]false to add advance notice.", e);
			map.put("success", false);
		}
		
		return map;
	}
	
	/**
	 * 修改广告栏
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateById")
	@ResponseBody
	public Object updateById(IndexAdvance indexAdvance){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[IndexAdvanceController:updateById]update advance .");
			indexAdvanceManager.updateById(indexAdvance);
			
			map.put("success", true);
		}catch(Exception e){
			log.error("[IndexAdvanceController:updateById]false to update advance .", e);
			map.put("success", false);
		}
		
		return map;
	}
	
	@RequestMapping("/queryAdvanceForAdd")
	@ResponseBody
	public Object queryAdvanceForAdd(HttpServletRequest request){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[IndexAdvanceController:queryAdvanceForAdd]query advance for add.");
			Long farmId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("productList",productInfoManager.queryProductForAdvanceAdd(farmId));
			map.put("farmList",farmManager.queryTunnelForAdvanceAdd(farmId));
			map.put("activityList",activityManager.queryActivityForAdvanceAdd(farmId));
			map.put("success", true);
		}catch(Exception e){
			log.error("[IndexAdvanceController:queryAdvanceForAdd]false to query advance for add.", e);
			map.put("success", false);
		}
		
		return map;
	}
}
