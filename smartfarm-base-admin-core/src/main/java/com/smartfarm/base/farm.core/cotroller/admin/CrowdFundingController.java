package com.smartfarm.base.farm.core.cotroller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.util.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.farm.core.entity.Crowdfunding;
import com.smartfarm.base.farm.core.entity.CrowdfundingOrder;
import com.smartfarm.base.farm.core.entity.ProgressInfo;
import com.smartfarm.base.farm.core.manager.CrowdFundingManager;
import com.smartfarm.base.farm.core.manager.CrowdFundingOrderManager;
import com.smartfarm.base.farm.core.manager.GradeManager;
import com.smartfarm.base.farm.core.manager.ProgressInfoManager;


/**
 * 众筹层
 * @author lyq
 *
 */
@Controller
@RequestMapping("/crowdFunding")
public class CrowdFundingController {
	
	private Logger log = Logger.getLogger(CrowdFundingController.class);
	
	@Resource
	private CrowdFundingManager crowdFundingMapper;
	
	@Resource
	private GradeManager gradeManager;
	
	@Resource
	private CrowdFundingOrderManager crowdFundingOrderManager;
	
	@Resource
	private ProgressInfoManager progressInfoManager;
	
	/**
	 * 分页查询所有众筹项目
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/quryCrowdFundingList")
	@ResponseBody
	public Object quryCrowdFundingList(HttpServletRequest request,Integer pageSize, Integer pageNumber) {
		Map<String, Object> map = new HashMap<String, Object>(16);
		try {
        	log.info("[CrowdFundingController:qurycrowdFundingList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}query crowdFunding info for page.");
			map.put("crowdFundingList", crowdFundingMapper.quryCrowdFundingList((pageNumber - 1) * pageSize, pageSize));
			map.put("total", crowdFundingMapper.queryCountCrowdFunding());
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CrowdFundingController:qurycrowdFundingList]false to query crowdFunding list.", e);
		}
		return map;
	}
	
	/**
	 * 添加众筹项目
	 * @param crowdfunding
	 * @param files
	 * @param cover
	 * @param gearListJson
	 * @return
	 */
	@RequestMapping("/addCrowdFunding")
	@ResponseBody
	public Object addCrowdFunding(HttpServletRequest request,Crowdfunding crowdfunding,
			@RequestParam(value = "files", required = false) MultipartFile[] files,
			@RequestParam(value = "covers", required = false) MultipartFile cover,
			@RequestParam(value = "gearListJson", required = false)String gearListJson){
		Map<String, Object> map = new HashMap<String, Object>(16);
		try {
			Long bussinessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			log.info("[CrowdFundingController:addCrowdFunding]{MultipartFile:" + files.length );
        	log.info("[CrowdFundingController:addCrowdFunding]add crowdFunding info.");
        	crowdfunding.setFarmId(bussinessId);
        	crowdFundingMapper.insert(crowdfunding, cover, files,gearListJson);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CrowdFundingController:addCrowdFunding]false to add crowdFunding.", e);
		}
		return map;
	}
	
	/**
	 * 根据id查询众筹信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryCrowdFundingById")
	@ResponseBody
	public Object queryCrowdFundingById(Long id){
		Map<String, Object> map = new HashMap<String, Object>(16);
		try {
        	log.info("[CrowdFundingController:queryCrowdFundingById]query crowdFunding info.");
        	map.put("crowdFundingInfo", crowdFundingMapper.queryCrowdFundingById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CrowdFundingController:queryCrowdFundingById]false to query crowdFunding.", e);
		}
		return map;
	}
	
	/**
	 * 根据id删除档位
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteGroupById")
	@ResponseBody
	public Object deleteGroupById(Long id){
		Map<String, Object> map = new HashMap<String, Object>(16);
		try {
        	log.info("[CrowdFundingController:deleteGroupById]detele Grade info.");
        	gradeManager.deleteGroupById(id);
        		map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CrowdFundingController:deleteGroupById]false to detele Grade.", e);
		}
		return map;
	}
	
	/**
	 * 根据id编辑众筹信息
	 * @param crowdfunding
	 * @param files
	 * @param cover
	 * @param gearListJson
	 * @return
	 */
	@RequestMapping("/editCrowdFundingById")
	@ResponseBody
	public Object editCrowdFundingById(Crowdfunding crowdfunding,
			@RequestParam(value = "editFiles", required = false) MultipartFile[] files,
			@RequestParam(value = "editCovers", required = false) MultipartFile cover,
			@RequestParam(value = "editGearListJson", required = false)String gearListJson){
		Map<String, Object> map = new HashMap<String, Object>(16);
		try {
			log.info("[CrowdFundingController:editCrowdFundingById]{MultipartFile:" + files.length );
        	log.info("[CrowdFundingController:editCrowdFundingById]edit crowdFunding info.");
        	crowdFundingMapper.editCrowdFundingById(crowdfunding, cover, files,gearListJson);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CrowdFundingController:editCrowdFundingById]false to edit crowdFunding.", e);
		}
		return map;
	}
	
	/**
	 * 查詢所有众筹订单列表
	 * @param request
	 * @param orderNo
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryOrderList")
	@ResponseBody
	public Object queryOrderList(HttpServletRequest request,Short shipStatus,Short status,String orderNo,Integer pageSize, Integer pageNumber) {
		Map<String, Object> map = new HashMap<String, Object>(16);
		try {
        	log.info("[CrowdFundingController:queryOrderList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "orderNo:"+orderNo+"}query crowdFundingOrder info for page.");
			map.put("crowdFundingOrderList", crowdFundingOrderManager.queryOrderList(shipStatus,status,orderNo,(pageNumber - 1) * pageSize, pageSize));
			map.put("total", crowdFundingOrderManager.queryCountOrderList(shipStatus,status,orderNo));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CrowdFundingController:queryOrderList]false to query crowdFundingOrder list.", e);
		}
		return map;
	}
	
	/**
	 * 查询订单详细
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryOrderDetailById")
	@ResponseBody
	public Object queryOrderDetailById(Long id){
		Map<String, Object> map = new HashMap<String, Object>(16);
		try {
        	log.info("[CrowdFundingController:queryOrderDetailById]query order info.");
        	map.put("orderInfo", crowdFundingOrderManager.queryOrderDetailById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CrowdFundingController:queryOrderDetailById]false to query order.", e);
		}
		return map;
	}
	
	/**
	 * 订单发货
	 * @param crowdfundingOrder
	 * @return
	 */
	@RequestMapping("/updateShipMsgById")
	@ResponseBody
	public Object updateShipMsgById(CrowdfundingOrder crowdfundingOrder){
		Map<String, Object> map = new HashMap<String, Object>(16);
		try {
        	log.info("[CrowdFundingController:updateShipMsgById]update order info.");
        	if(crowdfundingOrder.getShipStatus() == 2){
    			map.put("success", true);
        		crowdFundingOrderManager.updateOrderOk(crowdfundingOrder);
        		return map;
        	}
        	crowdFundingOrderManager.updateDetailById(crowdfundingOrder);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CrowdFundingController:updateShipMsgById]false to update order info.", e);
		}
		return map;
	}
	
	/**
	 * 订单已完成
	 * @param crowdfundingOrder
	 * @return
	 */
	@RequestMapping("/updateShipMsgOKById")
	@ResponseBody
	public Object updateShipMsgOKById(CrowdfundingOrder crowdfundingOrder){
		Map<String, Object> map = new HashMap<String, Object>(16);
		try {
        	log.info("[CrowdFundingController:updateShipMsgOKById]update order info.");
        	crowdFundingOrderManager.updateOrderOk(crowdfundingOrder);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CrowdFundingController:updateShipMsgOKById]false to update order info.", e);
		}
		return map;
	}
	
	/**
	 * 分页查询众筹列表
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryCrowOrderList")
	@ResponseBody
	public Object queryCrowOrderList(Long id,Integer pageSize, Integer pageNumber){
		Map<String, Object> map = new HashMap<String, Object>(16);
		try {
        	log.info("[CrowdFundingController:queryCrowOrderList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber +"}query crowdFundingOrder info for page.");
			map.put("crowdOrderList", crowdFundingOrderManager.queryCrowOrderList(id,(pageNumber - 1) * pageSize, pageSize));
			map.put("total", crowdFundingOrderManager.queryCountCrowdList(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CrowdFundingController:queryCrowOrderList]false to query crowdFundingOrder list.", e);
		}
		return map;
	}
	
	/**
	 * 根据orderid进行分红
	 * @param crowdfundingOrder
	 * @return
	 */
	@RequestMapping("/updateBonusById")
	@ResponseBody
	public Object updateBonusById(CrowdfundingOrder crowdfundingOrder){
		Map<String, Object> map = new HashMap<String, Object>(16);
		try {
        	log.info("[CrowdFundingController:updateBonusById]update order info.");
        	if(crowdFundingOrderManager.updateBonusById(crowdfundingOrder)>0){
    			map.put("success", true);
        	}
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CrowdFundingController:updateBonusById]false to update order info.", e);
		}
		return map;
	}
	
	/**
	 * 一键分红
	 * @return
	 */
	@RequestMapping("/updateAllBonus")
	@ResponseBody
	public Object updateAllBonus(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[CrowdFundingController:updateAllBonus]update allorder info.");
        	if(crowdFundingOrderManager.updateAllBonus()>0){
    			map.put("success", true);
        	}
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CrowdFundingController:updateBonusById]false to update allorder info.", e);
		}
		return map;
	}
	
	/**
	 * 根据id查看项目进展
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryAllOrderProgressById")
	@ResponseBody
	public Object queryAllOrderProgressById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[CrowdFundingController:queryOrderList]query queryAllOrderProgressById info for page.");
			map.put("progressInfoList", progressInfoManager.queryAllOrderProgress(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CrowdFundingController:queryOrderList]false to query queryAllOrderProgressById list.", e);
		}
		return map;
	}
	
	/**
	 * 添加項目進度
	 * @param request
	 * @param progressInfo
	 * @param files
	 * @return
	 */
	@RequestMapping("/addProgressInfo")
	@ResponseBody
	public Object addProgressInfo(HttpServletRequest request,ProgressInfo progressInfo,
			@RequestParam(value = "files", required = false) MultipartFile[] files){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[CrowdFundingController:addProgressInfo]{MultipartFile:" + files.length );
        	log.info("[CrowdFundingController:addProgressInfo]add progressInfo.");
        	progressInfo.setAdminUserId((Long) SessionUtil.getAttribute(request, SessionUtil.ADMIN_ID));
        	progressInfoManager.insert(progressInfo, files);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CrowdFundingController:addCrowdFunding]false to add crowdFunding.", e);
		}
		return map;
	}
	/**
	 * 根据id删除项目进度
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteProgressInfoById")
	@ResponseBody
	public Object deleteProgressInfoById(Long id){
		Map<String, Object> map = new HashMap<String, Object>(16);
		try {
        	log.info("[CrowdFundingController:deleteProgressInfoById]delete progressInfo.");
        	if(progressInfoManager.deleteProgressInfoById(id)>0){
    			map.put("success", true);
        	}
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CrowdFundingController:deleteProgressInfoById]false to delete progressInfo.", e);
		}
		return map;
	}
	/**
	 * 退款
	 * @param crowdfundingOrder
	 * @return
	 */
	@RequestMapping("/updateOrderRefundById")
	@ResponseBody
	public Object updateOrderRefundById(CrowdfundingOrder crowdfundingOrder){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[CrowdFundingController:updateOrderRefundById]update order info.");
        	if(crowdFundingOrderManager.updateById(crowdfundingOrder)>0){
    			map.put("success", true);
        	}
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CrowdFundingController:updateOrderRefundById]false to update order info.", e);
		}
		return map;
	}
}
