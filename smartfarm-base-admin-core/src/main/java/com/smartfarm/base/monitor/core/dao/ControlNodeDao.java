package com.smartfarm.base.monitor.core.dao;

import com.smartfarm.base.monitor.core.entity.ControlNode;
import com.smartfarm.base.monitor.core.entity.ControlNodeDetails;
import com.smartfarm.base.monitor.core.entity.GatewayNode;
import com.smartfarm.base.monitor.core.entity.vo.WeChatControlNodeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ControlNodeDao {
    /**
     * 根据id获取单个设备信息
     *
     * @param id {@link Long} 控制设备ID
     * @return {@link List}
     */
    ControlNode getById(@Param("id") Long id);

    /**
     * 根据id修改节点状态、信息
     *
     * @param controlNode {@link ControlNode} 控制设备对象
     * @return {@link int}
     */
    int updateById(ControlNode controlNode);

	/**
	 * 获取所有控制设备信息
	 * @return
	 */
	public List<ControlNode> getControlNodes();


	/**
	 * 获取所有控制设备信息详情
	 * @return
	 */
	public List<ControlNodeDetails> getControlNodesDetails();

	/**
	 * 查询农场主的设备
	 * @return
	 */
	public List<ControlNode> getFarmerControlNodes(@Param("gatewayNodes") List<GatewayNode> gatewayNodes);

	/**
	 * 查询Control_Node列表
	 *
	 * @return
	 */
	public List<ControlNode> queryControllerNodeList();

	/**
	 * 添加设备
	 * @param controlNode
	 * @return
	 */
	public int add(ControlNode controlNode);

	/**
	 * 根据id删除设备
	 * @param id
	 * @return
	 */
	int deleteById(@Param("id") Long id);

	/**
	 * 根据网关id查询
	 * @param gatewayId
	 * @return
	 */
	List<ControlNode> queryByGatewayId(Long gatewayId);

	/**
	 * 根据控制器id查询节点
	 * @param controlSetId
	 * @return
	 */
	public List<ControlNode> queryNodeBySetId(Long controlSetId);

    /**
     * 根据地块id获取对应控制节点
     *
     * @param landId {@link Long}地块id
     * @return {@link ControlNode}
     */
    ControlNode getNodeByLandId(@Param("landId") Long landId);

    /**
     * 查询农场的控制节点
     *
     * @param farmId {@link Long}农场id
     * @return {@link List}
     */
    List<ControlNode> getFarmControlNodes(@Param("farmId") Long farmId);

    /**
     * 根据网关ID获取其对应下的控制器及定时策略数量
     * 条件1:网关ID  条件2:控制器节点状态联机(status=2)
     *
     * @param gatewayId {@link Long} 网关ID
     * @return @return {@link Object}
     */
    List<WeChatControlNodeVo> listControllerNodeAndSetCountWithOnline(Long gatewayId);
}
