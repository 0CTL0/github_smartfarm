package com.smartfarm.base.farm.core.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.farm.core.entity.CrowdfundingOrder;
import com.smartfarm.base.farm.core.entity.vo.CrowFundingOrderVo;
import com.smartfarm.base.farm.core.entity.vo.CrowdfundingOrderDetailVo;

public interface CrowdFundingOrderManager {

	/**
     * 查詢所有众筹订单
     * @param orderNo
     * @param start
     * @param limit
     * @return
     */
    public List<CrowFundingOrderVo> queryOrderList(Short shipStatus, Short status, String orderNo, Integer start, Integer limit);

    
    /**
     * 查詢所有众筹订单数量
     * @param orderNo
     * @return
     */
    public int queryCountOrderList(Short shipStatus, Short status, String orderNo);
    
    /**
     * 根据id查询订单详细
     * @param id
     * @return
     */
    public CrowFundingOrderVo queryOrderDetailById(Long id);

    /**
     * 根据id修改
     * @param crowdfundingOrder
     * @return
     */
    public int updateById(CrowdfundingOrder crowdfundingOrder);

    /**
     * 更新订单
     * @param crowdfundingOrder
     */
	public void updateDetailById(CrowdfundingOrder crowdfundingOrder);

	/**
	 * 更新為已收货状态
	 * @param crowdfundingOrder
	 */
	 public void updateOrderOk(CrowdfundingOrder crowdfundingOrder);

	 /**
     * 查询众筹列表
     * @param start
     * @param limit
     * @return
     */
	 public List<CrowFundingOrderVo> queryCrowOrderList(Long id, Integer start, Integer limit);

	 /**
     * 查询众筹列表数量
     * @return
     */
     public int queryCountCrowdList(Long id);

     /**
      * 发放分红
      * @param crowdfundingOrder
      * @return
      */
     public int updateBonusById(CrowdfundingOrder crowdfundingOrder);

     /**
      * 一键分红
      * @return
      */
     public int updateAllBonus();

     /**
      * 保存订单(小程序端)
      * @param crowdfundingOrder
      * @return
      */
     public String saveCrowdfundingOrder(HttpServletRequest request, CrowdfundingOrder crowdfundingOrder) throws Exception;
     
     /**
      * 返回所有订单(小程序端)
      * @param status
      * @param shipStatus
      * @return
      */
     public List<CrowFundingOrderVo> getAllCrowdfundingOrderList(Short status, Short shipStatus);
     
     /**
      * 根据订单号查询订单详细
      * @param orderNo
      * @return
      */
     public CrowFundingOrderVo getCrowdfundingOrderDetail(String orderNo);
     
     /**
      * 查询结算订单详细
      * @param orderNo
      * @return
      */
     public CrowdfundingOrderDetailVo queryOrderDetailByOrderNo(String orderNo);
     
     /**
      * 查询我的订单列表
      * @return
      */
     public List<CrowdfundingOrderDetailVo> queryMemberOrderList(HttpServletRequest request);

     /**
      * 根据状态查询我的订单列表
      * @return
      */
     public List<CrowdfundingOrderDetailVo> queryMemberOrderListWithStatus(HttpServletRequest request, short status);
     
     /***
      * 取消订单
      * @param crowdfundingOrder
      * @return
      */
     public int cancelOrder(CrowdfundingOrder crowdfundingOrder);

    /**
     * 设置订单超时
     * @param id
     * @return
     */
     int payTimeOut(Long id);

    CrowdfundingOrder queryOrderById(Long id);
}
