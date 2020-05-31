package com.smartfarm.base.shop.core.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.util.UploadUtil;
import com.smartfarm.base.shop.core.entity.ActivityInfo;
import com.smartfarm.base.shop.core.entity.ActivityPrice;
import com.smartfarm.base.shop.core.entity.ActivityRegistration;
import com.smartfarm.base.shop.core.manager.ActivityManager;

@Controller
@RequestMapping("/activity")
public class ActivityController {
	private Logger log = Logger.getLogger(ActivityController.class);
	@Resource
	private ActivityManager activityManager;
	
	/**
	 * 分页查询活动列表
	 * @return
	 */
	@RequestMapping("/queryActivityList")
	@ResponseBody
	public Object queryActivityList(HttpServletRequest request, String name, Integer pageSize, Integer pageNumber){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[activityController:queyactivityList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}.");
			Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("activityList", activityManager.queryAllActivityInfo(name, (pageNumber - 1) * pageSize,  pageSize, businessId));
			
			log.info("[activityController:queyactivityList] enter"+"name:"+name+"pageSize:"+pageSize+"pageNumber:"+pageNumber);
			map.put("total",activityManager.countActivityList(name, businessId));

			log.info("[activityController:queyactivityList] enter"+"name:"+name+"pageSize:"+pageSize+"pageNumber:"+pageNumber);
			map.put("success", true);
		}
		catch(Exception e){
			log.info("[activityController:queyactivityList] false");
			
			map.put("success", false);
			
		}
		return map;
	}
	
	/**
	 * 添加活动
	 * @param request
	 * @param priceListJson
	 * @param activity
	 * @param file
	 * @return
	 */
	@RequestMapping("/addActivity")
	@ResponseBody
	public Object addActivity(HttpServletRequest request,String priceListJson,ActivityInfo activity,@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "files", required = false) MultipartFile[] files){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[activityController:addActivity]{gradeJson:" +priceListJson+ "}");
			log.info("[activityController:addActivity]{activity.needName:" +activity.getNeedName()+ "}");
			log.info("[activityController:addActivity]{activity.needIdCard:" +activity.getNeedIdcard()+ "}");
			log.info("[activityController:addActivity]{activity.needMobile:" +activity.getNeedMobile()+ "}");
			Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("add", true);
			String coverStr=UploadUtil.uploadFile(file, "/activity/",RandomUtil.generateNumber(10));		
			String pathListStr = "";
			for (MultipartFile descFile : files) {
				String path = UploadUtil.uploadFile(descFile, "/activity/",RandomUtil.generateNumber(10));
				pathListStr = pathListStr + path + ";";
			}
			pathListStr = pathListStr.substring(0, pathListStr.length() - 1);
			activity.setPicUrl(coverStr);
			activity.setDetail(pathListStr);
			//JSONObject  jo=JSONObject.fromObject(formJson);
			JSONArray ja = JSONArray.fromObject(priceListJson);
        	@SuppressWarnings("unchecked")
        	List<ActivityPrice> priceList = (List<ActivityPrice>) JSONArray.toCollection(ja,ActivityPrice.class);       							
			if(activity.getNeedName()==null){
				activity.setNeedName(false);
			}
			if(activity.getNeedIdcard()==null){
				activity.setNeedIdcard(false);
			}
			if(activity.getNeedMobile()==null){
				activity.setNeedMobile(false);
			}
			activity.setBusinessId(businessId);
			activityManager.addActivity(activity, priceList);	
			
			log.info("[activityController:addActivity]{success}");
			map.put("success", true);
		}catch(Exception e){
			log.error("[ProductController:addActivity]false to add addActivity.", e);
			map.put("success", false);
		}
		
		return map;
	}
	
	/**
	 *通过Id查询活动信息和活动价格档次
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryActivityInfoById")
	@ResponseBody
	public Object queryActivitytInfoById(Long id){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[ActivityController:addActivityInfo]{queryActivityInfoById}"+id);
			
			map.put("activityInfo", activityManager.selectById(id));
			
			map.put("priceList", activityManager.selectActivityPriceList(id));
			log.info("[ActivityController:addActivityInfo]{queryActivityInfoById} get!");
			map.put("success", true);
		}
		catch(Exception e){
			log.error("[ActivityController:addproductInfo]{queryActivityInfoById} false!",e);
			map.put("success", false);	
		}
		return map;
	}
	
	/**
	 * 删除活动价格档次
	 * @param activityPriceId
	 * @return
	 */
	@RequestMapping("/deleteActivityPriceById")
	@ResponseBody
	public Object deleteActivityPriceById(Long activityPriceId){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[ActivityController:addActivityInfo]{activityPriceId}"+activityPriceId);
			activityManager.deleteActivityPrice(activityPriceId);			
			log.info("[ActivityController:addActivityInfo]{activityPriceId} get!");
			map.put("success", true);
		}
		catch(Exception e){
			log.error("[ActivityController:addproductInfo]{activityPriceId} false!",e);
			map.put("success", false);	
		}
		return map;
	}
	
	/**
	 * 编辑活动信息
	 * @return
	 */
	@RequestMapping("/editActivityInfo")
	@ResponseBody
	public Object editActivityInfo(HttpServletRequest request,String priceListJson,ActivityInfo activity,@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "files", required = false) MultipartFile[] files){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[activityController:editActivityInfo]{gradeJson:" +priceListJson+ "}");
			log.info("[activityController:editActivityInfo]{activity.needName:" +activity.getNeedName()+ "}");
			
			if(file!=null){
				String coverStr=UploadUtil.uploadFile(file, "/upload/",RandomUtil.generateNumber(10));
				activity.setPicUrl(coverStr);
			}
			if(files.length>0){
				String pathListStr = "";
				for (MultipartFile descFile : files) {
					String path = UploadUtil.uploadFile(descFile, "/upload/",RandomUtil.generateNumber(10));
					pathListStr = pathListStr + path + ";";
				}
				pathListStr = pathListStr.substring(0, pathListStr.length() - 1);		
				activity.setDetail(pathListStr);
			}
			JSONArray ja = JSONArray.fromObject(priceListJson);
        	@SuppressWarnings("unchecked")
        	List<ActivityPrice> priceList = (List<ActivityPrice>) JSONArray.toCollection(ja,ActivityPrice.class);       							
			
			activityManager.editActivity(activity, priceList);
			map.put("success", true);		
		}
		catch(Exception e){
			map.put("success", false);
		}
		return map;
	}
	
	/**
	 * 改变活动的状态
	 * @return
	 */
	@RequestMapping("/changeStatus")
	@ResponseBody
	public Object changeStatus(@RequestParam("activityId")Long activityId){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			activityManager.changeStatus(activityId);
			map.put("success", true);
		}
		catch(Exception e){
			map.put("success", false);
		
		}
		return map;
	}
	
	/**
	 * 根据查询条件分页查询活动用户列表
	 * @return
	 */
	@RequestMapping("/queryActivityRegistList")
	@ResponseBody
	public Object queryActivityRegistList(HttpServletRequest request, String activityRegistrationJson, Integer pageSize, Integer pageNumber,Long activityId){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[productSkuController:queryActivityRegistList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber +",activityRegistrationJson:" +activityRegistrationJson+ "}.");
			JSONObject ja = JSONObject.fromObject(activityRegistrationJson);
        	ActivityRegistration activityRegistration= (ActivityRegistration) JSONObject.toBean(ja,ActivityRegistration.class);
        	
        	log.info("[productSkuController:queryActivityRegistList] enter"+"id:"+activityRegistration.getActivityId()+"pageSize:"+pageSize+"pageNumber:"+pageNumber);
			
        	map.put("activityRegistList", activityManager.queryActivityRegistrationList(activityRegistration, (pageNumber - 1) * pageSize,  pageSize));
				
        	map.put("total", activityManager.countActivityRegistrationList(activityRegistration));	
        	
        	log.info("[productSkuController:queryActivityRegistList]{activityId:" + activityId);
			
        	ActivityInfo activityInfo=activityManager.getActivity(activityId);
        	map.put("activityName", activityInfo.getName());	
        	
			map.put("success", true);
		}
		catch(Exception e){
			log.error("[productSkuController:queryActivityRegistList] false",e);		
			map.put("success", false);		
		}
		return map;
	}
	
	/**
	 * 票根号搜索
	 * @return
	 */
	@RequestMapping("/searchActivityRegist")
	@ResponseBody
	public Object searchActivityRegist(HttpServletRequest request,@RequestParam(value = "ticketNo", required = false)String ticketNo){
		Map<String,Object> map=new HashMap<String,Object>();
		try{			
        	log.info("[productSkuController:searchActivityRegist] enter "+"ticketNo:"+ticketNo);
        	Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
        	map.put("activityRegist",activityManager.searchActivityRegist(ticketNo,businessId));
			     	
        	map.put("success", true);
		}
		catch(Exception e){
			log.error("[productSkuController:searchActivityRegist] false",e);		
			map.put("success", false);		
		}
		return map;
	}

	/**
	 * 票根号搜索
	 * @return
	 */
	@RequestMapping("/updateTicke")
	@ResponseBody
	public Object updateTicke(Long id,Short status){
		Map<String,Object> map=new HashMap<String,Object>();
		try{			
        	log.info("[productSkuController:updateTicke]{id : " + id +",status:" + status + "}update ticket status.");
        	ActivityRegistration activityRegistration = new ActivityRegistration();
        	activityRegistration.setTicketStatus(status);
        	activityRegistration.setId(id);
        	activityManager.updateRegistration(activityRegistration);
        	map.put("success", true);
		}
		catch(Exception e){
			log.error("[productSkuController:updateTicke]update ticket status.",e);		
			map.put("success", false);		
		}
		return map;
	}
}
