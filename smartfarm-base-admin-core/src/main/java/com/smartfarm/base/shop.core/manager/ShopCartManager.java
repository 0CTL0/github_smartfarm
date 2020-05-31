package com.smartfarm.base.shop.core.manager;

import java.util.List;

import com.smartfarm.base.shop.core.entity.ShopCart;
import com.smartfarm.base.shop.core.entity.vo.ShopCartVo;



public interface ShopCartManager {
	
	/**
	 * 添加购物车
	 * @param shopCart
	 * @return
	 */
	public void addShopCart(ShopCart shopCart);
	
	/**
     * 查询用户购物车
     * @param memberId
     * @return
     */
    public List<ShopCartVo> queryByMemberId(Long memberId);
    
    /**
     * 根据id删除购物车
     * @param id
     */
    public void deleteShopCartById(Long id);
}
