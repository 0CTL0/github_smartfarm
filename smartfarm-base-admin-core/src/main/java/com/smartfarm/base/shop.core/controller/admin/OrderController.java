package com.smartfarm.base.shop.core.controller.admin;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smartfarm.base.util.*;
import com.smartfarm.base.shop.core.entity.AccessToken;
import com.smartfarm.base.shop.core.entity.OrderInfo;
import com.smartfarm.base.shop.core.entity.OrderRefund;
import com.smartfarm.base.shop.core.entity.vo.OrderProductVo;
import com.smartfarm.base.shop.core.manager.AccessTokenManager;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.smartfarm.base.shop.core.entity.OrderDetail;
import com.smartfarm.base.shop.core.manager.OrderInfoManager;


@Controller
@RequestMapping("/order")
public class OrderController {
	private Logger log = Logger.getLogger(OrderController.class);
	@Resource
	private OrderInfoManager orderInfoManager;
	@Resource
	private AccessTokenManager accessTokenManager;


	/**
	 * 分页查询订单订单佣金列表
	 * @param request
	 * @param commissionMemberName
	 * 	 *
	 * @param  @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/getOrderCommissionList")
	@ResponseBody
	public Object getOrderCommissionList(HttpServletRequest request, String commissionMemberName,
									  String orderMemberName, String orderNo, Integer pageSize, Integer pageNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("[OrderController:getOrderCommissionList]orderNo:"+orderNo+" commissionMemberName:"+commissionMemberName+" orderMemberName:"+orderMemberName);
		try {
			log.info("[OrderController:getOrderCommissionList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}query order info for page.");
			Long farmId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("orderCommissionList", orderInfoManager.getPageList(farmId,commissionMemberName,orderMemberName,orderNo, (pageNumber - 1) * pageSize, pageSize));
			map.put("total", orderInfoManager.countPageList(farmId,commissionMemberName,commissionMemberName,orderNo));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[OrderController:getOrderCommissionList]false to query order list.", e);
		}
		return map;
	}


	/**
	 * 分页查找所有订单
	 * @param status
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryAllOrder")
	@ResponseBody
	public Object queryAllOrder(HttpServletRequest request, Short status, String order_no, Integer pageSize, Integer pageNumber, String startDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("122");
		log.info("[OrderController:queryAllOrder]orderNo:"+order_no+"status:"+status+"startDate:"+startDate+"endDate:"+endDate);
		try {
        	log.info("[OrderController:queryAllOrder]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}query order info for page.");

        	Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("orderList", orderInfoManager.queryAllOrder(order_no,status, (pageNumber - 1) * pageSize, pageSize, businessId,startDate,endDate));
			map.put("total", orderInfoManager.queryCountAllOrder(order_no, status,businessId,startDate,endDate));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CommunityController:queryAllOrder]false to query order list.", e);
		}
		return map;
	}


	/**
	 * 根据订单id查看订单
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryOrderByOid")
	@ResponseBody
	public Object queryOrderByOid(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
        	log.info("[OrderController:queryOrderByOid]query order info for page.");
			
			map.put("orderInfo", orderInfoManager.queryOrderByOid(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CommunityController:queryOrderByOid]false to query order list.", e);
		}
		return map;
	}
	
	/**
	 * 分页查找所有订单详细信息
	 * @param status
	 * @param ship_no
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryAllOrderDetail")
	@ResponseBody
	public Object queryAllOrderDetail(HttpServletRequest request,Short status,String ship_no, Integer pageSize, Integer pageNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[OrderController:queryAllOrderDetail]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}query order info for page.");
        	Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("detailList", orderInfoManager.queryAllBusinessOrderDetail(businessId,ship_no,status, (pageNumber - 1) * pageSize, pageSize));
			
			map.put("total", orderInfoManager.queryCountBusinessOrderDetail(businessId,ship_no, status));
			
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CommunityController:queryAllOrderDetail]false to query order list.", e);
		}
		return map;
	}
	
	/**
	 * 根据订单详细id查看订单详细
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryOrderDetailById")
	@ResponseBody
	public Object queryOrderDetailById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
        	log.info("[OrderController:queryOrderDetailById]query order info for page.");
			
			map.put("orderDetailInfo", orderInfoManager.queryOrderDetailById(id));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CommunityController:queryOrderDetailById]false to query order list.", e);
		}
		return map;
	}
	
	/**
	 * 发货
	 * @param orderDetil
	 * @return
	 */
	@RequestMapping("/updateShipMsgById")
	@ResponseBody
	public Object updateShipMsgById(OrderDetail orderDetil){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[OrderController:updateShipMsgById]update order info for page.");
        	String shipCode = orderDetil.getShipCode();
        	orderDetil.setShipName(ShipUtil.getShipName(shipCode));
        	orderDetil.setStatus(Short.valueOf("2"));
			orderInfoManager.updateDetailById(orderDetil);
			map.put("update", true);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CommunityController:updateShipMsgById]false to update order list.", e);
		}
		return map;
	}
	
	
	@RequestMapping("/queryShipMsg")
	@ResponseBody
	public Object queryShipMsg(@Param("expCode")String expCode,@Param("expNo")String expNo){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShipQueryApi api = new ShipQueryApi();
			Object msg = JSONArray.parse(api.getOrderTracesByJson(expCode, expNo));
 			map.put("ShipMsg", msg);
        	log.info("[OrderController:queryShipMsg]query order info for page.");
        	
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CommunityController:queryShipMsg]false to query order list.", e);
		}
		return map;
	}

	//导出订单
	@RequestMapping("/exportOrder")
	@ResponseBody
	public Object exportOrder(String startDate, String endDate, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[OrderController:exportOrder]export order info.  startDate:"+startDate+"endDate:"+endDate);

			Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);

			String title = "订单表 (" + startDate + "-" + endDate + ")" ;
			List<OrderProductVo> list = orderInfoManager.queryOrderProductByDateAndFarmId(startDate, endDate, businessId);
			String[] rowsName=new String[]{"ID","订单id","订单编号","下单时间","用户昵称","商品名称","商品规格","销售单价","商品数量",
					"应付金额","实际支付","收货人","收货手机","收货地址","状态"};
			List<Object[]>  dataList = new ArrayList<Object[]>();
			Object[] objs = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for (int i = 0; i < list.size(); i++) {
				OrderProductVo vo = list.get(i);
				objs = new Object[rowsName.length];
				objs[0] = vo.getId();
				objs[1] = vo.getOrderId();
				objs[2] = vo.getOrderNo();
				Date date = format.parse(vo.getCreateTime());
				objs[3] = format2.format(date);
				objs[4] = vo.getNickName();
				objs[5] = vo.getProductName() + "(" + vo.getProductNorm() + ")";
				objs[6] = vo.getProductNorm();
				objs[7] = vo.getPrice();
				objs[8] = vo.getQuantity();
				objs[9] = vo.getTotalPrice();
				objs[10] = vo.getRealPrice();
				objs[11] = vo.getReceiveName();
				objs[12] = vo.getReceiveMobile();
				objs[13] = vo.getAddress();
				String shipStatus = "";
				if(vo.getShippingStatus() == 0) {
					shipStatus = "未发货";
				}else if(vo.getShippingStatus() == 1) {
					shipStatus = "已发货";
				}else if(vo.getShippingStatus() == 2) {
					shipStatus = "已签收";
				}else if(vo.getShippingStatus() == 4) {
					shipStatus = "已退货";
				}
				objs[14] = shipStatus;
				dataList.add(objs);
			}
			ExcelUtil ex =new ExcelUtil(title, rowsName, dataList,response);
			ex.exportData();

			map.put("success",true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[OrderController:exportOrder]false to export order info.",e);
		}
		return map;
	}

	/**
	 * 修改订单物流
	 * @param orderDetil
	 * @return
	 */
	@RequestMapping("/updateOrderDetail")
	@ResponseBody
	public Object updateOrderDetail(OrderDetail orderDetil){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[OrderController:updateOrderDetail]update OrderDetail for page.");
			orderInfoManager.updateDetailById(orderDetil);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CommunityController:updateOrderDetail]false to update OrderDetail.", e);
		}
		return map;
	}

	/**
	 * 分页查询今日的所有订单
	 * @param request
	 * @param nowDate
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryTodayOrder")
	@ResponseBody
	public Object queryTodayOrder(HttpServletRequest request,String nowDate, Integer pageSize, Integer pageNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("[OrderController:queryTodayOrder]orderNo:"+nowDate);
		try {
			log.info("[OrderController:queryTodayOrder]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}query order info for page.");
			Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("orderList", orderInfoManager.queryTodayOrder(nowDate, (pageNumber - 1) * pageSize, pageSize, businessId));
			map.put("total", orderInfoManager.queryCountTodayOrder(nowDate,businessId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CommunityController:queryTodayOrder]false to query order list.", e);
		}
		return map;
	}


	/**
	 * 分页查找所有的退款订单
	 * @param status
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryAllOrderRefund")
	@ResponseBody
	public Object queryAllOrderRefund(HttpServletRequest request,Short status,String order_no, Integer pageSize, Integer pageNumber,String startDate,String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("[OrderController:queryAllOrderRefund]orderNo:"+order_no+"status:"+status+"startDate:"+startDate+"endDate:"+endDate);
		try {
			log.info("[OrderController:queryAllOrderRefund]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}query order info for page.");
			Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			map.put("orderList", orderInfoManager.queryAllOrderRefund(order_no,status, (pageNumber - 1) * pageSize, pageSize, businessId,startDate,endDate));
			map.put("total", orderInfoManager.queryCountAllOrderRefund(order_no, status,businessId,startDate,endDate));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[CommunityController:queryAllOrderRefund]false to query order list.", e);
		}
		return map;
	}


	/**
	 * 用户申请退款
	 * @param request
	 * @return
	 */
	@RequestMapping("/applyRefund")
	@ResponseBody
	public Object applyRefund(HttpServletRequest request, Long  orderInfoId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[OrderController:applyRefund]orderInfoId:"+orderInfoId);

			OrderInfo orderInfo=orderInfoManager.queryById(orderInfoId);
			Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			AccessToken accessToken = accessTokenManager.queryAccessToken(businessId);
			String refundNo=RandomUtil.generateNumber(4);
			Integer totalFee=(int) (NumberUtil.mul(orderInfo.getTotalPrice(), 100));
			Integer refundFee=(int) (NumberUtil.mul(orderInfo.getTotalPrice(), 100));

			//申请退款
			weixinRefundUtil.applyRefund(accessToken.getAppid(),accessToken.getMchid(),orderInfo.getOrderNo(),refundNo,
					totalFee,refundFee,accessToken.getPayKey(),accessToken.getCertUrl());
			orderInfo.setStatus((short)-4);
			orderInfoManager.updateById(orderInfo);

			//添加退款日志记录
			OrderRefund orderRefund=new OrderRefund();
			orderRefund.setBusinessId(businessId);
			orderRefund.setOrderId(orderInfo.getId());
			orderRefund.setRefundNo(refundNo);
			orderRefund.setStatus(OrderRefund.STATUS_Request);
			orderInfoManager.addOrderRefund(orderRefund);
			map.put("success",true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[OrderController:applyRefund]false to apply refund by id.",e);
		}
		return map;
	}

	/**
	 * 拒绝退款
	 * @param orderInfo
	 * @return
	 */
	@RequestMapping("/refuseRefund")
	@ResponseBody
	public Object refuseRefund(OrderInfo orderInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[OrderController:editOrder]"+orderInfo);
			orderInfo.setStatus((short)-6);
			orderInfoManager.updateById(orderInfo);
			map.put("success",true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[OrderController:editOrder]false",e);
		}
		return map;
	}

}
