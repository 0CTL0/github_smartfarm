package com.smartfarm.base.shop.core.dao;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.MemberPointOrder;

public interface MemberPointOrderDao {


	int insert(MemberPointOrder memberPointOrder);

    int updateById(MemberPointOrder memberPointOrder);
    
    /**
     * 根据id查询
     * @param id
     * @return
     */
    MemberPointOrder queryById(Long id);
    
    /**
     * 根据订单号查询
     * @param orderNo
     * @return
     */
    MemberPointOrder queryByOrderNo(@Param("orderNo") String orderNo);

}