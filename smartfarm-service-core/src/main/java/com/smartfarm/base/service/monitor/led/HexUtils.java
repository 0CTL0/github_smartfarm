package com.smartfarm.base.service.monitor.led;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class HexUtils {
	private static String hexString = "0123456789ABCDEF";

	public static String encodeCN(String data) {
		byte[] bytes;
		try {
			bytes = data.getBytes("gbk");
			// StringBuilder sb = new StringBuilder(bytes.length * 2);
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < bytes.length; i++) {

				if (bytes[i] > 0 && bytes[i] <= 127) {
					sb.append("00");
				}

				sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
				sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static byte[] encodeCNByte(String data) {

		byte[] bytes;

		try {

			bytes = data.getBytes("gbk");

			ByteArrayOutputStream out = new ByteArrayOutputStream();

			for (int i = 0; i < bytes.length; i++) {

				if (bytes[i] > 0 && bytes[i] <= 127) {
					out.write((byte) 0x00);
				}

				out.write(bytes[i]);
			}
			return out.toByteArray();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encodeStr(String data) {
		String result = "";
		byte[] bytes;
		try {
			bytes = data.getBytes("Unicode");
			for (int i = 0; i < bytes.length; i++) {
				result += Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1);
			}
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static boolean isCN(String data) {
		boolean flag = false;
		String regex = "^[\u4e00-\u9fa5]*$";
		if (data.matches(regex)) {
			flag = true;
		}
		return flag;
	}

	public static String getHexResult(String targetStr) {
		StringBuilder hexStr = new StringBuilder();
		int len = targetStr.length();
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				char tempStr = targetStr.charAt(i);
				String data = String.valueOf(tempStr);
				if (isCN(data)) {
					hexStr.append(encodeCN(data));
				} else {
					hexStr.append(encodeStr(data));
				}
			}
		}
		return hexStr.toString();
	}

	public static byte[] intToByteArray(int value) {
		byte[] byteArray = new byte[2];
		byteArray[0] = (byte) (value & 0xFF);
		byteArray[1] = (byte) (value >> 8 & 0xFF);

		return byteArray;
	}

	public static String HexByte2String(byte[] bArray, int length) {
		StringBuffer sb = new StringBuffer(length);
		String sTemp;
		for (int i = 0; i < length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();

	}
}
