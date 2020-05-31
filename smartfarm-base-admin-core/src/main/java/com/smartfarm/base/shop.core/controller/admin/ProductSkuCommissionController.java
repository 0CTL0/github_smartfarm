package com.smartfarm.base.shop.core.controller.admin;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.shop.core.entity.ProductSkuCommission;
import com.smartfarm.base.shop.core.manager.ProductSkuCommissionManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/productSkuCommission")
public class ProductSkuCommissionController {
    private Logger log = Logger.getLogger(ProductSkuCommissionController.class);
    @Resource
    private ProductSkuCommissionManager productSkuCommissionManager;


    /**
     * 列表查询
     * @param name
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @RequestMapping("/getProductSkuCommissionList")
    @ResponseBody
    public Object queyProductSkuCommissionList(HttpServletRequest request,String name, Integer pageSize, Integer pageNumber){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
            log.info("[productSkuCommission:queyProductSkuCommissionList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}.");
            Long farmId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            log.info("farmId:"+farmId);
            map.put("skuCommissions",productSkuCommissionManager.getSkuCommissionList(farmId,name,(pageNumber - 1) * pageSize,  pageSize));
            map.put("total", productSkuCommissionManager.getSkuCommissionTotal(farmId,name));
            map.put("success", true);
        }
        catch(Exception e){
            log.error("[productSkuCommission:queyProductSkuCommissionList] false",e);
            map.put("success", false);
        }
        return map;
    }


    /**
     * 更新数据
     * @param productSkuCommission
     * @return
     */
    @RequestMapping("/editProductSkuCommission")
    @ResponseBody
    public Object editProductSkuCommission(ProductSkuCommission productSkuCommission){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
            log.info("[productSkuCommission:editProductSkuCommission]{productSkuCommission.getCommission():" +productSkuCommission.getCommission()+ "}.");
            productSkuCommissionManager.editProductSkuCommission(productSkuCommission);
            map.put("success", true);
        }
        catch(Exception e){
            log.error("[productSkuCommission:editProductSkuCommission] false",e);
            map.put("success", false);

        }
        return map;
    }
}
