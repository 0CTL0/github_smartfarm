package com.smartfarm.base.monitor.core.dao;

import com.smartfarm.base.monitor.core.entity.GatewayNode;
import com.smartfarm.base.monitor.core.entity.vo.GatewayNodeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GatewayNodeDao {

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
    List<GatewayNode> queryMonitorGatewayList(@Param("start") Integer start, @Param("limit") Integer limit);

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
    String queryProductCodeById(@Param("id") Long id);

    /**
     * 查询农场主可以查询的网关
     *
     * @param businessId {@link Long} 农场id
     * @param start      {@link Integer} 分页开始下标
     * @param limit      @link Integer} 分页行数
     * @return {@link List} 网关集合
     */
    List<GatewayNode> queryFarmerMonitorGatewayList(@Param("businessId") Long businessId,
                                                    @Param("start") Integer start, @Param("limit") Integer limit);

    /**
     * 查询农场主可以查询的网关数量
     *
     * @param businessId {@link Long} 农场id
     * @return {@link int} 网关数量
     */
    int queryCountFarmerMonitorGateway(@Param("businessId") Long businessId);

    /**
     * 查询农场主的所有网关id
     *
     * @param businessId {@link Long} 农场id
     * @return {@link List} 网关集合
     */
    List<GatewayNode> getGatewayIdByFarmer(@Param("businessId") Long businessId);

    /**
     * 根据商户id查询
     *
     * @param businessId {@link Long} 农场id
     * @return {@link List} 网关VO对象
     */
    List<GatewayNodeVo> queryVoByBusinessId(Long businessId);

    /**
     * 根据网关id查询
     *
     * @param id {@link Long} 网关id
     * @return {@link GatewayNode} 网关对象
     */
    GatewayNode queryGatewayNodeById(Long id);

    /**
     * 根据农场ID查询网关集合
     * 条件1:农场ID  条件2:状态为联机(status=2)
     *
     * @param businessId {@link Long} 农场ID
     * @return {@link List} 网关集合
     */
    List<GatewayNode> listGatewayByBusinessId(@Param("businessId") Long businessId);
}