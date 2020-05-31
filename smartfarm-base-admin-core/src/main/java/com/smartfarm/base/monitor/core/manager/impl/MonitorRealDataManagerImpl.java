package com.smartfarm.base.monitor.core.manager.impl;

import com.alibaba.fastjson.JSON;
import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.monitor.core.dao.ControlNodeDao;
import com.smartfarm.base.monitor.core.dao.MonitorControlSetConditionDao;
import com.smartfarm.base.monitor.core.dao.MonitorControlSetNodeDao;
import com.smartfarm.base.monitor.core.dao.MonitorRealDataDao;
import com.smartfarm.base.monitor.core.dao.MonitorWarningInfoDao;
import com.smartfarm.base.monitor.core.dao.SensorInfoDao;
import com.smartfarm.base.monitor.core.dao.SensorNodeDao;
import com.smartfarm.base.monitor.core.entity.ControlNode;
import com.smartfarm.base.monitor.core.entity.MonitorControlSetCondition;
import com.smartfarm.base.monitor.core.entity.MonitorControlSetNode;
import com.smartfarm.base.monitor.core.entity.MonitorRealData;
import com.smartfarm.base.monitor.core.entity.MonitorWarningInfo;
import com.smartfarm.base.monitor.core.entity.SensorNode;
import com.smartfarm.base.monitor.core.entity.vo.MonitorRealDataVo;
import com.smartfarm.base.monitor.core.entity.vo.MonitorSoilDataVo;
import com.smartfarm.base.monitor.core.entity.vo.WeChatTemperatureVo;
import com.smartfarm.base.monitor.core.manager.MonitorRealDataManager;
import com.smartfarm.base.monitor.core.service.EventService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("monitorRealDataManager")
public class MonitorRealDataManagerImpl implements MonitorRealDataManager {
    private Logger log = Logger.getLogger(MonitorRealDataManagerImpl.class);
    @Resource
    private MonitorRealDataDao monitorRealDataDao;

    @Resource
    private SensorNodeDao sensorNodeDao;

    @Resource
    private SensorInfoDao sensorInfoDao;

    @Resource
    private MonitorWarningInfoDao monitorWarningInfoDao;

    @Resource
    private MonitorControlSetConditionDao monitorControlSetConditionDao;
    @Resource
    private MonitorControlSetNodeDao monitorControlSetNodeDao;
    @Resource
    private EventService eventService;
    @Resource
    private ControlNodeDao controlNodeDao;

    @Override
    public List<MonitorRealDataVo> queryRealDataList(List<String> codes) {
        return monitorRealDataDao.queryRealDataList(codes);
    }

    @Override
    public MonitorRealData queryRealDataById(Long id) {
        return monitorRealDataDao.queryRealDataById(id);
    }

    @Override
    public List<MonitorRealDataVo> queryRealDataShowList(List<Long> ids) {
        return monitorRealDataDao.queryRealDataShowList(ids);
    }

    @Override
    public int insertBatch(List<MonitorRealData> list) {
        return monitorRealDataDao.insertBatch(list);
    }

    @Override
    public int insert(MonitorRealData record) {
        return monitorRealDataDao.insert(record);
    }

    @Override
    public List<MonitorRealDataVo> queryMeteorologicalRealDataList(String productCode) {
        return monitorRealDataDao.queryMeteorologicalRealDataList(productCode);
    }

    @Override
    public List<MonitorSoilDataVo> querySoilMessageList(List<String> codes) {
        return monitorRealDataDao.querySoilMessageList(codes);
    }

    @Override
    public List<MonitorRealDataVo> queryRealDataListWithToday(List<Long> gatewayIds, String today) {
        return monitorRealDataDao.queryRealDataListWithToday(gatewayIds, today);
    }

    @Override
    public List<MonitorRealDataVo> queryRealDataListWithWeek(List<Long> gatewayIds) {
        return monitorRealDataDao.queryRealDataListWithWeek(gatewayIds);
    }

    @Override
    public List<MonitorRealDataVo> queryRealDataListWithMonth(List<Long> gatewayIds) {
        return monitorRealDataDao.queryRealDataListWithMonth(gatewayIds);
    }

    @Override
    public List<MonitorRealDataVo> queryAvgMeteorologicalAndSoilList(
            String today, Long businessId) {
        return monitorRealDataDao.queryAvgMeteorologicalAndSoilList(today, businessId);
    }

    @Override
    public int getData(String topic, String jsonStr) {
        List<MonitorRealData> realDataList = new ArrayList<MonitorRealData>();

        Date datetime = new Date();
        SimpleDateFormat dateSDF = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeSDF = new SimpleDateFormat("HHmmss");
        String date = dateSDF.format(datetime);
        String time = timeSDF.format(datetime);

        String[] topicSubs = topic.split("/");
        String gatewayCode = topicSubs[topicSubs.length - 1];
        Map map = JSON.parseObject(jsonStr);

        Set<String> set = map.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (key != "id") {
                Map<String, Object> queryMap = new HashMap<String, Object>();
                queryMap.put("gatewayCode", gatewayCode);
                queryMap.put("sensorInfoCode", key);
                queryMap.put("productCode", map.get("id"));
                SensorNode sensorNodeGet = sensorNodeDao.getIdByProductCodeAndGateWayId(queryMap);
                long sensorNodeId = sensorNodeGet.getId();
                Double maxThreshold = sensorNodeGet.getMaxThreshold();
                Double minThreshold = sensorNodeGet.getMinThreshold();
                int realValue = (Integer) map.get(key);
                System.out.println(sensorNodeId);
                float resolutionRatio = sensorInfoDao.queryRatioByCode(key);
                System.out.println(resolutionRatio);
                MonitorRealData monitorRealData = new MonitorRealData();
                monitorRealData.setSersorNodeId(sensorNodeId);
                monitorRealData.setTransferDate(date);
                monitorRealData.setTransferTime(time);
                monitorRealData.setRealValue(realValue * resolutionRatio);
                realDataList.add(monitorRealData);
                System.out.println(monitorRealData);

                if (monitorRealData.getRealValue() > maxThreshold || monitorRealData.getRealValue() < minThreshold) {
                    MonitorWarningInfo monitorWarningInfo = new MonitorWarningInfo();
                    monitorWarningInfo.setSersorNodeId(sensorNodeId);
                    monitorWarningInfo.setMaxThreshold(maxThreshold);
                    monitorWarningInfo.setMinThreshold(minThreshold);
                    monitorWarningInfo.setRealValue(realValue * resolutionRatio);
                    monitorWarningInfo.setWarningTime(date + time);
                    monitorWarningInfo.setStatus((short) 1);
                    monitorWarningInfoDao.insert(monitorWarningInfo);
                }
                try {
                    //控制器
                    List<MonitorControlSetCondition> conditionList =
							monitorControlSetConditionDao.querySetConditionByvalue(DateUtil.formatCurrentDateTime(),
                            (double) realValue * resolutionRatio, sensorNodeId);
                    for (MonitorControlSetCondition vo : conditionList) {
                        List<MonitorControlSetNode> nodeList =
								monitorControlSetNodeDao.queryByControlSetId(vo.getControlSetId());
                        for (MonitorControlSetNode v : nodeList) {
                            String operate = "1";
                            if (vo.getKinds() == 1) {//打开
                                operate = "1";
                            } else {//关闭
                                operate = "0";
                            }
                            ControlNode controlNode = controlNodeDao.getById(v.getControlNodeId());
                            String topicNow = "stds/down/CT/" + gatewayCode + "/sLoop";//拼接topic
                            String productCode = controlNode.getProductCode();
                            String[] codes = productCode.split("-");
                            String sensorNodeCode = codes[0];
                            String locate = codes[1];
                            String json =
									"{\"id\":" + sensorNodeCode + ",\"sid\":" + locate + ",\"val\":" + operate + "," +
											"\"time\":0}";
                            eventService.sendProductMsg(topicNow, json);
                        }

                    }
                } catch (Exception e) {
                    log.error("set control error,", e);
                }
            }
        }
        int inserNum = monitorRealDataDao.insertBatch(realDataList);
        return inserNum;
    }

    @Override
    public List<MonitorRealDataVo> queryFarmerRealDataListWithToday(
            Long baseId, List<Long> gatewayIds, String today) {
        return monitorRealDataDao.queryFarmerRealDataListWithToday(baseId, gatewayIds, today);
    }

    @Override
    public List<MonitorRealDataVo> queryFarmerRealDataListWithWeek(Long baseId,
                                                                   List<Long> gatewayIds) {
        return monitorRealDataDao.queryFarmerRealDataListWithWeek(baseId, gatewayIds);
    }

    @Override
    public List<MonitorRealDataVo> queryFarmerRealDataListWithMonth(
            Long baseId, List<Long> gatewayIds) {
        return monitorRealDataDao.queryFarmerRealDataListWithMonth(baseId, gatewayIds);
    }

    @Override
    public Map<String, Object> queryLogicalData(Long farmId) {
        Map<String, Object> map = new HashMap<String, Object>();
        //保存气象数据
        List<MonitorRealDataVo> meteorologicalList = new ArrayList<MonitorRealDataVo>();
        //保存土壤数据
        List<MonitorRealDataVo> avgSoilList = new ArrayList<MonitorRealDataVo>();
        //保存液体数据
        List<MonitorRealDataVo> liquidList = new ArrayList<MonitorRealDataVo>();
        //查询出来的气象和土壤数据
        List<MonitorRealDataVo> realList =
				monitorRealDataDao.queryAvgMeteorologicalAndSoilList(DateUtil.formatCurrentDate(), farmId);
        for (MonitorRealDataVo monitorRealDataVo : realList) {
            if ("ill".equals(monitorRealDataVo.getCode())) {
                meteorologicalList.add(monitorRealDataVo);
            }
            if ("atm".equals(monitorRealDataVo.getCode())) {
                meteorologicalList.add(monitorRealDataVo);
            }
            if ("airT".equals(monitorRealDataVo.getCode())) {
                meteorologicalList.add(monitorRealDataVo);
            }
            if ("airH".equals(monitorRealDataVo.getCode())) {
                meteorologicalList.add(monitorRealDataVo);
            }
            if ("rainF".equals(monitorRealDataVo.getCode())) {
                meteorologicalList.add(monitorRealDataVo);
            }
            if ("windD".equals(monitorRealDataVo.getCode())) {
                meteorologicalList.add(monitorRealDataVo);
            }
            if ("windS".equals(monitorRealDataVo.getCode())) {
                meteorologicalList.add(monitorRealDataVo);
            }
            if ("soilT".equals(monitorRealDataVo.getCode())) {
                avgSoilList.add(monitorRealDataVo);
            }
            if ("soilH".equals(monitorRealDataVo.getCode())) {
                avgSoilList.add(monitorRealDataVo);
            }
            if ("soilC".equals(monitorRealDataVo.getCode())) {
                avgSoilList.add(monitorRealDataVo);
            }
            if ("soilS".equals(monitorRealDataVo.getCode())) {
                avgSoilList.add(monitorRealDataVo);
            }
            if ("dPH".equals(monitorRealDataVo.getCode())) {
                avgSoilList.add(monitorRealDataVo);
            }
            if ("level".equals(monitorRealDataVo.getCode())) {
                liquidList.add(monitorRealDataVo);
            }
            if ("LiquidC".equals(monitorRealDataVo.getCode())) {
                liquidList.add(monitorRealDataVo);
            }
            if ("wPH".equals(monitorRealDataVo.getCode())) {
                liquidList.add(monitorRealDataVo);
            }
        }
        map.put("meteorologicalList", meteorologicalList);
        map.put("avgSoilList", avgSoilList);
        map.put("liquidList", liquidList);
        map.put("success", true);
        return map;
    }

    @Override
    public List<MonitorRealDataVo> queryAvgDataByFarmTunelId(Long businessId, Long tunelId) {
        return monitorRealDataDao.queryAvgDataByFarmTunelId(businessId, tunelId);
    }

    @Override
    public List<MonitorRealDataVo> queryAvgDataByRentLandId(Long businessId, Long rentLandId) {
        return monitorRealDataDao.queryAvgDataByRentLandId(businessId, rentLandId);
    }

    /**
     * 根据传感器ID获取
     *
     * @param sensorId {@link Long}传感器ID
     * @param day      {@link String}哪一天,格式:yyyyMMdd
     * @return {@link List<WeChatTemperatureVo>}
     */
    @Override
    public List<WeChatTemperatureVo> getAvgRealDataWithDayBySensorId(Long sensorId, String day) {
        return monitorRealDataDao.getAvgRealDataWithDayBySensorId(sensorId,day);
    }
}
