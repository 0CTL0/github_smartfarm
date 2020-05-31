package com.smartfarm.base.farm.core.cotroller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.farm.core.entity.EmployeeInfo;
import com.smartfarm.base.farm.core.entity.SettingInfo;
import com.smartfarm.base.farm.core.manager.EmployeeInfoManager;
import com.smartfarm.base.farm.core.manager.SettingInfoManager;
import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.farm.core.entity.vo.FarmDelegateOrderVo;
import com.smartfarm.base.farm.core.manager.FarmDelegateOrderManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月23日 14:14:32
 * @version 1.0
 */
@RequestMapping("/farmDelegate")
@Controller
public class FarmDelegateController {

	private Logger log = Logger.getLogger(FarmDelegateController.class);
	
	@Resource
	private FarmDelegateOrderManager delegateOrderManager;
	@Resource
	private EmployeeInfoManager employeeInfoManager;
	@Resource
	private SettingInfoManager settingInfoManager;
	
	
	/**
	 * 后台查询委托单
	 * @param request
	 * @param
	 * @param startDate
	 * @param endDate
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/getDelegateOrderInfo")
	@ResponseBody
	public Object getDelegateOrderInfo(HttpServletRequest request,Short status,String paramCode,String startDate,String endDate,Integer pageSize,Integer pageNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[FarmDelegateController:getDelegateOrderInfo] begin to query delegateOrder info.");
			if (startDate==null || startDate=="") {
				if (endDate==null || endDate=="") {
					startDate = DateUtil.formatCurrentDate();
					endDate = DateUtil.formatAddDate(7);
				}else {
					endDate = endDate.replaceAll("-", "");
					startDate = DateUtil.formatAddDate(endDate, -7);
				}
			}else {
				if (endDate==null || endDate=="") {
					startDate = startDate.replaceAll("-", "");
					endDate = DateUtil.formatAddDate(startDate,7);
				}else {
					startDate = startDate.replaceAll("-", "");
					endDate = endDate.replaceAll("-", "");
				}
			}

			List<FarmDelegateOrderVo> delegateOrderVos = delegateOrderManager.queryDelegateOrderPageList((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID),paramCode,status, startDate, endDate, (pageNumber-1)*pageSize, pageSize);
			for(FarmDelegateOrderVo orderVo : delegateOrderVos){
			    orderVo.setApplyDate(DateUtil.formatDate(orderVo.getApplyDate()));
			    orderVo.setWorkDate(DateUtil.formatDate(orderVo.getWorkDate()));
			    orderVo.setParamName(orderVo.getParamName().substring(0,2));
            }
			int total = delegateOrderManager.queryDelegateOrderTotal((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID),paramCode,status, startDate, endDate);
			List<SettingInfo>  delegateTypes= settingInfoManager.queryDelegateType((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID));
			for (SettingInfo setting : delegateTypes){
				setting.setParamName(setting.getParamName().substring(0,2));
			}
			map.put("delegateOrderVos", delegateOrderVos);
			map.put("total", total);
			map.put("delegateTypes", delegateTypes);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmDelegateController:getDelegateOrderInfo] fail to query delegateOrder info.",e);
		}
		return map;
	}


	@RequestMapping("/getPublishInfo")
	@ResponseBody
	public Object getPublishInfo(HttpServletRequest request,Long orderId,String workType,String rentLandName) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[FarmDelegateController:getPublishInfo] begin to query delegate task info.");
			List<EmployeeInfo> employeeList = employeeInfoManager.queryEmployeeInfoByFarmId((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID));
			map.put("employeeList", employeeList);
			String workContent = delegateOrderManager.stitchDelegateContent(orderId, workType, rentLandName);
			map.put("workContent", workContent);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[FarmDelegateController:getPublishInfo] fail to query delegate task info.",e);
		}
		return map;
	}
	
}
