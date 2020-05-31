package com.smartfarm.base.farm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import com.smartfarm.base.farm.core.entity.vo.FarmDelegateExpressVo;
import com.smartfarm.base.farm.core.entity.vo.FarmDelegatePlantVo;
import com.smartfarm.base.farm.core.entity.vo.FarmMemberCropsVo;
import org.springframework.stereotype.Service;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.GenerateOrderNoUtil;
import com.smartfarm.base.farm.core.dao.FarmCropsInfoDao;
import com.smartfarm.base.farm.core.dao.FarmDelegateExpressDao;
import com.smartfarm.base.farm.core.dao.FarmDelegateOrderDao;
import com.smartfarm.base.farm.core.dao.FarmDelegatePlantDao;
import com.smartfarm.base.farm.core.dao.FarmMemberCropsDao;
import com.smartfarm.base.farm.core.dao.WateringRecordDao;
import com.smartfarm.base.farm.core.dao.WorkingLogDao;
import com.smartfarm.base.farm.core.entity.FarmCropsInfo;
import com.smartfarm.base.farm.core.entity.FarmDelegateExpress;
import com.smartfarm.base.farm.core.entity.FarmDelegateOrder;
import com.smartfarm.base.farm.core.entity.FarmDelegatePlant;
import com.smartfarm.base.farm.core.entity.FarmMemberCrops;
import com.smartfarm.base.farm.core.entity.WateringRecord;
import com.smartfarm.base.farm.core.entity.WorkingLog;
import com.smartfarm.base.farm.core.entity.vo.FarmDelegateOrderVo;
import com.smartfarm.base.farm.core.manager.FarmDelegateOrderManager;
import com.smartfarm.base.monitor.core.dao.ControlNodeDao;
import com.smartfarm.base.monitor.core.dao.GatewayNodeDao;
import com.smartfarm.base.monitor.core.entity.ControlNode;
import com.smartfarm.base.monitor.core.entity.GatewayNode;
import com.smartfarm.base.monitor.core.service.EventService;
import com.smartfarm.base.shop.core.dao.MemberInfoDao;
import com.smartfarm.base.shop.core.entity.MemberInfo;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月09日 14:32:58
 * @version 1.0
 */
@Service("farmDelegateOrderManager")
public class FarmDelegateOrderManagerImpl implements FarmDelegateOrderManager {

	@Resource
	private FarmDelegateOrderDao delegateOrderDao;
	@Resource
	private FarmDelegatePlantDao delegatePlantDao;
	@Resource
	private MemberInfoDao memberInfoDao;
	@Resource
	private FarmDelegateExpressDao delegateExpressDao;
	@Resource
	private ControlNodeDao controlNodeDao;
	@Resource
	private GatewayNodeDao gatewayNodeDao;
	@Resource
	private EventService eventService;
	@Resource
	private WateringRecordDao wateringRecordDao;
	@Resource
	private FarmMemberCropsDao memberCropsDao;
	@Resource
	private FarmCropsInfoDao cropsInfoDao;
	@Resource
	private WorkingLogDao workingLogDao;
	
	
	@Override
	public int addPlantDelegateRecord(FarmDelegateOrder delegateOrder,FarmDelegatePlant delegatePlant,MemberInfo memberInfo) {
		delegateOrder.setApplyNo(GenerateOrderNoUtil.getOrderNo());
		delegateOrder.setApplyDate(DateUtil.formatCurrentDate());
		delegateOrder.setWorkDate(delegateOrder.getWorkDate().replaceAll("-", ""));
		delegateOrder.setWorkType("Planting");
		delegateOrder.setStatus((short) 1);
		int addOrder = delegateOrderDao.insert(delegateOrder);
		
		Long orderId = delegateOrderDao.getIdByApplyNo(delegateOrder.getApplyNo());
		delegatePlant.setOrderId(orderId);
		int addPlant = delegatePlantDao.insert(delegatePlant);
		
		memberInfo.setPoint(memberInfo.getPoint()-delegateOrder.getIntegral());
		int updateMI = memberInfoDao.updateIntegralById(memberInfo);
		
		FarmCropsInfo cropsInfo = cropsInfoDao.getCropById(delegatePlant.getCropsId());
		WorkingLog log = new WorkingLog();
		log.setPlanDetailId(delegateOrder.getId());
		log.setPlanTime(delegateOrder.getApplyDate());
		log.setExecuteTime(delegateOrder.getApplyDate());
		log.setActualExecuteTime(DateUtil.formatCurrentDateTime());
		log.setOperator(memberInfo.getId());
		log.setMemberLandId(delegateOrder.getMemberLandId());
		log.setTaskType("Planting");
		log.setStatus(WorkingLog.STATUS_UNABLE);
		log.setIsShow((WorkingLog.STATUS_ABLED));
		log.setContent("委托农场种植"+cropsInfo.getName()+delegatePlant.getPlantArea()+"㎡，消耗"+delegateOrder.getIntegral()+"积分");
		int addLog = workingLogDao.insert(log);
		
		return addOrder*addPlant*updateMI*addLog;
	}

	@Override
	public String performWatering(Long landId,Long operator,Integer waterDuration) {
		ControlNode controlNode = controlNodeDao.getNodeByLandId(landId);
		GatewayNode gatewayNode = gatewayNodeDao.selectById(controlNode.getGatewayId());
		
		WateringRecord record = new WateringRecord();
		record.setMemberLandId(landId);
		record.setWateringDate(DateUtil.formatCurrentDate());
		record.setWateringTime(DateUtil.formatCurrentTime());
		
		String topic = "stds/down/CT/" + gatewayNode.getProductCode() + "/sLoop";//拼接topic
		String[] codes = controlNode.getProductCode().split("-");
		String sensorNodeCode = codes[0];
		String locate = codes[1];
		String json = "{\"id\":"+sensorNodeCode+",\"sid\":" + locate+",\"val\":1,\"time\":" + waterDuration + "}";
//		eventService.sendProductMsg(topic, json);
		wateringRecordDao.insert(record);
		
		WorkingLog log = new WorkingLog();
		log.setPlanTime(DateUtil.formatCurrentDate());
		log.setExecuteTime(DateUtil.formatCurrentDate());
		log.setActualExecuteTime(DateUtil.formatCurrentDateTime());
		log.setOperator(operator);
		log.setMemberLandId(record.getMemberLandId());
		log.setTaskType("Watering");
		log.setStatus((short) 2);
		log.setIsShow((short) 1);
		log.setContent("远程浇水" + (waterDuration/60) + "分钟");
		workingLogDao.insert(log);
		return topic + "----" + json;
	}

	@Override
	public int addDelegateOrder(FarmDelegateOrder delegateOrder, MemberInfo memberInfo,WorkingLog log) {
		delegateOrder.setApplyNo(GenerateOrderNoUtil.getOrderNo());
		delegateOrder.setApplyDate(DateUtil.formatCurrentDate());
		delegateOrder.setWorkDate(delegateOrder.getWorkDate().replaceAll("-", ""));
		delegateOrder.setStatus((short) 1);
		int addOrder = delegateOrderDao.insert(delegateOrder);
		
		memberInfo.setPoint(memberInfo.getPoint()-delegateOrder.getIntegral());
		int updateMI = memberInfoDao.updateIntegralById(memberInfo);
		
		if (delegateOrder.getWorkType()=="Picking") {
			FarmMemberCrops memberCrops = new FarmMemberCrops();
			memberCrops.setId(delegateOrder.getMemberCropsId());
			memberCrops.setStatus((short) 2);
			memberCropsDao.updateStatusById(memberCrops);
		}
		
		log.setPlanTime(DateUtil.formatCurrentDate());
		log.setPlanDetailId(delegateOrder.getId());
		log.setExecuteTime(DateUtil.formatCurrentDate());
		log.setActualExecuteTime(DateUtil.formatCurrentDateTime());
		log.setOperator(memberInfo.getId());
		log.setMemberLandId(delegateOrder.getMemberLandId());
		log.setTaskType(delegateOrder.getWorkType());
		log.setStatus((short) 2);
		log.setIsShow((short) 1);
		int addLog = workingLogDao.insert(log);
		
		return addOrder*updateMI*addLog;
	}

	@Override
	public int addPostDelegateRecord(FarmDelegateOrder delegateOrder, FarmDelegateExpress delegateExpress,
			MemberInfo memberInfo) {
		delegateOrder.setApplyNo(GenerateOrderNoUtil.getOrderNo());
		delegateOrder.setApplyDate(DateUtil.formatCurrentDate());
		delegateOrder.setWorkDate(delegateOrder.getWorkDate().replaceAll("-", ""));
		delegateOrder.setStatus((short) 1);
		int addOrder = delegateOrderDao.insert(delegateOrder);//添加委托单
		
		FarmMemberCrops memberCrops = memberCropsDao.getById(delegateOrder.getMemberCropsId());
		memberCrops.setInventor((short) (memberCrops.getInventory()-delegateExpress.getWeight()));
		int updateMC = memberCropsDao.updateInventory(memberCrops);//更新库存
		
		Long orderId = delegateOrderDao.getIdByApplyNo(delegateOrder.getApplyNo());
		delegateExpress.setDelegateOrderId(orderId);
		delegateExpress.setShopTime(delegateOrder.getWorkDate());
		delegateExpress.setStatus(FarmDelegateExpress.STATUS_UNSHIPPED);
		int addExpre = delegateExpressDao.insert(delegateExpress);//添加邮寄单
		
		memberInfo.setPoint(memberInfo.getPoint()-delegateOrder.getIntegral());
		int updateMI = memberInfoDao.updateIntegralById(memberInfo);//更新个人积分

		FarmCropsInfo cropsInfo = cropsInfoDao.getCropById(memberCrops.getCropsId());
		WorkingLog log = new WorkingLog();
		log.setActualExecuteTime(DateUtil.formatCurrentDateTime());
		log.setPlanDetailId(delegateOrder.getId());
		log.setOperator(memberInfo.getId());
		log.setMemberLandId(delegateOrder.getMemberLandId());
		log.setTaskType("Posting");
		log.setStatus((short) 2);
		log.setIsShow((short) 1);
		log.setContent("委托农场邮寄"+delegateExpress.getWeight()+"kg"+cropsInfo.getName()+"消耗"+delegateOrder.getIntegral()+"积分");
		int addLog = workingLogDao.insert(log);
		
		return addOrder*updateMC*addExpre*updateMI*addLog;
	}

	@Override
	public List<FarmDelegateOrderVo> queryDelegateOrderPageList(Long farmId,String paramCode,Short status, String startDate,
			String endDate, Integer start, Integer limit) {
		return delegateOrderDao.getDelegateOrderPageList(farmId,paramCode,status, startDate, endDate, start, limit);
	}

	@Override
	public int queryDelegateOrderTotal(Long farmId,String paramCode,Short status, String startDate, String endDate) {
		return delegateOrderDao.getDelegateOrderTotal(farmId,paramCode,status, startDate, endDate);
	}

	@Override
	public int addDelegateOrder(FarmDelegateOrder farmDelegateOrder) {
		return delegateOrderDao.insert(farmDelegateOrder);
	}

	@Override
	public int addDelegateExpress(FarmDelegateExpress farmDelegateExpress) {
		return delegateExpressDao.insert(farmDelegateExpress);
	}

	@Override
	public int addWorkingLog(WorkingLog workingLog) {
		return workingLogDao.insert(workingLog);
	}

	@Override
	public String stitchDelegateContent(Long orderId, String workType,String rentLandName) {
		String content = "";
		if (workType.equals("Planting")){
			FarmDelegatePlantVo plantVo = delegatePlantDao.getPlantDelegateInfo(orderId);
			content = "种植-" + plantVo.getCropsName() + "-" + plantVo.getPlantArea() + "m²";
		}
		if (workType.equals("Fertilizing")){
			FarmMemberCropsVo fertilVo = memberCropsDao.getPickDelegateInfo(orderId);
			content = "施肥-" + fertilVo.getName() + "-" + fertilVo.getArea() + "m²";
		}
		if (workType.equals("Picking")){
			FarmMemberCropsVo pickVo = memberCropsDao.getPickDelegateInfo(orderId);
			content = "采摘-" + pickVo.getName() + "-" + pickVo.getArea() + "m²";
		}
		if (workType.equals("Posting")){
			FarmDelegateExpressVo expressVo = delegateExpressDao.getExpressDelegateInfo(orderId);
			content = "邮寄-" + expressVo.getCropsName() + "-" + expressVo.getArea() + "m²-" + expressVo.getWeight() + "kg\n收件人: "
					+ expressVo.getReceiveName() + "\n收件人手机: " + expressVo.getReceiveMobile() + "\n收件人地址: " + expressVo.getAddress();
		}
		if (workType.equals("Weeding")){
			content = "除草";
		}
		if (workType.equals("Insecticide")){
			content = "捉虫";
		}
		if (workType.equals("SoilPreparate")){
			content = "整地";
		}
		if (workType.equals("Spraying")){
			content = "撒药";
		}
		return rentLandName + " : " + content;
	}

    @Override
    public int addReSoilDelegateOrder(FarmDelegateOrder delegateOrder, MemberInfo memberInfo, WorkingLog log, Long cropsId) {
        delegateOrder.setApplyNo(GenerateOrderNoUtil.getOrderNo());
        delegateOrder.setApplyDate(DateUtil.formatCurrentDate());
        delegateOrder.setWorkDate(delegateOrder.getWorkDate().replaceAll("-", ""));
        delegateOrder.setStatus((short) 1);
        int addOrder = delegateOrderDao.insert(delegateOrder);

        memberInfo.setPoint(memberInfo.getPoint()-delegateOrder.getIntegral());
        int updateMI = memberInfoDao.updateIntegralById(memberInfo);

        log.setPlanTime(DateUtil.formatCurrentDate());
        log.setExecuteTime(DateUtil.formatCurrentDate());
        log.setActualExecuteTime(DateUtil.formatCurrentDateTime());
        log.setOperator(memberInfo.getId());
        log.setMemberLandId(delegateOrder.getMemberLandId());
        log.setTaskType(delegateOrder.getWorkType());
        log.setStatus((short) 2);
        log.setIsShow((short) 1);
        int addLog = workingLogDao.insert(log);

        return addOrder*updateMI*addLog;
    }


}
