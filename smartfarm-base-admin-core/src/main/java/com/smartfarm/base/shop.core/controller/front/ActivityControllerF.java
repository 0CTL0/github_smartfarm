package com.smartfarm.base.shop.core.controller.front;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.util.WeixinPayUtil;
import com.smartfarm.base.util.WeixinUtil;
import com.smartfarm.base.shop.core.entity.AccessToken;
import com.smartfarm.base.shop.core.entity.ActivityInfo;
import com.smartfarm.base.shop.core.entity.ActivityPrice;
import com.smartfarm.base.shop.core.entity.ActivityRegistration;
import com.smartfarm.base.shop.core.entity.MemberInfo;
import com.smartfarm.base.shop.core.manager.AccessTokenManager;
import com.smartfarm.base.shop.core.manager.ActivityManager;
import com.smartfarm.base.shop.core.manager.AdvanceNoticeManager;
import com.smartfarm.base.shop.core.manager.MemberInfoManager;

@Controller
@RequestMapping("/activity")
public class ActivityControllerF {

	private Logger log = Logger.getLogger(ActivityControllerF.class);
	@Resource
	private ActivityManager activityManager;
	@Resource
	private AccessTokenManager accessTokenManager;
	@Resource
	private MemberInfoManager memberInfoManager;
	@Resource
	private AdvanceNoticeManager advanceNoticeManager;
	
	/**
	 * 查询可用的活动
	 * @return
	 */
	@RequestMapping("/queryUseableList")
    @ResponseBody
    public Object queryUseableList(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try { 
        	log.info("[ActivityController:queryUseableList]query useable activity list.");
        	Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
        	map.put("list", activityManager.queryUseableList(DateUtil.formatCurrentDateTime(),businessId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[ActivityController:queryUseableList]false to query useable activity list.",e);
        }
        return map;
    }
	
	/**
	 * 根据id查询活动详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryDetailById")
    @ResponseBody
    public Object queryDetailById(Long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[ActivityController:queryDetailById]query detail info by id.");
        	map.put("activityVo", activityManager.queryDetailActivityById(id));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[ActivityController:queryDetailById]false to query detail info by id.",e);
        }
        return map;
    }
	
	/**
	 * 新增订单
	 * @param activityId
	 * @param priceId
	 * @param num
	 * @return
	 */
	@RequestMapping("/addRegistration")
    @ResponseBody
    public Object addRegistration(HttpServletRequest request,Long activityId, Long priceId, Integer num) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[ActivityController:addRegistration]add registration.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	//检查时间
        	ActivityInfo activityInfo = activityManager.getActivity(activityId);
        	if(Long.valueOf(activityInfo.getJoinTime()) >= Long.valueOf(DateUtil.formatCurrentDateTime())) {
        		map.put("success",true);
        		map.put("error",true);
        		map.put("msg","还没到开始报名时间");
        		return map;
        	}
        	if(Long.valueOf(activityInfo.getJoinDeadline()) <= Long.valueOf(DateUtil.formatCurrentDateTime())) {
        		map.put("success",true);
        		map.put("error",true);
        		map.put("msg","已经过了报名截止时间");
        		return map;
        	}
        	//库存
        	ActivityPrice activityPrice = activityManager.queryActivityPrice(priceId);
        	int updateNum = activityManager.updateRemain(num * activityPrice.getJoinNum(), activityId);
        	if(updateNum == 0) {
        		map.put("success",true);
        		map.put("error",true);
        		map.put("msg","剩余名额不足");
        		return map;
        	}
        	
        	map.put("error",false);
        	map.put("id",activityManager.addRegistration(activityId, priceId, num, memberId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[ActivityController:addRegistration]false to add registration.",e);
        }
        return map;
    }
	
	/**
	 * 根据id查询活动详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryRegistration")
    @ResponseBody
    public Object queryRegistration(Long registrationId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[ActivityController:queryRegistration]query registration by id.");
        	ActivityRegistration activityRegistration = activityManager.queryRegistrationById(registrationId);
        	map.put("activityRegistration",activityRegistration);
        	map.put("activityInfo",activityManager.queryActivityById(activityRegistration.getActivityId()));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[ActivityController:queryRegistration]false to query registration by id.",e);
        }
        return map;
    }
	
	/**
	 * 订单支付
	 * @param request
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/payForMiniProgram")
    @ResponseBody
	public Object payForMiniProgram(HttpServletRequest request,Long registrationId,String userName,String mobile,String idCard) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[ActivityController:payForMiniProgram]{registrationId:" + registrationId + "}pay for miniProgram.");
        	Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
        	ActivityRegistration activityRegistration = activityManager.queryRegistrationById(registrationId);
        	activityRegistration.setUserName(userName);
        	activityRegistration.setMobile(mobile);
        	activityRegistration.setIdCard(idCard);
        	
        	if(activityRegistration.getStatus() == (short)3) {
        		map.put("success",true);
        		map.put("error",true);
        		map.put("msg","订单已超时");
        		return map;
        	}
        	if(Long.valueOf(DateUtil.formatCurrentDateTime()).longValue() >= Long.valueOf(activityRegistration.getPayEndTime()).longValue()) {
        		map.put("success",true);
        		map.put("error",true);
        		map.put("msg","订单已超时");
        		return map;
        	}
        	if(activityRegistration.getStatus() == (short)4) {
        		map.put("success",true);
        		map.put("error",true);
        		map.put("msg","订单已取消");
        		return map;
        	}
        	if(activityRegistration.getStatus() == (short)2) {
        		map.put("success",true);
        		map.put("error",true);
        		map.put("msg","订单已支付");
        		return map;
        	}
        	map.put("error",false);
        	String remoteAddr = request.getRemoteAddr();
        	AccessToken accessToken = accessTokenManager.queryAccessToken(businessId);
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	MemberInfo memberInfo = memberInfoManager.queryById(memberId);
        	
        	activityManager.updateRegistration(activityRegistration);
        	
        	//调用微信支付接口
        	Integer price = (int) (activityRegistration.getRealPrice() * 100);
        	String prepayId = WeixinPayUtil.unifiedOrder(accessToken.getAppid(), "抢鲜商城农场活动", (short)4, remoteAddr, price, activityRegistration.getOrderNo(), 
        			memberInfo.getOpenid(),activityRegistration.getPayEndTime(),accessToken.getMchid(),accessToken.getPayKey()); 
        	
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
            log.error("[ActivityController:payForMiniProgram]false to pay for miniProgram.",e);
        }
        return map;
	}
	
	/**
	 * 根据id查询活动详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryRegistationList")
    @ResponseBody
    public Object queryRegistationList(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[ActivityController:queryRegistationList]query registration list.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	map.put("list",activityManager.queryRegistationListByMemberId(memberId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[ActivityController:queryRegistationList]false to query registration list.",e);
        }
        return map;
    }
}
