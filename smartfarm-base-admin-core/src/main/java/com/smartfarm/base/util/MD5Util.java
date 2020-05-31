package com.smartfarm.base.util;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具
 * 
 */
public class MD5Util {

	/**
	 * MD5加密
	 * 
	 * @param plainText
	 *            原密文
	 * @return 加密后的密文
	 */
	public static String MD5Purity(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes("UTF-8"));
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			plainText = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return plainText;
	}

	/**
	 * MD5加密验证
	 * 
	 * @param plainText
	 *            原密文
	 * @param password
	 *            需验证的密文
	 * @return 验证结果
	 */
	public static boolean MD5PurityVerification(String plainText, String password) {
		plainText = MD5Purity(plainText);
		if (password.equals(plainText)) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 原密文加密后分解为两段，分别加密后合并为一个长字符串，再次进行加密
	 * 
	 * @param plainText
	 *            原密文
	 * @return 加密后的密文
	 */
	public static String MD5X2(String plainText) {
		// 先把密码加密成长度为32字符的密文
		String s = new StringBuffer(MD5Purity(plainText)).toString();
		// 把密码分割成两段
		String left = s.substring(0, 16);
		String right = s.substring(16, 32);
		// 分别加密后再合并
		s = MD5Purity(left) + MD5Purity(right);
		// 最后把长字串再加密一次，成为32字符密文
		return MD5Purity(s);
	}

	/**
	 * 两段加密验证
	 * 
	 * @param plainText
	 *            原密文
	 * @param password
	 *            需验证的密文
	 * @return 验证结果
	 */
	public static boolean MD5X2Verification(String plainText, String password) {
		plainText = MD5X2(plainText);
		if (password.equals(plainText)) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		//31dd9cba757467657e94916fcbd91fef
		System.out.println(MD5X2("123456"));
	}


	public final static String MD5(String s) {
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};


}
