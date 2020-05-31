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
import com.smartfarm.base.shop.core.entity.MemberAddress;
import com.smartfarm.base.shop.core.manager.MemberAddressManager;
import com.smartfarm.base.shop.core.manager.MemberInfoManager;

@Controller
@RequestMapping("/memberAddress")
public class MemberAddressController {

	private Logger log = Logger.getLogger(MemberAddressController.class);
	@Resource
	private MemberInfoManager memberInfoManager;
	@Resource
	private MemberAddressManager memberAddressManager;
	
	/**
	 * 查询用户地址
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryMemberAddress")
    @ResponseBody
    public Object queryMemberAddress(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[MemberAddressController:queryMemberAddress]query member address.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	map.put("addressList",memberAddressManager.queryAddressByMemberId(memberId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MemberAddressController:queryMemberAddress]false to query member address.",e);
        }
        return map;
    }
	
	/**
	 * 新增地址
	 * @param request
	 * @param memberAddress
	 * @return
	 */
	@RequestMapping("/addAddress")
    @ResponseBody
    public Object addAddress(HttpServletRequest request,MemberAddress memberAddress) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[MemberAddressController:addAddress]add member address info.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	memberAddress.setMemberId(memberId);
        	memberAddress.setCreateTime(DateUtil.formatCurrentDateTime());
        	memberAddress.setStatus((short)1);
        	memberAddressManager.insert(memberAddress);
        	map.put("addressList",memberAddressManager.queryAddressByMemberId(memberId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MemberAddressController:addAddress]false to add member address info.",e);
        }
        return map;
    }
	
	/**
	 * 修改地址
	 * @param request
	 * @param memberAddress
	 * @return
	 */
	@RequestMapping("/updateAddress")
    @ResponseBody
    public Object updateAddress(HttpServletRequest request,MemberAddress memberAddress) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[MemberAddressController:updateAddress]update member address info.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	memberAddressManager.updateById(memberAddress);
        	map.put("addressList",memberAddressManager.queryAddressByMemberId(memberId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MemberAddressController:updateAddress]update to add member address info.",e);
        }
        return map;
    }
}
