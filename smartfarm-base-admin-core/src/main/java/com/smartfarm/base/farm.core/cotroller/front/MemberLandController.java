package com.smartfarm.base.farm.core.cotroller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.farm.core.entity.FarmMemberCrops;
import com.smartfarm.base.farm.core.entity.WorkingLog;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.farm.core.entity.FarmMemberLand;
import com.smartfarm.base.farm.core.entity.vo.FarmMemberLandVo;
import com.smartfarm.base.farm.core.manager.FarmMemberLandManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月18日 16:06:17
 * @version 1.0
 */
@RequestMapping("/memberLand")
@Controller
public class MemberLandController {

	private Logger log = Logger.getLogger(MemberLandController.class);
	
	@Resource
	private FarmMemberLandManager memberLandManager;
	
	
	/**
	 * 会员查询所有土地
	 * @param request
	 * @param memberId
	 * @return
	 */
	@RequestMapping("/getMemberLands")
	@ResponseBody
	public Object getMemberLands(HttpServletRequest request,Long memberId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[FarmCropsInfoController:getMemberLands] begin to query member's lands info.");
			log.info("[FarmCropsInfoController:getMemberLands] { memberId:" + memberId + "}.");
			List<FarmMemberLandVo> memberLandVos = memberLandManager.queryLandsByMemberId(memberId);
			map.put("memberLandVos", memberLandVos);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmCropsInfoController:getMemberLands] fail to query member's lands info.",e);
		}
		return map;
	}
	
	/**
	 * 根据租赁土地id查询单个土地信息
	 * @param request
	 * @param landId
	 * @return
	 */
	@RequestMapping("/getMemberLandInfo")
	@ResponseBody
	public Object getMemberLandInfo(HttpServletRequest request,Long landId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[FarmCropsInfoController:getMemberLandInfo] begin to query member's single land info.");
			log.info("[FarmCropsInfoController:getMemberLandInfo] { landId:" + landId + "}.");
			FarmMemberLand memberLand = memberLandManager.queryMemberLandById(landId);
			//map.put("waitingPlantArea",memberLandManager.getCountDelegatePlantArea(landId));
			map.put("memberLand", memberLand);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmCropsInfoController:getMemberLandInfo] fail to query member's single land info.",e);
		}
		return map;
	}

	/**
	 * 获取用户土地未完成的委托种植面积
	 * @param request
	 * @param landId
	 * @return
	 */
	@RequestMapping("/getLandWaitingPlantArea")
	@ResponseBody
	public Object getLandWaitingPlantArea(HttpServletRequest request,Long landId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[FarmCropsInfoController:getLandWaitingPlantArea] { landId:" + landId + "}.");
			map.put("waitingPlantArea",memberLandManager.getCountDelegatePlantArea(landId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmCropsInfoController:getLandWaitingPlantArea] fail .",e);
		}
		return map;
	}
	
	/**
	 * 修改土地别名
	 * @param request
	 * @param memberLand
	 * @return
	 */
	@RequestMapping("/modifyAlias")
	@ResponseBody
	public Object modifyAlias(HttpServletRequest request,FarmMemberLand memberLand) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[FarmCropsInfoController:modifyAlias] begin to modify memberLand's alias.");
			log.info("[FarmCropsInfoController:modifyAlias] { memberLand:" + memberLand + "}.");
			memberLandManager.modifyLandAliasById(memberLand);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmCropsInfoController:modifyAlias] fail to modify memberLand's alias.",e);
		}
		return map;
	}

	/**
	 * 获取用户土地的地块Id
	 * @param memberLanId
	 * @return
	 */
	@RequestMapping("/getTunnelId")
	@ResponseBody
	public Object getTunnelId(Long memberLanId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[FarmCropsInfoController:getTunnelId] { memberLanId:" + memberLanId + "}.");
			map.put("tunnelId", memberLandManager.selectFarmTunnelId(memberLanId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmCropsInfoController:getTunnelId] fail .",e);
		}
		return map;
	}


	/**
	 * 种植登记
	 */
	@RequestMapping("/plantRegister")
	@ResponseBody
	public Object plantRegister(HttpServletRequest request, FarmMemberCrops farmMemberCrops) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[DelegateController:plantRegister] begin to deal with plantRegister .");
			log.info("farmMemberCrops:"+farmMemberCrops);
			memberLandManager.plantRegister(farmMemberCrops);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:plantingDelegate] fail to deal with plantRegister .",e);
		}
		return map;
	}

}
