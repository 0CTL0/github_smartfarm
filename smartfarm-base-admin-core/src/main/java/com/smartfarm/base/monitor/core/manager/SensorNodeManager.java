package com.smartfarm.base.monitor.core.manager;

import com.smartfarm.base.monitor.core.entity.SensorNode;
import com.smartfarm.base.monitor.core.entity.vo.SensorNodeVo;
import com.smartfarm.base.monitor.core.entity.vo.WeChatMonitorSensorVo;

import java.util.List;

public interface SensorNodeManager {
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
    List<SensorNodeVo> queryMonitorsensorNodeList(Integer start, Integer limit);

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
    List<SensorNodeVo> queryFarmerSensorNodeList(Long baseId, Integer start, Integer limit);

    /**
     * 农场主查询传感器节点数量
     *
     * @param baseId
     * @return
     */
    int queryCountFarmerSensorList(Long baseId);

    /**
     * 根据id查询传感器节点
     *
     * @param id
     * @return
     */
    SensorNodeVo querySensorNodeById(Long id);

    /**
     * 查找土地code和名称
     *
     * @param ids
     * @return
     */
    List<SensorNode> querySersorCodeList(List<Long> ids);

    /**
     * 根据gatewayId查询传感数据
     *
     * @param today
     * @param productCode
     * @return
     */
    List<SensorNodeVo> querySersorNodeByCode(String today, String productCode);

    /**
     * 根据code和sensorCode查询传感数据
     *
     * @param productCode
     * @param codes
     * @return
     */
    List<SensorNodeVo> querySersorNodeByCodeWithsSersorCode(String today, String productCode, List<String> codes);

    /**
     * 查询土壤数据平均值
     *
     * @param today
     * @param ids
     * @return
     */
    List<SensorNodeVo> queryAvgSersorNode(String today, List<Long> ids);

    List<SensorNodeVo> queryWarnningSersorByCode(Long gatewayId);

    List<Long> queryWarningLists(List<Long> ids);

    /**
     * 查询农场的传感器
     *
     * @param farmId
     * @return
     */
    List<SensorNode> queryFarmSensorNodes(Long farmId);

    /**
     * 根据网关ID查询所有传感器
     * 条件1:网关ID  条件2:状态在线(status=2)  条件3:今天
     *
     * @param today     今天
     * @param gatewayId 网关ID
     * @return {@link List}
     */
    List<WeChatMonitorSensorVo> listSensorNodeAndTodayNewestRealDataByGatewayId(String today, Long gatewayId);
}
