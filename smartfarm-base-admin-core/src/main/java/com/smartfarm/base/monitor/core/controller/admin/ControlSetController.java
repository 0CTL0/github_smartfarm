package com.smartfarm.base.monitor.core.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.monitor.core.entity.MonitorControlSet;
import com.smartfarm.base.monitor.core.entity.MonitorControlSetCondition;
import com.smartfarm.base.monitor.core.entity.MonitorControlSetNode;
import com.smartfarm.base.monitor.core.manager.MonitorControlSetManager;
import com.smartfarm.base.monitor.core.service.EventService;

@Controller
@RequestMapping("/controlSet")
public class ControlSetController {

	private Logger log = Logger.getLogger(ControlSetController.class);
	
	@Resource
	private MonitorControlSetManager monitorControlSetManager;
	@Resource
	private EventService eventService;
	
	/**
	 * 分页查询控制器
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/controlSetPageList")
	@ResponseBody
	public Object controlSetPageList(HttpServletRequest request, Integer pageSize, Integer pageNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ControlSetController:controlSetPageList]query control set list for page.");
			Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("list", monitorControlSetManager.queryPageControlSetList(businessId, (pageNumber - 1) * pageSize, pageSize));
			map.put("total", monitorControlSetManager.countPageControlSetList(businessId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ControlSetController:controlSetPageList]false to query control set list for page.", e);
		}
		return map;
	}
	
	
	@RequestMapping("/addControlSet")
	@ResponseBody
	public Object addControlSet(HttpServletRequest request, MonitorControlSet monitorControlSet, String controlSetNodeJson, String controlConditionJson) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ControlSetController:addControlSet]add control set.");
			Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			monitorControlSet.setBusinessId(businessId);
			
			JSONArray ja = JSONArray.fromObject(controlSetNodeJson);
        	@SuppressWarnings("unchecked")
        	List<MonitorControlSetNode> nodeList = (List<MonitorControlSetNode>) JSONArray.toCollection(ja,MonitorControlSetNode.class);
        	
        	JSONArray cja = JSONArray.fromObject(controlConditionJson);
        	@SuppressWarnings("unchecked")
        	List<MonitorControlSetCondition> conditionList = (List<MonitorControlSetCondition>) JSONArray.toCollection(cja,MonitorControlSetCondition.class);
        	
        	monitorControlSetManager.addControlSet(monitorControlSet, nodeList, conditionList);
			
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ControlSetController:addControlSet]false to add control set.", e);
		}
		return map;
	}
	
	/**
	 * 更新状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("/updateStatus")
	@ResponseBody
	public Object updateStatus(Long id, Short status) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ControlSetController:updateStatus]update control set.");
			monitorControlSetManager.updateStatus(id, status);
			eventService.resetControlSet();
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ControlSetController:updateStatus]false to update control set.", e);
		}
		return map;
	}
}
