package com.smartfarm.base.monitor.core.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.admin.core.manager.AdminManager;
import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.monitor.core.entity.SensorNode;
import com.smartfarm.base.monitor.core.manager.GatewayNodeManager;
import com.smartfarm.base.monitor.core.manager.MonitorRealDataManager;
import com.smartfarm.base.monitor.core.manager.SensorNodeManager;
import com.smartfarm.base.monitor.core.util.SersorParameterUtil;

@Controller
@RequestMapping("/farm")
public class FarmerController {
	
	private Logger log = Logger.getLogger(FarmerController.class);
	
	@Resource
	private GatewayNodeManager gatewayNodeManager;
	
	@Resource
	private SensorNodeManager sensorNodeManager;
	
	@Resource
	private AdminManager adminUserManager;
	
	@Resource
	private MonitorRealDataManager monitorRealDataManager;
	/**
	 * 农场主查询所拥有的网关
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryFarmerMonitorGatewayList")
	@ResponseBody
	public Object queryFarmerMonitorGatewayList(HttpServletRequest request,Integer pageSize, Integer pageNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[FarmerController:queryFarmerMonitorGatewayList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}query base info for page.");
        	Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("sensorGatewayList", gatewayNodeManager.queryFarmerMonitorGatewayList(businessId,(pageNumber - 1) * pageSize, pageSize));
			map.put("total", gatewayNodeManager.queryCountFarmerMonitorGateway(businessId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmerController:queryFarmerMonitorGatewayList]false to query base list.", e);
		}
		return map;
	}
	
	/**
	 * 农场主查询所拥有的的传感节点
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryFarmerSensorNodeList")
	@ResponseBody
	public Object queryFarmerSensorNodeList(HttpServletRequest request,Integer pageSize, Integer pageNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[FarmerController:queryFarmerSensorNodeList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}query base info for page.");
        	Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("sensorNodeList", sensorNodeManager.queryFarmerSensorNodeList(businessId,(pageNumber - 1) * pageSize, pageSize));
			map.put("total", sensorNodeManager.queryCountFarmerSensorList(businessId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmerController:queryFarmerSensorNodeList]false to query base list.", e);
		}
		return map;
	}
	
	/**
	 * 农场主根据id得传感器节点
	 * @param id
	 * @return
	 */
	@RequestMapping("/querySensorNodeById")
	@ResponseBody
	public Object querySensorNodeById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[FarmerController:querySensorNodeById]{id:" + id + "}query sensorNode byId.");
        	map.put("sensorNodeInfo", sensorNodeManager.querySensorNodeById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmerController:querySensorNodeById]false to query sensorNode byId.", e);
		}
		return map;
	}
	
	/**
	 * 农场主编辑传感器节点
	 * @param node
	 * @return
	 */
	@RequestMapping("/editSensorNodeById")
	@ResponseBody
	public Object editSensorNodeById(SensorNode node){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[FarmerController:editSensorNodeById]edit sensorNode byId.");
        	sensorNodeManager.updateById(node);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmerController:editSensorNodeById]false to edit sensorNode byId.", e);
		}
		return map;
	}
	
	/**
	 * 农场主查询折线图数据
	 * @param request
	 * @param range
	 * @return
	 */
	@RequestMapping("/queryFarmerRealDataList")
	@ResponseBody
	public Object queryFarmerRealDataList(HttpServletRequest request,String range) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[FarmerController:queryFarmerRealDataList]query MonitorRealData for page." + range);
        	//获取用户信息
        	Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
        	//封装土壤网关id数据
			List<Long> gatewayIds = new ArrayList<Long>();
			gatewayIds.add((Long) SersorParameterUtil.getShipName("GATEWAY_ONE"));
        	if("2".equals(range)){
        		map.put("realDataList", monitorRealDataManager.queryFarmerRealDataListWithWeek(businessId,gatewayIds));
        	}else if ("3".equals(range)) {
        		map.put("realDataList", monitorRealDataManager.queryFarmerRealDataListWithMonth(businessId,gatewayIds));
			}else {
				map.put("realDataList", monitorRealDataManager.queryFarmerRealDataListWithToday(businessId,gatewayIds,DateUtil.formatCurrentDate()));
			}
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmerController:queryFarmerRealDataList]false to query MonitorRealData list.", e);
		}
		return map;
	}
}
