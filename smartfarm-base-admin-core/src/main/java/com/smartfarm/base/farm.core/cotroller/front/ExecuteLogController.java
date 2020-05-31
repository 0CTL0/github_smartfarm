package com.smartfarm.base.farm.core.cotroller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.util.UploadUtil;
import com.smartfarm.base.farm.core.entity.ProduceExecuteLog;
import com.smartfarm.base.farm.core.manager.ProduceExecuteLogManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年11月29日 17:55:56
 * @version 1.0
 */
@RequestMapping("/execute")
@Controller
public class ExecuteLogController {

	private Logger log = Logger.getLogger(ExecuteLogController.class);
	
	@Resource
	private ProduceExecuteLogManager executeLogManager;
	
	
	@RequestMapping("/queryLogs")
	@ResponseBody
	public Object queryLogs(HttpServletRequest request,Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[EmployeeInfoController:queryLogs] begin to query logs by operator.");
			log.info("[EmployeeInfoController:queryLogs] { operator:"+id+"}.");
			List<ProduceExecuteLog> executeLogs = executeLogManager.queryLogsByOperator(id);
			for (ProduceExecuteLog executeLog:executeLogs){
				String plan = executeLog.getPlanTime();
				String exec = executeLog.getExecuteTime();
				executeLog.setPlanTime(plan.substring(0,4)+"-"+plan.substring(4,6)+"-"+plan.substring(6,8));
				executeLog.setExecuteTime(exec.substring(0,4)+"-"+exec.substring(4,6)+"-"+exec.substring(6,8));
				if (executeLog.getPic()!=null){
					executeLog.setPicArray(executeLog.getPic().split(","));
				}
			}
			map.put("executeLogs", executeLogs);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[EmployeeInfoController:queryLogs] fail to query logs by operator.",e);
		}
		return map;
	}
	
	@RequestMapping("/completeLog")
	@ResponseBody
	public Object completeLog(HttpServletRequest request,Long id,@RequestParam(value = "file", required = false)MultipartFile file) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[EmployeeInfoController:completeLog] begin to update log data.");
			log.info("[EmployeeInfoController:completeLog] { logId:"+id+"}.");
			log.info("[EmployeeInfoController:completeLog]{ MultipartFile:" + file);
			
			String pic = UploadUtil.uploadFile(file, "/execute_log/",RandomUtil.generateNumber(10));
			ProduceExecuteLog executeLog = new ProduceExecuteLog();
			executeLog.setId(id);
			executeLog.setStatus((short) 1);
			executeLog.setPic(pic);
			executeLogManager.WeChatPerformLog(executeLog);
			 
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[EmployeeInfoController:completeLog] fail to update log data.",e);
		}
		return map;
	}
}
