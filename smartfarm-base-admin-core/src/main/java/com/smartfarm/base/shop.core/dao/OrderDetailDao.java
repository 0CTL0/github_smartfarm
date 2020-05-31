package com.smartfarm.base.shop.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.OrderDetail;
import com.smartfarm.base.shop.core.entity.vo.OrderDetailVo;

public interface OrderDetailDao {
	
	/**
	 * 新增
	 * @param record
	 * @return
	 */
    public int insert(OrderDetail orderDetail);
    
    /**
     * 修改
     * @param record
     * @return
     */
    public int updateById(OrderDetail orderDetail);
    
    /**
     * 根据订单id查询子订单
     * @param orderId
     * @return
     */
    public List<OrderDetailVo> queryByOrderId(Long orderId);
    
    /**
     * 分页查找所有订单详细
     * @param ship_no
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<OrderDetail> queryAllOrderDetail(@Param("ship_no") String ship_no, @Param("status") Short status, @Param("start") Integer start, @Param("limit") Integer limit);
    /**
     * 查找所有订单详细数量
     * @param ship_no
     * @param status
     * @return
     */
    public int queryCountAllOrderDetail(@Param("ship_no") String ship_no, @Param("status") Short status);
    
    /**
     * 根据订单详情id查看订单详细
     * @param id
     * @return
     */
    public OrderDetailVo queryOrderDetailById(@Param("id") Long id);

    /**
     * 分页查找所有商户订单详细
     * @param businessId
     * @param ship_no
     * @param status
     * @param start
     * @param limit
     * @return
     */
	public List<OrderDetail> queryAllBusinessOrderDetail(@Param("businessId") Long businessId,
                                                         @Param("ship_no") String ship_no, @Param("status") Short status, @Param("start") Integer start, @Param("limit") Integer limit);

	/**
	 * 查找所有商户订单详细数量
	 * @param businessId
	 * @param ship_no
	 * @param status
	 * @return
	 */
	public int queryCountBusinessOrderDetail(@Param("businessId") Long businessId,
                                             @Param("ship_no") String ship_no, @Param("status") Short status);
}