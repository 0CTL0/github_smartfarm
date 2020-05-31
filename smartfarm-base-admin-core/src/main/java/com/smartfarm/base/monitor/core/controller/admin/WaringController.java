package com.smartfarm.base.monitor.core.controller.admin;

import com.smartfarm.base.admin.core.manager.AdminManager;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.monitor.core.manager.GatewayNodeManager;
import com.smartfarm.base.monitor.core.manager.MonitorRealDataManager;
import com.smartfarm.base.monitor.core.manager.MonitorWarningInfoManager;
import com.smartfarm.base.monitor.core.manager.SensorNodeManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/waring")
public class WaringController {
	
	private Logger log = Logger.getLogger(WaringController.class);
	
	@Resource
	private GatewayNodeManager gatewayNodeManager;
	
	@Resource
	private SensorNodeManager sensorNodeManager;
	
	@Resource
	private AdminManager adminUserManager;
	
	@Resource
	private MonitorRealDataManager monitorRealDataManager;
	
	@Resource
	private MonitorWarningInfoManager monitorWarningInfoManager;

	
	/**
	 * 分页查询预警
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryWarningPage")
	@ResponseBody
	public Object queryWarningPage(HttpServletRequest request,Integer pageSize, Integer pageNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[GatewayNodeController:queryWarningPage]query warning.");
        	Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
        	map.put("rows", monitorWarningInfoManager.queryPageWarningList(businessId, (pageNumber - 1) * pageSize, pageSize));
        	map.put("total", monitorWarningInfoManager.countPageWarningList(businessId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[GatewayNodeController:queryWarningPage]false to query warning.", e);
		}
		return map;
	}
}
