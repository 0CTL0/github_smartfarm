package com.smartfarm.base.farm.core.cotroller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.farm.core.entity.WorkingLog;
import com.smartfarm.base.farm.core.entity.vo.WorkingLogVo;
import com.smartfarm.base.farm.core.manager.FarmWorkingLogManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月23日 17:17:12
 * @version 1.0
 */
@RequestMapping("/fWorkingLog")
@Controller
public class FarmWorkingLogController {

	private Logger log = Logger.getLogger(FarmWorkingLogController.class);
	
	@Resource
	private FarmWorkingLogManager workingLogManager;
	
	
	/**
	 * 发布任务
	 * @param request
	 * @param workingLog
	 * @return
	 */
	@RequestMapping("/addWorkingLog")
	@ResponseBody
	public Object addWorkingLog(HttpServletRequest request,WorkingLog workingLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[FarmWorkingLogController:addWorkingLog] begin to add working log.");
			log.info("[FarmWorkingLogController:addWorkingLog] {taskDate:"+workingLog.getExecuteTime()+"}.");
			workingLog.setPlanTime(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			workingLog.setExecuteTime(workingLog.getExecuteTime().replaceAll("-", ""));
			workingLog.setStatus((short) 1);
			workingLog.setIsShow((short) 1);
			workingLogManager.addWorkingLog(workingLog);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmWorkingLogController:addWorkingLog] fail to add working log.",e);
		}
		return map;
	}
	
	/**
	 * 查询任务
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping("/getWorkingLogs")
	@ResponseBody
	public Object getWorkingLogs(HttpServletRequest request,String content,String planTime,String executeTime,Short status,Integer pageSize,Integer pageNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[FarmWorkingLogController:getWorkingLogs] begin to query working log.");
			WorkingLog workingLog = new WorkingLog();
			workingLog.setContent(content);
			if (planTime!=null) {
				workingLog.setPlanTime(planTime.replaceAll("-", ""));
			}
			if (executeTime!=null) {
				workingLog.setExecuteTime(executeTime.replaceAll("-", ""));
			}
			workingLog.setStatus(status);
			List<WorkingLogVo> workingLogVoList = workingLogManager.queryWorkingLogs((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID),content,planTime,executeTime,status, (pageNumber - 1) * pageSize, pageSize);
			for (WorkingLogVo logVo : workingLogVoList){
				logVo.setPlanTime(DateUtil.formatDate(logVo.getPlanTime()));
				logVo.setExecuteTime(DateUtil.formatDate(logVo.getExecuteTime()));
				if (logVo.getActualExecuteTime()!=null){
					logVo.setActualExecuteTime(DateUtil.formatDateTime(logVo.getActualExecuteTime()));
				}
			}
			map.put("workingLogVoList", workingLogVoList);
			map.put("total", workingLogManager.queryWorkingLogsTotal((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID),content,planTime,executeTime,status));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmWorkingLogController:getWorkingLogs] fail to query working log.",e);
		}
		return map;
	}
	
	/**
	 * 设置任务是否展示
	 * @param request
	 * @param id
	 * @param isShow
	 * @return
	 */
	@RequestMapping("/updateLogShowStatus")
	@ResponseBody
	public Object updateLogShowStatus(HttpServletRequest request,Long id,Short isShow) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[FarmWorkingLogController:updateLogShowStatus] begin to update log show status.");
			log.info("[FarmWorkingLogController:updateLogShowStatus] {id:"+id+"}.");
			log.info("[FarmWorkingLogController:updateLogShowStatus] {isShow:"+isShow+"}.");
			workingLogManager.modifyLogShowStatus(id, isShow);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmWorkingLogController:updateLogShowStatus] fail to update log show status.",e);
		}
		return map;
	}
	
	/**
	 * 删除任务，只能在任务完成之前
	 * @param request
	 * @param id
	 * @param detailId
	 * @return
	 */
	@RequestMapping("/deleteWorkingLog")
	@ResponseBody
	public Object deleteWorkingLog(HttpServletRequest request,Long id,Long detailId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[FarmWorkingLogController:deleteWorkingLog] begin to update log show status.");
			log.info("[FarmWorkingLogController:deleteWorkingLog] {id:"+id+"}.");
			log.info("[FarmWorkingLogController:deleteWorkingLog] {detailId:"+detailId+"}.");
			workingLogManager.deleteWorkingLog(id, detailId);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmWorkingLogController:deleteExecuteLog] fail to update log show status.",e);
		}
		return map;
	}
}
