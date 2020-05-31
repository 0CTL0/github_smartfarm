package com.smartfarm.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.smartfarm.base.shop.core.entity.vo.MiniProgramCodeVo;
import com.smartfarm.base.shop.core.entity.vo.Token;
import com.smartfarm.base.shop.core.entity.vo.WeixinUserVo;


public class WeixinUtil {
	
	private static Logger log = Logger.getLogger(WeixinUtil.class);
	
	public final static String CODE_URL = "https://api.weixin.qq.com/sns/oauth2/access_token"
    		+ "?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    
	// 网页授权获取用户信息
    public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/userinfo"
    		+ "?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
 	public static String encodeSHA1(String str) {
 		if (str == null) {
 			return null;
 		}
 		try {
 			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
 			messageDigest.update(str.getBytes());
 			return getFormattedText(messageDigest.digest());
 		} catch (Exception e) {
 			throw new RuntimeException(e);
 		}

 	}
    
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
    	JSONObject jsonObject = null;
        try {
        	// 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("连接超时：{}", ce);
        } catch (Exception e) {
            log.error("https请求异常：{}", e);
        }
        return jsonObject;
    }
    
    public static WeixinUserVo getUserInfo(String appid, String appsecret, String code){
    	WeixinUserVo weixinUserInfo = null;
    	Token token = null;
    	token = getToken(appid, appsecret, code);
    	if(null != token){
    		weixinUserInfo = getUserInfo(token.getAccessToken(), token.getOpenid());
    	}
    	return weixinUserInfo;
    }
    
    /**
     * 拉取用户信息
     * @param appid
     * @param appsecret
     * @param code
     * @return
     */
    public static Token getToken(String appid, String appsecret, String code) {
    	Token token = null;
    	String codeUrl = CODE_URL.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
    	JSONObject jsonObject = httpsRequest(codeUrl, "GET", null);
    	if(null != jsonObject){
    		 try {
    			 log.info("[getToken]"+jsonObject.toString());
                 token = new Token();
                 token.setAccessToken(jsonObject.getString("access_token"));
                 token.setExpiresIn(jsonObject.getInt("expires_in"));
                 token.setOpenid(jsonObject.getString("openid"));
             } catch (JSONException e) {
                 token = null;
                 // 获取token失败
                 log.error("获取token失败 errcode:{" + jsonObject.getInt("errcode") + "} errmsg:{" + jsonObject.getString("errmsg") + "}", e);
             }
    	}
		return token;
    }
    
    /**
     * 网页授权获取用户信息
     * @param accessToken
     * @param openId
     * @return
     */
    public static WeixinUserVo getUserInfo(String accessToken, String openId) {
    	WeixinUserVo weixinUserVo = null;
    	// 拼接请求地址
        String requestUrl = ACCESS_TOKEN_URL;
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 获取用户信息
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
            	weixinUserVo = new WeixinUserVo();
            	weixinUserVo.setOpenId(jsonObject.getString("openid"));
            	weixinUserVo.setCity(jsonObject.getString("city")!=null?jsonObject.getString("city"):"");
            	weixinUserVo.setCountry(jsonObject.getString("country")!=null?jsonObject.getString("country"):"");
            	weixinUserVo.setHeadImgUrl(jsonObject.getString("headimgurl"));
            	weixinUserVo.setNickName(jsonObject.getString("nickname"));
            	weixinUserVo.setProvince(jsonObject.getString("province")!=null?jsonObject.getString("province"):"");
            	weixinUserVo.setSex(jsonObject.getInt("sex"));
            }catch (Exception e) {
                log.error("获取用户信息失败 errcode:{" + jsonObject.getInt("errcode") + "} errmsg:{" + jsonObject.getString("errmsg") + "}", e);
            }
        }
        return weixinUserVo;
    }
    
    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        
        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }
        //到这里铁定包含
        StringBuilder buf = null;
        
        int len = source.length();
        
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            
            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                
                buf.append(codePoint);
            } else {
            }
        }
        
        if (buf == null) {
            return source;//如果没有找到 emoji表情，则返回源字符串
        } else {
            if (buf.length() == len) {//这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
        
    }
    
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || 
                (codePoint == 0x9) ||                            
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }
    
    /**
     * 检测是否有emoji字符
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }
        
        int len = source.length();
        
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }
        
        return false;
    }
    
    public static String getMethod(String url, Map<String, String> values) {
    	HttpClient httpClient = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		InputStream is = null;
		BufferedReader in = null;
		GetMethod method = null;
		StringBuffer result = new StringBuffer();
		try {
			method = new GetMethod(url);
			if (values != null) {
				method.setQueryString(buildNameValue(values));
			}
			httpClient.executeMethod(method);
			is = method.getResponseBodyAsStream();
			in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = null;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			log.error("发送get请求发生异常.", e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result.toString();
    }
    
    // 构建请求参数信息
    private static NameValuePair[] buildNameValue(Map<String, String> values) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        final Iterator<String> iterator = values.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = values.get(key);
            nvps.add(new NameValuePair(key, value));
        }
        return nvps.toArray(new NameValuePair[nvps.size()]);
    }
    
    // 返回随机数字
 	public static String getRandomStr() {
 		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
 		Random random = new Random();
 		StringBuffer sb = new StringBuffer();
 		for (int i = 0; i < 16; i++) {
 			int number = random.nextInt(base.length());
 			sb.append(base.charAt(number));
 		}
 		return sb.toString();
 	}
 	

	// MD5加密
	public static String encodeMD5(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	/**
	 * Takes the raw bytes from the digest and formats them correct.
	 * 
	 * @param bytes
	 *            the raw bytes from the digest.
	 * @return the formatted bytes.
	 */
	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	// 获取微信支付加密签名
	public static String getPaySign(Map<String, String> params, String key) throws Exception {
		String bizString = formatMapToQueryString(params, false);
		bizString = bizString + "&key=" + key;
		String md = encodeMD5(bizString).toUpperCase();
		return md;
	}
	
	// 格式化map为queryString
	public static String formatMapToQueryString(Map<String, String> paramMap, boolean encode) throws Exception {
		String buff = "";
		List<Entry<String, String>> infoIds = new ArrayList<Entry<String, String>>(paramMap.entrySet());

		Collections.sort(infoIds, new Comparator<Entry<String, String>>() {
			public int compare(Entry<String, String> o1, Entry<String, String> o2) {
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});

		for (int i = 0; i < infoIds.size(); i++) {
			Entry<String, String> item = infoIds.get(i);
			if (item.getKey() != "") {

				String key = item.getKey();
				String val = item.getValue();
				if (encode) {
					val = URLEncoder.encode(val, "utf-8");

				}
				buff += key + "=" + val + "&";

			}
		}
		

		if (buff.isEmpty() == false) {
			buff = buff.substring(0, buff.length() - 1);
		}

		return buff;
	}

	// 将map转换成xml格式字符串
	public static String map2Xml(Map<String, String> params) {
		String xml = "<xml>";

		Iterator<Entry<String, String>> iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			if (isNumeric(val)) {
				xml += "<" + key + ">" + val + "</" + key + ">";
			} else {
				xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
			}
		}

		xml += "</xml>";
		return xml;
	}

	private static boolean isNumeric(String str) {
		if (str.matches("\\d *")) {
			return true;
		} else {
			return false;
		}
	}
	
	// 返回时间戳（秒）
 	public static String getTimestamp() {
 		long currentTimeMillis = System.currentTimeMillis();
 		return String.valueOf(currentTimeMillis / 1000);
 	}
 	
 	/**
	 * 小程序登录获取openid
	 * @param appid
	 * @param appsecret
	 * @param code
	 * @return
	 */
	public static MiniProgramCodeVo getMPOpenId(String appid, String appsecret, String code) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + appsecret + "&js_code=" + code + "&grant_type=authorization_code";
		JSONObject jsonObject = httpsRequest(url, "GET", null);
		log.info("[WeixinUtil:getMPOpenId]json:" + jsonObject.toString());
		MiniProgramCodeVo miniProgramCodeVo = null;
		if(jsonObject == null || "40029".equals(jsonObject.has("errcode"))) {
			
		}else {
			miniProgramCodeVo = new MiniProgramCodeVo();
			miniProgramCodeVo.setOpenid(jsonObject.getString("openid"));
			miniProgramCodeVo.setSessionKey(jsonObject.getString("session_key"));
		}
    	return miniProgramCodeVo;
    }
}
