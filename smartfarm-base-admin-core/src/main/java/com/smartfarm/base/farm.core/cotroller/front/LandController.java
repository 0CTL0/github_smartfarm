package com.smartfarm.base.farm.core.cotroller.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.farm.core.entity.LandType;
import com.smartfarm.base.farm.core.entity.ProgressInfo;
import com.smartfarm.base.farm.core.entity.RentOrder;
import com.smartfarm.base.farm.core.entity.ShippingInfo;
import com.smartfarm.base.farm.core.entity.vo.LandInfoVo;
import com.smartfarm.base.farm.core.entity.vo.RentOrderDetailVo;
import com.smartfarm.base.farm.core.manager.LandInfoManager;
import com.smartfarm.base.farm.core.manager.RentOrderManager;
import com.smartfarm.base.farm.core.manager.SeedDetailManager;
import com.smartfarm.base.farm.core.manager.SeedInfoManager;
import com.smartfarm.base.farm.core.manager.ShipInfoManager;
import com.smartfarm.base.farm.core.manager.ShipStrategyManager;
import com.smartfarm.base.shop.core.manager.MemberAddressManager;

/**
 * 农场种植小程序接口
 * @author david
 *
 */
@Controller
@RequestMapping("/miniLand")
public class LandController {
	
	private Logger log = Logger.getLogger(LandController.class);
	
	@Resource
	private LandInfoManager landInfoManager;
	
	@Resource
	private SeedInfoManager seedInfoManager;
	
	@Resource
	private MemberAddressManager memberAddressManager;
	
	@Resource
	private RentOrderManager rentOrderManager;
	
	@Resource
	private ShipStrategyManager shipStrategyManager;
	
	@Resource
	private SeedDetailManager seedDetailManager;
	
	@Resource
	private ShipInfoManager shipInfoManager;
	
	/**
	 * 接口：获得可以租用土地的信息（状态为上架）
	 * @return
	 */
	@RequestMapping("/getAllLandInfoList")
	@ResponseBody
	public Object getAllLandInfoList(){
		Map<String,Object> map=new HashMap<String,Object>();
		try{			
			List<LandInfoVo> landInfoVoList=landInfoManager.getAllLandInfo();
			//以typeId为条件，划分为几个list,存入map中
			Map<Long,List<LandInfoVo>> landInfoMap=new HashMap<Long,List<LandInfoVo>>();			
			for(LandInfoVo landInfoVo : landInfoVoList){
				Long typeId=landInfoVo.getTypeId();
				if(map.containsKey(typeId)){
					landInfoMap.get(typeId).add(landInfoVo);
				}
				else{
					List<LandInfoVo> landList=new ArrayList<LandInfoVo>();
					landList.add(landInfoVo);
					landInfoMap.put(typeId, landList);
				}
			}
			map.put("landInfoList", landInfoMap);
			List<LandType> allTypes = landInfoManager.getAllLandType();
			map.put("landTypeList", allTypes);
			map.put("landInfoDetailList", landInfoManager.queryLangInfoWithTypeList(allTypes.get(0).getId()));
			map.put("success", true);					
		}
		catch(Exception e){
			map.put("success", false);
			log.error("[LandController:getLandInfoList]false!",e);
		}
		return map;
	}
	
	/**
	 * 根据名称查询土地详细列表
	 * @param name
	 * @return
	 */
	@RequestMapping("/queryMiNiLandInfoDetailListByName")
	@ResponseBody
	public Object queryMiNiLandInfoDetailListByName(String name){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[LandController:queryMiNiLandInfoDetailListByName]query landInfo list.");
			map.put("landInfoDetailList", landInfoManager.queryMiNiLandInfoDetailListByName(name));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[LandController:queryMiNiLandInfoDetailListByName]false to query landInfo list.", e);
		}
		return map;
	}
	
	/**
	 * 根据类型查询土地列表
	 * @param typeId
	 * @return
	 */
	@RequestMapping("/queryLangInfoWithTypeList")
	@ResponseBody
	public Object queryLangInfoWithTypeList(Long typeId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[LandController:queryLangInfoWithTypeList]query landInfo list.");
			map.put("landInfoDetailList", landInfoManager.queryLangInfoWithTypeList(typeId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[LandController:queryLangInfoWithTypeList]false to query landInfo list.", e);
		}
		return map;
	}
	
	
	/**
	 * 根据土地id查询该土地所关联的信息
	 * @param landId
	 * @return
	 */
	@RequestMapping("/queryLangInfoMessageById")
	@ResponseBody
	public Object queryLangInfoMessageById(Long landId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[LandController:queryLangInfoMessageById]query landInfo.");
			map.put("seedInfoList", landInfoManager.queryLangInfoMsgByLandId(landId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[LandController:queryLangInfoMessageById]false to query landInfo.", e);
		}
		return map;
	}
	
	/**
	 * 根据土地id查询种子列表
	 * @param landId
	 * @return
	 */
	@RequestMapping("/querySeedInfoByLandId")
	@ResponseBody
	public Object querySeedInfoByLandId(Long landId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[LandController:querySeedInfoByLandId]query seedInfo landId = " + landId);
			map.put("seedInfoList", seedInfoManager.querySeedInfoByLandId(landId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[LandController:querySeedInfoByLandId]false to query seedInfo.", e);
		}
		return map;
	}
	
	/**
	 * 根据土地id和地块信息查询
	 * @param landId
	 * @param areaId
	 * @return
	 */
	@RequestMapping("/queryLandInfoWithOrder")
	@ResponseBody
	public Object queryLandInfoWithOrder(Long landId,Long areaId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[LandController:queryLandInfoWithOrder]query landInfo landId = " + landId + "areaId = " + areaId);
			map.put("landInfoOrder", landInfoManager.queryLandInfoWithOrder(landId, areaId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[LandController:queryLandInfoWithOrder]false to query landInfo.", e);
		}
		return map;
	}
	
	
	/**
	 * 根据用户id获取收货地址列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryMemberAddressById")
	@ResponseBody
	public Object queryMemberAddressById(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[LandController:queryMemberAddressById]query memberAddress");
        	Long memberId = (Long) SessionUtil.getAttribute(request, SessionUtil.MEMBER_ID);
			log.info("memberId:"+memberId);
        	map.put("addressList", memberAddressManager.queryMemberAddressById(memberId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[LandController:queryMemberAddressById]false to query memberAddress.", e);
		}
		return map;
	}
	
	/**
	 * 插入订单
	 * @param request
	 * @param rentOrder
	 * @param seedDetails
	 * @return
	 */
	@RequestMapping("/insertRentOrder")
	@ResponseBody
	public Object insertRentOrder(HttpServletRequest request,RentOrder rentOrder,String seedDetails){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[LandController:insertRentOrder]insert rentOrder");
        	int count = rentOrderManager.insertRentOrder(request,rentOrder, seedDetails);
        	if(count < 1){
        		map.put("success", false);
        		return map;
        	}
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[LandController:insertRentOrder]false to insert rentOrder.", e);
		}
		return map;
	}
	
	/**
	 * 根据土地id获取配送周期信息
	 * @param landId
	 * @return
	 */
	@RequestMapping("/queryShipStrategyByLandId")
	@ResponseBody
	public Object queryShipStrategyByLandId(Long landId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[LandController:queryShipStrategyByLandId]query ShipStrategy list landId = " + landId);
			map.put("shipStrategyList", shipStrategyManager.queryShipStrategyByLandId(landId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[LandController:queryShipStrategyByLandId]false to query ShipStrategy list.", e);
		}
		return map;
	}
	
	/**
	 * 根据id得到种子信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/querySeedInfoById")
	@ResponseBody
	public Object querySeedInfoById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[LandController:querySeedInfoById]query seedInfo Id = " + id);
			map.put("seedInfo", seedInfoManager.getSeedInfo(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[LandController:querySeedInfoById]false to query seedInfo.", e);
		}
		return map;
	}
	
	
	/**
	 * 查询未支付的种植订单
	 * @param payStatus
	 * @return
	 */
	@RequestMapping("/queryRentOrderUnPayList")
	@ResponseBody
	public Object queryRentOrderUnPayList(short payStatus,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[LandController:queryRentOrderUnPayList]query rendOrder list payStatus = " + payStatus);
			map.put("rentOrderList", rentOrderManager.queryRentOrderUnPayList(payStatus,request));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[LandController:queryRentOrderUnPayList]false to query rendOrder list.", e);
		}
		return map;
	}
	
	
	/**
	 * 根据状态查询种植列表
	 * @param plantStatus
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryRentOrderPlantList")
	@ResponseBody
	public Object queryRentOrderPlantList(short plantStatus,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[LandController:queryRentOrderPlantList]query rendOrder list plantStatus = " + plantStatus);
			map.put("rentOrderList", rentOrderManager.queryRentOrderPlantList(plantStatus, request));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[LandController:queryRentOrderPlantList]false to query rendOrder list.", e);
		}
		return map;
	}
	
	
	/**
	 * 根据订单id获取订单详细
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/queryRentOrderStatusById")
	@ResponseBody
	public Object queryRentOrderStatusById(Long orderId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[LandController:queryRentOrderStatusById]query rentOrder orderId = " + orderId);
        	RentOrderDetailVo detailVo = rentOrderManager.queryRentOrderStatusById(orderId);
        	//存进第一次发货消息
        	if(detailVo.getUnDeliverCount() == null){
        		detailVo.setUnDeliverCount(0);
        	}
        	if(detailVo.getLeastShipTimes() > detailVo.getUnDeliverCount()){
        		ShippingInfo shippingInfo = shipInfoManager.queryShipInfoByOrderId(detailVo.getId()).get(0);
        		map.put("shipInfo", shippingInfo);
        	}
			map.put("rentOrder", detailVo);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[LandController:queryRentOrderStatusById]false to query rentOrder.", e);
		}
		return map;
	}
	
	/**
	 * 取消租借订单
	 * @param rentOrder
	 * @return
	 */
	@RequestMapping("/cancelRentOrder")
	@ResponseBody
	public Object cancelRentOrder(RentOrder rentOrder){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int count = rentOrderManager.cancelRentOrder(rentOrder);
			if(count < 1){
				map.put("success", false);
				return map;
			}
        	log.info("[LandController:cancelRentOrder]cancel rentOrder.");
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[LandController:cancelRentOrder]false to cancel rentOrder.", e);
		}
		return map;
	}
	
	/**
	 * 根据种子id查询种植的种子信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/querySeedDetailById")
	@ResponseBody
	public Object querySeedDetailById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[LandController:querySeedDetailById]query seedDetail detailId = " + id);
			map.put("seedInfo", seedDetailManager.querySeedDetailById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[LandController:querySeedDetailById]false to query seedDetail.", e);
		}
		return map;
	}
	
	/**
	 * 查看种子种植进展
	 * @param request
	 * @param progressInfo
	 * @return
	 */
	@RequestMapping("getProgressInfoList")
	@ResponseBody
	public Object getProgressInfoList(HttpServletRequest request,ProgressInfo progressInfo) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[LandController:getProgressInfoList]{rentOrder.getProjectId():" + progressInfo.getProjectId() +"}.");					
						
			map.put("progressInfoList",rentOrderManager.getProgressInfoList(progressInfo));
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[LandController:getProgressInfoList]false", e);
		}		
		return map;
	}
	
	/**
	 * 根据订单id查询配送物流
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryShipInfoByOrderId")
	@ResponseBody
	public Object queryShipInfoByOrderId(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[LandController:queryShipInfoByOrderId]query shipInfo orderId = " + id);
			map.put("shipInfoList", shipInfoManager.queryShipInfoByOrderId(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[LandController:queryShipInfoByOrderId]false to query shipInfo.", e);
		}
		return map;
	}
	
	/**
	 * 根据id获取物流详细
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryShipDetailById")
	@ResponseBody
	public Object queryShipDetailById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[LandController:queryShipDetailById]query shipInfo id = " + id);
			map.put("shipInfo", shipInfoManager.queryShipDetailById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[LandController:queryShipDetailById]false to query shipInfo.", e);
		}
		return map;
	}
	
}
