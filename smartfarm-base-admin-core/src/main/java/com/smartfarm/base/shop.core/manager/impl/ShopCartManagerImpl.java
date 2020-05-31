package com.smartfarm.base.shop.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.shop.core.dao.ProductInfoDao;
import com.smartfarm.base.shop.core.dao.ProductSkuDao;
import com.smartfarm.base.shop.core.dao.ShopCartDao;
import com.smartfarm.base.shop.core.entity.ProductInfo;
import com.smartfarm.base.shop.core.entity.ProductSku;
import com.smartfarm.base.shop.core.entity.ShopCart;
import com.smartfarm.base.shop.core.entity.vo.ShopCartVo;
import com.smartfarm.base.shop.core.manager.ShopCartManager;

@Service("shopCartManager")
public class ShopCartManagerImpl implements ShopCartManager {
	@Resource
	private ProductSkuDao productSkuDao;
	@Resource
	private ProductInfoDao productInfoDao;
	@Resource
	private ShopCartDao shopCartDao;

	@Override
	public void addShopCart(ShopCart shopCart) {
		ProductSku productSku = productSkuDao.queryById(shopCart.getProductSkuId());
		ProductInfo productInfo = productInfoDao.queryById(shopCart.getProductId());
		
		//查询是否有购物车
		ShopCart shopCartDb = shopCartDao.queryByMemberIdAndSkuId(shopCart.getMemberId(), shopCart.getProductSkuId());
		if(shopCartDb == null) {
			shopCart.setNorm(productSku.getNorm());
			shopCart.setPicUrl(productInfo.getPicUrl());
			shopCart.setPrice(productSku.getPrice());
			shopCart.setStatus((short)1);
			shopCartDao.insert(shopCart);
		}else {
			shopCartDb.setQuantity(shopCartDb.getQuantity() + shopCart.getQuantity());
			shopCartDb.setNorm(productSku.getNorm());
			shopCartDb.setPicUrl(productInfo.getPicUrl());
			shopCartDb.setPrice(productSku.getPrice());
			shopCartDao.updateById(shopCartDb);
		}
	}

	@Override
	public List<ShopCartVo> queryByMemberId(Long memberId) {
		return shopCartDao.queryByMemberId(memberId);
	}

	@Override
	public void deleteShopCartById(Long id) {
		ShopCart shopCart = new ShopCart();
		shopCart.setId(id);
		shopCart.setStatus((short)2);
		shopCartDao.updateById(shopCart);
	}
	
	
}


	
	
