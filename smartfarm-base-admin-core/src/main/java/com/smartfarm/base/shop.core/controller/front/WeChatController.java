package com.smartfarm.base.shop.core.controller.front;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.util.WechatDecryptDataUtil;
import com.smartfarm.base.util.WeixinUtil;
import com.smartfarm.base.shop.core.entity.AccessToken;
import com.smartfarm.base.shop.core.entity.MemberInfo;
import com.smartfarm.base.shop.core.entity.vo.MiniProgramCodeVo;
import com.smartfarm.base.shop.core.entity.vo.MiniProgramUserVo;
import com.smartfarm.base.shop.core.manager.AccessTokenManager;
import com.smartfarm.base.shop.core.manager.MemberInfoManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/weChat")
public class WeChatController {
	
	private Logger log = Logger.getLogger(WeChatController.class);
	@Resource
	private MemberInfoManager memberInfoManager;
	@Resource
	private AccessTokenManager accessTokenManager;
	
	/**
	 * 小程序登录
	 * @param code
	 * @param request
	 * @return
	 */
	@RequestMapping("/loginMP")
	public Object loginMP(String code,HttpServletRequest request,MiniProgramUserVo miniProgramUserVo,String signId,Long businessId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[WeChatController:loginMP]{code："+code+",businessId:" + businessId + "}login mini program.");
			AccessToken accessToken = accessTokenManager.queryAccessToken(businessId);
			String appid = accessToken.getAppid();
			String appsecret = accessToken.getSecret();			
			MiniProgramCodeVo miniProgramCodeVo = WeixinUtil.getMPOpenId(appid, appsecret, code);
			if(miniProgramCodeVo == null) {
				log.info("[WeChatController:loginMP]:can not query weixin user info.");
				map.put("success", false);
				return map;
			}
			//查询是否有openid
			log.info("[WeChatController:loginMP]openId:" + miniProgramCodeVo.getOpenid());
			MemberInfo memberInfo = memberInfoManager.queryByOpenId(miniProgramCodeVo.getOpenid(),businessId);
			Long memberId = null;
			//新用户，新建用户信息
			if(null == memberInfo) {
				log.info("[WeChatController:loginMP]start to new member info.");
				memberInfo = new MemberInfo();
				if(miniProgramUserVo != null) {
					memberInfo.setPicUrl(miniProgramUserVo.getAvatarUrl());
					memberInfo.setNickName(miniProgramUserVo.getNickName());
				}
				memberInfo.setCreateTime(DateUtil.formatCurrentDateTime());
				memberInfo.setOpenid(miniProgramCodeVo.getOpenid());
				memberInfo.setBusinessId(businessId);
				memberInfo.setSessionKey(miniProgramCodeVo.getSessionKey());
				memberInfo.setPoint((long) 0);
				memberInfoManager.insert(memberInfo);
			}else if(miniProgramUserVo != null) {
				memberInfo.setPicUrl(miniProgramUserVo.getAvatarUrl());
				memberInfo.setNickName(miniProgramUserVo.getNickName());
				memberInfo.setSessionKey(miniProgramCodeVo.getSessionKey());
				memberInfoManager.updateMemberInfo(memberInfo);
			}
			memberId = memberInfo.getId();
			SessionUtil.addAttribute(request, SessionUtil.MEMBER_ID, memberId);
			SessionUtil.addAttribute(request, SessionUtil.FARM_ID, businessId);
			map.put("sessionId", request.getSession().getId());
			map.put("memberInfo", memberInfoManager.queryById(memberId));
			map.put("success", true);
		} catch (Exception e) {
			log.error("[WeChatController:loginMP]false to login mini program.",e);
			map.put("success", false);
		}
		return map;
	}
	
	/**
	 * 小程序心跳
	 * @param request
	 * @return
	 */
	@RequestMapping("/heart")
    public Object heart(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	Long userId =(Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
            map.put("userId",userId);
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }
	
	/**
	 * 获取sessionId
	 * @param request
	 * @return
	 */
	@RequestMapping("/session")
    public Object session(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[WeChatController:session]{sessionId："+request.getSession().getId()+"}");
        	map.put("sessionId", request.getSession().getId());
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }
	
	/**
	 * 更新用户微信信息
	 * @param request
	 * @param miniProgramUserVo
	 * @return
	 */
	@RequestMapping("/updateLoginUser")
	public Object updateLoginUser(HttpServletRequest request,MiniProgramUserVo miniProgramUserVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[WeChatController:updateLoginUser]update mini program user.");
			Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
			MemberInfo memberInfo = memberInfoManager.queryById(memberId);
			
			if(memberInfo != null) {
				memberInfo.setPicUrl(miniProgramUserVo.getAvatarUrl());
				memberInfo.setNickName(miniProgramUserVo.getNickName());
				memberInfoManager.updateMemberInfo(memberInfo);
			}
			map.put("memberInfo", memberInfo);
			map.put("success", true);
		} catch (Exception e) {
			log.error("[WeChatController:updateLoginUser]false to update mini program user.",e);
			map.put("success", false);
		}
		return map;
	}
	
	@RequestMapping("/updateUserMobile")
	public Object updateUserMobile(HttpServletRequest request,String iv,String encryptedData) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[WeChatController:updateUserMobile]update user mobile.");
			Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
			MemberInfo memberInfo = memberInfoManager.queryById(memberId);
			String mobile = WechatDecryptDataUtil.decryptData(encryptedData, memberInfo.getSessionKey(), iv);
			memberInfo.setMobile(mobile);
			memberInfoManager.updateMemberInfo(memberInfo);
			map.put("success", true);
		} catch (Exception e) {
			log.error("[WeChatController:updateUserMobile]false to update user mobile.",e);
			map.put("success", false);
		}
		return map;
	}
}
