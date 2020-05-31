package com.smartfarm.base.shop.core.controller.front;

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
import com.smartfarm.base.shop.core.manager.ActivityManager;
import com.smartfarm.base.shop.core.manager.AdvanceNoticeManager;
import com.smartfarm.base.shop.core.manager.ProductInfoManager;

@Controller
@RequestMapping("/advanceNoticeF")
public class AdvanceNoticeControllerF {
	private Logger log = Logger.getLogger(AdvanceNoticeControllerF.class);
	@Resource
	private AdvanceNoticeManager advanceNoticeManager;
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
	public Object queryList(HttpServletRequest request,Short type){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[AdvanceNoticeController:queryList]query advanceNotice list.");
			Long farmId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("list",advanceNoticeManager.queryUseableAdvanceList(type, farmId));
			map.put("success", true);
		}
		catch(Exception e){
			log.error("[AdvanceNoticeController:queryList]false to query advanceNotice list.",e);
			
			map.put("success", false);
			
		}
		return map;
	}
	
	/**
	 * 查询首页推荐
	 * @param request
	 * @return
	 */
	@RequestMapping("/advanceIndex")
	@ResponseBody
	public Object advanceIndex(HttpServletRequest request){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[AdvanceNoticeController:advanceIndex]query advance list.");
			Long farmId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("productList",productInfoManager.queryIndexProduct(farmId));
			map.put("farmList",farmManager.queryTunnelForAdvance(farmId));
			map.put("activityList",activityManager.queryActivityForAdvance(farmId));
			map.put("success", true);
		}catch(Exception e){
			log.error("[AdvanceNoticeController:advanceIndex]false to query advance list.",e);
			map.put("success", false);
		}
		return map;
	}
}
