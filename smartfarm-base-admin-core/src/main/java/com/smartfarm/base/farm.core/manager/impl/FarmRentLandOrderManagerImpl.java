package com.smartfarm.base.farm.core.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.smartfarm.base.util.NumberUtil;
import com.smartfarm.base.farm.core.dao.SettingInfoDao;
import com.smartfarm.base.shop.core.dao.MemberInfoDao;
import com.smartfarm.base.shop.core.entity.MemberInfo;
import org.springframework.stereotype.Service;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.farm.core.dao.FarmMemberLandDao;
import com.smartfarm.base.farm.core.dao.FarmRentLandInfoDao;
import com.smartfarm.base.farm.core.dao.FarmRentLandOrderDao;
import com.smartfarm.base.farm.core.entity.FarmMemberLand;
import com.smartfarm.base.farm.core.entity.FarmRentLandInfo;
import com.smartfarm.base.farm.core.entity.FarmRentLandOrder;
import com.smartfarm.base.farm.core.entity.vo.FarmRentLandOrderVo;
import com.smartfarm.base.farm.core.manager.FarmRentLandOrderManager;
import com.smartfarm.base.shop.core.dao.BaseOrderDao;
import com.smartfarm.base.shop.core.entity.BaseOrder;
import com.smartfarm.base.shop.core.service.OrderService;

@Service("/farmRentLandOrderManagerImpl")
public class FarmRentLandOrderManagerImpl implements FarmRentLandOrderManager {
    @Resource
    private FarmRentLandOrderDao farmRentLandOrderDao;
    @Resource
    private FarmRentLandInfoDao farmRentLandInfoDao;
    @Resource
    private FarmMemberLandDao farmMemberLandDao;
    @Resource
    private BaseOrderDao baseOrderDao;
    @Resource
    private OrderService orderService;
    @Resource
    private SettingInfoDao settingInfoDao;
    @Resource
    private MemberInfoDao memberInfoDao;


    @Override
    public List<FarmRentLandOrder> getRentLandOrderList(Long farmId, String orderCode, String rentName, Short status, Integer start, Integer limit) {
        return farmRentLandOrderDao.selectPageList(farmId,orderCode,rentName,status,start,limit);
    }

    @Override
    public int countRentLandOrderTotal(Long farmId, String orderCode, String rentName, Short status) {
        return farmRentLandOrderDao.selectPageTotal(farmId,orderCode,rentName,status);
    }

    @Override
    public FarmRentLandOrder getRentOrderById(Long id) {
        return farmRentLandOrderDao.selectByPrimaryKey(id);
    }

    @Override
    public int editFarmRentOrder(FarmRentLandOrder farmRentLandOrder) {
        return farmRentLandOrderDao.updateByPrimaryKeySelective(farmRentLandOrder);
    }

    @Override
    public FarmRentLandOrder getRentOrderByOrderCode(String orderCode) {
        return farmRentLandOrderDao.selectByOrderCode(orderCode);
    }

    @Override
    public synchronized Boolean createRentOrder (FarmRentLandOrder farmRentLandOrder) throws Exception {
    	List<FarmRentLandOrder> list = farmRentLandOrderDao.queryByMemberIdAndLandId(farmRentLandOrder.getMemberId(), farmRentLandOrder.getRentLandId());
    	for(FarmRentLandOrder vo:list) {
    		BaseOrder baseOrder = baseOrderDao.queryByTypeAndRefId(BaseOrder.BASE_ORDER_TYPE_RENT, vo.getId());
            orderService.cleanOrder(baseOrder.getId());
    	}
    	
        //判断所租赁土地的状态
        FarmRentLandInfo farmRentLandInfo=farmRentLandInfoDao.selectByPrimaryKey(farmRentLandOrder.getRentLandId());
        if(farmRentLandInfo.getStatus().equals(FarmRentLandInfo.STATUS_ENALBE)){
            return false;
        }
        //生成订单
        farmRentLandOrder.setOrderCode(RandomUtil.generateNumber(4));
        farmRentLandOrder.setRentDate(DateUtil.formatCurrentDateTime());
        farmRentLandOrder.setStatus(FarmRentLandOrder.STATUS_UNPAY);
        farmRentLandOrder.setPayStatus(FarmRentLandOrder.PAYSTATUS_UNPAY);
        farmRentLandOrderDao.insertSelective(farmRentLandOrder);
        //处理租赁土地信息
        farmRentLandInfo.setStatus(FarmRentLandInfo.STATUS_ENALBE);
        farmRentLandInfoDao.updateByPrimaryKeySelective(farmRentLandInfo);
        
        BaseOrder baseOrder = new BaseOrder();
		baseOrder.setRefId(farmRentLandOrder.getId());
		baseOrder.setStatus(BaseOrder.BASE_ORDER_STATUS_INIT);
		baseOrder.setType(BaseOrder.BASE_ORDER_TYPE_RENT);
		baseOrderDao.insert(baseOrder);
        //加入订单定时器
        Map<String, Object> map = orderService.makeOrder(baseOrder.getId());
        farmRentLandOrder.setPayEndTime(DateUtil.formatAddMinuteTime(farmRentLandOrder.getRentDate(), Integer.valueOf(String.valueOf(map.get("timeOut")))));
        farmRentLandOrderDao.updateByPrimaryKeySelective(farmRentLandOrder);
        return true;
    }


    @Override
    public List<FarmRentLandOrderVo> getAllRentOrder(Long memberId) {
        return farmRentLandOrderDao.selectAllRentOrder(memberId);
    }

    @Override
    public int removeRentOrder(Long id) {
        return farmRentLandOrderDao.deleteById(id);
    }

    @Override
    public int cleanOrder(Long id) {
        FarmRentLandOrder farmRentLandOrder=farmRentLandOrderDao.selectByPrimaryKey(id);
        BaseOrder baseOrder = baseOrderDao.queryByTypeAndRefId(BaseOrder.BASE_ORDER_TYPE_RENT, farmRentLandOrder.getId());
        orderService.cleanOrder(baseOrder.getId());
        return 0;
    }

    @Override
    public int addFarmMemberLand(FarmMemberLand farmMemberLand) {
        return farmMemberLandDao.insert(farmMemberLand);
    }

	@Override
	public int orderCancel(Long id) {
		FarmRentLandOrder farmRentLandOrder=farmRentLandOrderDao.selectByPrimaryKey(id);
        farmRentLandOrder.setStatus(FarmRentLandOrder.STATUS_CANCEL);
        farmRentLandOrderDao.updateByPrimaryKeySelective(farmRentLandOrder);
        
        FarmRentLandInfo farmRentLandInfo=farmRentLandInfoDao.selectByPrimaryKey(farmRentLandOrder.getRentLandId());
        farmRentLandInfo.setStatus(FarmRentLandInfo.STATUS_ABLE);
        farmRentLandInfoDao.updateByPrimaryKeySelective(farmRentLandInfo);
        return 0;
	}

	@Override
	public void orderOutTime(Long id) {
		FarmRentLandOrder farmRentLandOrder=farmRentLandOrderDao.selectByPrimaryKey(id);
        farmRentLandOrder.setStatus(FarmRentLandOrder.STATUS_OVERTIME);
        farmRentLandOrderDao.updateByPrimaryKeySelective(farmRentLandOrder);
        
        FarmRentLandInfo farmRentLandInfo=farmRentLandInfoDao.selectByPrimaryKey(farmRentLandOrder.getRentLandId());
        farmRentLandInfo.setStatus(FarmRentLandInfo.STATUS_ABLE);
        farmRentLandInfoDao.updateByPrimaryKeySelective(farmRentLandInfo);
	}

    @Override
    public void orderSuccess(FarmRentLandOrder farmRentLandOrder) {
        //更新租地订单
        farmRentLandOrderDao.updateByPrimaryKeySelective(farmRentLandOrder);
        //生成用户的土地记录
        FarmRentLandInfo farmRentLandInfo=farmRentLandInfoDao.selectByPrimaryKey(farmRentLandOrder.getRentLandId());
        FarmMemberLand farmMemberLand=new FarmMemberLand();
        farmMemberLand.setRentLandId(farmRentLandOrder.getRentLandId());
        farmMemberLand.setExpiration(farmRentLandOrder.getRentTime());
        farmMemberLand.setRentOrderId(farmRentLandOrder.getId());
        farmMemberLand.setMemberId(farmRentLandOrder.getMemberId());
        farmMemberLand.setStatus(FarmMemberLand.STATUS_UNINVALID);
        farmMemberLand.setAlias(farmRentLandInfo.getName());
        farmMemberLand.setAres(farmRentLandInfo.getArea());
        farmMemberLandDao.insert(farmMemberLand);
        //赠送积分
        Long farmId=farmRentLandInfoDao.selectFarmId(farmRentLandOrder.getRentLandId());
        String settingCode="rent_present";
        String pointProportion=settingInfoDao.selectValuesBycodeAndFarmId(settingCode,farmId);
        MemberInfo memberInfo=memberInfoDao.queryById(farmRentLandOrder.getMemberId());
        if(pointProportion!=null&&pointProportion!=""){
            Long point=new Double(farmRentLandOrder.getPrice()*Integer.valueOf(pointProportion)/100).longValue();
            memberInfo.setPoint(memberInfo.getPoint()+point);
        }
        memberInfoDao.updateById(memberInfo);
    }
}


