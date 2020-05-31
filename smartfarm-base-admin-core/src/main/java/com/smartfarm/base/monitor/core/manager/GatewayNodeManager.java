package com.smartfarm.base.monitor.core.manager;

import com.smartfarm.base.monitor.core.entity.GatewayNode;
import com.smartfarm.base.monitor.core.entity.vo.GatewayNodeVo;

import java.util.List;

public interface GatewayNodeManager {
    int deleteById(Long id);

    int insert(GatewayNode record);

    GatewayNode selectById(Long id);

    int updateById(GatewayNode record);

    /**
     * 分页查询传感器网关
     *
     * @param start {@link Integer} 分页开始下标
     * @param limit {@link Integer} 分页行数
     * @return {@link List}
     */
    List<GatewayNode> queryMonitorGatewayList(Integer start, Integer limit);

    /**
     * 查询传感器网关数量
     *
     * @return {@link int}
     */
    int queryCountMonitorGatewayList();

    /**
     * 查询全部传感器网关
     *
     * @return {@link List}
     */
    List<GatewayNode> queryAllGatewayNode();

    /**
     * 根据网关id查询其编号
     *
     * @param id {@link Long} 网关id
     * @return {@link String}
     */
    String queryProductCodeById(Long id);

    /**
     * 分页查询农场主可以查询的网关
     *
     * @param baseId {@link Long} 农场ID
     * @param start  {@link Integer} 分页开始下标
     * @param limit  {@link Integer} 分页行数
     * @return {@link List}
     */
    List<GatewayNode> queryFarmerMonitorGatewayList(Long baseId, Integer start, Integer limit);

    /**
     * 查询农场主可以查询的网关数量
     *
     * @param baseId {@link Long} 农场ID
     * @return {@link List}
     */
    int queryCountFarmerMonitorGateway(Long baseId);

    /**
     * 查询农场主的所有网关id
     *
     * @param baseId {@link Long} 农场ID
     * @return {@link List}
     */
    List<GatewayNode> queryMonitorGatewayIdByFarmer(Long baseId);

    /**
     * 根据农场ID查询所有网关，以及网关下所有的控制器
     *
     * @param businessId {@link Long} 农场ID
     * @return {@link List}
     */
    List<GatewayNodeVo> queryGatewayVoForControl(Long businessId);

    /**
     * 根据农场ID查询所有网关，以及网关下所有的传感器
     *
     * @param businessId 农场ID
     * @return {@link List}
     */
    List<GatewayNodeVo> queryGatewayVoForSensor(Long businessId);

    /**
     * 根据农场ID查询网关集合
     * 条件1:农场ID  条件2:状态为联机(status=2)
     *
     * @param businessId {@link Long} 农场ID
     * @return {@link List}
     */
    List<GatewayNode> listGatewayByBusinessId(Long businessId);
}
