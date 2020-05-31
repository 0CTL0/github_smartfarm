package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.CrowdfundingOrder;
import com.smartfarm.base.farm.core.entity.vo.CrowFundingOrderVo;
import com.smartfarm.base.farm.core.entity.vo.CrowdfundingOrderDetailVo;

public interface CrowdfundingOrderDao {
	
	/**
	 * 添加
	 * @param crowdfundingOrder
	 * @return
	 */
    public int insert(CrowdfundingOrder crowdfundingOrder);

    /**
     * 根据id修改
     * @param crowdfundingOrder
     * @return
     */
    public int updateById(CrowdfundingOrder crowdfundingOrder);
    
    public void updateOrderOk(CrowdfundingOrder crowdfundingOrder);
    
    /**
     * 查詢所有众筹订单
     * @param orderNo
     * @param start
     * @param limit
     * @return
     */
    public List<CrowFundingOrderVo> queryOrderList(@Param("shipStatus") Short shipStatus, @Param("status") Short status, @Param("orderNo") String orderNo, @Param("start") Integer start, @Param("limit") Integer limit);

    
    /**
     * 查詢所有众筹订单数量
     * @param orderNo
     * @return
     */
    public int queryCountOrderList(@Param("shipStatus") Short shipStatus, @Param("status") Short status, @Param("orderNo") String orderNo);
    
    /**
     * 根据id查询订单详细
     * @param id
     * @return
     */
    public CrowFundingOrderVo queryOrderDetailById(@Param("id") Long id);
    
    /**
     * 查询众筹列表
     * @param start
     * @param limit
     * @return
     */
    public List<CrowFundingOrderVo> queryCrowOrderList(@Param("id") Long id, @Param("start") Integer start, @Param("limit") Integer limit);
    /**
     * 查询众筹列表数量
     * @return
     */
    public int queryCountCrowdList(@Param("id") Long id);
    
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
    public long saveCrowdfundingOrder(CrowdfundingOrder crowdfundingOrder);
    
    /**
     * 根据id返回订单号
     * @param id
     * @return
     */
    public String queryOrderNoById(@Param("id") Long id);
    
    /**
     * 返回所有订单(小程序端)
     * @param status
     * @param shipStatus
     * @return
     */
    public List<CrowFundingOrderVo> getAllCrowdfundingOrderList(@Param("status") Short status, @Param("shipStatus") Short shipStatus);
    
    /**
     * 根据订单号查询订单详细
     * @param orderNo
     * @return
     */
    public CrowFundingOrderVo getCrowdfundingOrderDetail(@Param("orderNo") String orderNo);
    
    /**
     * 查询结算订单详细
     * @param orderNo
     * @return
     */
    public CrowdfundingOrderDetailVo queryOrderDetailByOrderNo(@Param("orderNo") String orderNo);
    
    /**
     * 查询我的订单列表
     * @param memberId
     * @param status
     * @return
     */
    public List<CrowdfundingOrderDetailVo> queryMemberOrderList(@Param("memberId") Long memberId);
    /**
     * 根据状态查询我的订单列表
     * @param memberId
     * @param status
     * @return
     */
    public List<CrowdfundingOrderDetailVo> queryMemberOrderListWithStatus(@Param("memberId") Long memberId, @Param("status") short status);
    
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
    int payTimeOut(@Param("id") Long id);

    CrowdfundingOrder queryOrderById(Long id);
}