package com.smartfarm.base.farm.core.cotroller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.util.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.farm.core.manager.FarmRentLandOrderManager;

@Controller
@RequestMapping("/rentLandOrder")
public class FarmRentLandOrderController {


    private Logger log = Logger.getLogger(FarmRentLandOrderController.class);
    @Resource
    private FarmRentLandOrderManager farmRentLandOrderManager;

    @RequestMapping("/getRentLandOrderList")
    @ResponseBody
    public Object getRentLandOrderList(HttpServletRequest request,String orderCode, String rentName, Short status, Integer pageSize, Integer pageNumber){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
            log.info("[FarmController:getFarmList]{orderCode:"+orderCode+" rentName:"+rentName+
                    " status:"+status+" pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}.");
            Long farmId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            map.put("rentLandOrderList", farmRentLandOrderManager.getRentLandOrderList(farmId,orderCode,rentName,status,(pageNumber - 1) * pageSize, pageSize));
            map.put("total", farmRentLandOrderManager.countRentLandOrderTotal(farmId,orderCode,rentName,status));
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:getFarmList]false", e);
        }
        return map;
    }


}
