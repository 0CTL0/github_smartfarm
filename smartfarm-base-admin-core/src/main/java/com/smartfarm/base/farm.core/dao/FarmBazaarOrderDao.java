package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.FarmBazaarOrder;
import com.smartfarm.base.farm.core.entity.vo.FarmBazaarOrderVo;

public interface FarmBazaarOrderDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(FarmBazaarOrder record);

    FarmBazaarOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FarmBazaarOrder record);

    /**
     * 根据订单号查询
     * @param OrderCode
     * @return
     */
    FarmBazaarOrder selectByOrderCode(@Param("OrderCode") String OrderCode);


    /**
     * 查询全部的订单
     * @return
     */
    List<FarmBazaarOrderVo> selectAllOrder(@Param("memberId") Long memberId);

    /**
     * 获取用户土地的订单
     * @param memberLandId
     * @return
     */
    List<FarmBazaarOrderVo> selectMemberLandOrderList(@Param("memberLandId") Long memberLandId);
}