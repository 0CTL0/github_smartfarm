package com.smartfarm.base.monitor.core.manager;

import com.smartfarm.base.monitor.core.entity.ControlNode;
import com.smartfarm.base.monitor.core.entity.ControlNodeDetails;
import com.smartfarm.base.monitor.core.entity.GatewayNode;
import com.smartfarm.base.monitor.core.entity.vo.WeChatControlNodeVo;

import java.util.List;

public interface ControlNodeManager {

    /**
     * 获取所有设备信息
     *
     * @return
     */
    List<ControlNode> getControlNodes();

    /**
     * 查询农场主的设备
     *
     * @return
     */
    List<ControlNode> queryFarmerControlNodes(List<GatewayNode> gatewayNodes);
	/**
	 * 获取所有设备信息详情
	 * @return
	 */
	public List<ControlNodeDetails> getControlNodesDetails();

    /**
     * 根据id过去单个设备信息
     *
     * @param nodeId
     * @return
     */
    ControlNode getSingleControlNode(Long nodeId);

    /**
     * 根据id修改节点信息
     *
     * @param controlNode
     * @return
     */
    int updateControlNodeById(ControlNode controlNode);

    /**
     * 查询Control_Node列表
     *
     * @return
     */
    List<ControlNode> queryControllerNodeList();

    /**
     * 发送主题，控制节点开关
     *
     * @param gatewayCode
     * @param productCode
     * @param operateStatus
     */
    void publishMessage(String gatewayCode, String productCode, Short operateStatus);

    /**
     * 添加设备
     *
     * @param controlNode
     * @return
     */
    int addControlNode(ControlNode controlNode);

    /**
     * 根据id删除设备
     *
     * @param id
     * @return
     */
    int deleteControlNodeById(Long id);

    /**
     * 控制器操作
     *
     * @param id {@link Long} 控制器设备ID
     * @param status {@link Short} 操作状态（1、开 2、关 3、暂停）
     */
    void controlNodeOperate(Long id, Short status);

    /**
     * 查询农场的控制节点
     *
     * @param farmId
     * @return
     */
    List<ControlNode> queryFarmControlNodes(Long farmId);

    /**
     * 根据网关ID获取其对应下的控制器及定时策略数量
     * 条件1:网关ID  条件2:控制器节点状态联机(status=2)
     *
     * @param gatewayId {@link Long} 网关ID
     * @return @return {@link Object}
     */
    List<WeChatControlNodeVo> listControllerNodeAndSetCountWithOnline(Long gatewayId);
}
