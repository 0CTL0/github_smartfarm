package com.smartfarm.base.farm.core.cotroller.front;

import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.farm.core.entity.SettingInfo;
import com.smartfarm.base.farm.core.manager.SettingInfoManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/weSetting")
@Controller
public class WeSettingInfoController {

    private Logger log = Logger.getLogger(WeSettingInfoController.class);

    @Resource
    private SettingInfoManager settingInfoManager;


    @RequestMapping("/getDelegateSettingInfo")
    @ResponseBody
    public Object getDelegateSettingInfo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[WeSettingInfoController:getDelegateSettingInfo] begin to query farm's delegate setting info.");
            List<SettingInfo> delegateSettingList = settingInfoManager.queryFarmDelegateSetting((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID));
            Map<String,String> delegateSetting = new HashMap<String,String>();
            for (SettingInfo settingInfo : delegateSettingList){
                delegateSetting.put(settingInfo.getParamCode(),settingInfo.getParamValue());
            }
            map.put("delegateSetting", delegateSetting);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[WeSettingInfoController:getDelegateSettingInfo] fail to query farm's delegate setting info.",e);
        }
        return map;
    }
}
