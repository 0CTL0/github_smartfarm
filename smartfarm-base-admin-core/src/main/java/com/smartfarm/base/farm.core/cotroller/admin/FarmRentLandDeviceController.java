package com.smartfarm.base.farm.core.cotroller.admin;

import com.smartfarm.base.farm.core.entity.FarmRentLandDevice;
import com.smartfarm.base.farm.core.manager.FarmRentLandDeviceManager;
import com.smartfarm.base.monitor.core.entity.CameraMessage;
import com.smartfarm.base.monitor.core.entity.ControlNode;
import com.smartfarm.base.monitor.core.entity.SensorNode;
import com.smartfarm.base.monitor.core.manager.CameraMessageManager;
import com.smartfarm.base.monitor.core.manager.ControlNodeManager;
import com.smartfarm.base.monitor.core.manager.SensorNodeManager;
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

@RequestMapping("/landDevice")
@Controller
public class FarmRentLandDeviceController {

    private Logger log = Logger.getLogger(FarmRentLandDeviceController.class);

    @Resource
    private FarmRentLandDeviceManager landDeviceManager;
    @Resource
    private CameraMessageManager cameraMessageManager;
    @Resource
    private ControlNodeManager controlNodeManager;
    @Resource
    private SensorNodeManager sensorNodeManager;


    /**
     * 配置地块设备
     *
     * @param request
     * @param rentLandId
     * @param cameras
     * @param controlNodes
     * @param sensorNodes
     * @return
     */
    @RequestMapping("/configureDevice")
    @ResponseBody
    public Object configureDevice(HttpServletRequest request, Long rentLandId, String cameras, String controlNodes,
                                  String sensorNodes) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[FarmRentLandDeviceController:configureDevice] begin to configure land's device.");
            Long farmId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            String[] camerasS;
            Long[] camerasL = null;
            if (cameras != null && !cameras.equals("")) {
                camerasS = cameras.split(",");
                camerasL = new Long[camerasS.length];
                for (int i = 0; i < camerasS.length; i++) {
                    camerasL[i] = Long.parseLong(camerasS[i]);
                }
            }
            String[] controlNodesS;
            Long[] controlNodesL = null;
            log.info(controlNodes != null);
            log.info(controlNodes != "");
            log.info(":" + controlNodes);
            if (controlNodes != null && !controlNodes.equals("")) {
                controlNodesS = controlNodes.split(",");
                controlNodesL = new Long[controlNodesS.length];
                for (int i = 0; i < controlNodesS.length; i++) {
                    controlNodesL[i] = Long.parseLong(controlNodesS[i]);
                }
            }
            String[] sensorNodesS;
            Long[] sensorNodesL = null;
            if (sensorNodes != null && !sensorNodes.equals("")) {
                sensorNodesS = sensorNodes.split(",");
                sensorNodesL = new Long[sensorNodesS.length];
                for (int i = 0; i < sensorNodesS.length; i++) {
                    sensorNodesL[i] = Long.parseLong(sensorNodesS[i]);
                }
            }

            landDeviceManager.addLandDeviceBatch(farmId, rentLandId, camerasL, controlNodesL, sensorNodesL);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[FarmRentLandDeviceController:configureDevice] fail to configure land's device.", e);
        }
        return map;
    }

    /**
     * 获取农场已配置的硬件设备，以及地块已配置的设备
     *
     * @param request
     * @return
     */
    @RequestMapping("getFarmDeviceInfo")
    @ResponseBody
    public Object getFarmDeviceInfo(HttpServletRequest request, Long rentLandId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[FarmRentLandDeviceController:getFarmDeviceInfo]{ }.");
            Long farmId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            List<CameraMessage> cameraList = cameraMessageManager.queryFarmCameras(farmId);
            map.put("cameraList", cameraList);
            List<ControlNode> controlNodeList = controlNodeManager.queryFarmControlNodes(farmId);
            map.put("controlNodeList", controlNodeList);
            List<SensorNode> sensorNodeList = sensorNodeManager.queryFarmSensorNodes(farmId);
            map.put("sensorNodeList", sensorNodeList);
            List<FarmRentLandDevice> landDevices = landDeviceManager.queryFarmDeviceConfigByLandId(rentLandId);
            String cameraSelected = "", controlNodeSelected = "", sensorNodeSelected = "";
            for (FarmRentLandDevice device : landDevices) {
                if (device.getDeviceType() == 1) {
                    cameraSelected = cameraSelected + "," + device.getDeviceId();
                }
                if (device.getDeviceType() == 2) {
                    controlNodeSelected = controlNodeSelected + "," + device.getDeviceId();
                }
                if (device.getDeviceType() == 3) {
                    sensorNodeSelected = sensorNodeSelected + "," + device.getDeviceId();
                }
            }
            if (!"".equals(cameraSelected)) {
                cameraSelected = cameraSelected.substring(1);
            }
            if (!"".equals(controlNodeSelected)) {
                controlNodeSelected = controlNodeSelected.substring(1);
            }
            if (!"".equals(sensorNodeSelected)) {
                sensorNodeSelected = sensorNodeSelected.substring(1);
            }
            map.put("cameraSelected", cameraSelected);
            map.put("controlNodeSelected", controlNodeSelected);
            map.put("sensorNodeSelected", sensorNodeSelected);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[FarmRentLandDeviceController:getFarmDeviceInfo", e);
        }
        return map;
    }


}
