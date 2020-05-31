package com.smartfarm.base.util;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class weixinRefundUtil {
    private static final Logger log = Logger.getLogger(weixinRefundUtil.class);

    private static final String APPLY_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 申请退款
     */
    public static String applyRefund(String appId,String mchId, String orderNo,String refundNo,Integer orderFee,
                                     Integer refundFee,String payKey,String certUrl) throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        //公众账号ID
        map.put("appid", appId);
        //微信支付分配的商户号
        map.put("mch_id", mchId);
       //随机字符串
        map.put("nonce_str", WeixinUtil.getRandomStr());
        //退款结果通知url
        map.put("notify_url", "http://fservice.changxianshequ.com/httpService/wechatRefundCallBack.htm");
        //商户订单号
        map.put("out_trade_no", orderNo);
        //商户退款单号
        map.put("out_refund_no", refundNo);
        //退款金额
        map.put("refund_fee", refundFee+"");
        //订单金额
        map.put("total_fee", orderFee+"");
        //签名
        String refundSign = WeixinUtil.getPaySign(map, payKey);
        map.put("sign", refundSign);
        map.put("transaction_id", "");

        log.info("[WeixinRefund:refundUtil:applyRefund]拼装生成订单退款数据成功[data:" + map.toString() + "]");

        String mapToXml = WeixinUtil.map2Xml(map);
        log.info("mapToXml: "+mapToXml);
        String reponseXml = HttpUtil.sendSSLPostToWx(mapToXml, WechatConfig.getSslcsf(mchId,certUrl),APPLY_REFUND_URL);

        log.info("WeixinRefund:[refundUtil:applyRefund]申请退款获取到数据[data:" + reponseXml + "]");

        SAXReader sb = new SAXReader();
        sb.setFeature("http://xml.org/sax/features/external-general-entities", false);
        sb.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        sb.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        sb.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        Document doc = sb.read(new StringReader(reponseXml));
        Element root = doc.getRootElement();

        String returnCode = getElementValue(root, "return_code");
        String returnMessage = getElementValue(root, "return_msg");
        if ("SUCCESS".equals(returnCode)) {
            String resultCode = getElementValue(root, "result_code");
            if ("SUCCESS".equals(resultCode)) {
                return getElementValue(root, "refund_id");
            }
            throw new Exception(getElementValue(root, "err_code_des"));
        } else {
            throw new Exception(returnMessage);
        }
    }

    @SuppressWarnings("rawtypes")
    public static String getElementValue(Element parent, String xpath) {
        List children = parent.selectNodes(xpath);
        if (children.isEmpty()){
            return "";
        }
        Element childElement = (Element) children.get(0);
        if (childElement == null){
            return "";
        }
        return childElement.getTextTrim();
    }
}
