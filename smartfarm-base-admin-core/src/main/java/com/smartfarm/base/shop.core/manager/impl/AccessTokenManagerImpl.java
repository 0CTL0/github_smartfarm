package com.smartfarm.base.shop.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.smartfarm.base.util.HttpUtil;
import com.smartfarm.base.shop.core.dao.AccessTokenDao;
import com.smartfarm.base.shop.core.entity.AccessToken;
import com.smartfarm.base.shop.core.manager.AccessTokenManager;


@Service("accessTokenManager")
public class AccessTokenManagerImpl implements AccessTokenManager {
	
	 @Resource
	 private AccessTokenDao accessTokenDao;

	@Override
	public AccessToken queryAccessToken(Long businessId) {
		return accessTokenDao.queryAccessToken(businessId);
	}

	@Override
	public void updateAccessTokenInfo() {
		List<AccessToken> list = accessTokenDao.queryAll();
		for(AccessToken accessToken:list) {
			try {
				String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + accessToken.getAppid() 
						+ "&secret=" + accessToken.getSecret();
				
				String AccessTokenJsonStr = HttpUtil.sendGet(url);
				JSONObject AccessTokenJson = JSONObject.parseObject(AccessTokenJsonStr);
				String accessTokenStr = AccessTokenJson.getString("access_token");
				
				String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" 
						+ accessTokenStr + "&type=jsapi";
				String  jsonObjectTicketStr = HttpUtil.sendGet(ticketUrl);
				JSONObject jsonObjectTicket = JSONObject.parseObject(jsonObjectTicketStr);
				String ticket = jsonObjectTicket.getString("ticket");
				
				accessToken.setAccessToken(accessTokenStr);
				accessToken.setJsapiTicket(ticket);
				accessTokenDao.updateById(accessToken);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
		
	}

	@Override
	public AccessToken queryAccessTokenByAppId(String appid) {
		return accessTokenDao.queryAccessTokenByAppId(appid);
	}
}