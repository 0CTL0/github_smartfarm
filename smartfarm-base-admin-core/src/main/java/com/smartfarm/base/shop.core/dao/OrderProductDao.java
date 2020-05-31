package com.smartfarm.base.shop.core.dao;

import java.util.List;

import com.smartfarm.base.shop.core.entity.OrderProduct;
import com.smartfarm.base.shop.core.entity.vo.OrderProductVo;
import org.apache.ibatis.annotations.Param;

public interface OrderProductDao {
	/**
	 * 添加
	 * @param orderProduct
	 * @return
	 */
    public int insert(OrderProduct orderProduct);

    /**
     * 根据id修改
     * @param orderProduct
     * @return
     */
    public int updateById(OrderProduct orderProduct);
    
    /**
     * 根据订单id查询产品
     * @param orderId
     * @return
     */
    public List<OrderProduct> queryByOrderId(Long orderId);
    
    /**
     * 根据字订单id查询产品
     * @param orderDetailId
     * @return
     */
    public List<OrderProduct> queryByOrderDetailId(Long orderDetailId);

    public List<OrderProductVo> queryOrderProductByDateAndFarmId(@Param("startDate") String startDate, @Param("endDate") String endDate,
                                                                 @Param("farmId") Long farmId);
}