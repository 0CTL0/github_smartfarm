package com.smartfarm.base.shop.core.manager;

import com.smartfarm.base.shop.core.entity.AccessToken;


public interface AccessTokenManager {
	
	/**
     * 根据accountNo查询
     * @param businessId
     * @return
     */
    public AccessToken queryAccessToken(Long businessId);
    
    /**
     * 更新所有accesstoken
     */
    public void updateAccessTokenInfo();
    
    /**
	 * 根据appid查询
	 * @param appid
	 * @return
	 */
	public AccessToken queryAccessTokenByAppId(String appid);
}
