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

import com.smartfarm.base.farm.core.entity.FarmCropsInfo;
import com.smartfarm.base.farm.core.entity.vo.FarmMemberCropsVo;
import com.smartfarm.base.farm.core.manager.FarmCropsInfoManager;
import com.smartfarm.base.farm.core.manager.FarmMemberCropsManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月13日 14:18:59
 * @version 1.0
 */
@RequestMapping("/crops")
@Controller
public class FarmCropsInfoController {

	private Logger log = Logger.getLogger(FarmCropsInfoController.class);
	
	@Resource
	private FarmCropsInfoManager cropsInfoManager;
	@Resource
	private FarmMemberCropsManager memberCropsManager;
	
	
	/**
	 * 查询所有的种子数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCropsInfo")
	@ResponseBody
	public Object getCropsInfo(HttpServletRequest request,Long farmId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[FarmCropsInfoController:getCropsInfo] begin to query crops info.");
			log.info("[FarmCropsInfoController:getCropsInfo] { :" + "}.");
			List<FarmCropsInfo> cropsInfos = cropsInfoManager.queryCropsInfoList(farmId);
			map.put("cropsInfos", cropsInfos);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmCropsInfoController:getCropsInfo] fail to query crops info.",e);
		}
		return map;
	}
	
	/**
	 * 查询我的农作物
	 * @param request
	 * @param landId
	 * @return
	 */
	@RequestMapping("/getMyCrops")
	@ResponseBody
	public Object getMyCrops(HttpServletRequest request,Long landId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[FarmCropsInfoController:getMyCrops] begin to query my crops.");
			log.info("[FarmCropsInfoController:getMyCrops] { :" + "}.");
			List<FarmMemberCropsVo> cropsVos = memberCropsManager.queryMyCrops(landId);
			map.put("cropsVos", cropsVos);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmCropsInfoController:getMyCrops] fail to query my crops.",e);
		}
		return map;
	}
	
	/**
	 * 查询我的已采摘作物
	 * @param request
	 * @param landId
	 * @return
	 */
	@RequestMapping("/getMyMatureCrops")
	@ResponseBody
	public Object getMyMatureCrops(HttpServletRequest request,Long landId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[FarmCropsInfoController:getMyCrops] begin to query my crops.");
			log.info("[FarmCropsInfoController:getMyCrops] { :" + "}.");
			List<FarmMemberCropsVo> cropsVos = memberCropsManager.queryMyMatureCrops(landId);
			map.put("cropsVos", cropsVos);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmCropsInfoController:getMyCrops] fail to query my crops.",e);
		}
		return map;
	}
}
