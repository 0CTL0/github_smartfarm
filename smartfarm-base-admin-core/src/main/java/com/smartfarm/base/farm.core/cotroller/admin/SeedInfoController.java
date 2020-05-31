package com.smartfarm.base.farm.core.cotroller.admin;
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
import com.smartfarm.base.farm.core.entity.SeedInfo;
import com.smartfarm.base.farm.core.manager.SeedInfoManager;

@RequestMapping("seedInfo")
@Controller
public class SeedInfoController {
	private Logger log = Logger.getLogger(SeedInfoController.class);
	@Resource
	private SeedInfoManager seedInfoManager;
	/**
	 * 获得种子列表及查询
	 * @param request
	 * @param status
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("querySeedInfoPageList")
	@ResponseBody
	public Object queryEasyTaskInfoPageList(HttpServletRequest request, String name, Short status, Integer pageSize, Integer pageNumber) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[SeedInfoController:queryEasyTaskSeedInfoPageList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}.");
			List<SeedInfo> seedInfoList = seedInfoManager.getSeedInfoPageList(name, status, (pageNumber - 1) * pageSize, pageSize);
			map.put("seedInfoList", seedInfoList);
			
			map.put("total", seedInfoManager.countSeedInfoPageList(name, status));
			
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[SeedInfoController:querySeedInfoPageList]false to query seedInfo list.", e);
		}
		
		return map;
	}
	
	/**
	 * 改变种子的状态
	 * @param request
	 * @return
	 */
	@RequestMapping("changeStatus")
	@ResponseBody
	public Object changeStatus(HttpServletRequest request,@RequestParam("id")Long id) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[SeedInfoController:querySeedInfoPageList]{seedInfo id:" + id + "}.");
			SeedInfo seedInfo=seedInfoManager.getSeedInfo(id);
			log.info("[SeedInfoController:querySeedInfoPageList]{seedInfo status:" + seedInfo.getStatus() + "}.");
			if(seedInfo.getStatus()==1){
				Short status=2;
				seedInfo.setStatus(status);
			}
			else{
				Short status=1;
				seedInfo.setStatus(status);
			}
			seedInfoManager.editSeedInfo(seedInfo);
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[SeedInfoController:querySeedInfoPageList]false", e);
		}
		
		return map;
	}
	
	/**
	 * 新增种子信息
	 * @param request
	 * @param seedInfo
	 * @param file
	 * @param files
	 * @return
	 */
	@RequestMapping("/addSeedInfo")
	@ResponseBody
	public Object addSeedInfo(HttpServletRequest request,SeedInfo seedInfo,@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam(value = "files", required = false) MultipartFile[] files) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[SeedInfoController:addSeedInfo]{ seedInfo:" + seedInfo);			
			log.info("[SeedInfoController:addSeedInfo]{ MultipartFile:" + file);
			log.info("[SeedInfoController:addSeedInfo]{MultipartFiles[]:" + files.length );
			
			String coverStr=UploadUtil.uploadFile(file, "/upload/",RandomUtil.generateNumber(10));
			String carouselListStr="";			
			for(MultipartFile f :files){				
				String path = UploadUtil.uploadFile(f, "/upload/",RandomUtil.generateNumber(12));					
				carouselListStr=carouselListStr+path+";";
			}
			carouselListStr=carouselListStr.substring(0, carouselListStr.length()-1);			
			log.info("[SeedInfoController:addSeedInfo]{MultipartFile[]:" + carouselListStr );			
			Short status=2;
			seedInfo.setStatus(status);
			seedInfo.setCover(coverStr);
			seedInfo.setCarousel(carouselListStr);
			seedInfoManager.addSeedInfo(seedInfo);
			map.put("success", true);			
		}catch(Exception e){
			map.put("success", false);
			log.error("[EasyTaskInfoController:addStepAndGetId]false ", e);
		}		
		return map;
	}
	
	/**
	 * 获得种子信息
	 * @param request
	 * @return
	 */
	@RequestMapping("getSeedInfoEdit")
	@ResponseBody
	public Object getSeedInfoEdit(HttpServletRequest request,@RequestParam("id")Long id) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[SeedInfoController:getSeedInfoEdit]{seedInfo id:" + id + "}.");
			SeedInfo seedInfo=seedInfoManager.getSeedInfo(id);
			map.put("seedInfo", seedInfo);		
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[SeedInfoController:getSeedInfoEdit", e);
		}
		
		return map;
	}
		
	/**
	 * 编辑种子信息
	 * @param request
	 * @param seedInfo
	 * @param file
	 * @param files
	 * @return
	 */
	@RequestMapping("/editSeedInfo")
	@ResponseBody
	public Object editSeedInfo(HttpServletRequest request,SeedInfo seedInfo,@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam(value = "files", required = false) MultipartFile[] files) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[SeedInfoController:editSeedInfo]{ seedInfo:" + seedInfo);			
			log.info("[SeedInfoController:editSeedInfo]{ MultipartFile:" + file);
			log.info("[SeedInfoController:editSeedInfo]{MultipartFiles[]:" + files.length );			
			if(file!=null){
				String coverStr=UploadUtil.uploadFile(file, "/upload/",RandomUtil.generateNumber(10));
				seedInfo.setCover(coverStr);
			}
			if(files.length!=0){
				String carouselListStr="";			
				for(MultipartFile f :files){				
					String path = UploadUtil.uploadFile(f, "/upload/",RandomUtil.generateNumber(12));					
					carouselListStr=carouselListStr+path+";";					
				}
				carouselListStr=carouselListStr.substring(0, carouselListStr.length()-1);
				seedInfo.setCarousel(carouselListStr);
				log.info("[SeedInfoController:addSeedInfo]{MultipartFile[]:" + carouselListStr );
			}			
			seedInfoManager.editSeedInfo(seedInfo);
			map.put("success", true);			
		}catch(Exception e){
			map.put("success", false);
			log.error("[EasyTaskInfoController:addStepAndGetId]false ", e);
		}		
		return map;
	}
	
}
