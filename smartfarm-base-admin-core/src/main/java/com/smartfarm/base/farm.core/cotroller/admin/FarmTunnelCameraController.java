package com.smartfarm.base.farm.core.cotroller.admin;


import com.smartfarm.base.farm.core.entity.FarmTunnelCamera;
import com.smartfarm.base.farm.core.manager.FarmTunnelCameraManager;
import com.smartfarm.base.monitor.core.entity.CameraMessage;
import com.smartfarm.base.monitor.core.manager.CameraMessageManager;
import com.smartfarm.base.util.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/tunnelCamera")
@Controller
public class FarmTunnelCameraController {

    private Logger log = Logger.getLogger(FarmRentLandDeviceController.class);

    @Resource
    private CameraMessageManager cameraMessageManager;
    @Resource
    private FarmTunnelCameraManager tunnelCameraManager;

    /**
     * 查询地块配置的摄像头
     * @param request
     * @param tunnelId
     * @return
     */
    @RequestMapping("getFarmCameraInfo")
    @ResponseBody
    public Object getFarmCameraInfo(HttpServletRequest request, Long tunnelId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            log.info("[FarmRentLandDeviceController:getFarmCameraInfo]{ }.");
            Long farmId = (Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID);
            List<CameraMessage> cameraList = cameraMessageManager.queryFarmCameras(farmId);
            map.put("cameraList", cameraList);
            List<FarmTunnelCamera> tunnelCameras = tunnelCameraManager.queryFarmCameraConfigByTunnelId(tunnelId);
            String cameraSelected="";
            for (FarmTunnelCamera camera : tunnelCameras){
                cameraSelected =cameraSelected+","+camera.getCameraId();
            }
            if (!cameraSelected.equals("")){
                cameraSelected = cameraSelected.substring(1);
            }
            map.put("cameraSelected",cameraSelected);
            map.put("success", true);
        }catch(Exception e){
            map.put("success", false);
            log.error("[FarmRentLandDeviceController:getFarmCameraInfo", e);
        }
        return map;
    }

    /**
     * 配置地块摄像头
     * @param request
     * @param tunnelId
     * @param cameras
     * @return
     */
    @RequestMapping("/configureCamera")
    @ResponseBody
    public Object configureCamera(HttpServletRequest request, Long tunnelId, String cameras){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[FarmRentLandDeviceController:configureCamera] begin to configure tunnel's camera.");
            Long farmId = (Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID);
            String[] camerasS;
            Long[] camerasL = null;
            if (cameras!=null && !cameras.equals("")){
                camerasS = cameras.split(",");
                camerasL = new Long[camerasS.length];
                for (int i=0;i<camerasS.length;i++){
                    camerasL[i] = Long.parseLong(camerasS[i]);
                }
            }
            tunnelCameraManager.addTunnelCameraBatch(farmId,tunnelId,camerasL);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmRentLandDeviceController:configureCamera] fail to configure tunnel's camera.",e);
        }
        return  map;
    }
}
