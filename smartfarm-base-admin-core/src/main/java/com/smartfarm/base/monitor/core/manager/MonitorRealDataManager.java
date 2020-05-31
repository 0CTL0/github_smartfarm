package com.smartfarm.base.monitor.core.manager;

import com.smartfarm.base.monitor.core.entity.MonitorRealData;
import com.smartfarm.base.monitor.core.entity.vo.MonitorRealDataVo;
import com.smartfarm.base.monitor.core.entity.vo.MonitorSoilDataVo;
import com.smartfarm.base.monitor.core.entity.vo.WeChatTemperatureVo;

import java.util.List;
import java.util.Map;

public interface MonitorRealDataManager {
    /**
     * 查询传感器数据
     *
     * @param codes
     * @return
     */
    List<MonitorRealDataVo> queryRealDataList(List<String> codes);

    /**
     * 根据id获取realValue值
     *
     * @param id
     * @return
     */
    MonitorRealData queryRealDataById(Long id);

    /**
     * 展示最新realValue数据
     *
     * @return
     */
    List<MonitorRealDataVo> queryRealDataShowList(List<Long> ids);

    /**
     * 批量插入值
     *
     * @param list
     * @return
     */
    int insertBatch(List<MonitorRealData> list);

    int insert(MonitorRealData record);

    /**
     * 查询最新气象监测
     *
     * @return
     */
    List<MonitorRealDataVo> queryMeteorologicalRealDataList(String productCode);

    /**
     * 查询土壤信息
     *
     * @param codes
     * @return
     */
    List<MonitorSoilDataVo> querySoilMessageList(List<String> codes);

    /**
     * 获取当前天数据
     *
     * @param today
     * @return
     */
    List<MonitorRealDataVo> queryRealDataListWithToday(List<Long> gatewayIds, String today);

    /**
     * 获取当前周数据
     *
     * @return
     */
    List<MonitorRealDataVo> queryRealDataListWithWeek(List<Long> gatewayIds);

    /**
     * 获取当前月数据
     *
     * @return
     */
    List<MonitorRealDataVo> queryRealDataListWithMonth(List<Long> gatewayIds);


    /**
     * 农场主获取当前天数据
     *
     * @param today
     * @return
     */
    List<MonitorRealDataVo> queryFarmerRealDataListWithToday(Long baseId, List<Long> gatewayIds, String today);

    /**
     * 农场主获取当前周数据
     *
     * @return
     */
    List<MonitorRealDataVo> queryFarmerRealDataListWithWeek(Long baseId, List<Long> gatewayIds);

    /**
     * 农场主获取当前月数据
     *
     * @return
     */
    List<MonitorRealDataVo> queryFarmerRealDataListWithMonth(Long baseId, List<Long> gatewayIds);


    /**
     * 查询当前天的所有平均数据
     *
     * @param today
     * @return
     */
    List<MonitorRealDataVo> queryAvgMeteorologicalAndSoilList(String today, Long businessId);

    /**
     * 解析json字符串，并把内容插入到real_data表中
     *
     * @param jsonStr
     * @return
     */
    int getData(String topic, String jsonStr);

    /**
     * 根据农场查询数据
     *
     * @param farmId
     * @return
     */
    Map<String, Object> queryLogicalData(Long farmId);

    /**
     * 查询地块数据
     *
     * @param businessId 农场id
     * @param tunelId    地块id
     * @return
     */
    List<MonitorRealDataVo> queryAvgDataByFarmTunelId(Long businessId, Long tunelId);

    /**
     * 查询租地数据
     *
     * @param businessId 农场
     * @param rentLandId 租地
     * @return
     */
    List<MonitorRealDataVo> queryAvgDataByRentLandId(Long businessId, Long rentLandId);

    /**
     * 根据传感器ID获取
     *
     * @param sensorId {@link Long}传感器ID
     * @param day      {@link String}哪一天,格式:yyyyMMdd
     * @return {@link List<WeChatTemperatureVo>}
     */
    List<WeChatTemperatureVo> getAvgRealDataWithDayBySensorId(Long sensorId, String day);
}
