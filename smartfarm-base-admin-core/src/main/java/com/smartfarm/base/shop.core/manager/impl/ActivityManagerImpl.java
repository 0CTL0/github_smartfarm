package com.smartfarm.base.shop.core.manager.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.util.UploadUtil;
import com.smartfarm.base.shop.core.dao.ActivityInfoDao;
import com.smartfarm.base.shop.core.dao.ActivityPriceDao;
import com.smartfarm.base.shop.core.dao.ActivityRegistrationDao;
import com.smartfarm.base.shop.core.dao.BaseOrderDao;
import com.smartfarm.base.shop.core.entity.ActivityInfo;
import com.smartfarm.base.shop.core.entity.ActivityPrice;
import com.smartfarm.base.shop.core.entity.ActivityRegistration;
import com.smartfarm.base.shop.core.entity.BaseOrder;
import com.smartfarm.base.shop.core.entity.vo.ActivityInfoVo;
import com.smartfarm.base.shop.core.entity.vo.ActivityRegistrationVo;
import com.smartfarm.base.shop.core.manager.ActivityManager;
import com.smartfarm.base.shop.core.service.OrderService;

@Service("activityManager")
public class ActivityManagerImpl implements ActivityManager {
	@Resource
	private ActivityInfoDao activityInfoDao;
	@Resource
	private ActivityPriceDao activityPriceDao;
	@Resource
	private ActivityRegistrationDao activityRegistrationDao;
	@Resource
	private BaseOrderDao baseOrderDao;
	@Resource
	private OrderService orderService;

	@Override
	public List<ActivityInfo> queryAllActivityInfo(String name, Integer start,
			Integer limit, Long businessId) {
		return activityInfoDao.selectActivityInfoList(name, start, limit,businessId);
	}

	@Override
	public int countActivityList(String name, Long businessId) {
		return activityInfoDao.selectActivityTotal(name,businessId);
	}

	@Override
	public int insert(ActivityInfo activity, MultipartFile file,List<ActivityPrice> priceList)
			throws IOException {
		String path = UploadUtil.uploadFile(file, "/upload/",  DateUtil.formatCurrentDateTime());		
		String createTime=DateUtil.formatCurrentDateTime();
        Short status=2;
        activity.setPicUrl(path);
        activity.setCreateTime(createTime);
        activity.setStatus(status);
        activityInfoDao.insert(activity);
        for(ActivityPrice activityPrice:priceList){
        	activityPrice.setActivityId(activity.getId());
        	 DecimalFormat df = new DecimalFormat("#.00");
             double price=Double.parseDouble(df.format(activityPrice.getPrice()));
             activityPrice.setPrice(price);
        	activityPriceDao.insert(activityPrice);
        }
       
		return 0;
	}

	@Override
	public Object selectById(Long id) {
		return activityInfoDao.selectActivityById(id);
	}

	@Override
	public List<ActivityPrice> selectActivityPriceList(Long id) {
		return activityPriceDao.selectActivityList(id);
	}

	@Override
	public int deleteActivityPrice(Long id) {	
		return activityPriceDao.delectById(id);
	}

	@Override
	public int editActivityInfo(ActivityInfo activity, MultipartFile file,
			List<ActivityPrice> priceList) throws IOException {
		if(file!=null){
			String path = UploadUtil.uploadFile(file, "/upload/",  DateUtil.formatCurrentDateTime());
			activity.setPicUrl(path);
		}
		activityInfoDao.updateById(activity);
		for(ActivityPrice activityPrice : priceList){
			if(activityPrice.getActivityId()==null){
				activityPrice.setActivityId(activity.getId());
				activityPriceDao.insert(activityPrice);
			}
			else{
				activityPriceDao.updateByPrimaryKeySelective(activityPrice);
			}
		}
		return 0;
	}

	@Override
	public int changeStatus(Long id) {
		ActivityInfo activityInfo=activityInfoDao.selectActivityById(id);
		short status=activityInfo.getStatus();	
		if (status==1) {		
			status=2;
			activityInfo.setStatus(status);
		}else {
			status=1;
			activityInfo.setStatus(status);
		}
		return activityInfoDao.updateById(activityInfo);
	}
	

	@Override
	public ActivityInfo getActivity(Long activityId) {		
		return activityInfoDao.selectActivityById(activityId);
	}

	@Override
	public int countActivityRegistrationList(ActivityRegistration activityRegistration) {
		return activityRegistrationDao.countActivityRegistList(activityRegistration);
	}

	@Override
	public List<ActivityRegistrationVo> queryActivityRegistrationList(
			ActivityRegistration activityRegistration, Integer start,
			Integer limit) {
		return activityRegistrationDao.selectActivityRegistList(activityRegistration, start, limit);
	}

	@Override
	public ActivityRegistrationVo searchActivityRegist(String ticketNo,Long businessId) {
		return activityRegistrationDao.selectActivityRegistration(ticketNo,businessId);
	}

	@Override
	public List<ActivityInfoVo> queryUseableList(String nowDate, Long businessId) {
		return activityInfoDao.queryUseableList(nowDate,businessId);
	}

	@Override
	public ActivityInfoVo queryDetailActivityById(Long id) {
		ActivityInfoVo vo = activityInfoDao.queryVoById(id);
		vo.setPriceList(activityPriceDao.selectActivityList(id));
		vo.setRegistrationList(activityRegistrationDao.querySuccessListByActivityId(id));
		return vo;
	}

	@Override
	public int updateRemain(Integer num, Long id) {
		return activityInfoDao.updateRemain(num, id);
	}

	@Override
	public Long addRegistration(Long activityId, Long priceId, Integer num, Long memberId) throws Exception {
		ActivityPrice activityPrice = activityPriceDao.queryById(priceId);
		
		ActivityRegistration activityRegistration = new ActivityRegistration();
		activityRegistration.setActivityId(activityId);
		activityRegistration.setActivityPriceId(priceId);
		activityRegistration.setCreateTime(DateUtil.formatCurrentDateTime());
		activityRegistration.setMemberId(memberId);
		activityRegistration.setStatus((short)1);
		activityRegistration.setOrderNo(RandomUtil.generateNumber(4));
		activityRegistration.setJoinNum(activityPrice.getJoinNum());
		activityRegistration.setName(activityPrice.getName());
		activityRegistration.setTicketNum(num);
		activityRegistration.setTotalNum(num * activityPrice.getJoinNum());
		activityRegistration.setRealPrice(activityPrice.getPrice() * num);
		activityRegistration.setUnitPrice(activityPrice.getPrice());
		activityRegistration.setPriceRemark(activityPrice.getRemark());
		activityRegistration.setPriceName(activityPrice.getName());
		activityRegistrationDao.insert(activityRegistration);
		
		BaseOrder baseOrder = new BaseOrder();
		baseOrder.setRefId(activityRegistration.getId());
		baseOrder.setStatus(BaseOrder.BASE_ORDER_STATUS_INIT);
		baseOrder.setType(BaseOrder.BASE_ORDER_TYPE_ACTIVITY);
		baseOrderDao.insert(baseOrder);
		
		//加入订单定时器
		Map<String, Object> map = orderService.makeOrder(baseOrder.getId());
		activityRegistration.setPayEndTime(DateUtil.formatAddMinuteTime(activityRegistration.getCreateTime(), Integer.valueOf(String.valueOf(map.get("timeOut")))));
		activityRegistrationDao.updateById(activityRegistration);
		
		return activityRegistration.getId();
	}

	@Override
	public ActivityPrice queryActivityPrice(Long id) {
		return activityPriceDao.queryById(id);
	}

	@Override
	public void orderCancel(Long id) {
		ActivityRegistration activityRegistration = activityRegistrationDao.queryById(id);
		activityRegistration.setStatus((short)4);
		activityRegistrationDao.updateById(activityRegistration);
		
		ActivityInfo ActivityInfo = activityInfoDao.queryVoById(activityRegistration.getActivityId());
		activityInfoDao.addRemain(activityRegistration.getTotalNum(), ActivityInfo.getId());
	}

	@Override
	public void orderOutTime(Long id) {
		ActivityRegistration activityRegistration = activityRegistrationDao.queryById(id);
		activityRegistration.setStatus((short)3);
		activityRegistrationDao.updateById(activityRegistration);
		
		ActivityInfo ActivityInfo = activityInfoDao.queryVoById(activityRegistration.getActivityId());
		activityInfoDao.addRemain(activityRegistration.getTotalNum(), ActivityInfo.getId());
	}

	@Override
	public ActivityRegistration queryRegistrationById(Long id) {
		return activityRegistrationDao.queryById(id);
	}

	@Override
	public ActivityInfo queryActivityById(Long id) {
		return activityInfoDao.queryVoById(id);
	}

	@Override
	public void updateRegistration(ActivityRegistration activityRegistration) {
		activityRegistrationDao.updateById(activityRegistration);
	}

	@Override
	public void successOrder(String orderNo, String traderNo) {
		ActivityRegistration activityRegistration = activityRegistrationDao.queryByOrderNo(orderNo);
		activityRegistration.setStatus((short)2);
		activityRegistration.setTraderNo(traderNo);
		activityRegistration.setTicketStatus((short)1);
		activityRegistration.setTicketNo(activityRegistration.getMemberId() + RandomUtil.randomStr(9));
		activityRegistrationDao.updateById(activityRegistration);
		
		BaseOrder baseOrder = baseOrderDao.queryByTypeAndRefId(BaseOrder.BASE_ORDER_TYPE_ACTIVITY, activityRegistration.getId());
		orderService.paySuccess(baseOrder.getId());
	}

	@Override
	public List<ActivityRegistrationVo> queryRegistationListByMemberId(
			Long memberId) {
		return activityRegistrationDao.queryListByMemberId(memberId);
	}

	@Override
	public int editActivity(ActivityInfo activity, List<ActivityPrice> priceList) {
		activityInfoDao.updateById(activity);
		for(ActivityPrice activityPrice : priceList){
			if(activityPrice.getActivityId()==null){
				activityPrice.setActivityId(activity.getId());
				activityPriceDao.insert(activityPrice);
			}
			else{
				activityPriceDao.updateByPrimaryKeySelective(activityPrice);
			}
		}
		return 0;
	}

	@Override
	public int addActivity(ActivityInfo activity, List<ActivityPrice> priceList) {
		String createTime=DateUtil.formatCurrentDateTime();
        Short status=2;
        activity.setRemain(activity.getJoinNum());
        activity.setCreateTime(createTime);
        activity.setStatus(status);
        activityInfoDao.insert(activity);
        for(ActivityPrice activityPrice:priceList){
        	activityPrice.setActivityId(activity.getId());
        	 DecimalFormat df = new DecimalFormat("#.00");
             double price=Double.parseDouble(df.format(activityPrice.getPrice()));
             activityPrice.setPrice(price);
        	activityPriceDao.insert(activityPrice);
        }
		return 0;
	}

	@Override
	public List<ActivityInfoVo> queryActivityForAdvance(Long farmId) {
		return activityInfoDao.queryActivityForAdvance(farmId);
	}

	@Override
	public List<ActivityInfoVo> queryActivityForAdvanceAdd(Long farmId) {
		return activityInfoDao.queryActivityForAdvanceAdd(farmId);
	}


}
