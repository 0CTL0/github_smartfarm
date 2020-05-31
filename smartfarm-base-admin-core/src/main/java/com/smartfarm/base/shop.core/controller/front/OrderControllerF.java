package com.smartfarm.base.shop.core.controller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.util.*;
import com.smartfarm.base.shop.core.entity.*;
import com.smartfarm.base.shop.core.manager.*;
import net.sf.json.JSONArray;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/orderF")
public class OrderControllerF {

	private Logger log = Logger.getLogger(OrderControllerF.class);
	@Resource
	private ShopCartManager shopCartManager;
	@Resource
	private OrderInfoManager orderInfoManager;
	@Resource
	private MemberAddressManager memberAddressManager;
	@Resource
	private AccessTokenManager accessTokenManager;
	@Resource
	private MemberInfoManager memberInfoManager;
	@Resource
	private CouponInfoManager couponInfoManager;
	@Resource
	private ProductSkuCommissionManager productSkuCommissionManager;


	@RequestMapping("/getMemberOrderCommissions")
	@ResponseBody
	public Object getMemberOrderCommissions(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[OrderController:getMemberOrderCommissions]enter.");
			Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
			map.put("memberOrderCommissions",productSkuCommissionManager.getMemberOrderCommissions(memberId));
			map.put("success",true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[OrderController:getMemberOrderCommissions]false to add shop cart.",e);
		}
		return map;
	}


	/**
	 * 添加购物车
	 * @return
	 */
	@RequestMapping("/addShopCart")
    @ResponseBody
    public Object addShopCart(HttpServletRequest request, ShopCart shopCart) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[OrderController:addShopCart]add shop cart.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	shopCart.setMemberId(memberId);
        	shopCart.setCreateTime(DateUtil.formatCurrentDateTime());
        	shopCartManager.addShopCart(shopCart);
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[OrderController:addShopCart]false to add shop cart.",e);
        }
        return map;
	}
	
	/**
	 * 查询购物车信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/shopCart")
    @ResponseBody
    public Object shopCart(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[OrderController:shopCart]query shop cart.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	map.put("shopCartList",shopCartManager.queryByMemberId(memberId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[OrderController:shopCart]false to query shop cart.",e);
        }
        return map;
	}
	
	/**
	 * 生成购物车订单
	 * @param request
	 * @param shopCartJson
	 * @return
	 */
	@RequestMapping("/createCartOrder")
    @ResponseBody
    public Object createCartOrder(HttpServletRequest request,String shopCartJson) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[OrderController:createCartOrder]create cart order.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
        	JSONArray ja = JSONArray.fromObject(shopCartJson);
        	@SuppressWarnings("unchecked")
        	List<ShopCart> list = (List<ShopCart>) JSONArray.toCollection(ja,ShopCart.class);
        	
        	OrderInfo orderInfo = new OrderInfo();
        	orderInfo.setMemberId(memberId);
        	orderInfo.setBusinessId(businessId);
        	map.put("addOrder",orderInfoManager.createShopCardOrder(list,orderInfo));
        	map.put("orderId", orderInfo.getId());
        	map.put("shopCartList",shopCartManager.queryByMemberId(memberId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[OrderController:createCartOrder]false to create cart order.",e);
        }
        return map;
	}
	
	/**
	 * 产品直接下单
	 * @param request
	 * @param orderProduct
	 * @return
	 */
	@RequestMapping("/createOrderBySku")
    @ResponseBody
	public Object createOrderBySku(HttpServletRequest request,OrderProduct orderProduct) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[OrderController:createOrderBySku]create order for sku.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
        	OrderInfo orderInfo = new OrderInfo();
        	orderInfo.setMemberId(memberId);
        	orderInfo.setBusinessId(businessId);
        	map.put("addOrder",orderInfoManager.createSkuOrder(orderProduct,orderInfo,(short)1));
        	map.put("orderId", orderInfo.getId());
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[OrderController:createOrderBySku]false to create order for sku.",e);
        }
        return map;
	}
	
	/**
	 * 查询订单信息
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/queryOrderInfo")
    @ResponseBody
	public Object queryOrderInfo(HttpServletRequest request,Long orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[OrderController:queryOrderInfo]{orderId:" + orderId + "}query order info by id.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	map.put("orderInfo",orderInfoManager.queryById(orderId));
        	map.put("orderDetailList",orderInfoManager.queryOrderDetailByOrderId(orderId));
//        	map.put("orderProductList", orderInfoManager.queryProductByOrderId(orderId));
        	map.put("addressList",memberAddressManager.queryAddressByMemberId(memberId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[OrderController:queryOrderInfo]false to query order info by id.",e);
        }
        return map;
	}
	
	/**
	 * 查询订单信息
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/queryOrderInfoAndCoupon")
    @ResponseBody
	public Object queryOrderInfoAndCoupon(HttpServletRequest request,Long orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[OrderController:queryOrderInfoAndCoupon]{orderId:" + orderId + "}query order info by id.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	map.put("orderInfo",orderInfoManager.queryById(orderId));
        	map.put("orderDetailList",orderInfoManager.queryOrderDetailByOrderId(orderId));
//        	map.put("orderProductList", orderInfoManager.queryProductByOrderId(orderId));
        	map.put("addressList",memberAddressManager.queryAddressByMemberId(memberId));
        	map.put("couponList",couponInfoManager.queryCouponListByOrderId(orderId, memberId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[OrderController:queryOrderInfoAndCoupon]false to query order info by id.",e);
        }
        return map;
	}
	
	/**
	 * 订单支付
	 * @param request
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/payForMiniProgram")
    @ResponseBody
	public Object payForMiniProgram(HttpServletRequest request,Long orderId,String receiveName,String receiveMobile,String address,
									String orderDetailJson,Long couponId,Double freight) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[OrderController:payForMiniProgram]{orderId:" + orderId + "}pay for miniProgram.");
        	Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
        	OrderInfo orderInfo = orderInfoManager.queryById(orderId);
//        	List<OrderProduct> orderProductList = orderInfoManager.queryProductByOrderId(orderId);
			if (orderTimeOut(map, orderInfo)) return map;
			map.put("error",false);
        	String remoteAddr = request.getRemoteAddr();
        	AccessToken accessToken = accessTokenManager.queryAccessToken(businessId);
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	MemberInfo memberInfo = memberInfoManager.queryById(memberId);
        	
        	//更新子订单信息和收货
        	JSONArray ja = JSONArray.fromObject(orderDetailJson);
        	@SuppressWarnings("unchecked")
        	List<OrderDetail> list = (List<OrderDetail>) JSONArray.toCollection(ja,OrderDetail.class);
        	for(OrderDetail orderDetail:list) {
        		OrderDetail orderDetailDb = new OrderDetail();
        		orderDetailDb.setAddress(address);
        		orderDetailDb.setReceiveMobile(receiveMobile);
        		orderDetailDb.setReceiveName(receiveName);
        		orderDetailDb.setMessage(orderDetail.getMessage());
        		orderDetailDb.setId(orderDetail.getId());
        		orderInfoManager.updateDetailById(orderDetailDb);
        	}
        	//优惠券处理
        	if(couponId !=null) {
        		orderInfoManager.useCoupon(couponId, orderInfo);
        	}

			//邮费处理
			orderInfo.setRealPrice(NumberUtil.add(orderInfo.getRealPrice(),freight));

        	if(orderInfo.getRealPrice() == 0) {
        		//TODO 完成订单
				map.put("success",true);
				map.put("payed",true);
				return map;
			}

			map.put("payed",false);
        	//调用微信支付接口
        	Integer price = (int) (NumberUtil.mul(orderInfo.getRealPrice(), 100));
        	String prepayId = WeixinPayUtil.unifiedOrder(accessToken.getAppid(), "抢鲜商城商品", (short)3, remoteAddr, price, orderInfo.getOrderNo(), 
        			memberInfo.getOpenid(),orderInfo.getEndTime(),accessToken.getMchid(),accessToken.getPayKey()); 
        	
        	Map<String ,String > signMap=new HashMap<String ,String >();
			signMap.put("appId", accessToken.getAppid());
	        String timestamp = WeixinUtil.getTimestamp();
	        signMap.put("timeStamp", timestamp);
	        String payPackage = "prepay_id=" + prepayId;
	        signMap.put("package", payPackage);
	        String signType = "MD5";
	        signMap.put("signType", signType);
	        String nonceStr = WeixinUtil.getRandomStr();
	        signMap.put("nonceStr", nonceStr);
	        String paySign = WeixinUtil.getPaySign(signMap, accessToken.getPayKey());
        	
	        map.put("nonceStr", nonceStr);
			map.put("timestamp", timestamp);
			map.put("payPackage", payPackage);
			map.put("signType", signType);
			map.put("paySign", paySign);
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[OrderController:payForMiniProgram]false to pay for miniProgram.",e);
        }
        return map;
	}
	
	/**
	 * 查询我的订单信息
	 * @return
	 */
	@RequestMapping("/queryMyOrder")
    @ResponseBody
	public Object queryMyOrder(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[OrderController:queryMyOrder]query my order info.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	map.put("orderList",orderInfoManager.queryOrderByMemberId(memberId));
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[OrderController:queryMyOrder]false to query my order info.",e);
        }
        return map;
	}
	
	/**
	 * 删除购物车
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteShopCart")
    @ResponseBody
	public Object deleteShopCart(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[OrderController:deleteShopCart]delete shop cart by id.");
        	shopCartManager.deleteShopCartById(id);
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[OrderController:deleteShopCart]false to delete shop cart by id.",e);
        }
        return map;
	}
	
	/**
	 * 产品直接下单
	 * @param request
	 * @param orderProduct
	 * @return
	 */
	@RequestMapping("/createOrderBySkuForPoint")
    @ResponseBody
	public Object createOrderBySkuForPoint(HttpServletRequest request,OrderProduct orderProduct) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[OrderController:createOrderBySkuForPoint]create order for sku.");
        	Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	
        	OrderInfo orderInfo = new OrderInfo();
        	orderInfo.setMemberId(memberId);
        	map.put("addOrder",orderInfoManager.createSkuOrder(orderProduct,orderInfo,(short)2));
        	map.put("orderId", orderInfo.getId());
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[OrderController:createOrderBySkuForPoint]false to create order for sku.",e);
        }
        return map;
	}
	
	/**
	 * 积分订单支付
	 * @param request
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/payForPoint")
    @ResponseBody
	public Object payForPoint(HttpServletRequest request,Long orderId,String receiveName,String receiveMobile,String address,String orderDetailJson) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	log.info("[OrderController:payForPoint]{orderId:" + orderId + "}pay for miniProgram.");
        	OrderInfo orderInfo = orderInfoManager.queryById(orderId);
			if (orderTimeOut(map, orderInfo)) return map;
			Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        	MemberInfo memberInfo = memberInfoManager.queryById(memberId);
        	
        	if(memberInfo.getPoint() < orderInfo.getTotalPrice()) {
        		map.put("success",true);
        		map.put("error",true);
        		map.put("msg","您的积分不足");
        		return map;
        	}
        	map.put("error",false);
        	
        	//更新子订单信息和收货
        	JSONArray ja = JSONArray.fromObject(orderDetailJson);
        	@SuppressWarnings("unchecked")
        	List<OrderDetail> list = (List<OrderDetail>) JSONArray.toCollection(ja,OrderDetail.class);
        	
        	orderInfoManager.payPointOrder(orderInfo, list, memberId, address, receiveMobile, receiveName);
        	
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[OrderController:payForMiniProgram]false to pay for miniProgram.",e);
        }
        return map;
	}

	private boolean orderTimeOut(Map<String, Object> map, OrderInfo orderInfo) {
		if (orderInfo.getStatus() == (short) -2) {
			map.put("success", true);
			map.put("error", true);
			map.put("msg", "订单已超时");
			return true;
		}
		if (Long.valueOf(DateUtil.formatCurrentDateTime()).longValue() >= Long.valueOf(orderInfo.getEndTime()).longValue()) {
			map.put("success", true);
			map.put("error", true);
			map.put("msg", "订单已超时");
			return true;
		}
		if (orderInfo.getStatus() == (short) -1) {
			map.put("success", true);
			map.put("error", true);
			map.put("msg", "订单已取消");
			return true;
		}
		if (orderInfo.getStatus() == (short) 1) {
			map.put("success", true);
			map.put("error", true);
			map.put("msg", "订单已支付");
			return true;
		}
		return false;
	}

	/**
	 * 查询物流信息
	 * @param expCode
	 * @param expNo
	 * @return
	 */
	@RequestMapping("/queryShipMsg")
	@ResponseBody
	public Object queryShipMsg(@Param("expCode")String expCode,@Param("expNo")String expNo){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShipQueryApi api = new ShipQueryApi();
			Object msg = com.alibaba.fastjson.JSONArray.parse(api.getOrderTracesByJson(expCode, expNo));
 			map.put("ShipMsg", msg);
        	log.info("[OrderController:queryShipMsg]query order info for page.");
        	
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CommunityController:queryShipMsg]false to query order list.", e);
		}
		return map;
	}


	@RequestMapping("/applyRefund")
	@ResponseBody
	public Object applyRefund(Long id, String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[OrderController:applyRefund]apply refund by id.");
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setId(id);
			orderInfo.setStatus((short)-3);
			orderInfo.setRefundMsg(message);
			orderInfoManager.updateById(orderInfo);
			map.put("success",true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[OrderController:applyRefund]false to apply refund by id.",e);
		}
		return map;
	}


	/**
	 * 获取订单的邮费
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/getRreight")
	@ResponseBody
	public Object getRreight(@Param("orderId") Long orderId,String province){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[OrderController:getRreight]orderId:"+orderId);
			OrderInfo orderInfo = orderInfoManager.queryById(orderId);
			Double freight=orderInfoManager.calculateFreight(orderInfo,province);
			map.put("freight",freight);
			map.put("success",true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[OrderController:getRreight]false",e);
		}
		return map;
	}
}
