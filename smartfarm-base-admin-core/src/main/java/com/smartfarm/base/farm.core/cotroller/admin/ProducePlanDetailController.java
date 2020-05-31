package com.smartfarm.base.farm.core.cotroller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.farm.core.entity.EmployeeInfo;
import com.smartfarm.base.farm.core.entity.FarmSourceType;
import com.smartfarm.base.farm.core.manager.EmployeeInfoManager;
import com.smartfarm.base.farm.core.manager.FarmSourceTypeManager;
import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.smartfarm.base.farm.core.entity.ProducePlanDetail;
import com.smartfarm.base.farm.core.entity.vo.ProducePlanDetailVo;
import com.smartfarm.base.farm.core.manager.ProducePlanDetailManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年11月26日 10:11:49
 * @version 1.0
 */
@RequestMapping("/planDetail")
@Controller
public class ProducePlanDetailController {

	private Logger log = Logger.getLogger(ProducePlanDetailController.class);
	
	@Resource
	private ProducePlanDetailManager planDetailManager;
	@Resource
	private EmployeeInfoManager employeeInfoManager;
	@Resource
	private FarmSourceTypeManager sourceTypeManager;
	
	
	/**
	 * 根据masterId分页查询计划明细
	 * @param request
	 * @param masterId
	 * @return
	 */
	@RequestMapping("getPageListByMasterId")
	@ResponseBody
	public Object getPageListByMasterId(HttpServletRequest request,Integer pageSize,Integer pageNumber,Long masterId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProducePlanDetailController:getPageListByMasterId] begin to query detail by masterId.");
			log.info("[ProducePlanDetailController:getPageListByMasterId] { num:"+pageNumber +" }.");
			log.info("[ProducePlanDetailController:getPageListByMasterId] { size:"+pageSize +" }.");
			log.info("[ProducePlanDetailController:getPageListByMasterId] { masterId:"+masterId +" }.");
			List<ProducePlanDetail> planDetailList = planDetailManager.queryPageListByMasterId(masterId,(pageNumber - 1) * pageSize, pageSize);
			for (ProducePlanDetail detail : planDetailList){
				detail.setTaskDate(DateUtil.formatDate(detail.getTaskDate()));
			}
			map.put("planDetailList", planDetailList);
			map.put("total", planDetailManager.queryPageTotalByMasterId(masterId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProducePlanDetailController:getPageListByMasterId] fail to query detail by masterId.",e);
		}
		return map;
	}
	
	/**
	 * 查找单个计划信息
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/getDetailById")
	@ResponseBody
	public Object getDetailById(HttpServletRequest request,Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProducePlanDetailController:getDetailById] begin to query detail.");
			ProducePlanDetail planDetail = planDetailManager.querySingleDetailById(id);
			String taskDate = planDetail.getTaskDate();
			String taskDateStr = new StringBuilder().append(taskDate.substring(0,4)).append("-").append(taskDate.substring(4,6)).append("-").append(taskDate.substring(6,8)).toString();
			planDetail.setTaskDate(taskDateStr);
			map.put("planDetail", planDetail);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProducePlanDetailController:getDetailById] fail to query detail.",e);
		}
		return map;
	}
	
	/**
	 * 根据id修改计划
	 * @param request
	 * @param planDetail
	 * @return
	 */
	@RequestMapping("/modifyDetailById")
	@ResponseBody
	public Object modifyDetailById(HttpServletRequest request,ProducePlanDetail planDetail) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProducePlanDetailController:modifyDetailById] begin to update detail.");
			planDetail.setTaskDate(planDetail.getTaskDate().replaceAll("-", ""));
			planDetailManager.modifyDetailById(planDetail);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProducePlanDetailController:modifyDetailById] fail to update detail.",e);
		}
		return map;
	}
	
	/**
	 * 添加计划任务
	 * @param request
	 * @param planDetail
	 * @return
	 */
	@RequestMapping("/addPlanDetail")
	@ResponseBody
	public Object addPlanDetail(HttpServletRequest request,ProducePlanDetail planDetail) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProducePlanDetailController:addPlanDetail] begin to add plan detail.");
			planDetail.setTaskDate(planDetail.getTaskDate().replaceAll("-", ""));
			planDetailManager.addPlanDetail(planDetail);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProducePlanDetailController:addPlanDetail] fail to add plan detail.",e);
		}
		return map;
	}
	
	/**
	 * 删除计划
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteDetailById")
	@ResponseBody
	public Object deleteDetailById(HttpServletRequest request,Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProducePlanDetailController:deleteBDetailById] begin to delete plan detail.");
			planDetailManager.deleteDetailById(id);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProducePlanDetailController:deleteBDetailById] fail to delete plan detail.",e);
		}
		return map;
	}
	
	/**
	 * 查询时间范围内（默认未来一周）的所有生产计划
	 * @param request
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryAllDetails")
	@ResponseBody
	public Object queryAllDetails(HttpServletRequest request,String batchCode,String name,String startDate,String endDate,Integer pageSize,Integer pageNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProducePlanDetailController:queryAllDetails] begin to query details by condictions.");
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
			Long farmId = (Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID);
			List<ProducePlanDetailVo> detailVos =  planDetailManager.queryAllDetailsPageList(farmId,batchCode,name, startDate, endDate, (pageNumber - 1) * pageSize, pageSize);
			for (ProducePlanDetailVo detailVo : detailVos){
				detailVo.setTaskDate(DateUtil.formatDate(detailVo.getTaskDate()));
			}
			map.put("detailVoList", detailVos);
			map.put("total", planDetailManager.queryAllDetailsTotal(farmId,name, startDate, endDate));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProducePlanDetailController:queryAllDetails] fail to query details by condictions.",e);
		}
		return map;
	}

	/**
	 * 发布任务时，查询员工和溯源任务类别
	 * @param request
	 * @return
	 */
	@RequestMapping("/getTaskEquipInfo")
	@ResponseBody
	public Object getTaskEquipInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProducePlanDetailController:getTaskEquipInfo] begin to query farm's employee and workType info.");
			List<EmployeeInfo> employeeList = employeeInfoManager.queryEmployeeInfoByFarmId((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID));
			map.put("employeeList", employeeList);
			List<FarmSourceType> workTypeList = sourceTypeManager.querySourceTypeList();
			map.put("workTypeList", workTypeList);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProducePlanDetailController:getTaskEquipInfo] fail to query farm's employee and workType info.",e);
		}
		return map;
	}
}
