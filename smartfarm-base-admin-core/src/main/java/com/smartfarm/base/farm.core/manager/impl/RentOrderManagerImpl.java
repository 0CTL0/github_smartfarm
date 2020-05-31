package com.smartfarm.base.farm.core.manager.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.util.UploadUtil;
import com.smartfarm.base.farm.core.dao.AcreageInfoDao;
import com.smartfarm.base.farm.core.dao.LandInfoDao;
import com.smartfarm.base.farm.core.dao.ProgressInfoDao;
import com.smartfarm.base.farm.core.dao.RentOrderDao;
import com.smartfarm.base.farm.core.dao.SeedDetailDao;
import com.smartfarm.base.farm.core.dao.ShippingInfoDao;
import com.smartfarm.base.farm.core.entity.AcreageInfo;
import com.smartfarm.base.farm.core.entity.LandInfo;
import com.smartfarm.base.farm.core.entity.ProgressInfo;
import com.smartfarm.base.farm.core.entity.RentOrder;
import com.smartfarm.base.farm.core.entity.SeedDetail;
import com.smartfarm.base.farm.core.entity.SeedInfo;
import com.smartfarm.base.farm.core.entity.ShippingInfo;
import com.smartfarm.base.farm.core.entity.vo.RentOrderDetailVo;
import com.smartfarm.base.farm.core.entity.vo.RentOrderVo;
import com.smartfarm.base.farm.core.manager.RentOrderManager;

@Service("rentOrderManager")
public class RentOrderManagerImpl implements  RentOrderManager{
	@Resource
	private RentOrderDao rentOrderDao;
	@Resource
	private SeedDetailDao seedDetailDao;
	@Resource
	private ShippingInfoDao shippingInfoDao;
	@Resource
	private ProgressInfoDao progressInfoDao;
	@Resource
	private AcreageInfoDao acreageInfoDao;
	@Resource
	private LandInfoDao landInfoDao;

	@Override
	public List<RentOrderVo> getPageList(String orderNo, Short status,
			Integer start, Integer limit) {
		return rentOrderDao.selectPageList(orderNo, status, start, limit);
	}

	@Override
	public int countPageList(String orderNo, Short status) {
		return rentOrderDao.selectPageTotal(orderNo, status);
	}

	@Override
	public RentOrderVo getRentOrder(RentOrder rentOrder) {		
		return rentOrderDao.selectById(rentOrder);
	}

	@Override
	public List<SeedDetail> getSeedDatailByOrderId(SeedDetail seedDatail) {
		return seedDetailDao.selectByRentOrderId(seedDatail);
	}

	@Override
	public List<ShippingInfo> getShippingByOrderId(ShippingInfo shippingInfo) {
		return shippingInfoDao.selectByRentOrderId(shippingInfo);
	}

	@Override
	public int editRentOrder(RentOrder rentOrder) {
		return rentOrderDao.updateById(rentOrder);
	}

	@Override
	public int editSeedDetail(SeedDetail seedDetail) {
		return seedDetailDao.updateById(seedDetail);
	}

	@Override
	public List<ProgressInfo> getProgressInfoList(ProgressInfo progressInfo) {		
		return progressInfoDao.selectProgressList(progressInfo);
	}

	@Override
	public int insert(ProgressInfo progressInfo, MultipartFile[] files)
			throws IOException {
		String pathListStr = "";
		for (MultipartFile file : files) {
			String path = UploadUtil.uploadFile(file, "/upload/",RandomUtil.generateNumber(10));
			pathListStr = pathListStr + path + ";";
		}
		pathListStr = pathListStr.substring(0, pathListStr.length() - 1);
		progressInfo.setPics(pathListStr);
		
		progressInfo.setProjectType(ProgressInfo.TYPE_PLANT);
		progressInfo.setReportTime(DateUtil.formatCurrentDateTime());
		return progressInfoDao.insert(progressInfo);
	}

	@Override
	public int received(ShippingInfo shippingInfo) {
		RentOrder rentOrder=new RentOrder();
		rentOrder.setId(shippingInfo.getOrderId());
		rentOrder=rentOrderDao.selectById(rentOrder);
		//最后一次确认收货，则订单完成
		if(shippingInfo.getSerial()==(int)rentOrder.getShipTimes()){
			rentOrder.setStatus(RentOrder.STATUS_END);	
			rentOrderDao.updateById(rentOrder);
		}		
		shippingInfoDao.updateById(shippingInfo);
		return 0;
	}

	@Override
	public int shippingNow(ShippingInfo shippingInfo) {
		//第一次发货，生成所有配送的配送时间
		if(shippingInfo.getSerial()==1){
			List<ShippingInfo> shippingList=shippingInfoDao.selectByRentOrderId(shippingInfo);
			shippingList.set(0,shippingInfo);
			shippingList.get(0).setStatus(ShippingInfo.SHIPPING);
			//String dateTime=DateUtil.formatCurrentDateTime();
			RentOrder rentOrder=new RentOrder();
			rentOrder.setId(shippingInfo.getOrderId());
			rentOrder=rentOrderDao.selectById(rentOrder);
			int period=rentOrder.getPeriod();		
			Calendar calendar=Calendar.getInstance();   				
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
			for(int i=0;i<shippingList.size();i++){	
				calendar.add(Calendar.DAY_OF_MONTH, period*i);
				String shipTime=sdf.format(calendar.getTime());
				shippingList.get(i).setShipTime(shipTime);
				shippingInfoDao.updateById(shippingList.get(i));
			}
		}
		//其余的发货则直接添加发货信息
		else{
			shippingInfo.setStatus(ShippingInfo.SHIPPING);
			shippingInfoDao.updateById(shippingInfo);
		}
		return 0;
	}

	@Override
	public int insertRentOrder(HttpServletRequest request,RentOrder rentOrder, String seedDetails) {
		//获取用户id
		Long memberId = (Long) SessionUtil.getAttribute(request, SessionUtil.MEMBER_ID);
		//插入租借订单
		rentOrder.setOrderNo(UUID.randomUUID().toString().replaceAll("-", ""));
		rentOrder.setOrderTime(DateUtil.formatCurrentDateTime());
		rentOrder.setMemberId(memberId);
		rentOrder.setStatus((short)1);
		rentOrder.setPayStatus((short)1);
		int count = rentOrderDao.insert(rentOrder);
		
		//更新土地状态为以租
		AcreageInfo acreageInfo = acreageInfoDao.selectAcreageInfoById(rentOrder.getAcreageId());
		acreageInfo.setStatus((short)2);
		acreageInfoDao.updateById(acreageInfo);
		//解析字符后插入种子详细
		JSONArray ja = JSONArray.fromObject(seedDetails);
		@SuppressWarnings("unchecked")
		List<SeedInfo> infos = (List<SeedInfo>) JSONArray.toCollection(ja, SeedInfo.class);
		for (SeedInfo seedInfo : infos) {
			SeedDetail detail = new SeedDetail();
			detail.setOrderId(rentOrder.getId());
			detail.setName(seedInfo.getName());
			detail.setPrice(seedInfo.getPrice().doubleValue());
			detail.setArea(seedInfo.getArea());
			detail.setTotalAmount(seedInfo.getPrice().doubleValue() * seedInfo.getArea());
			detail.setSeedId(seedInfo.getId());
			detail.setPlantStatus((short)1);
			seedDetailDao.insert(detail);
		}
		
		//获得土地信息
		LandInfo landInfo = landInfoDao.selectLandInfoById(acreageInfo.getLandId());
		
		//根据土地最少配送周期来插入配送信息
		for(int i = 0;i<landInfo.getShipTimes();i++){
			ShippingInfo shippingInfo = new ShippingInfo();
			shippingInfo.setOrderId(rentOrder.getId());
			shippingInfo.setStatus((short)1);
			shippingInfo.setSerial(i+1);
			shippingInfo.setAddress(rentOrder.getAddress());
			shippingInfo.setAdminUserId(memberId);
			shippingInfo.setReceiveMobile(rentOrder.getReceiveMobile());
			shippingInfo.setReceiveName(rentOrder.getRentName());
			shippingInfoDao.insert(shippingInfo);
		}
		return count;
	}

	@Override
	public List<RentOrderVo> queryRentOrderUnPayList(short payStatus,HttpServletRequest request) {
		Long memberId = (Long) SessionUtil.getAttribute(request, SessionUtil.MEMBER_ID);
		return rentOrderDao.queryRentOrderUnPayList(payStatus,memberId);
	}

	@Override
	public List<RentOrderVo> queryRentOrderPlantList(short plantStatus,
			HttpServletRequest request) {
		Long memberId = (Long) SessionUtil.getAttribute(request, SessionUtil.MEMBER_ID);
		List<RentOrderVo> rentList = rentOrderDao.queryRentOrderPlantList(plantStatus, memberId);
		for (RentOrderVo rentOrderVo : rentList) {
			//根据订单id查询该订单的所有配送消息
			List<ShippingInfo> shippingInfos = shippingInfoDao.queryShipInfoByOrderId(rentOrderVo.getId());
		/*	if(shippingInfos.get(shippingInfos.size() - 1).getStatus() != 1){
				if(shippingInfos.get(shippingInfos.size() - 1).getSerial() == rentOrderVo.getLeastShipTimes()){
					
				}
			}*/
			boolean flag = true;
			for(int i = 0;i<shippingInfos.size()&&flag;i++){
				if(shippingInfos.get(i).getStatus() == 1 && i > 0){
					//设置下次配送时间
					try {
						rentOrderVo.setNextDeliveryTime(DateUtil.formatAddDateTime(shippingInfos.get(i - 1).getShipTime()+"000000", rentOrderVo.getPeriod()));
					} catch (Exception e) {
						e.printStackTrace();
					}
					flag = false;
				}
			}

			try {
				rentOrderVo.setOverdueTimeString(DateUtil.formatAddDateTime(rentOrderVo.getOrderTime(), rentOrderVo.getPeriod()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rentList;
	}

	@Override
	public RentOrderDetailVo queryRentOrderStatusById(Long orderId) {
		RentOrderDetailVo rentOrderVo = rentOrderDao.queryRentOrderStatusById(orderId);
		try {
			rentOrderVo.setOverdueTimeString(DateUtil.formatAddDateTime(rentOrderVo.getOrderTime(), rentOrderVo.getPeriod()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rentOrderVo;
	}

	@Override
	public int cancelRentOrder(RentOrder rentOrder) {
		rentOrder.setStatus((short)7);
		return rentOrderDao.updateById(rentOrder);
	}

	
}
