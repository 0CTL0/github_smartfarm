package com.smartfarm.base.shop.core.controller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.farm.core.manager.SettingInfoManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.farm.core.entity.EmployeeInfo;
import com.smartfarm.base.farm.core.manager.EmployeeInfoManager;
import com.smartfarm.base.shop.core.entity.MemberBank;
import com.smartfarm.base.shop.core.entity.MemberCashRecord;
import com.smartfarm.base.shop.core.entity.MemberInfo;
import com.smartfarm.base.shop.core.entity.MemberShareRecord;
import com.smartfarm.base.shop.core.manager.MemberBankManager;
import com.smartfarm.base.shop.core.manager.MemberInfoManager;

@Controller
@RequestMapping("/memberF")
public class MemberControllerF {

	private Logger log = Logger.getLogger(MemberControllerF.class);
	@Resource
	private MemberInfoManager memberInfoManager;
	@Resource
	private MemberBankManager memberBankManager;
	@Resource
	private EmployeeInfoManager employeeInfoManager;
	@Resource
	private SettingInfoManager settingInfoManager;

	/**
	 * 查询用户的粉丝addVisitRecord
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMemberFans")
	@ResponseBody
	public Object getMemberFans(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[MemberController:getMemberFans]query member info.");
			Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
			map.put("memberFans",memberInfoManager.getMemberFans(memberId));
			map.put("success",true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[MemberController:getMemberFans]false to query member info.",e);
		}
		return map;
	}

	/**
	 * 查询用户信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryMemberInfo")
    @ResponseBody
    public Object queryMemberInfo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[MemberController:queryMemberInfo]query member info.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	map.put("memberInfo",memberInfoManager.queryById(memberId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MemberController:queryMemberInfo]false to query member info.",e);
        }
        return map;
    }
	
	/**
	 * 查询用户个人中心信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryMemberIndex")
    @ResponseBody
    public Object queryMemberIndex(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[MemberController:queryMemberIndex]query member info.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	MemberInfo memberInfo = memberInfoManager.queryMemberVoById(memberId);
        	map.put("memberInfo",memberInfo);
        	map.put("bankList", memberBankManager.queryBankByMemberId(memberId));
        	map.put("Employee",false);
        	if(memberInfo != null &&  memberInfo.getMobile() != null) {
        		Long farmId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
        		EmployeeInfo employeeInfo = employeeInfoManager.queryEmployeeInfoByMobile(memberInfo.getMobile(), farmId);
        		if(employeeInfo != null) {
        			map.put("Employee",true);
        			map.put("employeeInfo",employeeInfo);
        		}
        	}
//			map.put("memberFansTotal",memberInfoManager.getMemberFansTotal(memberId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MemberController:queryMemberIndex]false to query member info.",e);
        }
        return map;
    }
	
	/**
	 * 更新用户信息
	 * @param memberInfo
	 * @return
	 */
	@RequestMapping("/updateMemberInfo")
    @ResponseBody
	public Object updateMemberInfo(MemberInfo memberInfo) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[MemberController:updateMemberInfo]update member info.");
        	memberInfoManager.updateMemberInfo(memberInfo);
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MemberController:updateMemberInfo]false to update member info.",e);
        }
        return map;
    }
	
	/**
	 * 添加分享记录
	 * @param request
	 * @param memberShareRecord
	 * @return
	 */
	@RequestMapping("/addShareRecord")
    @ResponseBody
	public Object addShareRecord(HttpServletRequest request,MemberShareRecord memberShareRecord) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[MemberController:addShareRecord]add share record.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	memberShareRecord.setMemberId(memberId);
        	memberShareRecord.setCreateTime(DateUtil.formatCurrentDateTime());
        	memberInfoManager.addShareRecord(memberShareRecord);
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MemberController:addShareRecord]false to add share record.",e);
        }
        return map;
    }
	
	/**
	 * 添加访问记录和绑定用户关系
	 * @param request
	 * @return
	 */
	@RequestMapping("/addVisitRecord")
    @ResponseBody
	public Object addVisitRecord(HttpServletRequest request,String code) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	long businessId=(Long)SessionUtil.getAttribute(request,SessionUtil.FARM_ID);
        	log.info("[MemberController:addVisitRecord]{code:" + code + ",memberId:" + memberId + "}add visit record.");
			memberInfoManager.addShareVisit(code, memberId);
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MemberController:addVisitRecord]false to add visit record.",e);
        }
        return map;
    }
	
	/**
	 * 添加用户银行卡信息
	 * @param request
	 * @param memberBank
	 * @return
	 */
	@RequestMapping("/addMemberBank")
    @ResponseBody
	public Object addMemberBank(HttpServletRequest request,MemberBank memberBank) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[MemberController:addMemberBank]add member bank info.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	memberBank.setMemberId(memberId);
        	memberBankManager.insert(memberBank);
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MemberController:addMemberBank]false to add member bank info.",e);
        }
        return map;
    }
	
	/**
	 * 添加用户银行卡信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryMemberBank")
    @ResponseBody
	public Object queryMemberBank(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[MemberController:queryMemberBank]query member bank info.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	map.put("list", memberBankManager.queryBankByMemberId(memberId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MemberController:queryMemberBank]false to query member bank info.",e);
        }
        return map;
    }
	
	/**
	 * 申请提现
	 * @param request
	 * @param money
	 * @return
	 */
	@RequestMapping("/submitMemberCash")
    @ResponseBody
	public Object submitMemberCash(HttpServletRequest request, Double money, Long bankId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[MemberController:submitMemberCash]{money:" + money + "}submit member money.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	//检查余额
        	MemberInfo memberInfo = memberInfoManager.queryById(memberId);
        	if(money == null || memberInfo.getCash().doubleValue() < money.doubleValue()) {
        		map.put("msg", "提现金额大于余额");
        		map.put("success",false);
        		return map;
        	}
        	
        	//检查提现中
        	List<MemberCashRecord> list = memberInfoManager.queryCashRecord((short)2, memberId, (short)2);
        	if(list.size() > 0) {
        		map.put("msg", "还有提现申请在处理中");
        		map.put("success",false);
        		return map;
        	}
        	memberInfoManager.cashRecordApply(memberId, money, bankId);
        	map.put("memberInfo",memberInfoManager.queryById(memberId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "申请失败");
            log.error("[MemberController:submitMemberCash]false to submit member money.",e);
        }
        return map;
    }
	
	/**
	 * 查询提现记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryCashRecordApply")
    @ResponseBody
	public Object queryCashRecordApply(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[MemberController:queryCashRecordApply]query member cash record.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	map.put("list", memberInfoManager.queryCashRecord(null, memberId, (short)2));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "申请失败");
            log.error("[MemberController:queryCashRecordApply]false to query member cash record.",e);
        }
        return map;
    }
	
	
		
	
}
