package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.RentOrder;
import com.smartfarm.base.farm.core.entity.vo.RentOrderDetailVo;
import com.smartfarm.base.farm.core.entity.vo.RentOrderVo;

public interface RentOrderDao {

	/**
	 * 新增
	 * @param rentOrder
	 * @return
	 */
    public int insert(RentOrder rentOrder);
    
    /**
     * 根据id修改
     * @param rentOrder
     * @return
     */
    public int updateById(RentOrder rentOrder);
    /**
     * 查询页面列表
     * @param name
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<RentOrderVo> selectPageList(@Param("orderNo") String orderNo, @Param("status") Short status, @Param("start") Integer start, @Param("limit") Integer limit);
   /**
    * 统计页面列表
    * @param name
    * @param status
    * @return
    */
    public int selectPageTotal(@Param("orderNo") String orderNo, @Param("status") Short status);
    /**
     * 根据id查询
     * @param rentOrder
     * @return
     */
    public RentOrderVo selectById(RentOrder rentOrder); 
    
    /**
     * 查询未支付种植的订单
     * @param payStatus
     * @return
     */
    public List<RentOrderVo> queryRentOrderUnPayList(@Param("payStatus") short payStatus, @Param("memberId") Long memberId);
    
    
    /**
     * 查询种植状态查询种植列表
     * @param plantStatus
     * @param memberId
     * @return
     */
    public List<RentOrderVo> queryRentOrderPlantList(@Param("plantStatus") short plantStatus, @Param("memberId") Long memberId);
    
    
    /**
     * 根据订单id得到订单详细
     * @param orderId
     * @return
     */
    public RentOrderDetailVo queryRentOrderStatusById(@Param("orderId") Long orderId);
}