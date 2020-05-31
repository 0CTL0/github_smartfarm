package com.smartfarm.base.monitor.core.manager.impl;

import com.smartfarm.base.monitor.core.dao.SensorNodeDao;
import com.smartfarm.base.monitor.core.entity.SensorNode;
import com.smartfarm.base.monitor.core.entity.vo.SensorNodeVo;
import com.smartfarm.base.monitor.core.entity.vo.WeChatMonitorSensorVo;
import com.smartfarm.base.monitor.core.manager.SensorNodeManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("sensorNodeManager")
public class SensorNodeManagerImpl implements SensorNodeManager {

    @Resource
    private SensorNodeDao sensorNodeDao;

    @Override
    public int deleteById(Long id) {
        return sensorNodeDao.deleteById(id);
    }

    @Override
    public int insert(SensorNode record) {
        record.setStatus((short) 1);
        return sensorNodeDao.insert(record);
    }

    @Override
    public SensorNode selectById(Long id) {
        return sensorNodeDao.selectById(id);
    }

    @Override
    public int updateById(SensorNode record) {
        return sensorNodeDao.updateById(record);
    }

    @Override
    public List<SensorNodeVo> queryMonitorsensorNodeList(Integer start,
                                                         Integer limit) {
        return sensorNodeDao.queryMonitorsensorNodeList(start, limit);
    }

    @Override
    public int queryCountMonitorsensorNodeList() {
        return sensorNodeDao.queryCountMonitorsensorNodeList();
    }

    @Override
    public SensorNodeVo querySensorNodeById(Long id) {
        return sensorNodeDao.querySensorNodeById(id);
    }

    @Override
    public List<SensorNode> querySersorCodeList(List<Long> ids) {
        return sensorNodeDao.querySersorCodeList(ids);
    }

    @Override
    public List<SensorNodeVo> querySersorNodeByCode(String today, String productCode) {
        return sensorNodeDao.querySersorNodeByCode(today, productCode);
    }

    @Override
    public List<SensorNodeVo> queryAvgSersorNode(String today, List<Long> ids) {
        return sensorNodeDao.queryAvgSersorNode(today, ids);
    }

    @Override
    public List<SensorNodeVo> queryWarnningSersorByCode(Long gatewayId) {
        return sensorNodeDao.queryWarnningSersorByCode(gatewayId);
    }

    /**
     * 统计土壤警报的具体哪一套
     */
    @Override
    public List<Long> queryWarningLists(List<Long> ids) {
        List<Long> warnnings = new ArrayList<Long>();
        for (Long gatewayId : ids) {
            List<SensorNodeVo> sens = sensorNodeDao.queryWarnningSersorByCode(gatewayId);
            for (SensorNodeVo sensorNodeVo : sens) {
                double real = sensorNodeVo.getRealValue();
                Double min = sensorNodeVo.getMinThreshold();
                Double max = sensorNodeVo.getMaxThreshold();
                if (real >= max || real <= min) {
                    warnnings.add(sensorNodeVo.getGatewayId());
                    break;
                }
            }
        }
        return warnnings;
    }

    @Override
    public List<SensorNode> queryFarmSensorNodes(Long farmId) {
        return sensorNodeDao.getFarmSensorNodes(farmId);
    }

    @Override
    public List<SensorNodeVo> querySersorNodeByCodeWithsSersorCode(String today,
                                                                   String productCode, List<String> codes) {
        return sensorNodeDao.querySersorNodeByCodeWithsSersorCode(today, productCode, codes);
    }

    @Override
    public List<SensorNodeVo> queryFarmerSensorNodeList(Long baseId,
                                                        Integer start, Integer limit) {
        return sensorNodeDao.queryFarmerSensorNodeList(baseId, start, limit);
    }

    @Override
    public int queryCountFarmerSensorList(Long baseId) {
        return sensorNodeDao.queryCountFarmerSensorList(baseId);
    }

    /**
     * 根据网关ID查询所有传感器
     * 条件1:网关ID  条件2:状态在线(status=2)  条件3:今天
     *
     * @param today     今天
     * @param gatewayId 网关ID
     * @return {@link List}
     */
    @Override
    public List<WeChatMonitorSensorVo> listSensorNodeAndTodayNewestRealDataByGatewayId(String today,Long gatewayId) {
        return this.sensorNodeDao.listSensorNodeAndTodayNewestRealDataByGatewayId(today,gatewayId);
    }
}
