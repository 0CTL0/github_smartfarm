package com.smartfarm.base.service.shop.timer;

import com.smartfarm.base.shop.core.manager.AccessTokenManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accessTokenJob")
public class AccessTokenJob {
	protected static Logger log = Logger.getLogger(AccessTokenJob.class);

	@Autowired
	private AccessTokenManager accessTokenManager;

	public void updateAccessToken() {
		try {
			log.info("[AccessTokenJob:updateAccessToken]update accessToken.");
			accessTokenManager.updateAccessTokenInfo();
			log.info("[AccessTokenJob:updateAccessToken]update accessToken success.");
		} catch (Exception e) {
			log.error("[AccessTokenJob:updateAccessToken]false to update accessToken.",e);
		}
	}
	
	
}
