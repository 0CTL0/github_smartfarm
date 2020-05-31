package com.smartfarm.base.monitor.core.service;

public interface EventService {
	
	/**
	 * 发送硬件消息
	 * @param code
	 * @param text
	 */
	public void sendProductMsg(String code, String text);
	
	/**
	 * 订阅硬件主题
	 * @param code
	 */
	public void subscribeTopic(String code);
	
	/**
	 * 重置定时器
	 */
	public void resetControlSet();
	
}
