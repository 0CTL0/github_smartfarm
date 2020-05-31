package com.smartfarm.base.util;

import java.util.HashMap;
import java.util.Map;

public class ShipUtil {

	static Map<String, String> ShipCodeMap = new HashMap<String,String>();
	static{
		
		ShipCodeMap.put("SF","顺丰速运");
		ShipCodeMap.put("ZTO","中通快递");
		ShipCodeMap.put("STO","申通快递");
		ShipCodeMap.put("YTO","圆通速递");
		ShipCodeMap.put("YD","韵达速递");
	}
	
	public static String getShipName(String code){
		return ShipCodeMap.get(code);
	}
}
