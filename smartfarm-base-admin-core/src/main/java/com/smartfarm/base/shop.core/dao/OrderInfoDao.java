package com.smartfarm.base.shop.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.OrderInfo;
import com.smartfarm.base.shop.core.entity.vo.OrderInfoVo;

public interface OrderInfoDao {

	/**
	 * 添加
	 * @param orderInfo
	 * @return
	 */
    public int insert(OrderInfo orderInfo);

    /**
     * 根据id修改
     * @param orderInfo
     * @return
     */
    public int updateById(OrderInfo orderInfo);
    
    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    public OrderInfo queryById(Long id);
    
    /**
     * 查询未支付订单id
     * @param nowDate
     * @return
     */
    public List<Long> queryOrderUnPayByDate(@Param("nowDate") String nowDate);
    
    /**
     * 根据订单号查询
     * @param orderNo
     * @return
     */
    public OrderInfo queryByOrderNo(@Param("orderNo") String orderNo);
    
    /**
     * 根据用户id查询
     * @param memberId
     * @return
     */
    public List<OrderInfoVo> queryOrderByMemberId(Long memberId);

    public List<OrderInfoVo> getOrderByMemberId(Long memberId);
    
    /**
     * 分页查找所有订单
     * @param orderNo
     * @param start
     * @param limit
     * @return
     */
    public List<OrderInfoVo> queryAllOrder(@Param("order_no") String orderNo, @Param("status") Short status, @Param("start") Integer start,
                                           @Param("limit") Integer limit, @Param("businessId") Long businessId, @Param("startDate") String startDate, @Param("endDate") String endDate);
    /**
     * 查找所有订单数量
     * @param orderNo
     * @param status
     * @return
     */
    public Integer queryCountAllOrder(@Param("order_no") String orderNo, @Param("status") Short status, @Param("businessId") Long businessId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 分页查找今日所有的订单
     * @param nowDate
     * @param start
     * @param limit
     * @param businessId
     * @return
     */
    List<OrderInfoVo> queryTodayOrder(@Param("nowDate") String nowDate, @Param("start") Integer start,
                                      @Param("limit") Integer limit, @Param("businessId") Long businessId);
    /**
     * 统计今日所有订单的数量
     * @param nowDate
     * @return
     */
    Integer queryCountTodayOrder(@Param("nowDate") String nowDate, @Param("businessId") Long businessId);


    /**
     * 根据订单id查看订单
     * @param id
     * @return
     */
    public OrderInfoVo queryOrderByOid(@Param("id") Long id);

    /**
     * 分页查找所有退款订单
     * @param orderNo
     * @param start
     * @param limit
     * @return
     */
    public List<OrderInfoVo> queryAllOrderRefund(@Param("order_no") String orderNo, @Param("status") Short status, @Param("start") Integer start,
                                                 @Param("limit") Integer limit, @Param("businessId") Long businessId, @Param("startDate") String startDate, @Param("endDate") String endDate);
    /**
     * 查找所有退款订单数量
     * @param orderNo
     * @param status
     * @return
     */
    public Integer queryCountAllOrderRefund(@Param("order_no") String orderNo, @Param("status") Short status, @Param("businessId") Long businessId, @Param("startDate") String startDate, @Param("endDate") String endDate);

}