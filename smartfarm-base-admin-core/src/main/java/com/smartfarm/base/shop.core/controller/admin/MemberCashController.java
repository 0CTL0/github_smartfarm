package com.smartfarm.base.shop.core.controller.admin;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.shop.core.entity.MemberCashRecord;
import com.smartfarm.base.shop.core.entity.vo.MemberCashRecordVo;
import com.smartfarm.base.shop.core.manager.MemberCashRecordManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/memberCash")
public class MemberCashController {
	private Logger log = Logger.getLogger(ActivityController.class);
	@Resource
	private MemberCashRecordManager memberCashRecordManager;
		
	@RequestMapping("queryMemberCashRecordPageList")
	@ResponseBody
	public Object queryEasyTaskInfoPageList(HttpServletRequest request, String memberName, Short status, Integer pageSize, Integer pageNumber) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[cashController:queryMemberCashRecordPageList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber);
			List<MemberCashRecordVo> memberCashRecordList = memberCashRecordManager.queryMemberCashRecordList(memberName, status, (pageNumber - 1) * pageSize, pageSize);
			map.put("memberCashRecordList", memberCashRecordList);
			
			map.put("total", memberCashRecordManager.countMemberCashRecordList(memberName, status));
			
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[cashController:queryMemberCashRecordPageList]false", e);
		}
		
		return map;
	}
	
	/**
	 * 改变会员提现的状态
	 * @return
	 */
	@RequestMapping("/updateMemberCashRecord")
	@ResponseBody
	public Object updateMemberCashRecord(HttpServletRequest request, MemberCashRecord memberCashRecord){
		//@RequestParam("easyTaskId")Long easyTaskId
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[cashController:queryMemberCashRecordPageList]{status: " + memberCashRecord.getStatus()+"id: "+memberCashRecord.getId());	
			String updateTime= DateUtil.formatCurrentDateTime();
			memberCashRecord.setUpdateTime(updateTime);		
			Long operateId=(Long)SessionUtil.getAttribute(request, SessionUtil.OPERATOR_ID);
			log.info("[cashController:queryMemberCashRecordPageList]{operateId:" + operateId);
			memberCashRecord.setOperatorId(operateId);
			
			memberCashRecordManager.updateMemberCashRecord(memberCashRecord);
			map.put("success", true);
		}
		catch(Exception e){
			map.put("success", false);
		
		}
		return map;
	}
	
}
