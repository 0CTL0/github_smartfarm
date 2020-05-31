package com.smartfarm.base.shop.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.ShopCart;
import com.smartfarm.base.shop.core.entity.vo.ShopCartVo;

public interface ShopCartDao {
	
	/**
	 * 添加
	 * @param shopCart
	 * @return
	 */
    public int insert(ShopCart shopCart);

    /**
     * 根据id修改
     * @param shopCart
     * @return
     */
    public int updateById(ShopCart shopCart);
    
    /**
     * 查询用户产品购物车情况
     * @param memberId
     * @param productSkuId
     * @return
     */
    public ShopCart queryByMemberIdAndSkuId(@Param("memberId") Long memberId, @Param("productSkuId") Long productSkuId);
    
    /**
     * 查询用户购物车
     * @param memberId
     * @return
     */
    public List<ShopCartVo> queryByMemberId(Long memberId);
}