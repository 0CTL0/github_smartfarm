package com.smartfarm.base.monitor.core.controller.admin;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.monitor.core.entity.CameraMessage;
import com.smartfarm.base.monitor.core.manager.CameraMessageManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/farmCamera")
@Controller
public class FarmCameraController {

    private Logger log = Logger.getLogger(FarmCameraController.class);

    @Resource
    private CameraMessageManager cameraMessageManager;


    /**
     * 查询农场的摄像头
     * @param request
     * @return
     */
    @RequestMapping("/getCameras")
    @ResponseBody
    public Object getCameras(HttpServletRequest request,Integer pageNumber,Integer pageSize){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[FarmCameraController:getCameras] begin to query cameras by farmId.");
            log.info("[FarmCameraController:getCameras] { farmId : "+SessionUtil.getAttribute(request,SessionUtil.FARM_ID)+"}.");
            List<CameraMessage> cameraList = cameraMessageManager.queryCamerasByFarmId((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID),(pageNumber - 1) * pageSize, pageSize);
            for (CameraMessage camera : cameraList){
                camera.setCreateTime(DateUtil.formatDateTime(camera.getCreateTime()));
            }
            map.put("cameraList",cameraList);
            map.put("total",cameraMessageManager.countCamerasTotal((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID)));
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmCameraController:getCameras] fail to query cameras by farmId.",e);
        }
        return  map;
    }

    /**
     * 农场添加摄像头
     * @param request
     * @param camera
     * @return
     */
    @RequestMapping("/addCamera")
    @ResponseBody
    public Object addCamera(HttpServletRequest request, CameraMessage camera){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[FarmCameraController:addCamera] begin to add farm camera.");
            camera.setCreateTime(DateUtil.formatCurrentDateTime());
            camera.setFarmId((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID));
            cameraMessageManager.addFarmCamera(camera);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmCameraController:addCamera] fail to add farm camera.",e);
        }
        return  map;
    }

    /**
     * 查询单个摄像头信息，打开修改窗口用
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/getSingleCamera")
    @ResponseBody
    public Object getSingleCamera(HttpServletRequest request, Long id){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[FarmCameraController:getSingleCamera] begin to query single camera.");
            CameraMessage cameraEdit = cameraMessageManager.queryCameraById(id);
            map.put("cameraEdit",cameraEdit);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmCameraController:getSingleCamera] fail to query single camera.",e);
        }
        return  map;
    }

    /**
     * 修改摄像头信息
     * @param request
     * @param cameraMessage
     * @return
     */
    @RequestMapping("/modifyCameraInfo")
    @ResponseBody
    public Object modifyCameraInfo(HttpServletRequest request, CameraMessage cameraMessage){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[FarmCameraController:modifyCameraInfo] begin to modify camera info by id.");
            cameraMessageManager.modifyCameraInfoById(cameraMessage);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmCameraController:modifyCameraInfo] fail to modify camera info by id.",e);
        }
        return  map;
    }

    /**
     * 根据id删除摄像头
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/deleteCamera")
    @ResponseBody
    public Object deleteCamera(HttpServletRequest request, Long id){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[FarmCameraController:deleteCamera] begin to delete camera by id.");
            cameraMessageManager.removeCameraById(id);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmCameraController:deleteCamera] fail to delete camera by id.",e);
        }
        return  map;
    }
}
