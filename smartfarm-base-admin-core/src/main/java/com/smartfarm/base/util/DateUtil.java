package com.smartfarm.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class DateUtil {
	
	/**
	 * 获取现在时间，yyyyMMddHHmmss格式
	 * @return
	 */
	public static String formatCurrentDateTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(new Date());
	}
	
	/**
	 * 获取现在时间，yyyyMMdd格式
	 * @return
	 */
	public static String formatCurrentDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(new Date());
	}
	
	
	/**
	 * 获取现在时间，yyyyMMddHHmmss格式
	 * @return
	 */
	public static Long formatCurrentDateTimeLong() {
		return Long.valueOf(formatCurrentDateTime());
	}
	
	/**
	 * 获取几个月的时间，yyyyMMddHHmmss格式
	 * @param mouth
	 * @return
	 */
	public static String formatMaouthDateTime(Integer mouth) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, mouth);
		Date m = c.getTime();
		return df.format(m);
	}
	
	/**
	 * 获取几天的时间（默认当前日期），yyyyMMddHHmmss格式
	 * @param date
	 * @return
	 */
	public static String formatAddDateTime(Integer date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, date);
		Date m = c.getTime();
		return df.format(m);
	}

	public static String formatAddDateTime(String dateTime, Integer date) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar c = Calendar.getInstance();
		c.setTime(df.parse(dateTime));
		c.add(Calendar.DATE, date);
		Date m = c.getTime();
		return df.format(m);
	}
	
	/**
	 * 获取几分后的时间
	 * @param dateTime
	 * @param minute
	 * @return
	 */
	public static String formatAddMinuteTime(String dateTime, Integer minute) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MINUTE, minute);
		Date m = c.getTime();
		return df.format(m);
	}
	
	/**
	 * 获取几小时后的时间
	 * @param dateTime
	 * @param hour
	 * @return
	 */
	public static String formatAddHourTime(String dateTime, Integer hour) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar c = Calendar.getInstance();
		c.setTime(df.parse(dateTime));
		c.add(Calendar.HOUR, hour);
		Date m = c.getTime();
		return df.format(m);
	}

	/**
	 * 获取本月
	 * @return
	 */
	public static String formatCurrentMonth() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		return df.format(new Date());
	}
	
	/**
	 * 返回两个时间的时间差，单位秒
	 * @param startTime yyyyMMDDHHmmss
	 * @param endTime yyyyMMDDHHmmss
	 * @return 
	 */
	public static long getSecondsBetweenTwoTime(String startTime, String endTime) throws Exception{
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		
		Date startDateTime = sdf.parse(startTime);
		Date endDateTime = sdf.parse(endTime);
		
		long returnValue = endDateTime.getTime() - startDateTime.getTime();
			
		return returnValue;
	}
	
	/**
	 * 获取几天的时间，yyyyMMdd格式
	 * @param day
	 * @return
	 */
	public static String formatAddDate(Integer day) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, day);
		Date m = c.getTime();
		return df.format(m);
	}
	
	public static String formatAddDate(String date, Integer day) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(df.parse(date));
		c.add(Calendar.DATE, day);
		Date m = c.getTime();
		return df.format(m);
	}
	
	/**
	 * 获取现在时间，HHmmss格式
	 * @return
	 */
	public static String formatCurrentTime() {
		SimpleDateFormat df = new SimpleDateFormat("HHmmss");
		return df.format(new Date());
	}

	/**
	 * 根据日期返回其固定格式，yyyy-MM-dd格式
	 * @param date
	 * @return
	 */
	public static String formatDate(String date){
		String res = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
		return res;
	}

	/**
	 * 根据日期时间返回其固定格式，yyyy-MM-dd HH:mm:ss格式
	 * @param dateTime
	 * @return
	 */
	public static String formatDateTime(String dateTime){
		String res = dateTime.substring(0,4)+"-"+dateTime.substring(4,6)+"-"+dateTime.substring(6,8)+" "+dateTime.substring(8,10)+":"+dateTime.substring(10,12)+":"+dateTime.substring(12,14);
		return res;
	}

	/**
	 * 根据时间返回其固定格式，HH:mm:ss格式
	 * @param time HHmmss格式时间
	 * @return HH:mm:ss格式时间
	 */
	public static String formatTime(String time){
		return time.substring(0,2)+":"+time.substring(2,4)+":"+time.substring(4,6);
	}
}
