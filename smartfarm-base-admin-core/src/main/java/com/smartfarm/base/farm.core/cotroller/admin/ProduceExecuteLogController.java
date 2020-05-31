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

import com.smartfarm.base.farm.core.entity.ProduceExecuteLog;
import com.smartfarm.base.farm.core.entity.vo.ProduceExecuteLogVo;
import com.smartfarm.base.farm.core.manager.ProduceExecuteLogManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年11月27日 15:41:58
 * @version 1.0
 */
@RequestMapping("/executeLog")
@Controller
public class ProduceExecuteLogController {

	private Logger log = Logger.getLogger(ProduceExecuteLogController.class);
	
	@Resource
	private ProduceExecuteLogManager produceExecuteLogManager;
	
	/**
	 * 发布生产任务/日志
	 * @param request
	 * @param executeLog
	 * @return
	 */
	@RequestMapping("/addExecuteLog")
	@ResponseBody
	public Object addExecuteLog(HttpServletRequest request,ProduceExecuteLog executeLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProduceExecuteLogController:addExecuteLog] begin to add execute log.");
			log.info("[ProduceExecuteLogController:addExecuteLog] {taskDate:"+executeLog.getExecuteTime()+"}.");
			executeLog.setPlanTime(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			executeLog.setExecuteTime(executeLog.getExecuteTime().replaceAll("-", ""));
			executeLog.setStatus((short) 0);
			executeLog.setIsShow((short) 1);
			produceExecuteLogManager.addExecuteLog(executeLog);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProduceExecuteLogController:addExecuteLog] fail to add execute log.",e);
		}
		return map;
	}

	/**
	 * 查询日志
	 * @param request
	 * @param content
	 * @param planTime
	 * @param executeTime
	 * @param status
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/getExecuteLogs")
	@ResponseBody
	public Object getExecuteLogs(HttpServletRequest request,String content,String planTime,String executeTime,Short status,Integer pageSize,Integer pageNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProduceExecuteLogController:getExecuteLogs] begin to query execute log.");
			ProduceExecuteLog executeLog = new ProduceExecuteLog();
			executeLog.setContent(content);
			if (planTime!=null) {
				executeLog.setPlanTime(planTime.replaceAll("-", ""));
			}
			if (executeTime!=null) {
				executeLog.setExecuteTime(executeTime.replaceAll("-", ""));
			}
			executeLog.setStatus(status);
			Long farmId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			List<ProduceExecuteLogVo> executeLogVoList = produceExecuteLogManager.queryExecuteLogs(farmId,content,planTime,executeTime,status, (pageNumber - 1) * pageSize, pageSize);
			for (ProduceExecuteLogVo executeLogVo : executeLogVoList){
				executeLogVo.setPlanTime(DateUtil.formatDate(executeLogVo.getPlanTime()));
				executeLogVo.setExecuteTime(DateUtil.formatDate(executeLogVo.getExecuteTime()));
			}
			map.put("executeLogVoList", executeLogVoList);
			map.put("total", produceExecuteLogManager.queryExecuteLogsTotal(farmId,content,planTime,executeTime,status));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProduceExecuteLogController:getExecuteLogs] fail to query execute log.",e);
		}
		return map;
	}
	
	/**
	 * 设置日志是否展示
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
			log.info("[ProduceExecuteLogController:updateLogShowStatus] begin to update log show status.");
			log.info("[ProduceExecuteLogController:updateLogShowStatus] {id:"+id+"}.");
			log.info("[ProduceExecuteLogController:updateLogShowStatus] {isShow:"+isShow+"}.");
			produceExecuteLogManager.modifyLogShowStatus(id, isShow);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProduceExecuteLogController:updateLogShowStatus] fail to update log show status.",e);
		}
		return map;
	}
	
	/**
	 * 删除日志，只能在任务完成之前
	 * @param request
	 * @param id
	 * @param detailId
	 * @return
	 */
	@RequestMapping("/deleteExecuteLog")
	@ResponseBody
	public Object deleteExecuteLog(HttpServletRequest request,Long id,Long detailId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProduceExecuteLogController:deleteExecuteLog] begin to update log show status.");
			log.info("[ProduceExecuteLogController:deleteExecuteLog] {id:"+id+"}.");
			log.info("[ProduceExecuteLogController:deleteExecuteLog] {detailId:"+detailId+"}.");
			produceExecuteLogManager.deleteExecuteLog(id, detailId);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProduceExecuteLogController:deleteExecuteLog] fail to update log show status.",e);
		}
		return map;
	}
	
	/**
	 * 后台查询对应类型的日志
	 * @param request
	 * @param sourceId
	 * @param taskType
	 * @return
	 */
	@RequestMapping("/getLogsBySourceIdAndTaskType")
	@ResponseBody
	public Object getLogsBySourceIdAndTaskType(HttpServletRequest request,Long sourceId,Short taskType) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProduceExecuteLogController:getLogsBySourceIdAndTaskType] begin to query logs by sourceId and taskType.");
			log.info("[ProduceExecuteLogController:getLogsBySourceIdAndTaskType] {sourceId:"+sourceId+"}.");
			log.info("[ProduceExecuteLogController:getLogsBySourceIdAndTaskType] {taskType:"+taskType+"}.");
			List<ProduceExecuteLogVo> executeLogVos = produceExecuteLogManager.queryLogsByTypeAndDetail(sourceId, taskType);
			for (ProduceExecuteLogVo executeLogVo : executeLogVos){
				executeLogVo.setExecuteTime(DateUtil.formatDate(executeLogVo.getExecuteTime()));
				if (executeLogVo.getPic()!=null){
					executeLogVo.setPicArray(executeLogVo.getPic().split(","));
				}
			}
			map.put("executeLogVos", executeLogVos);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProduceExecuteLogController:getLogsBySourceIdAndTaskType] fail to query logs by sourceId and taskType.",e);
		}
		return map;
	}
}
