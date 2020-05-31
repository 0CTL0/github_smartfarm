package com.smartfarm.base.shop.core.manager.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.shop.core.dao.BaseOrderDao;
import com.smartfarm.base.shop.core.dao.MemberInfoDao;
import com.smartfarm.base.shop.core.dao.MemberPointOrderDao;
import com.smartfarm.base.shop.core.entity.BaseOrder;
import com.smartfarm.base.shop.core.entity.MemberInfo;
import com.smartfarm.base.shop.core.entity.MemberPointOrder;
import com.smartfarm.base.shop.core.manager.MemberPointOrderManager;
import com.smartfarm.base.shop.core.service.OrderService;


@Service
public class MemberPointOrderManagerImpl implements MemberPointOrderManager {
	@Resource
	private OrderService orderService;
	@Resource
	private BaseOrderDao baseOrderDao;
	@Resource
	private MemberPointOrderDao memberPointOrderDao;
	@Resource
	private MemberInfoDao memberInfoDao;


	@Override
	public Boolean createOrder(MemberPointOrder memberPointOrder) throws Exception {
		//生成订单
		memberPointOrder.setCreateTime(DateUtil.formatCurrentDateTime());
		memberPointOrder.setStatus((short)1);
		memberPointOrder.setOrderNo(RandomUtil.generateNumber(4));
		memberPointOrderDao.insert(memberPointOrder);
		
		BaseOrder baseOrder = new BaseOrder();
		baseOrder.setStatus(BaseOrder.BASE_ORDER_STATUS_INIT);
		baseOrder.setRefId(memberPointOrder.getId());
		baseOrder.setType(BaseOrder.BASE_ORDER_TYPE_POINT);
		baseOrderDao.insert(baseOrder);
		
		//加入订单定时器
		Map<String, Object> map = orderService.makeOrder(baseOrder.getId());
		memberPointOrder.setEndTime(DateUtil.formatAddMinuteTime(memberPointOrder.getCreateTime(), Integer.valueOf(String.valueOf(map.get("timeOut")))));
		memberPointOrderDao.updateById(memberPointOrder);
		return true;
	}

	@Override
	public MemberPointOrder queryById(Long id) {
		return memberPointOrderDao.queryById(id);
	}

	@Override
	public void updateById(MemberPointOrder memberPointOrder) {
		memberPointOrderDao.updateById(memberPointOrder);
	}

	@Override
	public MemberPointOrder queryByOrderNo(String orderNo) {
		return memberPointOrderDao.queryByOrderNo(orderNo);
	}


	@Override
	public void orderSucess(MemberPointOrder memberPointOrder) {
		memberPointOrder.setStatus((short)3);
		memberPointOrderDao.updateById(memberPointOrder);
		
		MemberInfo memberInfo = memberInfoDao.queryById(memberPointOrder.getMemberId());
		Long point = memberInfo.getPoint() == null?0L:memberInfo.getPoint();
		memberInfo.setPoint(point + memberPointOrder.getPrice().longValue());
		memberInfoDao.updateById(memberInfo);
	}

}