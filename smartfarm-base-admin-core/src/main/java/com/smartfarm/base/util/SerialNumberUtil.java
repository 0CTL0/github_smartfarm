package com.smartfarm.base.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 序列号生成器
 * @author Johney
 *
 */
public class SerialNumberUtil {
	/**自定义进制*/
	private static final char[] r = new char[] { 'W', '8', 'A', '2', 'D', 'X', 'C', '7', 'P', '5', '3', 'M', 'J', 'F', 'R', 'Y', 'T', '6',
			'B', 'G', 'E', 'K', 'L', 'Q' };
	/**自动补全组(不能与自定义进制有重复)*/
	private static final char[] b = new char[] { 'S', '1', 'Z', '4', 'U', 'V', 'N', '9', 'H' };
	/**进制长度*/
	private static final int l = r.length;
	/**序列最小长度*/
	private static final int s = 6;

	/**
	  * 根据ID生成六位随机码
	  * @param num ID
	  * @return 随机码
	  */
	public static String toSerialNumber(long num) {
		return toSerialNumber(num, s);
	}

	/**
	 * 根据ID生成指定位数的随机码
	 * @param num
	 * @param len
	 * @return
	 */
	public static String toSerialNumber(long num, int len) {
		char[] buf = new char[32];
		int charPos = 32;

		while ((num / l) > 0) {
			buf[--charPos] = r[(int) (num % l)];
			num /= l;
		}
		buf[--charPos] = r[(int) (num % l)];
		String str = new String(buf, charPos, (32 - charPos));
		//不够长度的自动随机补全
		if (str.length() < len) {
			StringBuffer sb = new StringBuffer();
			Random rnd = new Random();
			for (int i = 0; i < len - str.length(); i++) {
				sb.append(b[rnd.nextInt(b.length - 1)]);
			}
			str += sb.toString();
		}
		return str;
	}

	public static void main(String[] args) {
		Map<String,Long> map = new HashMap<String,Long>();
		for(long i=22240; i<130000;i++){
			String code = toSerialNumber(i, 10);
			if(map.containsKey(code)){
				System.err.print(code);
				System.err.print(" ");
				System.err.print(i);
				System.err.print(" ");
				System.err.println(map.get(code));
				break;
			}else{
				System.err.print(code);
				System.err.print(" ");
				System.err.println(i);
				map.put(code, i);
			}
		}
	}
}
