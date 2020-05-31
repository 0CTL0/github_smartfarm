package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.FarmRentLandOrder;
import com.smartfarm.base.farm.core.entity.vo.FarmRentLandOrderVo;

public interface FarmRentLandOrderDao {

    int insertSelective(FarmRentLandOrder record);
    FarmRentLandOrder selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(FarmRentLandOrder record);


    List<FarmRentLandOrder> selectPageList(@Param("farmId") Long farmId, @Param("orderCode") String orderCode, @Param("rentName") String rentName, @Param("status") Short status,
                                           @Param("start") Integer start, @Param("limit") Integer limit);
    int selectPageTotal(@Param("farmId") Long farmId, @Param("orderCode") String orderCode, @Param("rentName") String rentName, @Param("status") Short status);

    /**
     * 查询某状态的全部订单
     * @param status
     * @return
     */
    List<Long> selectIdListByStatus(@Param("status") Short status);

    /**
     * 根据订单编号查询订单
     * @param orderCode
     * @return
     */
    FarmRentLandOrder selectByOrderCode(@Param("orderCode") String orderCode);

    /**
     * 查询用户全部的订单
     * @return
     */
    List<FarmRentLandOrderVo> selectAllRentOrder(Long memberId);

    /**
     * 根据id删除订单
     * @param id
     * @return
     */
    int deleteById(Long id);
    
    /**
     * 查询用户未支付的订单
     * @param memberId
     * @param rentLandId
     * @return
     */
    List<FarmRentLandOrder> queryByMemberIdAndLandId(@Param("memberId") Long memberId, @Param("rentLandId") Long rentLandId);

}