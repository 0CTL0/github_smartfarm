package com.smartfarm.base.monitor.core.util;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * 获取登录的accessToken
 * @author lyq
 *
 */
public class GetTokenUtil {
//	public static void main(String[] args) {
//		System.out.println(getSnapUrl("f187704b2ff649858c87229f9fd4ee13", "fc09acc112d01ea83fc33b5101c166ca").getData().getAccessToken());
//	}
	public static Token getSnapUrl(String AppKey,String Secret){
		// 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		
		// 封装参数
		StringBuffer params = new StringBuffer();
		params.append("appKey="+ AppKey);
		params.append("&");
		params.append("appSecret="+Secret);
		
		// 创建Post请求
		HttpPost httpPost = new HttpPost("https://open.ys7.com/api/lapp/token/get" + "?" + params);
		// 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
		httpPost.setHeader("Content-Type", "application/json;charset=utf8");
 
		// 响应模型
		CloseableHttpResponse response = null;
		
		try {
			// 由客户端执行(发送)Post请求
			response = httpClient.execute(httpPost);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
		
			if (responseEntity != null) {
				String responseString = EntityUtils.toString(responseEntity);
				JSONObject jsonObject = JSONObject.fromObject(responseString);
				Token t = (Token) JSONObject.toBean(jsonObject, Token.class);
				return t;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
