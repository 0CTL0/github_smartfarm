package com.smartfarm.base.shop.core.controller.front;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.util.NumberUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.util.WeixinPayUtil;
import com.smartfarm.base.util.WeixinUtil;
import com.smartfarm.base.shop.core.entity.AccessToken;
import com.smartfarm.base.shop.core.entity.MemberInfo;
import com.smartfarm.base.shop.core.entity.MemberPointOrder;
import com.smartfarm.base.shop.core.manager.AccessTokenManager;
import com.smartfarm.base.shop.core.manager.MemberInfoManager;
import com.smartfarm.base.shop.core.manager.MemberPointOrderManager;


@Controller
@RequestMapping("/memberPointOrder")
public class MemberPointOrderController {

	private Logger log = Logger.getLogger(MemberPointOrderController.class);
	@Resource
	private MemberPointOrderManager memberPointOrderManager;
	@Resource
	private AccessTokenManager accessTokenManager;
	@Resource
	private MemberInfoManager memberInfoManager;


	
	/**
	 * 充值下单
	 * @param request
	 * @param orderProduct
	 * @return
	 */
	@RequestMapping("/createOrder")
    @ResponseBody
	public Object createOrder(HttpServletRequest request,MemberPointOrder memberPointOrder) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[MemberPointOrderController:createOrder]create order for point.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	Long farmId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
        	memberPointOrder.setMemberId(memberId);
        	memberPointOrder.setFarmId(farmId);
        	map.put("addOrder",memberPointOrderManager.createOrder(memberPointOrder));
        	map.put("orderId", memberPointOrder.getId());
        	
        	AccessToken accessToken = accessTokenManager.queryAccessToken(farmId);
        	String remoteAddr = request.getRemoteAddr();
        	MemberInfo memberInfo = memberInfoManager.queryById(memberId);
        	
        	//调用微信支付接口
        	Integer price = (int) (NumberUtil.mul(memberPointOrder.getPrice(), 100));
        	String prepayId = WeixinPayUtil.unifiedOrder(accessToken.getAppid(), "会员积分充值", (short)7, remoteAddr, price, memberPointOrder.getOrderNo(), 
        			memberInfo.getOpenid(),memberPointOrder.getEndTime(),accessToken.getMchid(),accessToken.getPayKey()); 
        	
        	Map<String ,String > signMap=new HashMap<String ,String >();
			signMap.put("appId", accessToken.getAppid());
	        String timestamp = WeixinUtil.getTimestamp();
	        signMap.put("timeStamp", timestamp);
	        String payPackage = "prepay_id=" + prepayId;
	        signMap.put("package", payPackage);
	        String signType = "MD5";
	        signMap.put("signType", signType);
	        String nonceStr = WeixinUtil.getRandomStr();
	        signMap.put("nonceStr", nonceStr);
	        String paySign = WeixinUtil.getPaySign(signMap, accessToken.getPayKey());
        	
	        map.put("nonceStr", nonceStr);
			map.put("timestamp", timestamp);
			map.put("payPackage", payPackage);
			map.put("signType", signType);
			map.put("paySign", paySign);
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MemberPointOrderController:createOrder]false to create order for point.",e);
        }
        return map;
	}
	
}
