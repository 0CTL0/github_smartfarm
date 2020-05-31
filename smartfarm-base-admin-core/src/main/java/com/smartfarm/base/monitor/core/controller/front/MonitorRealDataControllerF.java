package com.smartfarm.base.monitor.core.controller.front;

import com.google.common.collect.Lists;
import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.monitor.core.entity.vo.MonitorRealDataVo;
import com.smartfarm.base.monitor.core.entity.vo.WeChatTemperatureVo;
import com.smartfarm.base.monitor.core.manager.MonitorRealDataManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 传感器-实时数据，控制层。
 *
 * @author HongSF
 * @version v1.0.0
 * @date 2020/3/31
 **/
@RestController
@RequestMapping("/realDataF")
public class MonitorRealDataControllerF {

    private Logger log = Logger.getLogger(MonitorRealDataControllerF.class);

    @Resource
    private MonitorRealDataManager monitorRealDataManager;

    /**
     * 封装气象和土壤数据
     *
     * @param request {@link HttpServletRequest} HttpServletRequest对象
     * @return {@link Object}
     */
    @RequestMapping("/queryMeteorologicalList")
    public Object queryMeteorologicalList(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>(4);
        try {
            Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            // 保存气象数据
            List<MonitorRealDataVo> meteorologicalList = new ArrayList<MonitorRealDataVo>();
            // 保存土壤数据
            List<MonitorRealDataVo> avgSoilList = new ArrayList<MonitorRealDataVo>();
            // 保存液体数据
            List<MonitorRealDataVo> liquidList = new ArrayList<MonitorRealDataVo>();
            // 查询出来的气象和土壤数据
            List<MonitorRealDataVo> realList =
                    monitorRealDataManager.queryAvgMeteorologicalAndSoilList(DateUtil.formatCurrentDate(), businessId);
            // 气象数据代码集合
            final List<String> monitorSensorCodes = Arrays.asList("ill", "atm", "airT", "airH", "rainF", "windD",
                    "windS");
            // 土壤数据代码集合
            final List<String> avgSoilCodes = Arrays.asList("soilT", "soilH", "soilC", "soilS", "dPH");
            // 液体数据代码集合
            final List<String> liquidCodes = Arrays.asList("level", "LiquidC", "wPH");
            for (MonitorRealDataVo monitorRealDataVo : realList) {
                if (monitorSensorCodes.contains(monitorRealDataVo.getCode())) {
                    meteorologicalList.add(monitorRealDataVo);
                } else if (avgSoilCodes.contains(monitorRealDataVo.getCode())) {
                    avgSoilList.add(monitorRealDataVo);
                } else if (liquidCodes.contains(monitorRealDataVo.getCode())) {
                    liquidList.add(monitorRealDataVo);
                }
            }
            log.info("[MonitorRealDataController:queryMeteorologicalList]{farmId:" + businessId + "}query " +
                    "Meteorological for page.");
            map.put("meteorologicalList", meteorologicalList);
            map.put("avgSoilList", avgSoilList);
            map.put("liquidList", liquidList);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MonitorRealDataController:queryMeteorologicalList]false to query Meteorological.", e);
        }
        return map;
    }

    /**
     * 封装气象和土壤数据
     *
     * @param farmTunelId
     * @return
     */
    @RequestMapping("/queryAvgDataByFarmTunelId")
    public Object queryAvgDataByFarmTunelId(HttpServletRequest request, Long farmTunelId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            //查询出来的气象和土壤数据
            List<MonitorRealDataVo> realList = monitorRealDataManager.queryAvgDataByFarmTunelId(businessId,
                    farmTunelId);
            log.info("[MonitorRealDataController:queryAvgDataByFarmTunelId]{farmId:" + businessId + "}query " +
                    "Meteorological for page.");
            map.put("realList", realList);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MonitorRealDataController:queryAvgDataByFarmTunelId]false to query Meteorological.", e);
        }
        return map;
    }

    /**
     * 封装气象和土壤数据
     *
     * @param rentLandId
     * @return
     */
    @RequestMapping("/queryAvgDataByRentLandId")
    public Object queryAvgDataByRentLandId(HttpServletRequest request, Long rentLandId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            //查询出来的气象和土壤数据
            List<MonitorRealDataVo> realList = monitorRealDataManager.queryAvgDataByRentLandId(businessId, rentLandId);

            log.info("[MonitorRealDataController:queryAvgDataByRentLandId]{farmId:" + businessId + "}query " +
                    "Meteorological for page.");
            map.put("realList", realList);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MonitorRealDataController:queryAvgDataByRentLandId]false to query Meteorological.", e);
        }
        return map;
    }

    /**
     * 根据传感器ID获取某天24小时实时平均数据
     *
     * @param sensorId 传感器ID
     * @param day      哪一天,格式:yyyyMMdd
     * @return 某天24小时实时平均数据
     */
    @RequestMapping("/avgRealDataWithDayBySensorId")
    public Object queryAvgRealDataWithDayBySensorId(Long sensorId, String day) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[MonitorRealDataController:queryAvgRealDataWithDayBySensorId]{sensorId:" + sensorId + "," +
                    "day :" + day + "}query Meteorological for page.");
            // 1.查询某天24小时实时平均数据
            List<WeChatTemperatureVo> list =
                    this.monitorRealDataManager.getAvgRealDataWithDayBySensorId(sensorId, day);
            // 2.封装real_value实时数据列表
            ArrayList<Double> realList = Lists.newArrayList();
            for (WeChatTemperatureVo weChatTemperatureVo : list) {
                realList.add(weChatTemperatureVo.getAvgRealValue());
            }
            map.put("realList", realList);
            map.put("name", list.get(0).getSensorNodeName());
            map.put("maxThreshold", list.get(0).getMaxThreshold());
            map.put("minThreshold", list.get(0).getMinThreshold());
            map.put("unit", list.get(0).getUnit());
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MonitorRealDataController:queryAvgDataByRentLandId]false to query Meteorological.", e);
        }
        return map;
    }

}
