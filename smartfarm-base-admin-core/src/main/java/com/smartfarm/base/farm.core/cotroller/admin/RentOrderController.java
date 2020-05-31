package com.smartfarm.base.farm.core.cotroller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.farm.core.entity.ProgressInfo;
import com.smartfarm.base.farm.core.entity.RentOrder;
import com.smartfarm.base.farm.core.entity.SeedDetail;
import com.smartfarm.base.farm.core.entity.ShippingInfo;
import com.smartfarm.base.farm.core.entity.vo.RentOrderVo;
import com.smartfarm.base.farm.core.manager.ProgressInfoManager;
import com.smartfarm.base.farm.core.manager.RentOrderManager;


@RequestMapping("rentOrder")
@Controller
public class RentOrderController {
	private Logger log = Logger.getLogger(RentOrderController.class);
	@Resource
	private RentOrderManager rentOrderManager;	
	@Resource
	private ProgressInfoManager progressInfoManager;
		
	/**
	 * 获得订单列表及查询
	 * @param request
	 * @param status
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("queryRentOrderPageList")
	@ResponseBody
	public Object queryRentOrderPageList(HttpServletRequest request,String orderNo, Short status, Integer pageSize, Integer pageNumber) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[RentOrderController:queryRentOrderPageList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + ",orderNo:"+orderNo+",status:"+status+"}.");
			List<RentOrderVo> rentOrderVoList = rentOrderManager.getPageList(orderNo, status, (pageNumber - 1) * pageSize, pageSize);
			map.put("rentOrderVoList", rentOrderVoList);		
			map.put("total", rentOrderManager.countPageList(orderNo, status));
			
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[RentOrderController:queryRentOrderPageList]false", e);
		}
		
		return map;
	}
	
	/**
	 * 获取订单详情
	 * @param request
	 * @param rentOrder
	 * @return
	 */
	@RequestMapping("getRentOrderDetail")
	@ResponseBody
	public Object getRentOrderDetail(HttpServletRequest request,RentOrder rentOrder) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[RentOrderController:getRentOrderById]{rentOrder.getId():" + rentOrder.getId() + "}.");			
			map.put("rentOrder",rentOrderManager.getRentOrder(rentOrder) );	
			ShippingInfo shippingInfo=new ShippingInfo();
			shippingInfo.setOrderId(rentOrder.getId());
			map.put("shipInfoList",rentOrderManager.getShippingByOrderId(shippingInfo));	
			log.info("[RentOrderController:getRentOrderById]{shippingInfo.getOrderId():" + shippingInfo.getOrderId()+ "}.");
			
			SeedDetail seedDetail=new SeedDetail();
			seedDetail.setOrderId(rentOrder.getId());
			map.put("seedDetailList",rentOrderManager.getSeedDatailByOrderId(seedDetail));	
			
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[RentOrderController:getRentOrderById]false", e);
		}
		
		return map;
	}
	
	/**
	 * 更新订单的预期到期时间
	 * @param request
	 * @param rentOrder
	 * @return
	 */
	@RequestMapping("editRentOrder")
	@ResponseBody
	public Object editRentOrder(HttpServletRequest request,RentOrder rentOrder) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[RentOrderController:editRentOrder]{rentOrder.getId():" + rentOrder.getId() +"rentOrder.getExpireTime():"+rentOrder.getExpireTime() +"}.");					
			rentOrderManager.editRentOrder(rentOrder);
;			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[RentOrderController:editRentOrder]false", e);
		}
		
		return map;
	}
	
	/**
	 * 更新种子的成熟时间
	 * @param request
	 * @return
	 */
	@RequestMapping("editSeedDetail")
	@ResponseBody
	public Object editSeedDetail(HttpServletRequest request,SeedDetail seedDetail) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[RentOrderController:editRentOrder]{rentOrder.getId():" + seedDetail.getId() +",rentOrder.getMatureTime():"+seedDetail.getMatureTime() +"}.");					
			rentOrderManager.editSeedDetail(seedDetail);
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[RentOrderController:editRentOrder]false", e);
		}
		
		return map;
	}
	
	/**
	 * 更新种子的状态
	 * @param request
	 * @return
	 */
	@RequestMapping("changeStatus")
	@ResponseBody
	public Object changeStatus(HttpServletRequest request,SeedDetail seedDetail) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[RentOrderController:changeStatus]{rentOrder.getId():" + seedDetail.getId() +",rentOrder.getMatureTime():"+seedDetail.getPlantStatus() +"}.");					
			if(seedDetail.getPlantStatus()==SeedDetail.UNPLANTED){
				seedDetail.setPlantStatus(SeedDetail.PLANTING);
			}else{
				seedDetail.setPlantStatus(SeedDetail.MATURE);
			}			
			rentOrderManager.editSeedDetail(seedDetail);
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[RentOrderController:changeStatus]false", e);
		}		
		return map;
	}
	
	/**
	 * 获得状态跟踪信息
	 * @param request
	 * @return
	 */
	@RequestMapping("getProgressInfoList")
	@ResponseBody
	public Object getProgressInfoList(HttpServletRequest request,ProgressInfo progressInfo) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[RentOrderController:getProgressInfoList]{rentOrder.getProjectId():" + progressInfo.getProjectId() +"}.");					
						
			map.put("progressInfoList",rentOrderManager.getProgressInfoList(progressInfo));
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[RentOrderController:getProgressInfoList]false", e);
		}		
		return map;
	}
	
	/**
	 * 添加状态信息
	 * @param request
	 * @param progressInfo
	 * @param files
	 * @return
	 */
	@RequestMapping("/addStatus")
	@ResponseBody
	public Object addProgressInfo(HttpServletRequest request,ProgressInfo progressInfo,
			@RequestParam(value = "files", required = false) MultipartFile[] files){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[CrowdFundingController:addProgressInfo]{MultipartFile:" + files.length );
        	log.info("[CrowdFundingController:addProgressInfo]add progressInfo.");
        	progressInfo.setAdminUserId((Long) SessionUtil.getAttribute(request, SessionUtil.ADMIN_ID));       	
        	rentOrderManager.insert(progressInfo, files);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CrowdFundingController:addCrowdFunding]false to add crowdFunding.", e);
		}
		return map;
	}
	
	/**
	 * 根据id删除状态信息
	 * @param id
	 * @return
	 */

	@RequestMapping("/deleteProgressInfoById")
	@ResponseBody
	public Object deleteProgressInfoById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
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
	 * 确认收货，若配送序号等于频次，则将订单状态改为已完成
	 * @param request
	 * @return
	 */
	@RequestMapping("changeShipStatus")
	@ResponseBody
	public Object changeShipStatus(HttpServletRequest request,ShippingInfo shippingInfo) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[RentOrderController:editShipInfo]{shippingInfo.id():" + shippingInfo+"}.");					
			shippingInfo.setStatus(ShippingInfo.RECEIVED);
			rentOrderManager.received(shippingInfo);		
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[RentOrderController:getProgressInfoList]false", e);
		}		
		return map;
	}
	
	/**
	 * 立即发货，若配送序号为1，则生成所有配送的时间
	 * @param request
	 * @param shippingInfo
	 * @return
	 */
	@RequestMapping("shippingNow")
	@ResponseBody
	public Object shippingNow(HttpServletRequest request,ShippingInfo shippingInfo) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[RentOrderController:shippingNow]{shippingInfo" + shippingInfo+"}.");					
			rentOrderManager.shippingNow(shippingInfo);	
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[RentOrderController:shippingNow]false", e);
		}		
		return map;
	}
	
	
	
	
	
	
}
