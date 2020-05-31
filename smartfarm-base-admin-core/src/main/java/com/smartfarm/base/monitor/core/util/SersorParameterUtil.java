package com.smartfarm.base.monitor.core.util;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author 传感器参数
 *
 */
public class SersorParameterUtil {
	//气象数据封装
	private static final String AIR_HUMIDITY = "AIR_HUMIDITY";//空气湿度
	private static final String WIND_SPEED = "WIND_SPEED";//风速
	private static final String CARBON_DIOXIDE = "CARBON_DIOXIDE";//二氧化碳
	private static final String AIR_TEMPERATURE = "AIR_TEMPERATURE";//空气温度
	private static final String ATMOSPHERIC_PRESSURE = "ATMOSPHERIC_PRESSURE";//大气压
	private static final String WIND_DIRECTION = "WIND_DIRECTION";//风向
	private static final String ILLUMINANCE = "ILLUMINANCE";//光照度
	//土壤数据封装
	private static final String SOIL_ONE = "SOIL_ONE";//土壤1号
	private static final String SOIL_TWO = "SOIL_TWO";//土壤2号
	private static final String SOIL_THREE = "SOIL_THREE";//土壤3号
	//根据网关显示数据列表
	private static final String GATEWAY_ONE = "GATEWAY_ONE";//网关1号
	
	static Map<String, Object> sersorMap = new HashMap<String,Object>();
	static{
		
		sersorMap.put(AIR_HUMIDITY,4L);
		sersorMap.put(WIND_SPEED,1L);
		sersorMap.put(CARBON_DIOXIDE,11L);
		sersorMap.put(AIR_TEMPERATURE,3L);
		sersorMap.put(ATMOSPHERIC_PRESSURE,5L);
		sersorMap.put(WIND_DIRECTION,2L);
		sersorMap.put(ILLUMINANCE,9L);
		
		sersorMap.put(SOIL_ONE, 2L);
		sersorMap.put(SOIL_TWO, 3L);
		sersorMap.put(SOIL_THREE, 4L);
		
		sersorMap.put(GATEWAY_ONE, 1L);
	}
	
	public static Object getShipName(String code){
		return sersorMap.get(code);
	}
}
