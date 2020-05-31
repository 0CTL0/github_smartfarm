package com.smartfarm.base.monitor.core.manager.impl;

import com.smartfarm.base.monitor.core.dao.ControlNodeDao;
import com.smartfarm.base.monitor.core.dao.GatewayNodeDao;
import com.smartfarm.base.monitor.core.dao.SensorNodeDao;
import com.smartfarm.base.monitor.core.entity.GatewayNode;
import com.smartfarm.base.monitor.core.entity.vo.GatewayNodeVo;
import com.smartfarm.base.monitor.core.manager.GatewayNodeManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("gatewayNodeManager")
public class GatewayNodeManagerImpl implements GatewayNodeManager {

    @Resource
    private GatewayNodeDao gatewayNodeDao;
    @Resource
    private ControlNodeDao controlNodeDao;
    @Resource
    private SensorNodeDao sensorNodeDao;

    @Override
    public int deleteById(Long id) {
        return gatewayNodeDao.deleteById(id);
    }

    @Override
    public int insert(GatewayNode record) {
        record.setStatus((short) 1);
        return gatewayNodeDao.insert(record);
    }

    @Override
    public GatewayNode selectById(Long id) {
        return gatewayNodeDao.selectById(id);
    }

    @Override
    public int updateById(GatewayNode record) {
        return gatewayNodeDao.updateById(record);
    }

    @Override
    public List<GatewayNode> queryMonitorGatewayList(Integer start,
                                                     Integer limit) {
        return gatewayNodeDao.queryMonitorGatewayList(start, limit);
    }

    @Override
    public int queryCountMonitorGatewayList() {
        return gatewayNodeDao.queryCountMonitorGatewayList();
    }

    @Override
    public List<GatewayNode> queryAllGatewayNode() {
        return gatewayNodeDao.queryAllGatewayNode();
    }

    @Override
    public String queryProductCodeById(Long id) {
        return gatewayNodeDao.queryProductCodeById(id);
    }

    @Override
    public List<GatewayNode> queryFarmerMonitorGatewayList(Long baseId, Integer start,
                                                           Integer limit) {
        return gatewayNodeDao.queryFarmerMonitorGatewayList(baseId, start, limit);
    }

    @Override
    public int queryCountFarmerMonitorGateway(Long baseId) {
        return gatewayNodeDao.queryCountFarmerMonitorGateway(baseId);
    }

    @Override
    public List<GatewayNode> queryMonitorGatewayIdByFarmer(Long baseId) {
        return gatewayNodeDao.getGatewayIdByFarmer(baseId);
    }

    @Override
    public List<GatewayNodeVo> queryGatewayVoForControl(Long businessId) {
        List<GatewayNodeVo> list = gatewayNodeDao.queryVoByBusinessId(businessId);
        for (GatewayNodeVo gatewayNodeVo : list) {
            gatewayNodeVo.setControlNodeList(controlNodeDao.queryByGatewayId(gatewayNodeVo.getId()));
        }
        return list;
    }

    @Override
    public List<GatewayNodeVo> queryGatewayVoForSensor(Long businessId) {
        List<GatewayNodeVo> list = gatewayNodeDao.queryVoByBusinessId(businessId);
        for (GatewayNodeVo gatewayNodeVo : list) {
            gatewayNodeVo.setSensorNodeList(sensorNodeDao.queryListByGatewayId(gatewayNodeVo.getId()));
        }
        return list;
    }

    /**
     * 根据农场ID查询网关集合
     * 条件1:农场ID  条件2:状态为联机(status=2)
     *
     * @param businessId {@link Long} 农场ID
     * @return {@link List} 网关集合
     */
    @Override
    public List<GatewayNode> listGatewayByBusinessId(Long businessId) {
        return gatewayNodeDao.listGatewayByBusinessId(businessId);
    }
}
