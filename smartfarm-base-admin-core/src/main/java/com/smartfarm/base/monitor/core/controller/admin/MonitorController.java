package com.smartfarm.base.monitor.core.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.monitor.core.entity.GatewayNode;
import com.smartfarm.base.monitor.core.entity.SensorInfo;
import com.smartfarm.base.monitor.core.entity.SensorNode;
import com.smartfarm.base.monitor.core.manager.CameraMessageManager;
import com.smartfarm.base.monitor.core.manager.GatewayNodeManager;
import com.smartfarm.base.monitor.core.manager.SensorInfoManager;
import com.smartfarm.base.monitor.core.manager.SensorNodeManager;

/**
 * 监控层
 * @author lyq
 *
 */
@Controller
@RequestMapping("/monitor")
public class MonitorController {
	
	@Resource
	private SensorInfoManager sensorInfoManager;
	
	@Resource
	private GatewayNodeManager gatewayNodeManager;
	
	@Resource
	private SensorNodeManager sensorNodeManager;
	
	@Resource
	private CameraMessageManager cameraMessageManager;
	
	private Logger log = Logger.getLogger(MonitorController.class);
	
	
	/**
	 * 分页查询传感器监控
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryMonitorsensorInfoList")
	@ResponseBody
	public Object queryMonitorsensorInfoList(Integer pageSize, Integer pageNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:queryMonitorsensorInfoList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}query SensorInfo for page.");
			map.put("sensorInfoList", sensorInfoManager.queryMonitorsensorInfoList((pageNumber - 1) * pageSize, pageSize));
			map.put("total", sensorInfoManager.queryCountMonitorsensorInfo());
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:queryMonitorsensorInfoList]false to query SensorInfo list.", e);
		}
		return map;
	}
	
	/**
	 * 新增
	 * @param info
	 * @return
	 */
	@RequestMapping("/addSensorInfo")
	@ResponseBody
	public Object addSensorInfo(SensorInfo info) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:addSensorInfo]add SensorInfo for page.");
        	sensorInfoManager.insert(info);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:addSensorInfo]false to add SensorInfo list.", e);
		}
		return map;
	}
	
	/**
	 * 根据id得到监控传感器
	 * @param id
	 * @return
	 */
	@RequestMapping("/querySensorInfoById")
	@ResponseBody
	public Object querySensorInfoById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:querySensorInfoById]{id:" + id + "}query SensorInfo byId.");
        	map.put("sensorInfo", sensorInfoManager.querySensorInfoById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:querySensorInfoById]false to query SensorInfo byId.", e);
		}
		return map;
	}
	
	/**
	 * 编辑
	 * @param sensorInfo
	 * @return
	 */
	@RequestMapping("/editSensorInfoById")
	@ResponseBody
	public Object editSensorInfoById(SensorInfo sensorInfo){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:editSensorInfoById]edit SensorInfo byId.");
        	sensorInfoManager.eidtSensorInfoById(sensorInfo);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:editSensorInfoById]false to edit SensorInfo byId.", e);
		}
		return map;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteSensorInfoById")
	@ResponseBody
	public Object deleteSensorInfoById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:deleteSensorInfoById]{id:" + id + "}delete SensorInfo byId.");
        	sensorInfoManager.deleteSensorInfoById(id);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:deleteSensorInfoById]false to delete SensorInfo byId.", e);
		}
		return map;
	}
	
	/**
	 * 分页查询传感器网关
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryMonitorGatewayList")
	@ResponseBody
	public Object queryMonitorGatewayList(Integer pageSize, Integer pageNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:queryMonitorGatewayList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}query gatewayNode for page.");
			map.put("sensorGatewayList", gatewayNodeManager.queryMonitorGatewayList((pageNumber - 1) * pageSize, pageSize));
			map.put("total", gatewayNodeManager.queryCountMonitorGatewayList());
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:queryMonitorGatewayList]false to query gatewayNode list.", e);
		}
		return map;
	}
	
	/**
	 * 新增传感器网关
	 * @param info
	 * @return
	 */
	@RequestMapping("/addSensorGateway")
	@ResponseBody
	public Object addSensorGateway(GatewayNode gatewayNode) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:addSensorGateway]add gatewayNode for page.");
        	gatewayNodeManager.insert(gatewayNode);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:addSensorGateway]false to add gatewayNode list.", e);
		}
		return map;
	}
	
	/**
	 * 根据id得传感器网关
	 * @param id
	 * @return
	 */
	@RequestMapping("/querySensorGatewayById")
	@ResponseBody
	public Object querySensorGatewayById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:querySensorGatewayById]{id:" + id + "}query gatewayNode byId.");
        	map.put("sensorGatewayInfo", gatewayNodeManager.selectById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:querySensorGatewayById]false to query gatewayNode byId.", e);
		}
		return map;
	}
	
	/**
	 * 编辑传感器网关
	 * @param sensorInfo
	 * @return
	 */
	@RequestMapping("/editSensorGatewayById")
	@ResponseBody
	public Object editSensorGatewayById(GatewayNode node){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:editSensorGatewayById]edit gatewayNode byId.");
        	gatewayNodeManager.updateById(node);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:editSensorGatewayById]false to edit gatewayNode byId.", e);
		}
		return map;
	}
	
	/**
	 * 删除传感器网关
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteSensorGatewayById")
	@ResponseBody
	public Object deleteSensorGatewayById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:deleteSensorGatewayById]{id:" + id + "}delete gatewayNode byId.");
        	gatewayNodeManager.deleteById(id);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:deleteSensorGatewayById]false to delete gatewayNode byId.", e);
		}
		return map;
	}
	
	/**
	 * 分页查询传感器节点
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryMonitorsensorNodeList")
	@ResponseBody
	public Object queryMonitorsensorNodeList(Integer pageSize, Integer pageNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:queryMonitorsensorNodeList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}query SensorNode for page.");
			map.put("sensorNodeList", sensorNodeManager.queryMonitorsensorNodeList((pageNumber - 1) * pageSize, pageSize));
			map.put("total", sensorNodeManager.queryCountMonitorsensorNodeList());
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:queryMonitorsensorNodeList]false to query SensorNode list.", e);
		}
		return map;
	}
	
	/**
	 * 查询传感器信息和网关
	 * @return
	 */
	@RequestMapping("/querySensorInfoAndGateway")
	@ResponseBody
	public Object querySensorInfoAndGateway(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:querySensorInfoAndGateway]query SensorInfo and gatewayNode for page.");
			map.put("allSensorInfoList", sensorInfoManager.queryAllSensorInfo());
			map.put("allGatewayList", gatewayNodeManager.queryAllGatewayNode());
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:querySensorInfoAndGateway]false to query SensorInfo and gatewayNode list.", e);
		}
		return map;
	}
	
	/**
	 * 插入传感器节点
	 * @param sensorNode
	 * @return
	 */
	@RequestMapping("/addSensorNode")
	@ResponseBody
	public Object addSensorNode(SensorNode sensorNode) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:addSensorNode]add sensorNode for page.");
        	sensorNodeManager.insert(sensorNode);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:addSensorNode]false to add sensorNode list.", e);
		}
		return map;
	}
	
	/**
	 * 根据id得传感器节点
	 * @param id
	 * @return
	 */
	@RequestMapping("/querySensorNodeById")
	@ResponseBody
	public Object querySensorNodeById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:querySensorNodeById]{id:" + id + "}query sensorNode byId.");
        	map.put("sensorNodeInfo", sensorNodeManager.querySensorNodeById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:querySensorNodeById]false to query sensorNode byId.", e);
		}
		return map;
	}
	
	/**
	 * 编辑传感器节点
	 * @param node
	 * @return
	 */
	@RequestMapping("/editSensorNodeById")
	@ResponseBody
	public Object editSensorNodeById(SensorNode node){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:editSensorNodeById]edit sensorNode byId.");
        	sensorNodeManager.updateById(node);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:editSensorNodeById]false to edit sensorNode byId.", e);
		}
		return map;
	}
	
	/**
	 * 删除传感器节点
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteSensorNodeById")
	@ResponseBody
	public Object deleteSensorNodeById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:deleteSensorNodeById]{id:" + id + "}delete gatewayNode byId.");
        	sensorNodeManager.deleteById(id);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:deleteSensorNodeById]false to delete gatewayNode byId.", e);
		}
		return map;
	}
	
	/**
	 * 根据id返回直播地址
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryCameraLiveAddressById")
	@ResponseBody
	public Object queryCameraLiveAddressById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:queryCameraLiveAddressById]{id:" + id + "}query liveAddress byId.");
        	map.put("liveAddress", cameraMessageManager.queryCameraLiveAddressById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:queryCameraLiveAddressById]false to query liveAddress byId.", e);
		}
		return map;
	}
	
	/**
	 * 抓拍
	 * @param id
	 * @return
	 */
	@RequestMapping("/snapPhoto")
	@ResponseBody
	public Object snapPhoto(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:snapPhoto]{id:" + id + "}success snapPhoto byId.");
        	cameraMessageManager.snapPhoto(id);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:snapPhoto]false to snapPhoto byId.", e);
		}
		return map;
	}
	
	/**
	 * 查询摄像头列表
	 * @return
	 */
	@RequestMapping("/queryCameraMessageList")
	@ResponseBody
	public Object queryCameraMessageList(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorController:queryCameraMessageList]query CameraMessage.");
        	map.put("cameraList", cameraMessageManager.queryCameraMessageList());
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorController:queryCameraMessageList]false to query CameraMessage.", e);
		}
		return map;
	}
}
