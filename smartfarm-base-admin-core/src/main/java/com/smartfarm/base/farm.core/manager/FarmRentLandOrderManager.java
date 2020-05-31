package com.smartfarm.base.farm.core.manager;

import java.util.List;

import com.smartfarm.base.farm.core.entity.FarmMemberLand;
import com.smartfarm.base.farm.core.entity.FarmRentLandOrder;
import com.smartfarm.base.farm.core.entity.vo.FarmRentLandOrderVo;

public interface FarmRentLandOrderManager {
    /**
     * 查询订单列表
     * @param orderCode
     * @param rentName
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<FarmRentLandOrder> getRentLandOrderList(Long farmId, String orderCode, String rentName, Short status, Integer start, Integer limit);

    /**
     * 统计订单列表
     * @param orderCode
     * @param rentName
     * @param status
     * @return
     */
    public int countRentLandOrderTotal(Long farmId, String orderCode, String rentName, Short status);

    /**
     * 通过id获取租赁订单
     * @param id
     * @return
     */
    public FarmRentLandOrder getRentOrderById(Long id);

    /**
     * 更新订单信息
     * @param farmRentLandOrder
     * @return
     */
    public int editFarmRentOrder(FarmRentLandOrder farmRentLandOrder);

    /**
     * 创建订单，加入订单定时器
     * @param farmRentLandOrder
     * @return
     */
    public Boolean createRentOrder(FarmRentLandOrder farmRentLandOrder) throws Exception;

    /**
     * 通过订单编号获取订单
     * @param orderCode
     * @return
     */
    public FarmRentLandOrder getRentOrderByOrderCode(String orderCode);

    /**
     * 获取用户全部的订单
     * @return
     */
    public List<FarmRentLandOrderVo> getAllRentOrder(Long memberId);

    /**
     * 删除订单
     * @param id
     * @return
     */
    public int removeRentOrder(Long id);

    /**
     * 取消订单
     * @param id
     * @return
     */
    public int cleanOrder(Long id);

    /**
     * 生成用户土地记录
     * @param farmMemberLand
     * @return
     */
    public int addFarmMemberLand(FarmMemberLand farmMemberLand);

    /**
     * 取消订单
     * @param id
     * @return
     */
    public int orderCancel(Long id);
    
    /**
     * 订单超时
     * @param id
     */
    public void orderOutTime(Long id);

    /**
     * 订单支付成功
     * @param farmRentLandOrder
     */
    void orderSuccess(FarmRentLandOrder farmRentLandOrder);
}
