package com.smartfarm.base.monitor.core.controller.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.farm.core.entity.FarmTunnelCamera;
import com.smartfarm.base.farm.core.manager.FarmTunnelCameraManager;
import com.smartfarm.base.monitor.core.manager.CameraMessageManager;

@Controller
@RequestMapping("/camera")
public class CameraController {
	
	private Logger log = Logger.getLogger(CameraController.class);
	
	@Resource
	private CameraMessageManager cameraMessageManager;
	@Resource
	private FarmTunnelCameraManager farmTunnelCameraManager;
	
	
	/**
	 * 查询视频地址
	 * @param cameraId
	 * @return
	 */
	@RequestMapping("/queryVideoAddress")
	@ResponseBody
	public Object queryVideoAddress(Long cameraId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[CameraController:queryVideoAddress]query video address by id.");
			map.put("liveAddress", cameraMessageManager.queryCameraLiveAddressById(cameraId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CameraController:queryVideoAddress]false to query video address by id.", e);
		}
		return map;
	}
	
	/**
	 * 查询视频地址
	 * @param baseId
	 * @return
	 */
	@RequestMapping("/queryVideoAddressByBase")
	@ResponseBody
	public Object queryVideoAddressByBase(Long baseId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[CameraController:queryVideoAddressByBase]query video address by id.");
			List<FarmTunnelCamera> list = farmTunnelCameraManager.getFarmCameraByBaseId(baseId);
			List<String> urlList = new ArrayList<String>();
			for(FarmTunnelCamera camera:list) {
				urlList.add(cameraMessageManager.queryCameraLiveAddressById(camera.getCameraId()));
			}
			map.put("urlList", urlList);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CameraController:queryVideoAddressByBase]false to query video address by id.", e);
		}
		return map;
	}
}
