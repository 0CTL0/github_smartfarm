package com.smartfarm.base.service.shop.service;

import com.smartfarm.base.admin.core.util.AESUtil;
import com.smartfarm.base.admin.core.util.DateUtil;
import com.smartfarm.base.admin.core.util.WeixinUtil;
import com.smartfarm.base.farm.core.entity.FarmBazaarOrder;
import com.smartfarm.base.farm.core.entity.FarmRentLandOrder;
import com.smartfarm.base.farm.core.manager.FarmMemberCropsManager;
import com.smartfarm.base.farm.core.manager.FarmRentLandOrderManager;
import com.smartfarm.base.shop.core.entity.*;
import com.smartfarm.base.shop.core.manager.*;
import com.smartfarm.base.shop.core.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/httpService")
public class NotifyDataServlet {
	protected static Logger log = Logger.getLogger(NotifyDataServlet.class);
	@Autowired
	private OrderInfoManager orderInfoManager;
	@Resource
	private OrderService orderService;
	@Resource
	private BaseOrderManager baseOrderManager;
	@Resource
	private ActivityManager activityManager;
	@Resource
	private AccessTokenManager accessTokenManager;
	@Resource
	private FarmRentLandOrderManager farmRentLandOrderManager;
	@Resource
	private FarmMemberCropsManager farmMemberCropsManager;
	@Resource
	private MemberPointOrderManager memberPointOrderManager;


	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	@ResponseBody
    @RequestMapping(value="/wechatCallBack",method = RequestMethod.POST,
    consumes = {MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
	protected void wechatCallBack(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		BufferedInputStream bis = new BufferedInputStream(req.getInputStream());
		int offset = 0;
		int remain = req.getContentLength();
		byte[] buffer = new byte[remain];
		while (remain > 0) {
			int nread = bis.read(buffer, offset, remain);
			if (nread == -1) {
				// 已经读完所有的数据
				break;
			}
			offset += nread;
			remain -= nread;
		}

		String seq = getLogSeq();
		StringBuilder sb = new StringBuilder(seq).append("notifyData1支付回调接口收到请求:[").append(uri).append("]").append(new String(buffer));
		log.info(sb.toString());

		Map<String, String> params = new HashMap<String, String>();
		params.put("notifyData", new String(buffer));

		String msg = payNotify(seq, params);

		if (msg != null && !"".equals(msg)) {
			resp.setContentType("text/html");
			resp.getWriter().write(msg);
		} else {
			resp.getWriter().write("success");
		}
		resp.flushBuffer();
	}

	@ResponseBody
	@RequestMapping(value="/wechatRefundCallBack",method = RequestMethod.POST,
			consumes = {MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
	protected void wechatRefundCallBack(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		BufferedInputStream bis = new BufferedInputStream(req.getInputStream());
		int offset = 0;
		int remain = req.getContentLength();
		byte[] buffer = new byte[remain];
		while (remain > 0) {
			int nread = bis.read(buffer, offset, remain);
			if (nread == -1) {
				// 已经读完所有的数据
				break;
			}
			offset += nread;
			remain -= nread;
		}

		String seq = getLogSeq();
		StringBuilder sb = new StringBuilder(seq).append("notifyData1退款回调接口收到请求:[").append(uri).append("]").append(new String(buffer));
		log.info(sb.toString());

		Map<String, String> params = new HashMap<String, String>();
		params.put("notifyData", new String(buffer));

		String msg = refundNotify(seq, params);

		if (msg != null && !"".equals(msg)) {
			resp.setContentType("text/html");
			resp.getWriter().write(msg);
		} else {
			resp.getWriter().write("success");
		}
		resp.flushBuffer();
	}

	public void init(ServletConfig config) throws ServletException {
//		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
//		paybackService = (PaybackService) wac.getBean("payService", PaybackService.class);
//		super.init();
	}

	private String getLogSeq() {
		StringBuilder sb = new StringBuilder("[").append(String.valueOf(System.currentTimeMillis())).append("]");
		return sb.toString();
	}
	
	private String payNotify(String seq, Map<String, String> params) {
		try {
			String result = params.get("notifyData");
			log.info("报文" + result);
			Map<String, String> respData = parseXml(result);
			String returnCode = respData.get("return_code");
			if ("SUCCESS".equals(returnCode)){
				String sign = respData.get("sign");
				Map<String, String> signMap = new HashMap<String, String>();
				signMap.put("appid", respData.get("appid"));
				signMap.put("attach", respData.get("attach"));
				signMap.put("bank_type", respData.get("bank_type"));
				signMap.put("cash_fee", respData.get("cash_fee"));
				signMap.put("fee_type", respData.get("fee_type"));
				signMap.put("is_subscribe", respData.get("is_subscribe"));
				signMap.put("mch_id", respData.get("mch_id"));
				signMap.put("nonce_str", respData.get("nonce_str"));
				signMap.put("openid", respData.get("openid"));
				signMap.put("out_trade_no", respData.get("out_trade_no"));
				signMap.put("result_code", respData.get("result_code"));
				signMap.put("return_code", respData.get("return_code"));
				signMap.put("time_end", respData.get("time_end"));
				signMap.put("total_fee", respData.get("total_fee"));
				signMap.put("trade_type", respData.get("trade_type"));
				signMap.put("transaction_id", respData.get("transaction_id"));
				boolean isVerify = verifySign(seq, signMap, sign);
				if(isVerify) {
					String resultCode = respData.get("result_code");
					if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
						String orderNo = respData.get("out_trade_no");// 支付订单号
						String traderNo = respData.get("transaction_id");// 支付平台流水号
						log.info("[NotifyDataServlet:payNotify]{orderNo" + orderNo + ",traderNo" + traderNo);
						
						//业务处理
						if("3".equals(respData.get("attach"))) {//订单
							log.info("[NotifyDataServlet:payNotify]{orderNo" + orderNo + ",traderNo" + traderNo + "}shop");
							OrderInfo orderInfo = orderInfoManager.queryByOrderNo(orderNo);
							orderInfoManager.orderSucess(orderInfo);
							BaseOrder baseOrder = baseOrderManager.queryByTypeAndRefId(BaseOrder.BASE_ORDER_TYPE_ORDER, orderInfo.getId());
							orderService.paySuccess(baseOrder.getId());
						}else if("4".equals(respData.get("attach"))) {//农场活动
							log.info("[NotifyDataServlet:payNotify]{orderNo" + orderNo + ",traderNo" + traderNo + "}farm activity");
							activityManager.successOrder(orderNo, traderNo);
						}else if("5".equals(respData.get("attach"))) {//地块租赁
							log.info("[NotifyDataServlet:payNotify]{orderNo" + orderNo + ",traderNo" + traderNo + "}rent order");
							String timeEnd=respData.get("time_end");
							FarmRentLandOrder farmRentLandOrder= farmRentLandOrderManager.getRentOrderByOrderCode(orderNo);
	                        farmRentLandOrder.setPayNo(traderNo);
	                        farmRentLandOrder.setPayTime(timeEnd);
	                        farmRentLandOrder.setStatus(FarmRentLandOrder.STATUS_PAYED);
	                        farmRentLandOrder.setPayStatus(FarmRentLandOrder.PAYSTATUS_PAYED);
	                        farmRentLandOrderManager.orderSuccess(farmRentLandOrder);
	                        BaseOrder baseOrder = baseOrderManager.queryByTypeAndRefId(BaseOrder.BASE_ORDER_TYPE_RENT, farmRentLandOrder.getId());
	                        orderService.paySuccess(baseOrder.getId());
						}else if("6".equals(respData.get("attach"))) { //自由销售
							log.info("[NotifyDataServlet:payNotify]{orderNo" + orderNo + ",traderNo" + traderNo + "}farm bazaar order");
							String timeEnd=respData.get("time_end");
                            FarmBazaarOrder farmBazaarOrder=farmMemberCropsManager.getBazaarOrderByOrderCode(orderNo);
                           	farmBazaarOrder.setOrderStatus(FarmBazaarOrder.ORDERSTATUS_PAYED);
                            farmBazaarOrder.setPayTime(timeEnd);
                            farmBazaarOrder.setPayNo(traderNo);
                            farmBazaarOrder.setPayStatus(FarmBazaarOrder.PAYSTATUS_PAYED);
							farmMemberCropsManager.orderSuccess(farmBazaarOrder);
                            BaseOrder baseOrder = baseOrderManager.queryByTypeAndRefId(BaseOrder.BASE_ORDER_TYPE_FARMBAZAAR, farmBazaarOrder.getId());
	                        orderService.paySuccess(baseOrder.getId());
						}else if("7".equals(respData.get("attach"))) {//积分充值
							log.info("[NotifyDataServlet:payNotify]{orderNo" + orderNo + ",traderNo" + traderNo + "}member point");
							MemberPointOrder memberPointOrder = memberPointOrderManager.queryByOrderNo(orderNo);
							memberPointOrderManager.orderSucess(memberPointOrder);
							BaseOrder baseOrder = baseOrderManager.queryByTypeAndRefId(BaseOrder.BASE_ORDER_TYPE_POINT, memberPointOrder.getId());
							orderService.paySuccess(baseOrder.getId());
						}
						
						log.info("[NotifyDataServlet:doPay]{orderNo:" + orderNo + "}支付成功，更新状态");
						return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
					} else {
						log.error("[NotifyDataServlet:payNotify]{sep:" + seq + "}参数未包含订单号和订单状态！");
						return "fail";
					}
				} else {
					log.error("[NotifyDataServlet:payNotify]{sep:"+seq + "}签名验证失败！");
					return "fail";
				}
			}else{
				log.error("[NotifyDataServlet:payNotify]{sep:"+seq + "}支付失败!");
			}
		} catch (Exception e) {
			log.error("[NotifyDataServlet:payNotify]payNotify方法出现异常！" + e);
			e.printStackTrace();
		}
		return "fail";
	}


	private String refundNotify(String seq, Map<String, String> params) {
		try {
			String result = params.get("notifyData");
			log.info("报文" + result);
			Map<String, String> respData = parseXml(result);
			String returnCode = respData.get("return_code");
			if ("SUCCESS".equals(returnCode)){
				Map<String, String> signMap = new HashMap<String, String>();

				//加密信息处理
				String req_info=respData.get("req_info");
				//log.info("req_info解密前："+req_info);
				String reqInfoXml=AESUtil.decryptData(req_info);
				//log.info("req_info解密后："+ reqInfoXml);
				Map<String, String> reqInfoData = parseXml(reqInfoXml);


					String resultStatus = reqInfoData.get("refund_status");
					log.info("resultStatus:"+resultStatus);
					if ("SUCCESS".equals(resultStatus)) {
						String orderNo = reqInfoData.get("out_trade_no");// 支付订单号
						String traderNo = reqInfoData.get("transaction_id");// 支付平台流水号
						log.info("[NotifyDataServlet:refundNotify]{out_trade_no:" + orderNo + ",transaction_id:" + traderNo);

						//业务处理
						OrderRefund orderRefund = new OrderRefund();
						orderRefund.setTransactionId(reqInfoData.get("transaction_id"));
						orderRefund.setRefundId(reqInfoData.get("refund_id"));
						orderRefund.setRefundFee(reqInfoData.get("refund_fee"));
						orderRefund.setRefundNo(reqInfoData.get("out_refund_no"));

						log.info("success_time=" + reqInfoData.get("success_time"));
						//String str=reqInfoData.get("success_time");
						//String successTime=str.substring(0,4)+str.substring(5,7)+str.substring(8,10)+str.substring(11,13)+str.substring(14,16)+str.substring(17,19);
						orderRefund.setSuccessTime(DateUtil.formatCurrentDateTime());
						log.info("orderRefund:"+orderRefund);
						orderInfoManager.refundSuccess(orderRefund);
						log.info("orderRefund:"+orderRefund);

						log.info("[NotifyDataServlet:doRefund]{orderNo:" + orderNo + "}退款成功，更新状态！");
						return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
					}
					if("CHANGE".equals(resultStatus)){
						log.error("[NotifyDataServlet:refundNotify]{sep:" + seq + "}退款异常！");
						}
					if("REFUNDCLOSE".equals(resultStatus)){
						log.error("[NotifyDataServlet:refundNotify]{sep:" + seq + "}退款关闭！");
						}
			}
		} catch (Exception e) {
			log.error("[NotifyDataServlet:refundNotify]refundNotify方法出现异常！" + e);
			e.printStackTrace();
		}
		return "fail";
	}
	
	public boolean verifySign(String seq, Map<String, String> params, String sign) {
		String paySign;
		try {
			String appid = params.get("appid");
			AccessToken accessToken = accessTokenManager.queryAccessTokenByAppId(appid);
			paySign = WeixinUtil.getPaySign(params, accessToken.getPayKey());
			//log.info("sign:"+sign);
			//log.info("paySign:"+paySign);
			if (sign.equals(paySign)) {
				return true;
			}
		} catch (Exception e) {
			log.error("[NotifyDataServlet:verifySign]：调用WeixinUtil.getPaySign()方法签名失败！");
			e.printStackTrace();
		}
		return false;
	}
	
	// 解析XML
	private static  Map<String, String> parseXml(String protocolXML) {
		Map<String, String> respData = new HashMap<String, String>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setExpandEntityReferences(false);
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			org.w3c.dom.Document doc = builder.parse(new InputSource(new StringReader(protocolXML)));

			org.w3c.dom.Element root = doc.getDocumentElement();
			NodeList books = root.getChildNodes();
			if (books != null) {
				for (int i = 0; i < books.getLength(); i++) {
					Node book = books.item(i);
					respData.put(book.getNodeName(), book.getFirstChild() == null ? "" : book.getFirstChild().getNodeValue());
				}
			}
		} catch (Exception e) {
			log.error("[NotifyDataServlet:parseXml]转换xml失败",e);
		}
		return respData;
	}

	
	@RequestMapping("/test")
    @ResponseBody
    public Object test(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("测试");
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }


}
