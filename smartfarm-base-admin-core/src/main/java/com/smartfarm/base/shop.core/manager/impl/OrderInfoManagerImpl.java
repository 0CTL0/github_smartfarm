package com.smartfarm.base.shop.core.manager.impl;

import java.util.*;

import javax.annotation.Resource;

import com.smartfarm.base.util.*;
import com.smartfarm.base.farm.core.dao.SettingInfoDao;
import com.smartfarm.base.farm.core.entity.SettingInfo;
import com.smartfarm.base.shop.core.dao.*;
import com.smartfarm.base.shop.core.entity.*;
import com.smartfarm.base.shop.core.entity.vo.MemberOrderCommissionVo;
import com.smartfarm.base.shop.core.entity.vo.OrderProductVo;
import com.smartfarm.base.shop.core.pattern.CommissionContext;
import com.smartfarm.base.shop.core.pattern.CommissionOrder;
import com.smartfarm.base.shop.core.pattern.CommissionSku;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.smartfarm.base.shop.core.entity.vo.OrderDetailVo;
import com.smartfarm.base.shop.core.entity.vo.OrderInfoVo;
import com.smartfarm.base.shop.core.manager.OrderInfoManager;
import com.smartfarm.base.shop.core.service.OrderService;


@Service("orderInfoManager")
public class OrderInfoManagerImpl implements OrderInfoManager {

	@Resource
	private OrderInfoDao orderInfoDao;
	@Resource
	private OrderProductDao orderProductDao;
	@Resource
	private ProductInfoDao productInfoDao;
	@Resource
	private ProductSkuDao productSkuDao;
	@Resource
	private ShopCartDao shopCartDao;
	@Resource
	private OrderService orderService;
	@Resource
	private OrderDetailDao orderDetailDao;
	@Resource
	private BaseOrderDao baseOrderDao;
	@Resource
	private CouponInfoDao couponInfoDao;
	@Resource
	private CouponBatchRuleDao couponBatchRuleDao;
	@Resource
	private OrderCouponDao orderCouponDao;
	@Resource
	private MemberInfoDao memberInfoDao;
	@Resource
	private MemberOrderCommissionDao memberOrderCommissionDao;
	@Resource
	private OrderRefundDao orderRefundDao;
	@Resource
	private SettingInfoDao settingInfoDao;
	@Resource
	private CommissionSku commissionSku;
	@Resource
	private CommissionOrder commissionOrder;
	@Resource
	private ProductLogisticsTemplateDao productLogisticsTemplateDao;
	@Resource
	private ProductTemplateDetailDao templateDetailDao;

	@Override
	public List<MemberOrderCommissionVo> getPageList(Long farmId, String commissionMemberName, String orderMemberName, String orderNo, Integer start, Integer limit) {
		return memberOrderCommissionDao.selectPageList(farmId,commissionMemberName,orderMemberName,orderNo,start,limit);
	}

	@Override
	public int countPageList(Long farmId, String commissionMemberName, String orderMemberName, String orderNo) {
		return memberOrderCommissionDao.selectPageTotal(farmId,commissionMemberName,orderMemberName,orderNo);
	}

	@Override
	public int orderCommissionHandle(OrderInfo orderInfo) {
			//获取商家的分佣模式

			SettingInfo settingInfo=settingInfoDao.getInfoByType(orderInfo.getBusinessId(), "commission_pattern");
			if(settingInfo!=null){
				if(SettingInfo.VALUE_COMMISSION_PATTERN_ENABLE.equals(settingInfo.getParamValue())){
					//按订单SKU计算分销金额
					Double commissionAmount=0.00;
					if(SettingInfo.VALUE_COMMISSION_PATTERN_SKU.equals(settingInfo.getParamValue())){
						CommissionContext commissionContext=new CommissionContext(commissionSku);
						commissionAmount=commissionContext.getCommission(orderInfo);
					}
					//按订单比例计算分销金额
					if(SettingInfo.VALUE_COMMISSION_PATTERN_ORDER.equals(settingInfo.getParamValue())){
						CommissionContext commissionContext=new CommissionContext(commissionOrder);
						commissionAmount=commissionContext.getCommission(orderInfo);
					}
					//分佣
					MemberInfo memberInfo = memberInfoDao.queryById(orderInfo.getMemberId());
					if (memberInfo.getMemberId() != null) {
						//一级分佣 分佣日志
						MemberInfo firstRecommendMember = memberInfoDao.queryById(memberInfo.getMemberId());
						SettingInfo settingFirst = settingInfoDao.getInfoByType(orderInfo.getBusinessId(), "commission_first_proportion");
						firstRecommendMember.setCash(NumberUtil.add(firstRecommendMember.getCash(), NumberUtil.mul(commissionAmount, Integer.parseInt(settingFirst.getParamValue())/100)));
						memberInfoDao.updateById(firstRecommendMember);
						MemberOrderCommission memberOrderCommissionFirst=new MemberOrderCommission();
						memberOrderCommissionFirst.setCommissionSum(NumberUtil.mul(commissionAmount, Double.parseDouble(settingFirst.getParamValue())));
						memberOrderCommissionFirst.setOrderMemberId(firstRecommendMember.getId());
						memberOrderCommissionFirst.setMemberId(settingFirst.getId());
						memberOrderCommissionFirst.setOrderId(orderInfo.getId());
						memberOrderCommissionFirst.setCreateTime(DateUtil.formatCurrentDateTime());
						memberOrderCommissionDao.insertSelective(memberOrderCommissionFirst);
						//二级分佣 分佣日志
						SettingInfo settingLevel = settingInfoDao.getInfoByType(orderInfo.getBusinessId(), "commission_level");
						if(SettingInfo.VALUE_COMMISSION_LEVEL_FIRST.equals(settingLevel.getParamValue())){
							if(firstRecommendMember.getMemberId()!=null){
								MemberInfo secondRecommendMember = memberInfoDao.queryById(memberInfo.getMemberId());
								SettingInfo settingSecond = settingInfoDao.getInfoByType(orderInfo.getBusinessId(), "commission_second_proportion");
								secondRecommendMember.setCash(NumberUtil.add(secondRecommendMember.getCash(), NumberUtil.mul(commissionAmount, Integer.parseInt(settingFirst.getParamValue())/100)));
								memberInfoDao.updateById(secondRecommendMember);
								MemberOrderCommission memberOrderCommissionSecond=new MemberOrderCommission();
								memberOrderCommissionSecond.setCommissionSum(NumberUtil.mul(commissionAmount, Double.parseDouble(settingSecond.getParamValue())));
								memberOrderCommissionSecond.setMemberId(secondRecommendMember.getId());
								memberOrderCommissionSecond.setOrderMemberId(memberInfo.getId());
								memberOrderCommissionSecond.setOrderId(orderInfo.getId());
								memberOrderCommissionSecond.setCreateTime(DateUtil.formatCurrentDateTime());
								memberOrderCommissionDao.insertSelective(memberOrderCommissionSecond);
							}
						}
					}
				}
			}
			return 0;
	}

	@Override
	public Boolean createShopCardOrder(List<ShopCart> list,OrderInfo orderInfo) throws Exception {
		Double totalPrice = 0D;

		Map<Long, List<ShopCart>> businessOrderMap = new HashMap<Long, List<ShopCart>>();
		for(ShopCart shopCart:list) {
			ProductSku productSku = productSkuDao.queryById(shopCart.getProductSkuId());
			ProductInfo productInfo = productInfoDao.queryById(productSku.getProductId());
			if(productSku.getStock() < shopCart.getQuantity()) {
				return false;
			}
			totalPrice = totalPrice + productSku.getPrice() * shopCart.getQuantity();

			//分类商户商品
			if(businessOrderMap.get(productInfo.getBusinessId()) != null) {//新商户
				List<ShopCart> shopCartList = businessOrderMap.get(productInfo.getBusinessId());
				shopCartList.add(shopCart);
				businessOrderMap.put(productInfo.getBusinessId(), shopCartList);
			}else {
				List<ShopCart> shopCartList = new ArrayList<ShopCart>();
				shopCartList.add(shopCart);
				businessOrderMap.put(productInfo.getBusinessId(), shopCartList);
			}
		}
		//生成订单
		orderInfo.setCreateTime(DateUtil.formatCurrentDateTime());
		orderInfo.setCouponPrice(0D);
		orderInfo.setFreightPrice(0D);
		orderInfo.setTotalPrice(totalPrice);
		orderInfo.setIntegralPrice(0D);
		orderInfo.setOrderPrice(orderInfo.getTotalPrice() + orderInfo.getFreightPrice() - orderInfo.getCouponPrice());
		orderInfo.setRealPrice(orderInfo.getOrderPrice() - orderInfo.getIntegralPrice());
		orderInfo.setPayStatus((short)0);
		orderInfo.setOrderNo(RandomUtil.generateNumber(4));
		orderInfo.setStatus((short)0);
		orderInfoDao.insert(orderInfo);

		int bindex = 1;
		//生成子订单
		for(Map.Entry<Long, List<ShopCart>> entry : businessOrderMap.entrySet()) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderId(orderInfo.getId());
			orderDetail.setBusinessId(entry.getKey());
			orderDetail.setOrderNo(orderInfo.getOrderNo() + bindex);
			bindex++;
			orderDetail.setStatus((short)0);
			orderDetail.setMemberId(orderInfo.getMemberId());
			orderDetail.setFreightPrice(0D);
			orderDetail.setCreateTime(orderInfo.getCreateTime());
			orderDetail.setShippingStatus((short)0);
			orderDetail.setPayStatus((short)0);
			orderDetailDao.insert(orderDetail);
			Double detailPrice = 0D;

			for(ShopCart shopCart:entry.getValue()) {
				ProductSku productSku = productSkuDao.queryById(shopCart.getProductSkuId());
				ProductInfo productInfo = productInfoDao.queryById(productSku.getProductId());
				if(productSku.getStock() < shopCart.getQuantity()) {
					return false;
				}
				OrderProduct orderProduct = new OrderProduct();
				orderProduct.setPicUrl(productInfo.getPicUrl());
				orderProduct.setPrice(productSku.getPrice());
				orderProduct.setProductId(productInfo.getId());
				orderProduct.setProductName(productInfo.getName());
				orderProduct.setProductNorm(productSku.getNorm());
				orderProduct.setProductSkuId(productSku.getId());
				orderProduct.setQuantity(shopCart.getQuantity());
				orderProduct.setStatus((short)1);
				orderProduct.setOrderId(orderInfo.getId());
				orderProduct.setOrderDetailId(orderDetail.getId());
				orderProductDao.insert(orderProduct);

				detailPrice = detailPrice + productSku.getPrice() * shopCart.getQuantity();

				//处理库存
				int coutIndex = productSkuDao.subSkuStock(productSku.getId(), shopCart.getQuantity());
				if(coutIndex == 0) {
					throw new Exception("库存不足");
				}

				//处理购物车
				shopCart.setStatus((short)2);
				shopCartDao.updateById(shopCart);
			}
			orderDetail.setTotalPrice(detailPrice);
			orderDetailDao.updateById(orderDetail);
		}
		BaseOrder baseOrder = new BaseOrder();
		baseOrder.setStatus(BaseOrder.BASE_ORDER_STATUS_INIT);
		baseOrder.setRefId(orderInfo.getId());
		baseOrder.setType(BaseOrder.BASE_ORDER_TYPE_ORDER);
		baseOrderDao.insert(baseOrder);

		//加入订单定时器
		Map<String, Object> map = orderService.makeOrder(baseOrder.getId());
		orderInfo.setEndTime(DateUtil.formatAddMinuteTime(orderInfo.getCreateTime(), Integer.valueOf(String.valueOf(map.get("timeOut")))));
		orderInfoDao.updateById(orderInfo);
		return true;


	}

	@Override
	public Boolean createSkuOrder(OrderProduct orderProduct, OrderInfo orderInfo, Short type) throws Exception {
		ProductSku productSku = productSkuDao.queryById(orderProduct.getProductSkuId());
		ProductInfo productInfo = productInfoDao.queryById(productSku.getProductId());
		if(productSku.getStock() < orderProduct.getQuantity()) {
			return false;
		}
		//生成订单
		orderInfo.setCreateTime(DateUtil.formatCurrentDateTime());
		orderInfo.setPayStatus((short)0);
		orderInfo.setOrderNo(RandomUtil.generateNumber(4));
		orderInfo.setStatus((short)0);
		orderInfo.setCouponPrice(0D);
		Double freightPrice=0D;
		if(type == 1) {
			orderInfo.setFreightPrice(freightPrice);
			orderInfo.setIntegralPrice(0D);
			orderInfo.setTotalPrice(productSku.getPrice() * orderProduct.getQuantity());
			orderInfo.setOrderPrice(orderInfo.getTotalPrice() + orderInfo.getFreightPrice() - orderInfo.getCouponPrice());
			orderInfo.setRealPrice(orderInfo.getOrderPrice() - orderInfo.getIntegralPrice());
		}else if(type == 2) {
			orderInfo.setFreightPrice(freightPrice);
			orderInfo.setTotalPrice((double)productSku.getPoint() * orderProduct.getQuantity());
			orderInfo.setOrderPrice((double)productSku.getPoint() * orderProduct.getQuantity());
			orderInfo.setRealPrice(0D);
		}

		orderInfo.setType(type);
		orderInfoDao.insert(orderInfo);

		//生成子订单
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderId(orderInfo.getId());
		orderDetail.setBusinessId(productInfo.getBusinessId());
		orderDetail.setOrderNo(orderInfo.getOrderNo() + 1);
		orderDetail.setStatus((short)0);
		orderDetail.setMemberId(orderInfo.getMemberId());
		orderDetail.setFreightPrice(freightPrice);
		orderDetail.setCreateTime(orderInfo.getCreateTime());
		orderDetail.setShippingStatus((short)0);
		if(type == 1) {
			orderDetail.setTotalPrice(productSku.getPrice() * orderProduct.getQuantity());
		}else if(type == 2) {
			orderDetail.setTotalPrice((double)productSku.getPoint() * orderProduct.getQuantity());
		}
		orderDetail.setPayStatus((short)0);
		orderDetailDao.insert(orderDetail);

		orderProduct.setPicUrl(productInfo.getPicUrl());
		if(type == 1) {
			orderProduct.setPrice(productSku.getPrice());
		}else if(type == 2) {
			orderProduct.setPrice((double)productSku.getPoint());
		}
		orderProduct.setProductName(productInfo.getName());
		orderProduct.setProductNorm(productSku.getNorm());
		orderProduct.setStatus((short)1);
		orderProduct.setOrderId(orderInfo.getId());
		orderProduct.setOrderDetailId(orderDetail.getId());
		orderProductDao.insert(orderProduct);

		//处理库存
		int coutIndex = productSkuDao.subSkuStock(productSku.getId(), orderProduct.getQuantity());
		if(coutIndex == 0) {
			throw new Exception("库存不足");
		}

		BaseOrder baseOrder = new BaseOrder();
		baseOrder.setStatus(BaseOrder.BASE_ORDER_STATUS_INIT);
		baseOrder.setRefId(orderInfo.getId());
		baseOrder.setType(BaseOrder.BASE_ORDER_TYPE_ORDER);
		baseOrderDao.insert(baseOrder);

		//加入订单定时器
		Map<String, Object> map = orderService.makeOrder(baseOrder.getId());
		orderInfo.setEndTime(DateUtil.formatAddMinuteTime(orderInfo.getCreateTime(), Integer.valueOf(String.valueOf(map.get("timeOut")))));
		orderInfoDao.updateById(orderInfo);
		return true;
	}

	@Override
	public OrderInfo queryById(Long id) {
		return orderInfoDao.queryById(id);
	}

	@Override
	public List<OrderProduct> queryProductByOrderId(Long orderId) {
		return orderProductDao.queryByOrderId(orderId);
	}

	@Override
	public void updateById(OrderInfo orderInfo) {
		orderInfoDao.updateById(orderInfo);
	}

	@Override
	public List<Long> queryOrderUnPayByDate(String nowDate) {
		return orderInfoDao.queryOrderUnPayByDate(nowDate);
	}

	@Override
	public OrderInfo queryByOrderNo(String orderNo) {
		return orderInfoDao.queryByOrderNo(orderNo);
	}

	@Override
	public void orderOutTime(Long orderId) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setStatus((short)-2);
		orderInfo.setId(orderId);
		orderInfoDao.updateById(orderInfo);

		List<OrderDetailVo> orderDetailList = orderDetailDao.queryByOrderId(orderId);
		for(OrderDetailVo orderDetailVo:orderDetailList) {
			orderDetailVo.setStatus((short)-2);
			orderDetailDao.updateById(orderDetailVo);
		}

		//处理库存
		List<OrderProduct> orderProductList = orderProductDao.queryByOrderId(orderId);
		for(OrderProduct orderProduct:orderProductList) {
			productSkuDao.addSkuStock(orderProduct.getProductSkuId(), orderProduct.getQuantity());
		}

		//优惠券
		OrderCoupon orderCoupon = orderCouponDao.queryUsingByOrderId(orderId);
		if(orderCoupon != null) {
			orderCoupon.setStatus((short)3);
			orderCouponDao.updateById(orderCoupon);

			CouponInfo couponInfo = couponInfoDao.queryById(orderCoupon.getCouponId());
			couponInfo.setStatus((short)1);
			couponInfo.setUseTime(couponInfo.getUseTime() + 1);
			couponInfoDao.updateById(couponInfo);
		}

	}

	@Override
	public void orderCancel(Long orderId) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setStatus((short)-1);
		orderInfo.setId(orderId);
		orderInfoDao.updateById(orderInfo);

		List<OrderDetailVo> orderDetailList = orderDetailDao.queryByOrderId(orderId);
		for(OrderDetailVo orderDetailVo:orderDetailList) {
			orderDetailVo.setStatus((short)-1);
			orderDetailDao.updateById(orderDetailVo);
		}

		//处理库存
		List<OrderProduct> orderProductList = orderProductDao.queryByOrderId(orderId);
		for(OrderProduct orderProduct:orderProductList) {
			productSkuDao.addSkuStock(orderProduct.getProductSkuId(), orderProduct.getQuantity());
		}

		//优惠券
		OrderCoupon orderCoupon = orderCouponDao.queryUsingByOrderId(orderId);
		if(orderCoupon != null) {
			orderCoupon.setStatus((short)3);
			orderCouponDao.updateById(orderCoupon);

			CouponInfo couponInfo = couponInfoDao.queryById(orderCoupon.getCouponId());
			couponInfo.setStatus((short)1);
			couponInfo.setUseTime(couponInfo.getUseTime() + 1);
			couponInfoDao.updateById(couponInfo);
		}

	}

	@Override
	public List<OrderDetailVo> queryOrderDetailByOrderId(Long orderId) {
		List<OrderDetailVo> list = orderDetailDao.queryByOrderId(orderId);
		for(OrderDetailVo vo:list) {
			vo.setProductList(orderProductDao.queryByOrderDetailId(vo.getId()));
		}
		return list;
	}

	@Override
	public void updateDetailById(OrderDetail orderDetail) {
		orderDetailDao.updateById(orderDetail);
	}

	@Override
	public List<OrderInfoVo> queryOrderByMemberId(Long memberId) {
		List<OrderInfoVo> list = orderInfoDao.queryOrderByMemberId(memberId);
		for(OrderInfoVo orderVo:list) {
			List<OrderDetailVo> detailList = orderDetailDao.queryByOrderId(orderVo.getId());
			for(OrderDetailVo vo:detailList) {
				vo.setProductList(orderProductDao.queryByOrderDetailId(vo.getId()));
			}
			orderVo.setOrderDetailVoList(detailList);
		}
		return list;
	}
	@Override
	public List<OrderInfoVo> queryAllOrder(String orderNo, Short status,
			Integer start, Integer limit, Long businessId,String startDate,String endDate) {
		return orderInfoDao.queryAllOrder(orderNo, status, start, limit, businessId,startDate,endDate);
	}

	@Override
	public Integer queryCountAllOrder(String orderNo, Short status, Long businessId,String startDate,String endDate) {
		return orderInfoDao.queryCountAllOrder(orderNo, status, businessId,startDate, endDate);
	}

	@Override
	public OrderInfoVo queryOrderByOid(Long id) {
		return orderInfoDao.queryOrderByOid(id);
	}

	@Override
	public List<OrderDetail> queryAllOrderDetail(String ship_no, Short status,
			Integer start, Integer limit) {
		return orderDetailDao.queryAllOrderDetail(ship_no, status, start, limit);
	}

	@Override
	public int queryCountAllOrderDetail(String ship_no, Short status) {
		return orderDetailDao.queryCountAllOrderDetail(ship_no, status);
	}

	@Override
	public OrderDetailVo queryOrderDetailById(Long id) {
		return orderDetailDao.queryOrderDetailById(id);
	}

	@Override
	public List<OrderDetail> queryAllBusinessOrderDetail(Long businessId,
			String ship_no, Short status, Integer start, Integer limit) {
		return orderDetailDao.queryAllBusinessOrderDetail(businessId,ship_no, status, start, limit);
	}

	@Override
	public int queryCountBusinessOrderDetail(Long businessId, String ship_no,
			Short status) {
		return orderDetailDao.queryCountBusinessOrderDetail(businessId,ship_no, status);
	}

	@Override
	public void useCoupon(Long couponId, OrderInfo orderInfo) {
		CouponInfo couponInfo = couponInfoDao.queryById(couponId);
		//检验
		if(couponInfo.getStatus() != 1) {
			return;
		}
		if(Long.valueOf(couponInfo.getEndTime().substring(0,8) + "235959") <= Long.valueOf(DateUtil.formatCurrentDateTime())) {
			return;
		}
		List<CouponBatchRule> ruleList = couponBatchRuleDao.queryByBatchId(couponInfo.getCouponBatchId());
		Boolean allFlag = false;//标记是否全局产品
		List<Long> skuIdList = new ArrayList<Long>(); //记录优惠产品id
		for(CouponBatchRule couponBatchRule:ruleList) {
			if(couponBatchRule.getRuleType() == 1) {//sku
				if(couponBatchRule.getParamValue().equals("all")) {
					allFlag = true;
				}else {
					skuIdList.add(Long.valueOf(couponBatchRule.getParamValue()));
				}
			}
		}
		if(orderInfo.getTotalPrice() < couponInfo.getReductPrice() && allFlag) {
			return;
		}
		if(!allFlag && !checkOrderCoupon(skuIdList, couponInfo, orderInfo)) {
			return;
		}
		OrderCoupon orderCoupon = orderCouponDao.queryUsingByOrderId(orderInfo.getId());
		if(orderCoupon != null && orderCoupon.getCouponId().longValue() == couponId.longValue()) {
			return;
		}else if(orderCoupon != null) {//处理已有优惠券
			orderCoupon.setStatus((short)3);
			orderCouponDao.updateById(orderCoupon);

			CouponInfo couponInfoOld = couponInfoDao.queryById(orderCoupon.getCouponId());
			couponInfoOld.setStatus((short)1);
			couponInfoOld.setUseTime(couponInfoOld.getUseTime() + 1);
			couponInfoDao.updateById(couponInfoOld);
		}

		if(couponInfo.getType() == 1) {//满减
			orderInfo.setCouponPrice(couponInfo.getAmount());
			Double price = orderInfo.getTotalPrice() + orderInfo.getFreightPrice() - orderInfo.getCouponPrice();
			if(price < 0D) {
				price = 0D;
			}
			orderInfo.setOrderPrice(price);
			orderInfo.setRealPrice(orderInfo.getOrderPrice() - orderInfo.getIntegralPrice());
			orderInfoDao.updateById(orderInfo);
		}else if(couponInfo.getType() == 2) {//折扣
			orderInfo.setCouponPrice(NumberUtil.div(NumberUtil.mul(orderInfo.getTotalPrice(),(10 - couponInfo.getDiscount())),10));
			Double price = orderInfo.getTotalPrice() + orderInfo.getFreightPrice() - orderInfo.getCouponPrice();

			if(price < 0D) {
				price = 0D;
			}
			orderInfo.setOrderPrice(price);
			orderInfo.setRealPrice(orderInfo.getOrderPrice() - orderInfo.getIntegralPrice());
			orderInfoDao.updateById(orderInfo);
		}
		OrderCoupon orderCouponNew = new OrderCoupon();
		orderCouponNew.setCouponId(couponId);
		orderCouponNew.setCreateTime(DateUtil.formatCurrentDateTime());
		orderCouponNew.setOrderId(orderInfo.getId());
		orderCouponNew.setStatus((short)1);
		orderCouponDao.insert(orderCouponNew);


		if(couponInfo.getUseTime() <= 1) {
			couponInfo.setStatus((short)2);
			couponInfo.setUseTime(couponInfo.getUseTime() - 1);
		}else {
			couponInfo.setUseTime(couponInfo.getUseTime() - 1);
		}
		couponInfoDao.updateById(couponInfo);
	}

	private Boolean checkOrderCoupon(List<Long> skuIdList, CouponInfo couponInfo, OrderInfo orderInfo) {
		Double allPrice = 0D;
		List<OrderProduct> productList = orderProductDao.queryByOrderId(orderInfo.getId());
		for(OrderProduct orderProduct:productList) {
			if(skuIdList.contains(orderProduct.getProductSkuId())) {
				allPrice = allPrice + orderProduct.getPrice() * orderProduct.getQuantity();
			}
		}
		if(allPrice >= couponInfo.getReductPrice()) {
			return true;
		}
		return false;
	}

	@Override
	public void orderSucess(OrderInfo orderInfo) {
		System.out.println(1);
		orderInfo.setStatus((short)1);
		orderInfo.setPayTime(DateUtil.formatCurrentDateTime());
		orderInfo.setPayStatus((short)2);
		orderInfoDao.updateById(orderInfo);

		List<OrderDetailVo> detailList = orderDetailDao.queryByOrderId(orderInfo.getId());
		for(OrderDetail orderDetail:detailList) {
			orderDetail.setStatus((short)1);
			orderDetail.setPayStatus((short)2);
			orderDetailDao.updateById(orderDetail);
		}

		//优惠券处理
		OrderCoupon orderCoupon = orderCouponDao.queryUsingByOrderId(orderInfo.getId());
		if(orderCoupon != null) {
			orderCoupon.setStatus((short)2);
			orderCouponDao.updateById(orderCoupon);
		}

		//订单佣金处理
		orderCommissionHandle(orderInfo);

		//积分、经验值处理
		MemberInfo memberInfo = memberInfoDao.queryById(orderInfo.getMemberId());
		SettingInfo pointSetting=settingInfoDao.getInfoByType(orderInfo.getBusinessId(), "order_point_proportion");
		if(pointSetting!=null){
			memberInfo.setPoint(memberInfo.getPoint()+Integer.valueOf(pointSetting.getParamValue())/100*Math.round(orderInfo.getTotalPrice()));
		}
		SettingInfo experienceSetting=settingInfoDao.getInfoByType(orderInfo.getBusinessId(), "order_experience_proportion");
		if(experienceSetting!=null){
			memberInfo.setExperience(memberInfo.getExperience()+Integer.valueOf(experienceSetting.getParamValue())/100*Math.round(orderInfo.getTotalPrice()));
		}
		memberInfoDao.updateById(memberInfo);

	}

	@Override
	public void payPointOrder(OrderInfo orderInfo, List<OrderDetail> list,
			Long memberId, String address, String receiveMobile, String receiveName) {
		for(OrderDetail orderDetail:list) {
    		OrderDetail orderDetailDb = new OrderDetail();
    		orderDetailDb.setAddress(address);
    		orderDetailDb.setReceiveMobile(receiveMobile);
    		orderDetailDb.setReceiveName(receiveName);
    		orderDetailDb.setMessage(orderDetail.getMessage());
    		orderDetailDb.setId(orderDetail.getId());
    		orderDetailDb.setStatus((short)1);
    		orderDetailDb.setPayStatus((short)2);
    		orderDetailDao.updateById(orderDetailDb);
    	}

		orderInfo.setStatus((short)1);
		orderInfo.setPayStatus((short)2);
    	orderInfo.setIntegralPrice(orderInfo.getTotalPrice());
    	orderInfo.setOrderPrice(orderInfo.getTotalPrice() + orderInfo.getFreightPrice());
    	orderInfo.setRealPrice(orderInfo.getFreightPrice());
    	orderInfoDao.updateById(orderInfo);

    	MemberInfo memberInfo = memberInfoDao.queryById(memberId);
    	memberInfo.setPoint(memberInfo.getPoint() - orderInfo.getTotalPrice().longValue());
    	memberInfoDao.updateById(memberInfo);

    	// TODO
//    	MemberPointRecord memberPointRecord = new MemberPointRecord();
//    	memberPointRecord.setCreateTime(DateUtil.formatCurrentDateTime());
//    	memberPointRecord.setMemberId(memberId);
//    	memberPointRecord.setOrderId(orderInfo.getId());
//    	memberPointRecord.setPoint(orderInfo.getTotalPrice().longValue());
//    	memberPointRecord.setType((short)2);
//    	memberPointRecordDao.insert(memberPointRecord);

    	BaseOrder baseOrder = baseOrderDao.queryByTypeAndRefId(BaseOrder.BASE_ORDER_TYPE_ORDER, orderInfo.getId());
    	orderService.paySuccess(baseOrder.getId());
	}

	@Override
	public List<OrderProductVo> queryOrderProductByDateAndFarmId(@Param("startDate")String startDate, @Param("endDate")String endDate,
																 @Param("farmId")Long farmId) {
		return orderProductDao.queryOrderProductByDateAndFarmId(startDate, endDate, farmId);
	}


	@Override
	public List<OrderInfoVo> queryTodayOrder(String nowDate, Integer start, Integer limit, Long businessId) {
		return orderInfoDao.queryTodayOrder(nowDate,start,limit,businessId);
	}

	@Override
	public Integer queryCountTodayOrder(String nowDate, Long businessId) {
		return orderInfoDao.queryCountTodayOrder(nowDate,businessId);
	}

	@Override
	public List<OrderInfoVo> queryAllOrderRefund(String orderNo, Short status, Integer start, Integer limit, Long businessId, String startDate, String endDate) {
		List<OrderInfoVo> list=orderInfoDao.queryAllOrderRefund(orderNo, status, start, limit, businessId,startDate,endDate);
		for(OrderInfoVo orderInfoVo:list){
			orderInfoVo.setOrderRefund(orderRefundDao.selectByOrderId(orderInfoVo.getId()));
		}
		return list;
	}

	@Override
	public Integer queryCountAllOrderRefund(String orderNo, Short status, Long businessId, String startDate, String endDate) {
		return orderInfoDao.queryCountAllOrderRefund(orderNo, status, businessId,startDate, endDate);
}

	@Override
	public Integer addOrderRefund(OrderRefund orderRefund) {
		return orderRefundDao.insertSelective(orderRefund);
	}

	@Override
	public Integer refundSuccess(OrderRefund orderRefund) {
		OrderRefund orderRefundOld=orderRefundDao.selectByRefundNo(orderRefund.getRefundNo());

		orderRefund.setStatus(OrderRefund.STATUS_REPLIED);
		orderRefund.setId(orderRefundOld.getId());
		orderRefundDao.updateByPrimaryKeySelective(orderRefund);

		OrderInfo orderInfo=orderInfoDao.queryById(orderRefundOld.getOrderId());
		orderInfo.setStatus((short)-5);
		orderInfoDao.updateById(orderInfo);
		return null;
	}

	@Override
	public double calculateFreight(OrderInfo orderInfo,String province) {
		Double freightPrice=0D;
		List<OrderDetailVo> orderDetailVos=orderDetailDao.queryByOrderId(orderInfo.getId());
		OrderDetailVo orderDetailVo=orderDetailVos.get(0);
		List<OrderProduct> orderProducts=orderProductDao.queryByOrderId(orderInfo.getId());
		for(OrderProduct orderProduct:orderProducts){
			ProductSku productSku=productSkuDao.queryById(orderProduct.getProductSkuId());
			if(productSku.getFreePostage()!=null){  //避免之前sku数据没有设置是否包邮
				//计算不包邮的sku邮费
				if(productSku.getFreePostage().equals(ProductSku.NO_FREE_POSTAGE)){
					ProductLogisticsTemplate productLogisticsTemplate=productLogisticsTemplateDao.selectByPrimaryKey(productSku.getLogisticsTemplateId());
					if(productLogisticsTemplate!=null){ //避免之前sku数据没有设置物流模板
						List<ProductTemplateDetail> templateDetails=templateDetailDao.selectByTemplateId(productLogisticsTemplate.getId());
						ProductTemplateDetail addressTemplateDetail=null;
						for(ProductTemplateDetail productTemplateDetail:templateDetails){
							String provincesStr=productTemplateDetail.getShipArea();
							List<String> provinces= Arrays.asList(provincesStr.split(","));
							if(provinces.contains(province)){
								addressTemplateDetail=productTemplateDetail;
								break;
							}
						}
						//邮寄地址没有匹配到物流模板，则不计算邮费
						if(addressTemplateDetail!=null){
							freightPrice=NumberUtil.add(freightPrice,addressTemplateDetail.getFirstFreight());
							Double skuContinueFreight=0D;
							Double ContinueNumber; //续重续件次数
							if(productLogisticsTemplate.getType().equals(ProductLogisticsTemplate.TYPE_WERIGHT)){
								Double skuWeightSum=NumberUtil.mul(productSku.getWeight(),orderProduct.getQuantity());
								if(skuWeightSum>addressTemplateDetail.getFirstHeavy()){
									ContinueNumber=NumberUtil.div(NumberUtil.sub(skuWeightSum,addressTemplateDetail.getFirstHeavy()),addressTemplateDetail.getContinueHeavy());
									skuContinueFreight=NumberUtil.mul(addressTemplateDetail.getContinueFreight(),ContinueNumber);
									freightPrice=NumberUtil.add(freightPrice,skuContinueFreight);
								}
							}
							if(productLogisticsTemplate.getType().equals(ProductLogisticsTemplate.TYPE_NUMBER)){
								ContinueNumber=NumberUtil.sub(orderProduct.getQuantity(),addressTemplateDetail.getFirstHeavy());
								skuContinueFreight=NumberUtil.mul(addressTemplateDetail.getContinuePiece(),ContinueNumber/addressTemplateDetail.getContinuePiece());
								freightPrice=NumberUtil.add(freightPrice,skuContinueFreight);
							}
						}
					}
				}
			}
		}

		orderInfo.setFreightPrice(freightPrice);
		orderInfo.setRealPrice(NumberUtil.add(orderInfo.getRealPrice(),freightPrice));
		OrderDetail orderDetail=new OrderDetail();
		orderDetail.setId(orderDetailVo.getId());
		orderDetail.setFreightPrice(freightPrice);
		orderInfoDao.updateById(orderInfo);
		orderDetailDao.updateById(orderDetail);
		return freightPrice;
	}
}