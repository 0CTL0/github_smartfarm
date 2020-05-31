package com.smartfarm.base.shop.core.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.shop.core.entity.CouponBatchSend;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.shop.core.entity.CouponBatch;
import com.smartfarm.base.shop.core.entity.CouponInfo;
import com.smartfarm.base.shop.core.manager.CouponBatchManager;
import com.smartfarm.base.shop.core.manager.CouponBatchRuleManager;
import com.smartfarm.base.shop.core.manager.CouponInfoManager;
import com.smartfarm.base.shop.core.manager.MemberInfoManager;
import com.smartfarm.base.shop.core.manager.ProductCategoryManager;
import com.smartfarm.base.shop.core.manager.ProductSkuManager;

/**
 * 优惠券层
 * @author lyq
 *
 */
@Controller
@RequestMapping("/coupon")
public class CouponController {
	
	private Logger log = Logger.getLogger(CouponController.class);
	
	@Resource
	private CouponBatchManager couponBatchManager;
	
	@Resource
	private CouponInfoManager couponInfoManager;
	
	@Resource
	private ProductCategoryManager productCategoryManager;
	
	@Resource
	private ProductSkuManager productSkuManager;
	
	@Resource
	private CouponBatchRuleManager couponBatchRuleManager;
	
	@Resource
	private MemberInfoManager memberInfoManager;
	/**
	 * 查询优惠券列表
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryCouponBatchList")
	@ResponseBody
	public Object queryCouponBatchList(HttpServletRequest request, Integer pageSize, Integer pageNumber,Short type) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
        	log.info("[CouponController:queryCouponBatchList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}query couponBatch info for page.");
			Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("couponBatchList", couponBatchManager.queryCouponBatchList((pageNumber - 1) * pageSize, pageSize,type,businessId));
			map.put("total", couponBatchManager.queryCountCouponBatchList(type,businessId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:queryCouponBatchList]false to query couponBatch list.", e);
		}
		return map;
	}
	
	/**
	 * 新增批次
	 * @param couponBatch
	 * @return
	 */
	@RequestMapping("/addCouponBatch")
	@ResponseBody
	public Object addCouponBatch(CouponBatch couponBatch,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[CouponController:addCouponBatch]add couponBatch.");
        	Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
        	couponBatch.setBusinessId(businessId);
        	if(couponBatchManager.insert(couponBatch)<0){
        		map.put("success", false);
        		return map;
        	}
        	map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:addCouponBatch]false to add couponBatch.", e);
		}
		return map;
	}
	
	/**
	 * 生成优惠券
	 * @param request
	 * @param id
	 * @param num
	 * @return
	 */
	@RequestMapping("/addCouponInfoList")
	@ResponseBody
	public Object addCouponInfoList(HttpServletRequest request,Long id,Integer num){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[CouponController:addCouponInfoList]add couponInfoList.");
        	if(couponInfoManager.insertCouponInfoList(id, num) != num){
        		map.put("success", false);
        		return map;
        	}
        	map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:addCouponInfoList]false to add couponInfoList.", e);
		}
		return map;
	}
	
	/**
	 * 查询优惠券信息列表
	 * @param request
	 * @param status
	 * @param nickName
	 * @param couponName
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryCouponInfoList")
	@ResponseBody
	public Object queryCouponInfoList(HttpServletRequest request,Short status,String nickName,String couponName, Integer pageSize, Integer pageNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
        	log.info("[CouponController:queryCouponInfoList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}query couponInfo for page.");
        	Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("couponInfoList", couponInfoManager.queryCouponInfoList(status, nickName, couponName, (pageNumber - 1) * pageSize,
					pageSize, businessId));
			map.put("total", couponInfoManager.queryCountCouponInfoList(status, nickName, couponName, businessId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:queryCouponInfoList]false to query couponInfo list.", e);
		}
		return map;
	}
	
	/**
	 * 查询所有产品分类
	 * @return
	 */
	@RequestMapping("/queryAllProductCategory")
	@ResponseBody
	public Object queryAllProductCategory(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[CouponController:queryAllProductCategory]query ProductCategory for page.");
        	Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("productCategoryList", productCategoryManager.queryAllProductCategoryList(businessId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:queryAllProductCategory]false to query ProductCategory list.", e);
		}
		return map;
	}
	
	/**
	 * 查询详细规格参数
	 * @param id
	 * @return
	 */
	@RequestMapping("/querySkuDetailsById")
	@ResponseBody
	public Object querySkuDetailsById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
        	log.info("[CouponController:querySkuDetailsById]query productSku for page.");
			
			map.put("productSkuList", productSkuManager.querySkuDetailsById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:querySkuDetailsById]false to query productSku list.", e);
		}
		return map;
	}
	/**
	 * 添加规则
	 * @param couponBatchId
	 * @param ruleType
	 * @param productSkuListJson
	 * @return
	 */
	@RequestMapping("/addBatchRule")
	@ResponseBody
	public Object addBatchRule(Long couponBatchId,Short ruleType,String productSkuListJson){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
        	log.info("[CouponController:addBatchRule]add batchRule for page.");
			couponBatchRuleManager.add(couponBatchId, ruleType, productSkuListJson);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:addBatchRule]false to add batchRule list.", e);
		}
		return map;
	}
	/**&
	 * 添加匹配所有规则
	 * @param couponBatchId
	 * @param ruleType
	 * @return
	 */
	@RequestMapping("/addAllBatchRules")
	@ResponseBody
	public Object addAllBatchRules(Long couponBatchId,Short ruleType){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
        	log.info("[CouponController:addAllBatchRules]{couponBatchId:" + couponBatchId + ","
        			+ ",ruleType:" + ruleType + "}add allBatchRule for page.");
			couponBatchRuleManager.addAllBatchRules(couponBatchId, ruleType);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:addAllBatchRules]false to add allBatchRule list.", e);
		}
		return map;
	}
	/**
	 * 查询产品规格
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryProductSkuById")
	@ResponseBody
	public Object queryProductSkuById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
        	log.info("[CouponController:queryProductSkuById]query productSku for page.");
			
			map.put("skusList", productSkuManager.queryProductSkuById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:queryProductSkuById]false to query productSku list.", e);
		}
		return map;
	}
	
	/**
	 * 根据id查询优惠券批次
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryCouponBatchById")
	@ResponseBody
	public Object queryCouponBatchById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
        	log.info("[CouponController:queryCouponBatchById]query couponBatch ById.");
			
			map.put("batchInfo", couponBatchManager.queryDetailBatchById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:queryCouponBatchById]false to query couponBatch ById.", e);
		}
		return map;
	}
	
	/**
	 * 根据id更新优惠券批次
	 * @param couponBatch
	 * @return
	 */
	@RequestMapping("/updateCouponBatch")
	@ResponseBody
	public Object updateCouponBatch(CouponBatch couponBatch){
		log.info("id==========================="+couponBatch.getDisplayName());
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
        	log.info("[CouponController:updateCouponBatch]update couponBatch ById.");
			couponBatchManager.updateCouponBatch(couponBatch);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:updateCouponBatch]false to update couponBatch ById.", e);
		}
		return map;
	}
	
	/**
	 * 分配优惠券
	 * @param couponId
	 * @param memberId
	 * @return
	 */
	@RequestMapping("/updateCouponForUser")
	@ResponseBody
	public Object updateCouponForUser(Long couponId, Long memberId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[CouponController:updateCouponForUser]update coupon for user.");
			CouponInfo couponInfo = new CouponInfo();
			couponInfo.setId(couponId);
			couponInfo.setMemberId(memberId);
			couponInfo.setStatus((short)1);
			couponInfoManager.updateCouponInfo(couponInfo);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:updateCouponForUser]false to update coupon for user.", e);
		}
		return map;
	}
	
	@RequestMapping("/queryAllMember")
	@ResponseBody
	public Object queryAllMember(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[CouponController:queryAllMember]query all member.");
        	Long farmId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
        	map.put("memberList", memberInfoManager.queryByFarmId(farmId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:queryAllMember]false to query all member.", e);
		}
		return map;
	}

	/**
	 * 查询优惠券信息列表
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryCouponSendList")
	@ResponseBody
	public Object queryCouponSendList(HttpServletRequest request,Integer pageSize, Integer pageNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			log.info("[CouponController:queryCouponSendList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}query coupon send for page.");
			Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("couponInfoList", couponInfoManager.queryCouponSendPage(businessId, (pageNumber - 1) * pageSize,
					pageSize));
			map.put("total", couponInfoManager.countCouponSendPage(businessId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:queryCouponSendList]false to query coupon send list.", e);
		}
		return map;
	}

	/**
	 * 新增
	 * @param couponBatchSend
	 * @return
	 */
	@RequestMapping("/addCouponSend")
	@ResponseBody
	public Object addCouponSend( CouponBatchSend couponBatchSend){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[CouponController:addCouponSend]add coupon send.");
			CouponBatchSend couponBatchSendDb = couponInfoManager.querySendByBatchId(couponBatchSend.getCouponBatchId());
			if(couponBatchSendDb != null) {
				map.put("success", true);
				map.put("hasUsed", true);
				return map;
			}
			map.put("hasUsed", false);
			couponBatchSend.setRemainNum(couponBatchSend.getTotalNum());
			couponBatchSend.setStatus((short)1);
			couponInfoManager.insertCouponSend(couponBatchSend);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:addCouponSend]false to add coupon send.", e);
		}
		return map;
	}

	/**
	 * 修改状态
	 * @return
	 */
	@RequestMapping("/updateSendStatus")
	@ResponseBody
	public Object updateSendStatus( Long id, Short status){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[CouponController:updateSendStatus]update coupon send.");
			CouponBatchSend couponBatchSend = new CouponBatchSend();
			couponBatchSend.setId(id);
			couponBatchSend.setStatus(status);
			couponInfoManager.updateCouponSendById(couponBatchSend);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:updateSendStatus]false to update coupon send.", e);
		}
		return map;
	}

	/**
	 * 修改
	 * @return
	 */
	@RequestMapping("/updateSend")
	@ResponseBody
	public Object updateSend(CouponBatchSend couponBatchSend){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[CouponController:updateSend]update coupon send.");

			couponInfoManager.updateCouponSendById(couponBatchSend);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:updateSend]false to update coupon send.", e);
		}
		return map;
	}

	/**
	 * 查询所有优惠券批次
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAllBatch")
	@ResponseBody
	public Object queryAllBatch(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[CouponController:queryAllBatch]query all coupon batch.");
			Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("list", couponInfoManager.getPrizes(businessId));

			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CouponController:queryAllBatch]false to query all coupon batch.", e);
		}
		return map;
	}
}
