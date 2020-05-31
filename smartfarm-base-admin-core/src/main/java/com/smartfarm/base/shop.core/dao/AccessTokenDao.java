package com.smartfarm.base.shop.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.AccessToken;


public interface AccessTokenDao {
	
	/**
	 * 根据id修改
	 * @param accessToken
	 */
	public void updateById(AccessToken accessToken);
	
	/**
	 * 查询，目前系统只有一行数据
	 * @return
	 */
	public AccessToken queryAccessToken(Long businessId);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<AccessToken> queryAll();
	
	/**
	 * 根据appid查询
	 * @param appid
	 * @return
	 */
	public AccessToken queryAccessTokenByAppId(@Param("appid") String appid);
}