package com.smartfarm.base.shop.core.controller.admin;

import java.util.Date;
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

import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.util.UploadUtil;
import com.smartfarm.base.shop.core.entity.AdvanceNotice;
import com.smartfarm.base.shop.core.manager.AdvanceNoticeManager;

@Controller
@RequestMapping("/advanceNotice")
public class AdvanceNoticeController {
	private Logger log = Logger.getLogger(AdvanceNoticeController.class);
	@Resource
	private AdvanceNoticeManager advanceNoticeManager;
	
	/**
	 * 查询列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryList")
	@ResponseBody
	public Object queryList(HttpServletRequest request){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[AdvanceNoticeController:queryList]query advanceNotice list.");
			Long farmId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("list",advanceNoticeManager.queryAdvanceList(farmId));
			map.put("success", true);
		}
		catch(Exception e){
			log.error("[AdvanceNoticeController:queryList]false to query advanceNotice list.",e);
			
			map.put("success", false);
			
		}
		return map;
	}
	
	/**
	 * 添加广告栏
	 * @param request
	 * @param advanceNotice
	 * @param file
	 * @param file2
	 * @return
	 */
	@RequestMapping("/addAdvanceNotice")
	@ResponseBody
	public Object addAdvanceNotice(HttpServletRequest request, AdvanceNotice advanceNotice,
			@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam(value = "file2", required = false) MultipartFile file2){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[AdvanceNoticeController:addAdvanceNotice]add advance notice.");
			Long farmId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			
			String path = UploadUtil.uploadFile(file, "/file/advancenotice/",  new Date().getTime() + RandomUtil.randomStr(6));
			String path2 = UploadUtil.uploadFile(file2, "/file/advancenotice/",new Date().getTime() + RandomUtil.randomStr(6));
			advanceNotice.setFarmId(farmId);
			advanceNotice.setUrl(path);
			advanceNotice.setDetailUrl(path2);
			advanceNotice.setStatus((short)1);
			advanceNoticeManager.insert(advanceNotice);
			map.put("success", true);
		}catch(Exception e){
			log.error("[AdvanceNoticeController:addAdvanceNotice]false to add advance notice.", e);
			map.put("success", false);
		}
		
		return map;
	}
	
	/**
	 * 修改广告栏
	 * @param request
	 * @param advanceNotice
	 * @param file
	 * @param file2
	 * @return
	 */
	@RequestMapping("/updateAdvanceNotice")
	@ResponseBody
	public Object updateAdvanceNotice(HttpServletRequest request, AdvanceNotice advanceNotice,
			@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam(value = "file2", required = false) MultipartFile file2){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[AdvanceNoticeController:updateAdvanceNotice]update advance notice.");
			
			if(file != null) {
				String path = UploadUtil.uploadFile(file, "/file/advancenotice/",  new Date().getTime() + RandomUtil.randomStr(6));
				advanceNotice.setUrl(path);
			}
			if(file2 != null) {
				String path2 = UploadUtil.uploadFile(file2, "/file/advancenotice/",new Date().getTime() + RandomUtil.randomStr(6));
				advanceNotice.setDetailUrl(path2);
			}
			advanceNoticeManager.updateById(advanceNotice);
			map.put("success", true);
		}catch(Exception e){
			log.error("[AdvanceNoticeController:updateAdvanceNotice]false to update advance notice.", e);
			map.put("success", false);
		}
		
		return map;
	}
	
}
