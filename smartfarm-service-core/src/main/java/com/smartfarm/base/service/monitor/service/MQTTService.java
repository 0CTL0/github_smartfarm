package com.smartfarm.base.service.monitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class MQTTService implements InitializingBean {
	private static Logger log = Logger.getLogger(MQTTService.class);
	@Autowired
	private Client client;
	
	public void afterPropertiesSet() throws Exception {
        //client.start();
        log.info("服务器启动");
        
	}
}
