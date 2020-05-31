package com.smartfarm.base.util;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class WeixinPayUtil {

	private static final Logger log = Logger.getLogger(WeixinPayUtil.class);
	
	private static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
//	public static final String MCHID = "1543173271"; 
	
//	public static final String PAY_KEY = "she1qu2chang3xian4green5sun6xcx7";
	
	//
	// 微信下单
	//
	/**
	 * 
	 * @param appId
	 * @param productName
	 * @param type 1商户vip购买  2商户充值  3商城订单  4农场活动 5租赁地块 6 自由销售商品 7会员积分充值
	 * @param addressIp
	 * @param price
	 * @param orderNo
	 * @param openId
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public static String unifiedOrder(String appId, String productName, Short type, String addressIp,Integer price, String orderNo,String openId,
			String endTime,String mchId, String payKey) throws Exception {
		
		Map<String, String> map = new HashMap<String, String>();
		//公众账号ID
		map.put("appid", appId);
		//微信支付分配的商户号
		map.put("mch_id", mchId);
		//随机字符串
		map.put("nonce_str", WeixinUtil.getRandomStr());
		//商品或支付单简要描述   如：Ipad mini  16G  白色
		map.put("body", productName);
		//商户订单号 即：order_no
		map.put("out_trade_no", orderNo);
		//订单总金额，单位为分
		map.put("total_fee", price + "");
		//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
		map.put("spbill_create_ip", addressIp);
		//接收微信支付异步通知回调地址 （后台回调地址）
		map.put("notify_url", "http://fservice.changxianshequ.com/httpService/wechatCallBack.htm");
		map.put("attach", type + "");
		//交易类型
		map.put("trade_type", "JSAPI");
		
		map.put("attach", type + "");
		map.put("time_expire", endTime);
		//用户标识	
		map.put("openid", openId);
		String paySign = WeixinUtil.getPaySign(map, payKey);
		//签名
		map.put("sign", paySign);

		log.info("[WeixinXFCZPay:PayUtil:unifiedOrder]拼装生成预订单数据成功[data:" + map.toString() + "]");

		String xml = WeixinUtil.map2Xml(map);
		String result = HttpUtil.postMethod(UNIFIED_ORDER_URL, xml, null);
		
		log.info("WeixinXFCZPay:[PayUtil:unifiedOrder]下单获取到数据[data:" + result + "]");
		
		SAXReader sb = new SAXReader();
		sb.setFeature("http://xml.org/sax/features/external-general-entities", false);
		sb.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		sb.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		sb.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		Document doc = sb.read(new StringReader(result));
		Element root = doc.getRootElement();
		
		String returnCode = getElementValue(root, "return_code");
		String returnMessage = getElementValue(root, "return_msg");
		if ("SUCCESS".equals(returnCode)) {
			String resultCode = getElementValue(root, "result_code");
			if ("SUCCESS".equals(resultCode)) {
				return getElementValue(root, "prepay_id");
			}
			throw new Exception(getElementValue(root, "err_code_des"));
		} else {
			throw new Exception(returnMessage);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static String getElementValue(Element parent, String xpath) {
		List children = parent.selectNodes(xpath);
		if (children.isEmpty()) { return ""; }
		Element childElement = (Element) children.get(0);
		if (childElement == null) { return ""; }

		return childElement.getTextTrim();
	}
	
	/**
	 * pc微信下单生成支付二维码
	 * @param appId
	 * @param productName
	 * @param type
	 * @param addressIp
	 * @return
	 * @throws Exception
	 */
	public static String payForQrcode(String appId, String productName, Short type, String addressIp,Integer price, String orderNo,
			String mchId, String payKey) throws Exception {
		Map<String ,String > signMap=new HashMap<String ,String >();
		signMap.put("appid", appId);
		signMap.put("mch_id", mchId);
		String nonceStr = WeixinUtil.getRandomStr();
        signMap.put("nonce_str", nonceStr);
        signMap.put("body", productName);
        signMap.put("out_trade_no", orderNo);
        signMap.put("total_fee", price + "");
        signMap.put("spbill_create_ip", addressIp);
        signMap.put("notify_url", "http://service.changxianshequ.com/httpService/wechatCallBack.htm");
        signMap.put("attach", type + "");
        signMap.put("trade_type", "NATIVE");
        String paySign = WeixinUtil.getPaySign(signMap, payKey);
        signMap.put("sign", paySign);
        String xml = WeixinUtil.map2Xml(signMap);
        String result = HttpUtil.postMethod("https://api.mch.weixin.qq.com/pay/unifiedorder", xml, null);
        
        SAXReader sb = new SAXReader();
		sb.setFeature("http://xml.org/sax/features/external-general-entities", false);
		sb.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		sb.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		sb.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		Document doc = sb.read(new StringReader(result));
		Element root = doc.getRootElement();
        
		String returnCode = WeixinPayUtil.getElementValue(root, "return_code");
		String returnMessage = WeixinPayUtil.getElementValue(root, "return_msg");
		log.info("[weixinPayUtil:payForQrcode]returnMessage:" + returnMessage);
		if ("SUCCESS".equals(returnCode)) {
			String resultCode = WeixinPayUtil.getElementValue(root, "result_code");
			System.out.println(resultCode);
			if ("SUCCESS".equals(resultCode)) {
//				String prepayId = WeixinPayUtil.getElementValue(root, "prepay_id");
				return WeixinPayUtil.getElementValue(root, "code_url");
			}
		}
		return null;
		
	}
	
}
