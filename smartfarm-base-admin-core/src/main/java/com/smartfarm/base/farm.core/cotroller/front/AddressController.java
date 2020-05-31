package com.smartfarm.base.farm.core.cotroller.front;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.util.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.shop.core.entity.MemberAddress;
import com.smartfarm.base.shop.core.manager.MemberAddressManager;

@Controller
@RequestMapping("/address")
public class AddressController {

	private Logger log = Logger.getLogger(AddressController.class);
	
	@Resource
	private MemberAddressManager memberAddressManager;
	
	/**
	 * 插入地址
	 * @param request
	 * @param memberAddress
	 * @return
	 */
	@RequestMapping("/saveMemberAddress")
	@ResponseBody
	public Object saveMemberAddress(HttpServletRequest request,MemberAddress memberAddress){
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[AddressController:saveMemberAddress]save MemberAddress for page.");
        	memberAddress.setMemberId((Long) SessionUtil.getAttribute(request, SessionUtil.MEMBER_ID));
        	memberAddressManager.insert(memberAddress);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[AddressController:saveMemberAddress]false to save MemberAddress.", e);
		}
		return map;
	}
	
	/**
	 * 根据id得到地址信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/getAddressDetailById")
	@ResponseBody
	public Object getAddressDetailById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[AddressController:getAddressDetailById]query memberAddress info.");
			map.put("addressInfo", memberAddressManager.getAddressDetailById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[AddressController:getAddressDetailById]false to query memberAddress info.", e);
		}
		return map;
	}
	
	/**
	 * 更新地址
	 * @param memberAddress
	 * @return
	 */
	@RequestMapping("/updateAddressById")
	@ResponseBody
	public Object updateAddressById(MemberAddress memberAddress){
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[AddressController:updateAddressById]update MemberAddress for page.");
        	memberAddressManager.updateById(memberAddress);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[AddressController:updateAddressById]false to update MemberAddress.", e);
		}
		return map;
	}
}
