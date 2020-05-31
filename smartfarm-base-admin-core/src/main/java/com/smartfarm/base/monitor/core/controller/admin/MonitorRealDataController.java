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

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.monitor.core.entity.vo.MonitorRealDataVo;
import com.smartfarm.base.monitor.core.manager.CameraMessageManager;
import com.smartfarm.base.monitor.core.manager.ControlNodeManager;
import com.smartfarm.base.monitor.core.manager.MonitorRealDataManager;
import com.smartfarm.base.monitor.core.manager.MonitorWarningInfoManager;
import com.smartfarm.base.monitor.core.manager.SensorNodeManager;
import com.smartfarm.base.monitor.core.util.SersorParameterUtil;

@Controller
@RequestMapping("/realData")
public class MonitorRealDataController {
	
	private Logger log = Logger.getLogger(MonitorRealDataController.class);
	
	@Resource
	private MonitorRealDataManager monitorRealDataManager;
	
	@Resource
	private SensorNodeManager sensorNodeManager;
	
	@Resource
	private ControlNodeManager controlNodeManager;
	
	@Resource
	private MonitorWarningInfoManager monitorWarningInfoManager;
	
	@Resource
	private CameraMessageManager cameraMessageManager;
	
	/**
	 * 查询传感器数据 
	 * @return
	 */
	@RequestMapping("/queryRealDataList")
	@ResponseBody
	public Object queryRealDataList(String range) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorRealDataController:queryRealDataList]query MonitorRealData for page." + range);
        	//封装土壤网关id数据
			List<Long> gatewayIds = new ArrayList<Long>();
//			gatewayIds.add((Long) SersorParameterUtil.getShipName("SOIL_ONE"));
//			gatewayIds.add((Long) SersorParameterUtil.getShipName("SOIL_TWO"));
//			gatewayIds.add((Long) SersorParameterUtil.getShipName("SOIL_THREE"));
			gatewayIds.add((Long) SersorParameterUtil.getShipName("GATEWAY_ONE"));
        	if("2".equals(range)){
        		map.put("realDataList", monitorRealDataManager.queryRealDataListWithWeek(gatewayIds));
        	}else if ("3".equals(range)) {
        		map.put("realDataList", monitorRealDataManager.queryRealDataListWithMonth(gatewayIds));
			}else {
				map.put("realDataList", monitorRealDataManager.queryRealDataListWithToday(gatewayIds,DateUtil.formatCurrentDate()));
			}
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorRealDataController:queryRealDataList]false to query MonitorRealData list.", e);
		}
		return map;
	}
	
	/**
	 * 根据id得到传感real_value
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryRealDataById")
	@ResponseBody
	public Object queryRealDataById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorRealDataController:queryRealDataById]query MonitorRealData for page.");
			map.put("realValue", monitorRealDataManager.queryRealDataById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorRealDataController:queryRealDataById]false to query MonitorRealData.", e);
		}
		return map;
	}
	
	/**
	 * 展示传感值得所有数据列表
	 * @return
	 */
	@RequestMapping("/queryRealDataShowList")
	@ResponseBody
	public Object queryRealDataShowList(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[MonitorRealDataController:queryRealDataShowList]show MonitorRealData for page.");
        	
        	//封装查询气象数据
			List<Long> gatewayIds = new ArrayList<Long>();
			gatewayIds.add((Long) SersorParameterUtil.getShipName("GATEWAY_ONE"));
			map.put("realValueList", monitorRealDataManager.queryRealDataShowList(gatewayIds));
			
			//封装土壤网关id数据
			List<Long> soilIds = new ArrayList<Long>();
			soilIds.add((Long) SersorParameterUtil.getShipName("SOIL_ONE"));
			soilIds.add((Long) SersorParameterUtil.getShipName("SOIL_TWO"));
			soilIds.add((Long) SersorParameterUtil.getShipName("SOIL_THREE"));
			map.put("soilList", sensorNodeManager.querySersorCodeList(soilIds));
			
			//查询平均值
			map.put("avgSoilList", sensorNodeManager.queryAvgSersorNode(DateUtil.formatCurrentDate(),soilIds));

			//查询Control_node节点控制器
			map.put("controlNodeList", controlNodeManager.queryControllerNodeList());
			
			//查询土地警列表
			map.put("soilWarnList", monitorWarningInfoManager.querySoilWarningList(soilIds));
			
			map.put("warning_list",sensorNodeManager.queryWarningLists(soilIds));
			
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorRealDataController:queryRealDataShowList]false to show MonitorRealData.", e);
		}
		return map;
	}
	
	/**
	 * 封装气象和土壤数据
	 * @param productCode
	 * @return
	 */
	@RequestMapping("/queryMeteorologicalList")
	@ResponseBody
	public Object queryMeteorologicalList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			//保存气象数据
			List<MonitorRealDataVo> meteorologicalList = new ArrayList<MonitorRealDataVo>();
			//保存土壤数据
			List<MonitorRealDataVo> avgSoilList = new ArrayList<MonitorRealDataVo>();
			//保存液体数据
			List<MonitorRealDataVo> liquidList = new ArrayList<MonitorRealDataVo>();
			//查询出来的气象和土壤数据
			List<MonitorRealDataVo> realList = monitorRealDataManager.queryAvgMeteorologicalAndSoilList(DateUtil.formatCurrentDate(),businessId);
			for (MonitorRealDataVo monitorRealDataVo : realList) {
				if("ill".equals(monitorRealDataVo.getCode())){
					meteorologicalList.add(monitorRealDataVo);
				}
				if("atm".equals(monitorRealDataVo.getCode())){
					meteorologicalList.add(monitorRealDataVo);
				}
				if("airT".equals(monitorRealDataVo.getCode())){
					meteorologicalList.add(monitorRealDataVo);
				}
				if("airH".equals(monitorRealDataVo.getCode())){
					meteorologicalList.add(monitorRealDataVo);
				}
				if("rainF".equals(monitorRealDataVo.getCode())){
					meteorologicalList.add(monitorRealDataVo);
				}
				if("windD".equals(monitorRealDataVo.getCode())){
					meteorologicalList.add(monitorRealDataVo);
				}
				if("windS".equals(monitorRealDataVo.getCode())){
					meteorologicalList.add(monitorRealDataVo);
				}
				if("soilT".equals(monitorRealDataVo.getCode())){
					avgSoilList.add(monitorRealDataVo);
				}
				if("soilH".equals(monitorRealDataVo.getCode())){
					avgSoilList.add(monitorRealDataVo);
				}
				if("soilC".equals(monitorRealDataVo.getCode())){
					avgSoilList.add(monitorRealDataVo);
				}
				if("soilS".equals(monitorRealDataVo.getCode())){
					avgSoilList.add(monitorRealDataVo);
				}
				if("dPH".equals(monitorRealDataVo.getCode())){
					avgSoilList.add(monitorRealDataVo);
				}
				if("level".equals(monitorRealDataVo.getCode())){
					liquidList.add(monitorRealDataVo);
				}
				if("LiquidC".equals(monitorRealDataVo.getCode())){
					liquidList.add(monitorRealDataVo);
				}
				if("wPH".equals(monitorRealDataVo.getCode())){
					liquidList.add(monitorRealDataVo);
				}
			}
        	log.info("[MonitorRealDataController:queryMeteorologicalList]query Meteorological for page.");
			map.put("meteorologicalList", meteorologicalList);
			map.put("avgSoilList", avgSoilList);
			map.put("liquidList", liquidList);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorRealDataController:queryMeteorologicalList]false to query Meteorological.", e);
		}
		return map;
	}
	/**
	 * 根据产品码查询土壤数据
	 * @param code
	 * @return
	 */
	@RequestMapping("/querySersorNodeByCode")
	@ResponseBody
	public Object querySersorNodeByCode(String productCode,Long cameraId,Boolean showCamera,String nodes){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[MonitorRealDataController:querySersorNodeByCode]query SoilMonitorRealDataById for page.");
			List<String> codeList = new ArrayList<String>();
			String[] codes = nodes.split(",");
			for (String code : codes) {
				codeList.add(code);
			}
			map.put("sersorSoilDataList", sensorNodeManager.querySersorNodeByCodeWithsSersorCode(DateUtil.formatCurrentDate(),productCode,codeList));
			if(showCamera) {
				map.put("liveAddress", cameraMessageManager.queryCameraLiveAddressById(cameraId));
			}
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorRealDataController:querySersorNodeByCode]false to query SoilMonitorRealDataById.", e);
		}
		return map;
	}
	
	/**
	 * 查询视频地址
	 * @param cameraId
	 * @return
	 */
	@RequestMapping("/queryVideoAddress")
	@ResponseBody
	public Object queryVideoAddress(Long cameraId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[MonitorRealDataController:queryVideoAddress]query SoilMonitorRealDataById for page.");
			map.put("liveAddress", cameraMessageManager.queryCameraLiveAddressById(cameraId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MonitorRealDataController:queryVideoAddress]false to query SoilMonitorRealDataById.", e);
		}
		return map;
	}
}
