package com.smartfarm.base.shop.core.controller.front;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.shop.core.entity.CouponBatchSend;
import com.smartfarm.base.shop.core.entity.CouponInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.shop.core.manager.CouponInfoManager;


@Controller
@RequestMapping("/couponF")
public class CouponControllerF {

	private Logger log = Logger.getLogger(CouponControllerF.class);
	@Resource
	private CouponInfoManager couponInfoManager;
	
	
	/**
	 * 查询订单可用优惠券
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryCouponByOrderId")
    @ResponseBody
	public Object queryCouponByOrderId(Long id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[CouponController:queryCouponByOrderId]query coupon by order id.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	map.put("couponList",couponInfoManager.queryCouponListByOrderId(id, memberId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[CouponController:queryCouponByOrderId]false to query coupon by order id.",e);
        }
        return map;
	}
	
	/**
	 * 查询用户优惠券列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryMemberCouponList")
    @ResponseBody
	public Object queryMemberCouponList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[CouponController:queryMemberCouponList]query coupon list of member.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	String nowTime = DateUtil.formatCurrentDateTime();
        	
        	map.put("useableCouponList",couponInfoManager.queryUseableByMemberId(memberId, nowTime));
        	map.put("usedCouponList",couponInfoManager.queryUsedByMemberId(memberId));
        	map.put("expiredCouponList",couponInfoManager.queryExpiredByMemberId(memberId, nowTime));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[CouponController:queryMemberCouponList]false to query coupon list of member.",e);
        }
        return map;
	}

	/**
	 * 根据短码生成优惠券
	 * @param request
	 * @return
	 */
	@RequestMapping("/createCouponByCode")
	@ResponseBody
	public Object createCouponByCode(HttpServletRequest request,String code,Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Long userId =(Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
			log.info("[CouponController:createCouponByCode]{userId:"+userId+",code:" + code + ",id:" + id + "}create coupon by code.");
			CouponInfo couponInfo = couponInfoManager.queryCouponByCode(code);
			if(couponInfo == null) {
				map.put("success",false);
				map.put("msg","优惠券不存在");
				return map;
			}
			if(couponInfo.getStatus() == 1) {
				map.put("success",false);
				map.put("msg","优惠券已被使用");
				return map;
			}
			if(Long.valueOf(couponInfo.getEndTime() + "235959") - Long.valueOf(DateUtil.formatCurrentDateTime()) <= 0) {
				map.put("success",false);
				map.put("msg","此优惠券已过期");
				return map;
			}

			couponInfo.setMemberId(userId);
			couponInfo.setStatus((short) 1);
			couponInfoManager.updateCouponInfo(couponInfo);

			map.put("couponList",couponInfoManager.queryCouponListByOrderId(id, userId));

			map.put("success",true);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg","优惠券兑换失败");
			log.error("[CouponController:createCouponByCode]false to create coupon by code,", e);
		}
		return map;
	}

	/**
	 * 查询可用的优惠券赠送活动
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryCouponSendList")
	@ResponseBody
	public Object queryCouponSendList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[CouponController:queryCouponSendList]query coupon send list.");
			Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("list",couponInfoManager.queryUseableCouponSend(businessId));
			map.put("success",true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:queryCouponSendList]false to query coupon send list.",e);
		}
		return map;
	}

	/**
	 * 领取优惠券
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/getSendCoupon")
	@ResponseBody
	public Object getSendCoupon(HttpServletRequest request,Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[CouponController:getSendCoupon]get coupon send.");
			Long userId =(Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
			CouponBatchSend couponBatchSend = couponInfoManager.querySendById(id);
			if(couponBatchSend.getStatus() == 1) {
				map.put("success",true);
				map.put("error",true);
				map.put("msg","优惠券已下架");
				return map;
			}
			int num = couponInfoManager.countCouponByUserIdAndBatchId(couponBatchSend.getCouponBatchId(),userId);
			if(num >= couponBatchSend.getTimeForUser()) {
				map.put("success",true);
				map.put("error",true);
				map.put("msg","您已领取过了");
				return map;
			}
			int flag = couponInfoManager.updateRemainNumSend(id);
			if(flag == 0) {
				map.put("success",true);
				map.put("error",true);
				map.put("msg","优惠券已被抢光");
				return map;
			}
			couponInfoManager.createCouponForMember(userId, couponBatchSend.getCouponBatchId(), 1);
			map.put("error",false);
			map.put("success",true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:getSendCoupon]false to get coupon send.",e);
		}
		return map;
	}
}
