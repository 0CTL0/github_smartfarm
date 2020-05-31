package com.smartfarm.base.monitor.core.controller.admin;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.monitor.core.entity.ControlNodeDetails;
import com.smartfarm.base.monitor.core.manager.SensorInfoManager;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.admin.core.manager.AdminManager;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.monitor.core.entity.ControlNode;
import com.smartfarm.base.monitor.core.entity.ControlRecord;
import com.smartfarm.base.monitor.core.entity.GatewayNode;
import com.smartfarm.base.monitor.core.manager.ControlNodeManager;
import com.smartfarm.base.monitor.core.manager.ControlRecordManager;
import com.smartfarm.base.monitor.core.manager.GatewayNodeManager;

@Controller
@RequestMapping("/controlNode")
public class ControlNodeController {
	
	private Logger log = Logger.getLogger(ControlNodeController.class);

	@Resource
	private ControlNodeManager controlNodeManager;
	
	@Resource
	private ControlRecordManager controlRecordManager;
	
	@Resource
	private GatewayNodeManager gatewayNodeManager;

	@Resource
	private SensorInfoManager sensorInfoManager;

	@Resource
	private  AdminManager adminUserManager;
	
	/**
	 * 获取所有设备信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getControlNodes")
	@ResponseBody
	public Object getControlNodes(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ControlNodeController:getControlNodes]getControlNodes had been started.");
			log.info("[ControlNodeController:getControlNodes]sessionName:"+SessionUtil.getAttribute(request, "name")+ ".");
			List<ControlNodeDetails> controlNodesList = controlNodeManager.getControlNodesDetails();
			map.put("controlNodesList", controlNodesList);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ControlNodeController:getControlNodes]false to query controlNode list.", e);
		}
		return map;
	}
	
	/**
	 * 农场主查询设备节点
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFarmControlNodes")
	@ResponseBody
	public Object getFarmControlNodes(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
        	List<GatewayNode> gatewayNodes = gatewayNodeManager.queryMonitorGatewayIdByFarmer(businessId);
        	
			log.info("[ControlNodeController:getFarmControlNodes]getControlNodes had been started.");
			log.info("[ControlNodeController:getFarmControlNodes]sessionName:"+SessionUtil.getAttribute(request, "name")+ ".");
			List<ControlNode> controlNodesList = controlNodeManager.queryFarmerControlNodes(gatewayNodes);
			map.put("controlNodesList", controlNodesList);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ControlNodeController:getControlNodes]false to query controlNode list.", e);
		}
		return map;
	}
	
	@RequestMapping("/getSingleControlNode")
	@ResponseBody
	public Object getSingleControlNode(HttpServletRequest request,@Param("id") Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ControlNodeController:getSingleControlNode] id:" + id);
			ControlNode controlNode = controlNodeManager.getSingleControlNode(id);
			map.put("controlNode", controlNode);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ControlNodeController:getControlNodes]false to query controlNode list.", e);
		}
		return map;
	}
	
	@RequestMapping("/updateControlNode")
	@ResponseBody
	public Object updateControlNode(HttpServletRequest request,ControlNode controlNode,ControlRecord controlRecord){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProficientController:updateControlNode]{ controlNode:" + controlNode);
			log.info("[ProficientController:updateControlNode]{ controlNodeid:" + controlNode.getId());
			log.info("[ProficientController:updateControlNode]{ controlNodename:" + controlNode.getName());
			controlNodeManager.updateControlNodeById(controlNode);
			
			ControlNode controlNodeGet = controlNodeManager.getSingleControlNode(controlNode.getId());
			String gatewayCode = gatewayNodeManager.queryProductCodeById(controlNodeGet.getGatewayId());
			
			Date date = new Date();
			String pattern = "yyyyMMddHHmmss";//24小时制
			SimpleDateFormat sdft = new SimpleDateFormat(pattern);
			String dateStart = sdft.format(date);
			log.info("当前系统时间为: " + date + " .");
			log.info("当前系统时间的yyyyMMddhhmmss格式为: " + dateStart + " .");
			
			if(controlNode.getOperateStatus()!=2){
				controlRecord.setStartTime(dateStart);
				controlRecord.setEndTime("暂无");
				controlRecord.setDuration("0天0时0分钟");
				controlRecord.setOperator((Long) SessionUtil.getAttribute(request, SessionUtil.ADMIN_ID));
				
				log.info("[ControlRecordController:addControlRecord] controlRecordId : " + controlRecord.getNodeId() + ".");
				controlRecordManager.addControlRecord(controlRecord);
				controlNodeManager.publishMessage(gatewayCode, controlNodeGet.getProductCode(), (short)1);
				log.info("gatewayCode:"+gatewayCode);
			}else{
				ControlRecord cRecord = controlRecordManager.getLastRcdByNodeId(controlRecord);
				
				controlRecord.setId(cRecord.getId());
				log.info("[ControlRecordController:updateCtrlRecord] controlRecordId.. : "+cRecord.getId());
				String dateEnd = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				controlRecord.setEndTime(dateEnd);
				
				SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
				String start = cRecord.getStartTime();
				String end = dateEnd;
				log.info("start:"+start);
				Date tStart;
				Date tEnd;

				try {
					tStart = ft.parse(start);
					tEnd = ft.parse(end);
					int t = (int) ((tEnd.getTime() - tStart.getTime()) / 1000);
					
					int d = 0;
					int h = 0;
					int m = 0;

					if (t / (24 * 60 * 60) > 0) {
						d = t / (24 * 60 * 60);
						t -= d * 24 * 60 * 60;
					}
					if (t / (60 * 60) > 0) {
						h = t / (60 * 60);
						t -= h * 60 * 60;
					}
					if (t / 60 > 0) {
						m = t / 60;
					}
					String duration = d + "天" + h + "时" + m + "分钟";
					log.info("duration:"+duration);
					controlRecord.setDuration(duration);
				} catch (ParseException e) {
					System.out.println("Unparseable using " + ft);
				}
				
				log.info("[ControlRecordController:updateCtrlRecord] controlRecordId : " + controlRecord.getNodeId() + ".");
				controlRecordManager.updateCtrlRecordById(controlRecord);
				controlNodeManager.publishMessage(gatewayCode, controlNodeGet.getProductCode(), (short)2);
					
			}
			
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ControlNodeController:getControlNodes]false to query controlNode list.", e);
		}
		return map;
	}
	
	@RequestMapping("/addControlNode")
	@ResponseBody
	public Object addControlNode(HttpServletRequest request,ControlNode controlNode){
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("[ControlRecordController:addControlNode] Name : " + controlNode.getName() + ".");
		log.info("[ControlRecordController:addControlNode] gatewayId : " + controlNode.getGatewayId() + ".");
		log.info("[ControlRecordController:addControlNode] productCode : " + controlNode.getProductCode() + ".");
		log.info("[ControlRecordController:addControlNode] type : " + controlNode.getType() + ".");
		log.info("[ControlRecordController:addControlNode] type : " + controlNode.getSersorType() + ".");
		
		controlNode.setNodeStatus((short) 1);
		controlNode.setOperateStatus((short) 2);
		try {
			log.info("[ControlRecordController:addControlNode] controlNode : " + controlNode + ".");
			controlNodeManager.addControlNode(controlNode);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ControlNodeController:addControlNode]fail to add controlNode.", e);
		}
		
		return map;
	}
	
	@RequestMapping("/updateControlNodeInfo")
	@ResponseBody
	public Object updateControlNodeInfo(HttpServletRequest request,ControlNode controlNode){
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			log.info("[ControlNodeController:updateControlNodeInfo] controlNode : " + controlNode + ".");
			controlNodeManager.updateControlNodeById(controlNode);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ControlNodeController:updateControlNodeInfo]fail to update controlNode.", e);
		}
		return map;
	}
	
	@RequestMapping("/deleteControlNode")
	@ResponseBody
	public Object deleteControlNode(HttpServletRequest request,Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			log.info("[ControlNodeController:deleteControlNode] controlNode : " + ".");
			controlNodeManager.deleteControlNodeById(id);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ControlNodeController:deleteControlNode]fail to delete controlNode.", e);
		}
		
		return map;
	}
	
	/**
	 * 查询网关
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryControlNodeListByGate")
	@ResponseBody
	public Object queryControlNodeListByGate(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			log.info("[ControlNodeController:queryControlNodeListByGate]query control node list.");
			Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("list", gatewayNodeManager.queryGatewayVoForControl(businessId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ControlNodeController:queryControlNodeListByGate]fail to query control node list.", e);
		}
		
		return map;
	}
	
	/**
	 * 查询网关
	 * @param request
	 * @return
	 */
	@RequestMapping("/querySensorNodeListByGate")
	@ResponseBody
	public Object querySensorNodeListByGate(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			log.info("[ControlNodeController:querySensorNodeListByGate]query control node list.");
			Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("list", gatewayNodeManager.queryGatewayVoForSensor(businessId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ControlNodeController:querySensorNodeListByGate]fail to query control node list.", e);
		}
		
		return map;
	}

	/**
	 * 查询设备（传感器）类型信息和网关
	 * @return
	 */
	@RequestMapping("/querySensorInfoAndGateway")
	@ResponseBody
	public Object querySensorInfoAndGateway(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ControlNodeController:querySensorInfoAndGateway]query SensorInfo and gatewayNode for page.");
			map.put("allSensorInfoList", sensorInfoManager.queryAllSensorInfo());
			map.put("allGatewayList", gatewayNodeManager.queryAllGatewayNode());
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ControlNodeController:querySensorInfoAndGateway]false to query SensorInfo and gatewayNode list.", e);
		}
		return map;
	}
}
