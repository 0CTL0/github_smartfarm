package com.smartfarm.base.farm.core.cotroller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.farm.core.entity.WorkInfo;
import com.smartfarm.base.farm.core.manager.SourceInfoManager;
import com.smartfarm.base.farm.core.manager.WorkAttributeManager;
import com.smartfarm.base.farm.core.manager.WorkInfoManager;
import com.smartfarm.base.farm.core.manager.WorkTypeManager;

@Controller
@RequestMapping("/work")
public class WorkController {
	
	private Logger log = Logger.getLogger(WorkController.class);
	
	@Resource
	private WorkTypeManager workTypeManager;
	
	@Resource
	private WorkAttributeManager workAttributeManager;
	
	@Resource
	private WorkInfoManager workInfoManager;
	
	@Resource
	private SourceInfoManager sourceInfoManager;
	
	/**
	 * 查询所有workType
	 * @return
	 */
	@RequestMapping("/queryWorkTypeList")
	@ResponseBody
	public Object queryWorkTypeList(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[WorkController:queryWorkTypeList]query workType info for page.");
			map.put("workTypeList", workTypeManager.queryWorkTypeList());
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[WorkController:queryWorkTypeList]false to query workType list.", e);
		}
		return map;
	}
	
	/**
	 * 根据id查询Attribute
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryAttributeById")
	@ResponseBody
	public Object queryAttributeById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[WorkController:queryAttributeById]query workAttribute info for page.");
			map.put("workAttributeList", workAttributeManager.queryAttributeById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[WorkController:queryAttributeById]false to query workAttribute list.", e);
		}
		return map;
	}
	
	@RequestMapping("/queryWorkTypeById")
	@ResponseBody
	public Object queryWorkTypeById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[WorkController:queryWorkTypeById]query workType info.");
			map.put("workTypeInfo", workTypeManager.queryWorkTypeById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[WorkController:queryWorkTypeById]false to query workType.", e);
		}
		return map;
	}
	
	/**
	 * 添加Work信息
	 * @param request
	 * @param workInfo
	 * @param files
	 * @param attributeListJson
	 * @return
	 */
	@RequestMapping("/addWorkInfoWithDetail")
	@ResponseBody
	public Object addWorkInfoWithDetail(HttpServletRequest request,WorkInfo workInfo,
			@RequestParam(value = "files", required = false) MultipartFile[] files,
			@RequestParam(value = "attributeListJson", required = false)String attributeListJson){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Long operatorId= (Long) SessionUtil.getAttribute(request, SessionUtil.ADMIN_ID);
			workInfo.setOperator(operatorId);
			workInfo.setReportTime(DateUtil.formatCurrentDateTime());
			workInfoManager.addWorkInfoWithDetail(workInfo, files, attributeListJson);
			map.put("success", true);
		}catch (Exception e) {
			map.put("success", false);
			log.error("[WorkController:addWorkInfoWithDetail]false to add workInfo.", e);
		}
		return map;
	}
	
	/**
	 * 根据溯源id查询溯源相关信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryAttributeDataById")
	@ResponseBody
	public Object queryAttributeDataById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[WorkController:queryAttributeDataById]query sourceInfoData.");
			map.put("sourceInfoData", sourceInfoManager.queryAttributeDataById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[WorkController:queryAttributeDataById]false to query sourceInfoData.", e);
		}
		return map;
	}
}
