package com.smartfarm.base.shop.core.dao;

import com.smartfarm.base.shop.core.entity.OrderRefund;
import org.apache.ibatis.annotations.Param;

public interface OrderRefundDao {
    int deleteByPrimaryKey(Long id);


    int insertSelective(OrderRefund record);

    OrderRefund selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderRefund record);

    /**
     * 通过退款单号查询
     * @param refundNo
     * @return
     */
    OrderRefund selectByRefundNo(@Param("refundNo") String refundNo);

    /**
     * 通过订单id查询
     * @param orderId
     * @return
     */
    OrderRefund selectByOrderId(@Param("orderId") Long orderId);
}