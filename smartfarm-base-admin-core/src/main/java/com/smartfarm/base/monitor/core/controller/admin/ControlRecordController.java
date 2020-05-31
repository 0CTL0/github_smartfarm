package com.smartfarm.base.monitor.core.controller.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.monitor.core.entity.ControlRecord;
import com.smartfarm.base.monitor.core.manager.ControlRecordManager;

@Controller
@RequestMapping("/controlRecord")
public class ControlRecordController {

	private Logger log = Logger.getLogger(ControlRecordController.class);
	
	@Resource
	private ControlRecordManager controlRecordManager;
	
	/**
	 * 根据设备id查询操作记录
	 * @param request
	 * @param nodeId
	 * @return
	 */
	@RequestMapping("/getControlRecordsByNodeId")
	@ResponseBody
	public Object getControlRecordsByNodeId(HttpServletRequest request,@Param("nodeId") Long nodeId, @Param("formData") Integer pageSize, @Param("pageNumber") Integer pageNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ControlRecordController:getControlRecordsByNodeId] nodeId : " + nodeId + ".");
			List<ControlRecord> controlRecords = controlRecordManager.getControlRecordsByNodeIdPageList(nodeId, (pageNumber - 1) * pageSize, pageSize);
			map.put("controlRecords", controlRecords);
			map.put("total", controlRecordManager.countCtrlRecordsByNodeIdPageList(nodeId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ControlRecordController:getControlRecordsByNodeId]false to query controlRecord list.", e);
		}
		return map;
	}
	
	/**
	 * 点击开启时，插入一条操作记录
	 * @param request
	 * @param controlRecord
	 * @return
	 */
	@RequestMapping("/addControlRecord")
	@ResponseBody
	public Object addControlRecord(HttpServletRequest request,ControlRecord controlRecord){
		
		Date date = new Date();
		String pattern = "yyyyMMddhhmmss";
		SimpleDateFormat sdft = new SimpleDateFormat(pattern);
		String dateStr = sdft.format(date);
		log.info("当前系统时间为: " + date + " .");
		log.info("当前系统时间的yyyyMMddhhmmss格式为: " + dateStr + " .");
		
		controlRecord.setStartTime(dateStr);
		controlRecord.setEndTime("暂无");
		controlRecord.setDuration("0天0时0分钟");
		controlRecord.setOperator((Long) SessionUtil.getAttribute(request, SessionUtil.ADMIN_ID));
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ControlRecordController:addControlRecord] controlRecordId : " + controlRecord.getNodeId() + ".");
			controlRecordManager.addControlRecord(controlRecord);
			map.put("addSuccess", true);
		} catch (Exception e) {
			map.put("addSuccess", false);
			log.error("[ControlRecordController:addControlRecord]false to add controlRecord.", e);
		}
		return map;
	}
	
	@RequestMapping("/updateCtrlRecord")
	@ResponseBody
	public Object updateCtrlRecord(HttpServletRequest request,ControlRecord controlRecord){
		Map<String, Object> map = new HashMap<String, Object>();
		ControlRecord cRecord = controlRecordManager.getLastRcdByNodeId(controlRecord);
		
		controlRecord.setId(cRecord.getId());
		log.info("[ControlRecordController:updateCtrlRecord] controlRecordId.. : "+cRecord.getId());
		String dateStr = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		controlRecord.setEndTime(dateStr);
		
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmss");
		String start = cRecord.getStartTime();
		String end = dateStr;
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
		
		try {
			log.info("[ControlRecordController:updateCtrlRecord] controlRecordId : " + controlRecord.getNodeId() + ".");
			controlRecordManager.updateCtrlRecordById(controlRecord);
			map.put("updateSuccess", true);
		} catch (Exception e) {
			map.put("updateSuccess", false);
			log.error("[ControlRecordController:updateCtrlRecord]false to update controlRecord.", e);
		}	
		return map;
	}
}
