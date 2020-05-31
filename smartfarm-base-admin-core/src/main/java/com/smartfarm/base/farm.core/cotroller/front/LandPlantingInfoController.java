package com.smartfarm.base.farm.core.cotroller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.farm.core.entity.WorkingLog;
import com.smartfarm.base.farm.core.entity.vo.FarmMemberCropsVo;
import com.smartfarm.base.farm.core.manager.FarmMemberCropsManager;
import com.smartfarm.base.farm.core.manager.FarmWorkingLogManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月18日 09:54:26
 * @version 1.0
 */
@RequestMapping("/landPlants")
@Controller
public class LandPlantingInfoController {

	private Logger log = Logger.getLogger(LandPlantingInfoController.class);
	@Resource
	private FarmMemberCropsManager memberCropsManager;
	@Resource
	private FarmWorkingLogManager workingLogManager;
	
	
	/**
	 * 查询我的地块种植情况
	 * @param request
	 * @param landId
	 * @return
	 */
	@RequestMapping("/getLandPlantInfo")
	@ResponseBody
	public Object getLandPlantInfo(HttpServletRequest request,Long landId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[FarmCropsInfoController:getLandPlantInfo] begin to query land planting info.");
			log.info("[FarmCropsInfoController:getLandPlantInfo] { :" + "}.");
			List<FarmMemberCropsVo> memberCropsVos = memberCropsManager.queryLandPlantingSituation(landId);
			map.put("memberCropsVos", memberCropsVos);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmCropsInfoController:getLandPlantInfo] fail to query land planting info.",e);
		}
		return map;
	}

	/**
	 * 重新种植，暂不使用
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/replanting")
	@ResponseBody
	public Object replanting(HttpServletRequest request,Long id) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[FarmCropsInfoController:replanting] begin to delete crop to replanting.");
			log.info("[FarmCropsInfoController:replanting] { :" + "}.");
			memberCropsManager.deleteMyCropsById(id);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmCropsInfoController:replanting] fail to delete crop to replanting.",e);
		}
		return map;
	}

	@RequestMapping("/getDelegates")
	@ResponseBody
	public Object getDelegates(HttpServletRequest request,Long landId,Long operator) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[FarmCropsInfoController:getDelegates] begin to get user's delegate log.");
			List<WorkingLog> delegateLogs = workingLogManager.queryLandDelegateLogs(landId, operator);
			map.put("delegateLogs",delegateLogs);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmCropsInfoController:getDelegates] fail to get user's delegate log.",e);
		}
		return map;
	}
}
