package com.smartfarm.base.farm.core.cotroller.front;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.farm.core.entity.FarmBazaarOrder;
import com.smartfarm.base.farm.core.entity.FarmBazaarProduct;
import com.smartfarm.base.farm.core.entity.FarmDelegateExpress;
import com.smartfarm.base.farm.core.entity.FarmDelegateOrder;
import com.smartfarm.base.farm.core.entity.FarmDelegatePlant;
import com.smartfarm.base.farm.core.entity.FarmMemberCrops;
import com.smartfarm.base.farm.core.entity.FarmRentLandInfo;
import com.smartfarm.base.farm.core.entity.SettingInfo;
import com.smartfarm.base.farm.core.entity.WorkingLog;
import com.smartfarm.base.farm.core.entity.vo.FarmMemberCropsVo;
import com.smartfarm.base.farm.core.manager.SettingInfoManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.NumberUtil;
import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.util.UploadUtil;
import com.smartfarm.base.util.WeixinPayUtil;
import com.smartfarm.base.util.WeixinUtil;
import com.smartfarm.base.farm.core.manager.FarmDelegateOrderManager;
import com.smartfarm.base.farm.core.manager.FarmMemberCropsManager;
import com.smartfarm.base.farm.core.manager.WateringRecordManager;
import com.smartfarm.base.shop.core.entity.AccessToken;
import com.smartfarm.base.shop.core.entity.MemberInfo;
import com.smartfarm.base.shop.core.manager.AccessTokenManager;
import com.smartfarm.base.shop.core.manager.MemberInfoManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月09日 13:44:17
 * @version 1.0
 */
@RequestMapping("/delegate")
@Controller
public class DelegateController {

	private Logger log = Logger.getLogger(DelegateController.class);
	
	@Resource
	private FarmDelegateOrderManager delegateOrderManager;
	@Resource
	private FarmMemberCropsManager memberCropsManager;
	@Resource
	private MemberInfoManager memberInfoManager;
	@Resource
	private WateringRecordManager wateringRecordManager;
	@Resource
	private AccessTokenManager accessTokenManager;
	@Resource
	private SettingInfoManager settingInfoManager;
	
	
	@RequestMapping("/planting")
	@ResponseBody
	public Object plantingDelegate(HttpServletRequest request,Integer isDelegate,String openId,Long businessId,FarmDelegateOrder delegateOrder,FarmDelegatePlant delegatePlant) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[DelegateController:plantingDelegate] begin to deal with planting delegate.");
			log.info("openId:"+openId);
			log.info("businessId:"+businessId);
			MemberInfo memberInfo = memberInfoManager.queryByOpenId(openId, businessId);
			SettingInfo delegateType = settingInfoManager.queryByWorkType(businessId,"Planting");
			delegateOrder.setIntegral(Long.valueOf(delegateType.getParamValue()));
			boolean flag = true;
			if (isDelegate==1) {
				if (Long.valueOf(delegateType.getParamValue()) > memberInfo.getPoint()) {
					flag = false;
					map.put("errorMsg", "积分不足！");
				}else {
					delegateOrderManager.addPlantDelegateRecord(delegateOrder, delegatePlant,memberInfo);
				}
			}
			map.put("success", flag);
			
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:plantingDelegate] fail to deal with planting delegate.",e);
		}
		return map;
	}

	/**
	 * 浇水操作
	 * @param request
	 * @param landId
	 * @param operator
	 * @param openId
	 * @return
	 */
	@RequestMapping("/watering")
	@ResponseBody
	public Object wateringOperation(HttpServletRequest request,Long landId,Long operator,String openId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[DelegateController:watering] begin to deal with watering operation.");
			log.info("[DelegateController:watering] { :" + "}.");
			Long farmId = (Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID);
			FarmMemberCrops memberCrops = memberCropsManager.queryCropsByLandId(landId);
			if (memberCrops==null ||memberCrops.getStatus()==2) {
				map.put("errMsg", "尚未种植！");
				map.put("success", false);
			}else {
				int wateringTimes = wateringRecordManager.queryWateringTimes(landId, DateUtil.formatCurrentDate());
				SettingInfo WaterTimes = settingInfoManager.queryByWorkType(farmId,"WaterTimes");
				if(WaterTimes==null){
					map.put("errMsg", "请先设置浇水参数！");
					map.put("success", false);
				}
				else{
					if (wateringTimes<Integer.parseInt(WaterTimes.getParamValue())) {
						MemberInfo memberInfo = memberInfoManager.queryByOpenId(openId, farmId);
						SettingInfo delegateType = settingInfoManager.queryByWorkType(farmId,"Watering");
						if (Long.valueOf(delegateType.getParamValue()) > memberInfo.getPoint()) {
							map.put("errMsg", "积分不足！");
							map.put("success", false);
						}else {
							SettingInfo waterDuration = settingInfoManager.queryByWorkType(farmId,"WaterDuration");
							String reString = delegateOrderManager.performWatering(landId, operator,Integer.parseInt(waterDuration.getParamValue())*60);
							log.info("[DelegateController:watering] { reString:" + reString + "}.");
							map.put("success", true);
						}
					}else {
						map.put("errMsg", "次数达上限！");
						map.put("success", false);
					}
				}
			}
		} catch (Exception e) {
			map.put("errMsg", "浇水失败！");
			map.put("success", false);
			log.error("[DelegateController:watering] fail to deal with watering operation.",e);
		}
		return map;
	}
	
	/**
	 * 施肥任务委托
	 * @param request
	 * @param delegateOrder
	 * @param openId
	 * @return
	 */
	@RequestMapping("/fertilizing")
	@ResponseBody
	public Object fertilizingDelegate(HttpServletRequest request,Long businessId,FarmDelegateOrder delegateOrder,String openId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[DelegateController:fertilizingDelegate] begin to deal with fertilizing delegate.");
			log.info("[DelegateController:fertilizingDelegate] { :" + "}.");
			FarmMemberCrops memberCrops = memberCropsManager.queryCropsByLandId(delegateOrder.getMemberLandId());
			if (memberCrops==null ||memberCrops.getStatus()==2) {
				map.put("errMsg", "当前土地未种植，无法进行施肥！");
				map.put("success", false);
			}else {
				MemberInfo memberInfo = memberInfoManager.queryByOpenId(openId, businessId);
				SettingInfo delegateType = settingInfoManager.queryByWorkType((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID),"Fertilizing");
				delegateOrder.setIntegral(Long.valueOf(delegateType.getParamValue()));
				if (Long.valueOf(delegateType.getParamValue()) > memberInfo.getPoint()) {
					map.put("errMsg", "积分不足！");
					map.put("success", false);
				}else {
					delegateOrder.setWorkType("Fertilizing");
					WorkingLog wlog = new WorkingLog();
					wlog.setContent("委托农场施肥，消耗"+delegateOrder.getIntegral()+"积分");
					delegateOrderManager.addDelegateOrder(delegateOrder, memberInfo,wlog);
					map.put("success", true);
				}
			}
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:fertilizingDelegate] fail to deal with fertilizing delegate.",e);
		}
		return map;
	}
	
	/**
	 * 采摘委托
	 * @param request
	 * @param openId
	 * @param delegateOrder
	 * @return
	 */
	@RequestMapping("/picking")
	@ResponseBody
	public Object pickingDelegate(HttpServletRequest request,Long businessId,String openId,FarmDelegateOrder delegateOrder) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[DelegateController:pickingDelegate] begin to deal with picking delegate.");
			log.info("[DelegateController:pickingDelegate] { :" + "}.");
			FarmMemberCrops memberCrops = memberCropsManager.queryCropsByLandId(delegateOrder.getMemberLandId());
			if (memberCrops==null ||memberCrops.getStatus()==2) {
				map.put("errMsg", "当前土地未种植，无法进行采摘！");
				map.put("success", false);
			}else {
				MemberInfo memberInfo = memberInfoManager.queryByOpenId(openId, businessId);
				SettingInfo delegateType = settingInfoManager.queryByWorkType((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID),"Picking");
				delegateOrder.setIntegral(Long.valueOf(delegateType.getParamValue()));
				if (Long.valueOf(delegateType.getParamValue()) > memberInfo.getPoint()) {
					map.put("errMsg", "积分不足！");
					map.put("success", false);
				}else {
					delegateOrder.setWorkType("Picking");
					WorkingLog wlog = new WorkingLog();
					wlog.setContent("委托农场采摘，消耗"+delegateOrder.getIntegral()+"积分");
					delegateOrderManager.addDelegateOrder(delegateOrder, memberInfo,wlog);
					map.put("success", true);
				}
			}
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:pickingDelegate] fail to deal with picking delegate.",e);
		}
		return map;
	}
	
	/**
	 * 委托邮寄
	 * @param request
	 * @param openId
	 * @param delegateOrder
	 * @param delegateExpress
	 * @return
	 */
	@RequestMapping("/postal")
	@ResponseBody
	public Object postalDelegate(HttpServletRequest request,Long businessId,String openId,FarmDelegateOrder delegateOrder,FarmDelegateExpress delegateExpress) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[DelegateController:postalDelegate] begin to deal with postal delegate.");
			log.info("[DelegateController:postalDelegate] { :" + "}.");
			FarmMemberCrops memberCrops = memberCropsManager.queryCropsByLandId(delegateOrder.getMemberLandId());
			if (memberCrops==null ||memberCrops.getStatus()==1) {
				map.put("errMsg", "当前作物未采摘，无法进行邮寄！");
				map.put("success", false);
			}else {
				MemberInfo memberInfo = memberInfoManager.queryByOpenId(openId, businessId);
				SettingInfo delegateType = settingInfoManager.queryByWorkType((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID),"Posting");
				delegateOrder.setIntegral(Long.valueOf(delegateType.getParamValue()));
				if (Long.valueOf(delegateType.getParamValue()) > memberInfo.getPoint()) {
					map.put("errMsg", "积分不足");
					map.put("success", false);
				}else {
					delegateOrder.setWorkType("Posting");
					delegateOrderManager.addPostDelegateRecord(delegateOrder, delegateExpress, memberInfo);
					map.put("success", true);
				}
			}
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:postalDelegate] fail to deal with postal delegate.",e);
		}
		return map;
	}
	
	/**
	 * 除草委托
	 * @param request
	 * @param delegateOrder
	 * @param openId
	 * @return
	 */
	@RequestMapping("/weeding")
	@ResponseBody
	public Object weedingDelegate(HttpServletRequest request,Long businessId,FarmDelegateOrder delegateOrder,String openId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[DelegateController:weedingDelegate] begin to deal with weeding delegate.");
			log.info("[DelegateController:weedingDelegate] { :" + "}.");
			FarmMemberCrops memberCrops = memberCropsManager.queryCropsByLandId(delegateOrder.getMemberLandId());
			if (memberCrops==null ||memberCrops.getStatus()==2) {
				map.put("errMsg", "当前土地未种植，无法进行除草！");
				map.put("success", false);
			}else {
				MemberInfo memberInfo = memberInfoManager.queryByOpenId(openId, businessId);
				SettingInfo delegateType = settingInfoManager.queryByWorkType((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID),"Weeding");
				delegateOrder.setIntegral(Long.valueOf(delegateType.getParamValue()));
				if (Long.valueOf(delegateType.getParamValue()) > memberInfo.getPoint()) {
					map.put("errMsg", "积分不足！");
					map.put("success", false);
				}else {
					delegateOrder.setWorkType("Weeding");
					WorkingLog wlog = new WorkingLog();
					wlog.setContent("委托农场除草，消耗"+delegateOrder.getIntegral()+"积分");
					delegateOrderManager.addDelegateOrder(delegateOrder, memberInfo,wlog);
					map.put("success", true);
				}
			}
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:weedingDelegate] fail to deal with weeding delegate.",e);
		}
		return map;
	}
	
	/**
	 * 捉虫委托
	 * @param request
	 * @param delegateOrder
	 * @param openId
	 * @return
	 */
	@RequestMapping("/insectsCatching")
	@ResponseBody
	public Object insectsCatchingDelegate(HttpServletRequest request,Long businessId,FarmDelegateOrder delegateOrder,String openId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[DelegateController:insectsCatchingDelegate] begin to deal with insectsCatching delegate.");
			log.info("[DelegateController:insectsCatchingDelegate] { :" + "}.");
			FarmMemberCrops memberCrops = memberCropsManager.queryCropsByLandId(delegateOrder.getMemberLandId());
			if (memberCrops==null ||memberCrops.getStatus()==2) {
				map.put("errMsg", "当前土地未种植，无法进行捉虫！");
				map.put("success", false);
			}else {
				MemberInfo memberInfo = memberInfoManager.queryByOpenId(openId, businessId);
				SettingInfo delegateType = settingInfoManager.queryByWorkType((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID),"Insecticide");
				delegateOrder.setIntegral(Long.valueOf(delegateType.getParamValue()));
				if (Long.valueOf(delegateType.getParamValue()) > memberInfo.getPoint()) {
					map.put("errMsg", "积分不足！");
					map.put("success", false);
				}else {
					delegateOrder.setWorkType("Insecticide");
					WorkingLog wlog = new WorkingLog();
					wlog.setContent("委托农场捉虫，消耗"+delegateOrder.getIntegral()+"积分");
					delegateOrderManager.addDelegateOrder(delegateOrder, memberInfo,wlog);
					map.put("success", true);
				}
			}
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:insectsCatchingDelegate] fail to deal with insectsCatching delegate.",e);
		}
		return map;
	}
	
	/**
	 * 撒药委托
	 * @param request
	 * @param delegateOrder
	 * @param openId
	 * @return
	 */
	@RequestMapping("/spraying")
	@ResponseBody
	public Object sprayingDelegate(HttpServletRequest request,Long businessId,FarmDelegateOrder delegateOrder,String openId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[DelegateController:sprayingDelegate] begin to deal with spraying delegate.");
			log.info("[DelegateController:sprayingDelegate] { :" + "}.");
			FarmMemberCrops memberCrops = memberCropsManager.queryCropsByLandId(delegateOrder.getMemberLandId());
			if (memberCrops==null ||memberCrops.getStatus()==2) {
				map.put("errMsg", "当前土地未种植，无法进行撒药！");
				map.put("success", false);
			}else {
				MemberInfo memberInfo = memberInfoManager.queryByOpenId(openId, businessId);
				SettingInfo delegateType = settingInfoManager.queryByWorkType((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID),"Spraying");
				delegateOrder.setIntegral(Long.valueOf(delegateType.getParamValue()));
				if (Long.valueOf(delegateType.getParamValue()) > memberInfo.getPoint()) {
					map.put("errMsg", "积分不足！");
					map.put("success", false);
				}else {
					delegateOrder.setWorkType("Spraying");
					WorkingLog wlog = new WorkingLog();
					wlog.setContent("委托农场撒药，消耗"+delegateOrder.getIntegral()+"积分");
					delegateOrderManager.addDelegateOrder(delegateOrder, memberInfo,wlog);
					map.put("success", true);
				}
			}
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:sprayingDelegate] fail to deal with spraying delegate.",e);
		}
		return map;
	}
	
	/**
	 * 整地委托（种植前）
	 * @param request
	 * @param delegateOrder
	 * @param openId
	 * @return
	 */
	@RequestMapping("/soilPreparating")
	@ResponseBody
	public Object soilPreparatingDelegate(HttpServletRequest request,Long businessId,FarmDelegateOrder delegateOrder,String openId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[DelegateController:soilPreparatingDelegate] begin to deal with soilPreparating delegate.");
			log.info("[DelegateController:soilPreparatingDelegate] { :" + "}.");
			FarmMemberCrops memberCrops = memberCropsManager.queryCropsByLandId(delegateOrder.getMemberLandId());
			if (memberCrops!=null) {
				map.put("errMsg", "当前土地已种植，无法进行整地！");
				map.put("success", false);
			}else {
				MemberInfo memberInfo = memberInfoManager.queryByOpenId(openId, businessId);
				SettingInfo delegateType = settingInfoManager.queryByWorkType((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID),"SoilPreparate");
				delegateOrder.setIntegral(Long.valueOf(delegateType.getParamValue()));
				if (Long.valueOf(delegateType.getParamValue()) > memberInfo.getPoint()) {
					map.put("errMsg", "积分不足！");
					map.put("success", false);
				}else {
					delegateOrder.setWorkType("SoilPreparate");
					WorkingLog wlog = new WorkingLog();
					wlog.setContent("委托农场整地，消耗"+delegateOrder.getIntegral()+"积分");
					delegateOrderManager.addDelegateOrder(delegateOrder, memberInfo,wlog);
					map.put("success", true);
				}
			}
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:soilPreparatingDelegate] fail to deal with soilPreparating delegate.",e);
		}
		return map;
	}



	/**
	 * 获取用户土地已收获的全部农作物
	 * @param memberLandId
	 * @return
	 */
	@RequestMapping("/getAllMemberCrops")
	@ResponseBody
	public Object getAllMemberCrops(Long memberLandId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[DelegateController:getAllMemberCrops] memberLandId:"+memberLandId);
			map.put("memberCropsList",memberCropsManager.getAllMemberCrops(memberLandId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:getAllMemberCrops] fail.",e);
		}
		return map;
	}

	/**
	 * 出售我的农作物
	 * @param farmBazaarProduct
	 * @return
	 */
	@RequestMapping("/addBazaarProduct")
	@ResponseBody
	public Object addBazaarProduct(FarmBazaarProduct farmBazaarProduct,@RequestParam(value = "file", required = false) MultipartFile file) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[DelegateController:addBazaarProduct] farmBazaarProduct:"+farmBazaarProduct);
			FarmMemberCrops farmMemberCrops=memberCropsManager.getFarmMemberCropsById(farmBazaarProduct.getMemberCropsId());
			//校验库存
			if(farmBazaarProduct.getStock()>farmMemberCrops.getInventory()){
				map.put("success", false);
				return map;
			}
			String pic = UploadUtil.uploadFile(file, "/upload/",RandomUtil.generateNumber(30));
			farmBazaarProduct.setCover(pic);
			memberCropsManager.addFarmBazaarProduct(farmBazaarProduct);


			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:addBazaarProduct] fail.",e);
		}
		return map;
	}

	/**
	 * 获取用户土地在售的全部商品
	 * @param memberLandId
	 * @return
	 */
	@RequestMapping("/getAllMemberLandProduct")
	@ResponseBody
	public Object getAllMemberLandProduct(Long memberLandId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[DelegateController:getAllMemberLandProduct] memberLandId:"+memberLandId);
			map.put("memberLandProductList",memberCropsManager.getMemberLandProduct(memberLandId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:getAllMemberLandProduct] fail.",e);
		}
		return map;
	}

	/**
	 * 下架用户土地的在售农作物
	 * @param farmBazaarProductId
	 * @return
	 */
	@RequestMapping("/removeMemberLandProduct")
	@ResponseBody
	public Object removeMemberLandProduct(Long farmBazaarProductId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[DelegateController:removeMemberLandProduct] farmBazaarProductId:"+farmBazaarProductId);
			memberCropsManager.removeFarmBazaarProduct(farmBazaarProductId);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:removeMemberLandProduct] fail.",e);
		}
		return map;
	}

	/**
	 * 查询全部出售状态的农作物
	 * @return
	 */
	@RequestMapping("/getAllBazaarProduct")
	@ResponseBody
	public Object getAllBazaarProduct(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[DelegateController:getAllBazaarProduct]");
			Long farmId = (Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID);
			map.put("bazaarProductList",memberCropsManager.getAllBazaarProduct(farmId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:getAllBazaarProduct] fail.",e);
		}
		return map;
	}

	/**
	 * 创建自由销售订单，并发起预支付
	 * @param farmBazaarOrder
	 * @param request
	 * @return
	 */
	@RequestMapping("/createBazaarOrder")
	@ResponseBody
	public Object createBazaarOrder(HttpServletRequest request,FarmBazaarOrder farmBazaarOrder) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[DelegateController:createBazaarOrder]farmBazaarOrder:"+farmBazaarOrder);
			//创建订单
			farmBazaarOrder.setOrderCode(RandomUtil.generateNumber(4));
			farmBazaarOrder.setOrderStatus(FarmBazaarOrder.ORDERSTATUS_UNPAY);
			farmBazaarOrder.setPayStatus(FarmBazaarOrder.PAYSTATUS_UNPAY);
			farmBazaarOrder.setCreateTime(DateUtil.formatCurrentDateTime());
			int addStatus=memberCropsManager.addBazaarOrder(farmBazaarOrder);
			if(addStatus==-1){
				map.put("success",true);
				map.put("error",true);
				map.put("msg","库存不足");
				return map;
			}
			log.info("[DelegateController:createBazaarOrder]创建自由销售订单！");

			//发起预支付
			Integer payPrice=(int)(NumberUtil.mul(farmBazaarOrder.getAmount(),100));  //支付金额以分为单位
			String remoteAddr = request.getRemoteAddr();
			Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			AccessToken accessToken = accessTokenManager.queryAccessToken(businessId);
			Long memberId = (Long) SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
			MemberInfo memberInfo = memberInfoManager.queryById(memberId);
			String prepayId = WeixinPayUtil.unifiedOrder(accessToken.getAppid(), "自由销售商品", (short)6, remoteAddr,
					payPrice, farmBazaarOrder.getOrderCode(), memberInfo.getOpenid(), DateUtil.formatAddMinuteTime(farmBazaarOrder.getCreateTime(), 10),
					accessToken.getMchid(),accessToken.getPayKey());
			Map<String ,String > signMap=new HashMap<String ,String >();
			signMap.put("appId", accessToken.getAppid());
			String payPackage = "prepay_id=" + prepayId;
			signMap.put("package", payPackage);
			String timestamp = WeixinUtil.getTimestamp();
			signMap.put("timeStamp", timestamp);
			String nonceStr = WeixinUtil.getRandomStr();
			signMap.put("nonceStr", nonceStr);
			String signType = "MD5";
			signMap.put("signType", signType);
			String paySign = WeixinUtil.getPaySign(signMap, accessToken.getPayKey());
			map.put("timestamp", timestamp);
			map.put("nonceStr", nonceStr);
			map.put("signType", signType);
			map.put("payPackage", payPackage);
			map.put("paySign", paySign);
			log.info("[DelegateController:createBazaarOrder]生成预支付统一下单！");

			map.put("error",false);
			map.put("success", true);
		} catch (Exception e) {
			map.put("error",false);
			map.put("success", false);
			log.error("[DelegateController:createBazaarOrder] fail.",e);
		}
		return map;
	}


	/**
	 * 查询用户全部的集市订单列表
	 * @param memberId
	 * @return
	 */
	@RequestMapping("/getAllMemberBazaarOrder")
	@ResponseBody
	public Object getAllMemberBazaarOrder(Long memberId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[DelegateController:getAllMemberBazaarOrder]memberId:"+memberId);
			map.put("memberBazaarOrderList",memberCropsManager.getAllOrder(memberId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:getAllMemberBazaarOrder] fail.",e);
		}
		return map;
	}


	/**
	 * 确认收货
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateToReceived")
	@ResponseBody
	public Object updateToReceived(Long id) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[DelegateController:updateToReceived]id:"+id);
			memberCropsManager.toReceived(id);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:updateToReceived] fail.",e);
		}
		return map;
	}


	/**
	 * 获取用户土地的自由销售订单
	 * @param memberLandId
	 * @return
	 */
	@RequestMapping("/getMemberLandOrderList")
	@ResponseBody
	public Object getMemberLandOrderList(Long memberLandId) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			log.info("[DelegateController:getMemberLandOrderList]id:"+memberLandId);
			map.put("memberLandOrderList",memberCropsManager.getMemberLandOrderList(memberLandId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:getMemberLandOrderList] fail.",e);
		}
		return map;
	}

	/**
	 * 重新整地
	 * @param request
	 * @param delegateOrder
	 * @param openId
	 * @param cropsId
	 * @return
	 */
	@RequestMapping("/reSoilPrepare")
	@ResponseBody
	public Object reSoilPrepare(HttpServletRequest request,FarmDelegateOrder delegateOrder,String openId,Long cropsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[DelegateController:soilPreparatingDelegate] begin to deal with soilPreparating delegate.");
			log.info("[DelegateController:soilPreparatingDelegate] { :" + "}.");
			Long farmId = (Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID);
			FarmMemberCropsVo cropsVo = memberCropsManager.queryMemberCropsVoById(cropsId);
			FarmRentLandInfo land = memberCropsManager.queryRentLandByMemberLandId(delegateOrder.getMemberLandId());
			MemberInfo memberInfo = memberInfoManager.queryByOpenId(openId, farmId);
			SettingInfo delegateType = settingInfoManager.queryByWorkType(farmId, "SoilPreparate");
			delegateOrder.setIntegral(Long.valueOf(delegateType.getParamValue()));
			if (Long.valueOf(delegateType.getParamValue()) > memberInfo.getPoint()) {
				map.put("errMsg", "积分不足！");
				map.put("success", false);
			} else {
				delegateOrder.setWorkType("SoilPreparate");
				WorkingLog wlog = new WorkingLog();
				wlog.setContent("整地-" + land.getName() + "-" + cropsVo.getArea() + "m²" + cropsVo.getName());
				delegateOrderManager.addReSoilDelegateOrder(delegateOrder, memberInfo, wlog,cropsId);
				map.put("success", true);
			}
		} catch (Exception e) {
			map.put("success", false);
			log.error("[DelegateController:soilPreparatingDelegate] fail to deal with soilPreparating delegate.", e);
		}
		return map;
	}



}
