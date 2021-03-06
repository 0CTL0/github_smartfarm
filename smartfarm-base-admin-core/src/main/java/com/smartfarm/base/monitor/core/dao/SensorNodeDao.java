package com.smartfarm.base.monitor.core.dao;

import com.smartfarm.base.monitor.core.entity.SensorNode;
import com.smartfarm.base.monitor.core.entity.vo.SensorNodeVo;
import com.smartfarm.base.monitor.core.entity.vo.WeChatMonitorSensorVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SensorNodeDao {

    int deleteById(Long id);

    int insert(SensorNode record);

    SensorNode selectById(Long id);

    int updateById(SensorNode record);

    /**
     * 分页查询传感器节点
     *
     * @param start
     * @param limit
     * @return
     */
    List<SensorNodeVo> queryMonitorsensorNodeList(@Param("start") Integer start, @Param("limit") Integer limit);

    /**
     * 查询传感器节点数量
     *
     * @return
     */
    int queryCountMonitorsensorNodeList();

    /**
     * 农场主分页查询传感器节点
     *
     * @param baseId
     * @param start
     * @param limit
     * @return
     */
    List<SensorNodeVo> queryFarmerSensorNodeList(@Param("baseId") Long baseId, @Param("start") Integer start, @Param(
            "limit") Integer limit);

    /**
     * 农场主查询传感器节点数量
     *
     * @param baseId
     * @return
     */
    int queryCountFarmerSensorList(@Param("baseId") Long baseId);

    /**
     * 根据id查询传感器节点
     *
     * @param id
     * @return
     */
    SensorNodeVo querySensorNodeById(@Param("id") Long id);

    /**
     * 查找土地code和名称
     *
     * @param ids
     * @return
     */
    List<SensorNode> querySersorCodeList(@Param("ids") List<Long> ids);

    /**
     * 根据code查询传感数据
     *
     * @param productCode
     * @return
     */
    List<SensorNodeVo> querySersorNodeByCode(@Param("today") String today, @Param("productCode") String productCode);

    /**
     * 根据code和sensorCode查询传感数据
     *
     * @param productCode
     * @param codes
     * @return
     */
    List<SensorNodeVo> querySersorNodeByCodeWithsSersorCode(@Param("today") String today,
                                                            @Param("productCode") String productCode,
                                                            @Param("codes") List<String> codes);

    /**
     * 查询土壤数据平均值
     *
     * @param today
     * @param ids
     * @return
     */
    List<SensorNodeVo> queryAvgSersorNode(@Param("today") String today, @Param("ids") List<Long> ids);

    List<SensorNodeVo> queryWarnningSersorByCode(@Param("gatewayId") Long gatewayId);

    /**
     * 根据网关编号product_code(gateway_id)和传感器编号product_code来查询传感器的id
     *
     * @param map
     * @return
     */
    SensorNode getIdByProductCodeAndGateWayId(Map<String, Object> map);

    /**
     * 根据网关查询
     *
     * @param gatewayId
     * @return
     */
    List<SensorNode> queryListByGatewayId(Long gatewayId);

    /**
     * 查询农场的传感器
     *
     * @param farmId
     * @return
     */
    List<SensorNode> getFarmSensorNodes(@Param("farmId") Long farmId);

    /**
     * 根据网关ID查询所有传感器
     * 条件1:网关ID  条件2:状态在线(status=2)  条件3:今天
     *
     * @param today     今天
     * @param gatewayId 网关ID
     * @return {@link List}
     */
    List<WeChatMonitorSensorVo> listSensorNodeAndTodayNewestRealDataByGatewayId(@Param("today") String today,
                                                                                @Param("gatewayId") Long gatewayId);
}