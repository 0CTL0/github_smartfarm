package com.smartfarm.base.shop.core.manager;

import java.util.List;

import com.smartfarm.base.shop.core.entity.*;
import com.smartfarm.base.shop.core.entity.vo.MemberOrderCommissionVo;
import com.smartfarm.base.shop.core.entity.vo.OrderDetailVo;
import com.smartfarm.base.shop.core.entity.vo.OrderInfoVo;
import com.smartfarm.base.shop.core.entity.vo.OrderProductVo;
import org.apache.ibatis.annotations.Param;


public interface OrderInfoManager {

	/**
	 * 查询订单佣金提成列表数据
	 * @param farmId
	 * @param commissionMemberName
	 * @param orderMemberName
	 * @param orderNo
	 * @param start
	 * @param limit
	 * @return
	 */
	List<MemberOrderCommissionVo> getPageList(Long farmId, String commissionMemberName, String orderMemberName,
                                              String orderNo, Integer start, Integer limit);
	int countPageList(Long farmId, String commissionMemberName, String orderMemberName,
                      String orderNo);

	/**
	 * 若订单的用户存在推荐人，则将订单的佣金发放给推荐者
	 * @param orderInfo
	 * @return
	 */
	int orderCommissionHandle(OrderInfo orderInfo);
	
	/**
	 * 购物车生成订单
	 * @param list
	 */
	public Boolean createShopCardOrder(List<ShopCart> list, OrderInfo orderInfo) throws Exception;
	
	/**
	 * 生成购买订单
	 * @param orderProduct
	 * @param orderInfo
	 * @return
	 */
	public Boolean createSkuOrder(OrderProduct orderProduct, OrderInfo orderInfo, Short type) throws Exception;
	
	/**
     * 根据id查询订单
     * @param id
     * @return
     */
    public OrderInfo queryById(Long id);
    
    /**
     * 根据订单id查询产品
     * @param orderId
     * @return
     */
    public List<OrderProduct> queryProductByOrderId(Long orderId);
    
    /**
     * 根据id修改
     * @param orderInfo
     */
    public void updateById(OrderInfo orderInfo);
    
    /**
     * 查询未支付订单id
     * @param nowDate
     * @return
     */
    public List<Long> queryOrderUnPayByDate(String nowDate);
    
    /**
     * 查询订单
     * @param orderNo
     * @return
     */
    public OrderInfo queryByOrderNo(String orderNo);
    
    /**
     * 处理订单超时
     * @param orderId
     */
    public void orderOutTime(Long orderId);
    /**
     * 处理订单超时
     * @param orderId
     */
    public void orderCancel(Long orderId);
    
    /**
     * 根据订单id查询子订单
     * @param orderId
     * @return
     */
    public List<OrderDetailVo> queryOrderDetailByOrderId(Long orderId);
    
    /**
     * 根据id修改
     */
    public void updateDetailById(OrderDetail orderDetail);
    
    /**
     * 根据用户id查询
     * @param memberId
     * @return
     */
    public List<OrderInfoVo> queryOrderByMemberId(Long memberId);
    /**
     * 分页查找所有订单
     * @param orderNo
     * @param start
     * @param limit
     * @return
     */
    public List<OrderInfoVo> queryAllOrder(String orderNo, Short status, Integer start, Integer limit, Long businessId, String startDate, String endDate);
    /**
     * 查找所有订单数量
     * @param orderNo
     * @param status
     * @return
     */
    public Integer queryCountAllOrder(String orderNo, Short status, Long businessId, String startDate, String endDate);
    /**
     * 根据订单id查看订单
     * @param id
     * @return
     */
    public OrderInfoVo queryOrderByOid(Long id);
    /**
     * 分页查找所有订单详细
     * @param ship_no
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<OrderDetail> queryAllOrderDetail(String ship_no, Short status, Integer start, Integer limit);
    /**
     * 查找所有订单详细数量
     * @param ship_no
     * @param status
     * @return
     */
    public int queryCountAllOrderDetail(String ship_no, Short status);
    /**
     * 根据订单详情id查看订单详细
     * @param id
     * @return
     */
    public OrderDetailVo queryOrderDetailById(Long id);

    /**
     * 分页查找所有商户订单详细
     * @param businessId
     * @param ship_no
     * @param status
     * @param start
     * @return
     */
	public List<OrderDetail> queryAllBusinessOrderDetail(Long businessId, String ship_no,
                                                         Short status, Integer start, Integer limit);

	/**
	 * 查找所有商户订单详细数量
	 * @param businessId
	 * @param ship_no
	 * @param status
	 * @return
	 */
	public int queryCountBusinessOrderDetail(Long businessId, String ship_no,
                                             Short status);
	
	/**
	 * 使用优惠券
	 * @param orderInfo
	 */
	public void useCoupon(Long couponId, OrderInfo orderInfo);
	
	/**
	 * 订单支付成功
	 * @param orderInfo
	 */
	public void orderSucess(OrderInfo orderInfo);
	
	/**
	 * 支付积分订单
	 * @param orderInfo
	 * @param list
	 * @param memberId
	 */
	public void payPointOrder(OrderInfo orderInfo, List<OrderDetail> list, Long memberId, String address, String receiveMobile, String receiveName);


	public List<OrderProductVo> queryOrderProductByDateAndFarmId(@Param("startDate") String startDate, @Param("endDate") String endDate,
                                                                 @Param("farmId") Long farmId);

	/**
	 * 查询今日的所有订单
	 * @param nowDate
	 * @param start
	 * @param limit
	 * @param businessId
	 * @return
	 */
	public List<OrderInfoVo> queryTodayOrder(String nowDate, Integer start, Integer limit, Long businessId);
	/**
	 * 统计今日订单的所有订单
	 * @param nowDate
	 * @param businessId
	 * @return
	 */
	public Integer queryCountTodayOrder(String nowDate, Long businessId);

	/**
	 * 分页查找所有退款订单
	 * @param orderNo
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<OrderInfoVo> queryAllOrderRefund(String orderNo, Short status, Integer start, Integer limit, Long businessId, String startDate, String endDate);
	/**
	 * 查找所有退款订单数量
	 * @param orderNo
	 * @param status
	 * @return
	 */
	public Integer queryCountAllOrderRefund(String orderNo, Short status, Long businessId, String startDate, String endDate);

	/**
	 * 添加退款记录
	 * @param orderRefund
	 * @return
	 */
	public Integer addOrderRefund(OrderRefund orderRefund);

	/**
	 * 退款成功处理
	 * @param orderRefund
	 * @return
	 */
	public Integer refundSuccess(OrderRefund orderRefund);

	/**
	 * 计算运费
	 * @param orderInfo
	 * @return
	 */
	public double calculateFreight(OrderInfo orderInfo, String province);

}
