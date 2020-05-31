package com.smartfarm.base.farm.core.cotroller.admin;

import com.mysql.cj.Session;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.farm.core.entity.SettingInfo;
import com.smartfarm.base.farm.core.manager.SettingInfoManager;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/setting")
@Controller
public class SettingInfoController {

    private Logger log = Logger.getLogger(SettingInfoController.class);

    @Resource
    private SettingInfoManager settingInfoManager;

    /**
     * 查询农场的设置信息
     * @param request
     * @return
     */
    @RequestMapping("/getSettingInfo")
    @ResponseBody
    public Object getSettingInfo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[SettingInfoController:getSettingInfo]{ begin to query farm's setting into. }");
            List<SettingInfo> settingInfoList = settingInfoManager.querySettingInfoByFarmId((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID));
            map.put("settingInfoList", settingInfoList);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[SettingInfoController:getSettingInfo]false to begin to query farm's setting into. }", e);
        }
        return map;
    }

    /**
     * 查询农场的设置
     * @param request
     * @return
     */
    @RequestMapping("/getFarmSettingLists")
    @ResponseBody
    public Object getFarmSettingLists(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[SettingInfoController:getFarmSettingLists]{ begin }");
            List<SettingInfo> settingInfoList = settingInfoManager.querySettingInfoByFarmId((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID));
            Long farmId= (Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID);
            List<List> lists=settingInfoManager.queryFarmSettingLists(farmId);
            //HashMap<Integer,Object> settingMap=new HashMap<Integer, Object>();
            map.put("settingLists", lists);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[SettingInfoController:getFarmSettingLists]false }", e);
        }
        return map;
    }

    @RequestMapping("/saveSettingInfo")
    @ResponseBody
    public Object saveSettingInfo(HttpServletRequest request,String settingInfos) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[SettingInfoController:saveSettingInfo]{ begin to update farm's setting into. }"+settingInfos);
            JSONArray jsonArray = JSONArray.fromObject(settingInfos);
            List<SettingInfo> settingInfoList = (List<SettingInfo>) JSONArray.toCollection(jsonArray,SettingInfo.class);
            settingInfoManager.modifyFarmSettingInfo(settingInfoList);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[SettingInfoController:saveSettingInfo]false to begin to update farm's setting into. }", e);
        }
        return map;
    }
}
