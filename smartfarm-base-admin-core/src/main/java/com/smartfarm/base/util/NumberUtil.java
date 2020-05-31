package com.smartfarm.base.util;

import java.math.BigDecimal;


public class NumberUtil {
	
	/**
     * 加法运算
     * @param num1 被加数
     * @param num2 加数
     * @return 两数之和
     */
    public static double add(double num1, double num2){
        BigDecimal b1 = new BigDecimal(Double.toString(num1));
        BigDecimal b2 = new BigDecimal(Double.toString(num2));
        return b1.add(b2).doubleValue();
    }
    /**
     * 减法运算
     * @param num1 被减数
     * @param num2 减数
     * @return 两数之差
     */
    public static double sub(double num1, double num2){
        BigDecimal b1 = new BigDecimal(Double.toString(num1));
        BigDecimal b2 = new BigDecimal(Double.toString(num2));
        return b1.subtract(b2).doubleValue();
    }
    /**
     * 乘法运算
     * @param num1 被乘数
     * @param num2 乘数
     * @return 两数之积
     */
    public static double mul(double num1, double num2){
        BigDecimal b1 = new BigDecimal(Double.toString(num1));
        BigDecimal b2 = new BigDecimal(Double.toString(num2));
        return b1.multiply(b2).doubleValue();
    }
    /**
     * 除法运算（小数点后10位）
     * @param num1 被除数
     * @param num2 除数
     * @return 两数之商
     */
    public static double div(double num1, double num2){
        return div(num1, num2, 10);
    }
    
    /**
     * 除法运算
     * @param num1 被除数
     * @param num2 除数
     * @param scale 小数点后精度位数
     * @return 两数之商
     */
    public static double div(double num1, double num2, int scale){
        if(scale<0)
            throw new IllegalArgumentException("The scale must be a positive integer or zero");  
        BigDecimal b1 = new BigDecimal(Double.toString(num1));
        BigDecimal b2 = new BigDecimal(Double.toString(num2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * 四舍五入
     * @param num 需要四舍五入的数
     * @param scale 小数点后精度位数
     * @return 四舍五入值
     */
    public static double round(double num, int scale){
        if(scale<0)
            throw new IllegalArgumentException("The scale must be a positive integer or zero");  
        BigDecimal b1 = new BigDecimal(Double.toString(num));
        return b1.divide(new BigDecimal("1"), scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
