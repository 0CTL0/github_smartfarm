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
 * 抓拍工具类
 * @author lyq
 *
 */
public class SnapUtil {
//	public static void main(String[] args) {
//		System.out.println(getSnapUrl("at.bxhxjaar8cih7ev97nffq40tac58zag0-8381u913eu-0tfroi6-rg3n7xab2", "D57137391", 1));
//	}
	
	/**
	 * 获取抓拍url路径
	 * @param accessToken
	 * @param deviceSerial
	 * @param channelNo
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static String getSnapUrl(String accessToken,String deviceSerial,int channelNo){
		// 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		
		// 封装参数
		StringBuffer params = new StringBuffer();
		params.append("accessToken="+ accessToken);
		params.append("&");
		params.append("deviceSerial="+deviceSerial);
		params.append("&");
		params.append("channelNo="+channelNo);
		
		// 创建Post请求
		HttpPost httpPost = new HttpPost("https://open.ys7.com/api/lapp/device/capture" + "?" + params);
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
				Picture p = (Picture) JSONObject.toBean(jsonObject, Picture.class);
				return p.getData().getPicUrl();
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
