package com.smartfarm.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;

public class RandomUtil {
	private static AtomicInteger serial = new AtomicInteger(1);
	private static SimpleDateFormat sdfdate = new SimpleDateFormat("yyMMddHHmmss");
	
    public static String getOrderIdByUUId() {
        //最大支持1-9个集群机器部署
        int machineId = 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        //有可能是负数
        if(hashCodeV < 0) {
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return machineId + String.format("%015d", hashCodeV);

    }
    
    /**
     * 生成唯一数字串
     * @param maxLength
     * @return
     */
    public static String generateNumber(int maxLength) {
		StringBuffer sb = new StringBuffer();
		sb.append(sdfdate.format(new Date()));

		int serialNo = serial.getAndIncrement();
		sb.append(StringUtils.leftPad(String.valueOf(serialNo), maxLength, '0'));
		return sb.toString();
	}
    
    public static Integer getRomdomInt(int minNum,int maxNum) {
    	Random random = new Random();
    	int i = maxNum - minNum + 1;
    	return random.nextInt(i) + minNum;
    }
    
    public static String randomStr(int length) {
    	String str="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
    	Random random=new Random();
    	StringBuffer sb=new StringBuffer();
        //长度为几就循环几次
        for(int i=0; i<length; ++i) {
        	int number = random.nextInt(62);
        	sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
    	System.out.println(generateNumber(6));
    	System.out.println(generateNumber(6));
	}
    
}
