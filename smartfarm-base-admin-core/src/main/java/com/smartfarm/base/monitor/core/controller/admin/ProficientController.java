package com.smartfarm.base.monitor.core.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.util.UploadUtil;
import com.smartfarm.base.monitor.core.entity.Proficient;
import com.smartfarm.base.monitor.core.manager.ProficientManager;

@Controller
@RequestMapping("/proficient")
public class ProficientController {

	private Logger log = Logger.getLogger(ProficientController.class);
	
	
	@Resource
	private ProficientManager proficientManager;
	
	
	/**
	 * 分页获取专家列表
	 * @param request
	 * @param mobile
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("queryProficientPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request, String mobile, Integer pageSize, Integer pageNumber) {
		log.info("had enter getPageList");
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[ProficientController:getPageList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}.");
			List<Proficient> proficlientList = proficientManager.getPageList(mobile, (pageNumber - 1) * pageSize, pageSize);
			map.put("proficientList", proficlientList);
			
			map.put("total", proficientManager.countProficlientPageList(mobile));
			
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[ProficientController:getPageList]false to query proficlient list.", e);
		}
		
		return map;
	}
	
	/**
	 * 添加专家
	 * @param request
	 * @param proficient
	 * @param file
	 * @return
	 */
	@RequestMapping("/addProficient")
	@ResponseBody
	public Object addProficlient(HttpServletRequest request,Proficient proficient,@RequestParam(value = "file", required = false) MultipartFile file) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[ProficientController:addProficient]{ proficient:" + proficient);			
			log.info("[ProficientController:addProficient]{ MultipartFile:" + file);

			String coverStr=UploadUtil.uploadFile(file, "/proficient/",RandomUtil.generateNumber(10));
			proficient.setHeadPic(coverStr);

			proficientManager.addProficlient(proficient);
			map.put("success", true);			
		}catch(Exception e){
			map.put("success", false);
			log.error("[EasyTaskInfoController:addStepAndGetId]false ", e);
		}		
		return map;
	}
	
	/**
	 * 根据id查找专家信息
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("getProficientEdit")
	@ResponseBody
	public Object getProficientEdit(HttpServletRequest request,@RequestParam("id")Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			log.info("[ProficientController:getProficientEdit]{proficient id:" + id + "}.");
			Proficient proficient = proficientManager.selectProficientById(id);
			map.put("proficient", proficient);		
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[ProficientController:getProficientEdit", e);
		}
		return map;
	}
	
	/**
	 * 更新专家信息
	 * @param request
	 * @param proficient
	 * @param file
	 * @return
	 */
	@RequestMapping("/editProficient")
	@ResponseBody
	public Object editProficient(HttpServletRequest request,Proficient proficient,@RequestParam(value = "file", required = false) MultipartFile file) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			log.info("[ProficientController:editProficient]{ proficient:" + proficient.getId());			
			log.info("[ProficientController:editProficient]{ MultipartFile:" + file);
			if(file!=null){
				String coverStr=UploadUtil.uploadFile(file, "/proficient/",RandomUtil.generateNumber(10));
				proficient.setHeadPic(coverStr);
			}
			proficientManager.updateProficient(proficient);
			map.put("success", true);			
		}catch(Exception e){
			map.put("success", false);
			log.error("[EasyTaskInfoController:addStepAndGetId]false ", e);
		}		
		return map;
	}
	
	/**
	 * 根据id删除专家
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteProficient")
	@ResponseBody
	public Object deleteProficient(HttpServletRequest request,@Param("id") Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProficientController:deleteProficient]{proficient id:" + id + "}.");
			proficientManager.deleteProficient(id);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[EasyTaskInfoController:addStepAndGetId]false ", e);
		}
		return map;
	}
	
	/**
	 * 初始化专家列表
	 * @return
	 */
	@RequestMapping("/queryProficientList")
	@ResponseBody
	public Object queryProficientList(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProficientController:queryProficientList] success query proficient list.");
			map.put("proficientList", proficientManager.queryProficientList());
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProficientController:queryProficientList]false query proficient list", e);
		}
		return map;
	}
}
