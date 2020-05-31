package com.smartfarm.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单号生成器
 * @author david
 *
 */
public class GenerateOrderNoUtil {
	private static long orderNum = 0l;
	private static String date;
 
	/**
	 * 生成订单编号
	 * @return
	 */
	public static synchronized String getOrderNo() {
		String str = new SimpleDateFormat("yyMMddHHmm").format(new Date());
		if (date == null || !date.equals(str)) {
			date = str;
			orderNum = 0l;
		}
		orderNum++;
		long orderNo = Long.parseLong((date)) * 10000;
		orderNo += orderNum;
		return orderNo + "";
	}
}
