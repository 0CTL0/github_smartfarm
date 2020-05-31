package com.smartfarm.base.farm.core.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.shop.core.dao.BaseOrderDao;
import com.smartfarm.base.shop.core.entity.BaseOrder;
import com.smartfarm.base.shop.core.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.util.ShipUtil;
import com.smartfarm.base.farm.core.dao.CrowdfundingOrderDao;
import com.smartfarm.base.farm.core.entity.CrowdfundingOrder;
import com.smartfarm.base.farm.core.entity.vo.CrowFundingOrderVo;
import com.smartfarm.base.farm.core.entity.vo.CrowdfundingOrderDetailVo;
import com.smartfarm.base.farm.core.manager.CrowdFundingOrderManager;

@Service("crowdFundingOrderManager")
public class CrowdFundingOrderManagerImpl implements CrowdFundingOrderManager{

	@Resource
	private CrowdfundingOrderDao crowdfundingOrderDao;
	@Resource
	private BaseOrderDao baseOrderDao;
	@Resource
	private OrderService orderService;

	@Override
	public List<CrowFundingOrderVo> queryOrderList(Short shipStatus,Short status,String orderNo,
			Integer start, Integer limit) {
		return crowdfundingOrderDao.queryOrderList(shipStatus,status,orderNo, start, limit);
	}

	@Override
	public int queryCountOrderList(Short shipStatus,Short status,String orderNo) {
		return crowdfundingOrderDao.queryCountOrderList(shipStatus,status,orderNo);
	}

	@Override
	public CrowFundingOrderVo queryOrderDetailById(Long id) {
		return crowdfundingOrderDao.queryOrderDetailById(id);
	}

	@Override
	public int updateById(CrowdfundingOrder crowdfundingOrder) {
		return crowdfundingOrderDao.updateById(crowdfundingOrder);
	}

	@Override
	public void updateDetailById(CrowdfundingOrder crowdfundingOrder) {
		String shipCode = crowdfundingOrder.getShipCode();

    	crowdfundingOrder.setShipTime(DateUtil.formatCurrentDateTime());
		
		crowdfundingOrder.setShipName(ShipUtil.getShipName(shipCode));
		
		crowdfundingOrder.setShipStatus(Short.valueOf("2"));
		crowdfundingOrderDao.updateById(crowdfundingOrder);
	}

	@Override
	public void updateOrderOk(CrowdfundingOrder crowdfundingOrder) {
		crowdfundingOrder.setShipStatus(Short.valueOf("3"));
		crowdfundingOrderDao.updateOrderOk(crowdfundingOrder);
	}

	@Override
	public List<CrowFundingOrderVo> queryCrowOrderList(Long id,Integer start,
			Integer limit) {
		return crowdfundingOrderDao.queryCrowOrderList(id,start, limit);
	}

	@Override
	public int queryCountCrowdList(Long id) {
		return crowdfundingOrderDao.queryCountCrowdList(id);
	}

	@Override
	public int updateBonusById(CrowdfundingOrder crowdfundingOrder) {
		crowdfundingOrder.setGrantTime(DateUtil.formatCurrentDateTime());
		return crowdfundingOrderDao.updateBonusById(crowdfundingOrder);
	}

	@Override
	public int updateAllBonus() {
		return crowdfundingOrderDao.updateAllBonus();
	}

	@Override
	public String saveCrowdfundingOrder(HttpServletRequest request,
			CrowdfundingOrder crowdfundingOrder) throws Exception{
		//用户id
		Long memberId = (Long) SessionUtil.getAttribute(request, SessionUtil.MEMBER_ID);
		
		crowdfundingOrder.setMemberId(memberId);
		crowdfundingOrder.setGrantTime(null);
		crowdfundingOrder.setOrderNo(RandomUtil.getOrderIdByUUId());
		crowdfundingOrder.setOrderTime(DateUtil.formatCurrentDateTime());
		crowdfundingOrder.setPayStatus((short)1);
		crowdfundingOrder.setShipCode(null);
		crowdfundingOrder.setShipName(null);
		crowdfundingOrder.setShipNo(null);
		crowdfundingOrder.setShipStatus((short)1);
		crowdfundingOrder.setShipTime(null);
		crowdfundingOrder.setStatus((short)1);
//		crowdfundingOrder.setRealAmount(crowdfundingOrder.getQuantity() * crowdfundingOrder.getPrice() - crowdfundingOrder.getDiscount());
		crowdfundingOrder.setRealAmount(crowdfundingOrder.getQuantity() * crowdfundingOrder.getPrice());
		crowdfundingOrder.setTotalAmount(crowdfundingOrder.getQuantity() * crowdfundingOrder.getPrice());
		crowdfundingOrderDao.saveCrowdfundingOrder(crowdfundingOrder);

		BaseOrder baseOrder = new BaseOrder();
		baseOrder.setStatus(BaseOrder.BASE_ORDER_TYPE_CROW);
		baseOrder.setRefId(crowdfundingOrder.getId());
		baseOrder.setType(BaseOrder.BASE_ORDER_TYPE_ORDER);
		baseOrderDao.insert(baseOrder);

		//加入订单定时器
		orderService.makeOrder(baseOrder.getId());
		return crowdfundingOrderDao.queryOrderNoById(crowdfundingOrder.getId());
	}

	@Override
	public List<CrowFundingOrderVo> getAllCrowdfundingOrderList(Short status,
			Short shipStatus) {
		return crowdfundingOrderDao.getAllCrowdfundingOrderList(status,shipStatus);
	}

	@Override
	public CrowFundingOrderVo getCrowdfundingOrderDetail(String orderNo) {
		return crowdfundingOrderDao.getCrowdfundingOrderDetail(orderNo);
	}

	@Override
	public CrowdfundingOrderDetailVo queryOrderDetailByOrderNo(String orderNo) {
		return crowdfundingOrderDao.queryOrderDetailByOrderNo(orderNo);
	}

	@Override
	public List<CrowdfundingOrderDetailVo> queryMemberOrderList(HttpServletRequest request) {
		Long memberId = (Long) SessionUtil.getAttribute(request, SessionUtil.MEMBER_ID);
		return crowdfundingOrderDao.queryMemberOrderList(memberId);
	}


	@Override
	public List<CrowdfundingOrderDetailVo> queryMemberOrderListWithStatus(HttpServletRequest request,
			 short status) {
		Long memberId = (Long) SessionUtil.getAttribute(request, SessionUtil.MEMBER_ID);
		return crowdfundingOrderDao.queryMemberOrderListWithStatus(memberId, status);
	}

	@Override
	public int cancelOrder(CrowdfundingOrder crowdfundingOrder) {
		crowdfundingOrder.setStatus((short)7);
		return crowdfundingOrderDao.cancelOrder(crowdfundingOrder);
	}

	@Override
	public int payTimeOut(Long id) {
		return crowdfundingOrderDao.payTimeOut(id);
	}

	@Override
	public CrowdfundingOrder queryOrderById(Long id) {
		return crowdfundingOrderDao.queryOrderById(id);
	}

}
