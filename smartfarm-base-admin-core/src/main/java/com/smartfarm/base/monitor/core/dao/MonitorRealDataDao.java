package com.smartfarm.base.monitor.core.dao;

import com.smartfarm.base.monitor.core.entity.MonitorRealData;
import com.smartfarm.base.monitor.core.entity.vo.MonitorRealDataVo;
import com.smartfarm.base.monitor.core.entity.vo.MonitorSoilDataVo;
import com.smartfarm.base.monitor.core.entity.vo.WeChatTemperatureVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MonitorRealDataDao {

    int deleteById(Long id);

    int insert(MonitorRealData record);

    MonitorRealData selectById(Long id);

    int updateById(MonitorRealData record);

    /**
     * 查询传感器数据
     *
     * @return
     */
    List<MonitorRealDataVo> queryRealDataList(@Param("codes") List<String> codes);

    /**
     * 根据id获取realValue值
     *
     * @param id
     * @return
     */
    MonitorRealData queryRealDataById(@Param("id") Long id);


    /**
     * 展示最新realValue数据
     *
     * @param ids
     * @return
     */
    List<MonitorRealDataVo> queryRealDataShowList(@Param("ids") List<Long> ids);

    /**
     * 批量插入值
     *
     * @param list
     * @return
     */
    int insertBatch(List<MonitorRealData> list);

    /**
     * 查询最新气象监测
     *
     * @return
     */
    List<MonitorRealDataVo> queryMeteorologicalRealDataList(@Param("productCode") String productCode);

    /**
     * 查询土壤信息
     *
     * @param codes
     * @return
     */
    List<MonitorSoilDataVo> querySoilMessageList(@Param("codes") List<String> codes);

    /**
     * 获取当前天数据
     *
     * @param today
     * @return
     */
    List<MonitorRealDataVo> queryRealDataListWithToday(@Param("gatewayIds") List<Long> gatewayIds,
                                                       @Param("today") String today);

    /**
     * 获取当前周数据
     *
     * @return
     */
    List<MonitorRealDataVo> queryRealDataListWithWeek(@Param("gatewayIds") List<Long> gatewayIds);

    /**
     * 获取当前月数据
     *
     * @return
     */
    List<MonitorRealDataVo> queryRealDataListWithMonth(@Param("gatewayIds") List<Long> gatewayIds);


    /**
     * 农场主获取当前天数据
     *
     * @param today
     * @return
     */
    List<MonitorRealDataVo> queryFarmerRealDataListWithToday(@Param("baseId") Long baseId,
                                                             @Param("gatewayIds") List<Long> gatewayIds, @Param(
            "today") String today);

    /**
     * 农场主获取当前周数据
     *
     * @return
     */
    List<MonitorRealDataVo> queryFarmerRealDataListWithWeek(@Param("baseId") Long baseId,
                                                            @Param("gatewayIds") List<Long> gatewayIds);

    /**
     * 农场主获取当前月数据
     *
     * @return
     */
    List<MonitorRealDataVo> queryFarmerRealDataListWithMonth(@Param("baseId") Long baseId,
                                                             @Param("gatewayIds") List<Long> gatewayIds);


    /**
     * 查询当前天的所有平均数据
     *
     * @param today
     * @return
     */
    List<MonitorRealDataVo> queryAvgMeteorologicalAndSoilList(@Param("today") String today,
                                                              @Param("businessId") Long businessId);

    /**
     * 查询地块数据
     *
     * @param businessId 农场id
     * @param tunelId    地块id
     * @return
     */
    List<MonitorRealDataVo> queryAvgDataByFarmTunelId(@Param("businessId") Long businessId,
                                                      @Param("tunelId") Long tunelId);

    /**
     * 查询租地数据
     *
     * @param businessId 农场id
     * @param rentLandId 租地id
     * @return {@link List}
     */
    List<MonitorRealDataVo> queryAvgDataByRentLandId(@Param("businessId") Long businessId,
                                                     @Param("rentLandId") Long rentLandId);

    /**
     * 根据传感器ID获取
     *
     * @param sensorId {@link Long}传感器ID
     * @param day      {@link String}哪一天,格式:yyyyMMdd
     * @return {@link List<WeChatTemperatureVo>}
     */
    List<WeChatTemperatureVo> getAvgRealDataWithDayBySensorId(@Param("sensorId") Long sensorId,
                                                              @Param("day") String day);
}