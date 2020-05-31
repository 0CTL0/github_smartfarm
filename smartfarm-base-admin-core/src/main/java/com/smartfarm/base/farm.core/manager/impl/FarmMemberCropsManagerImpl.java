package com.smartfarm.base.farm.core.manager.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.smartfarm.base.farm.core.dao.FarmBazaarOrderDao;
import com.smartfarm.base.farm.core.dao.FarmBazaarProductDao;
import com.smartfarm.base.farm.core.dao.FarmDelegateExpressDao;
import com.smartfarm.base.farm.core.dao.FarmDelegateOrderDao;
import com.smartfarm.base.farm.core.dao.FarmMemberCropsDao;
import com.smartfarm.base.farm.core.dao.FarmRentLandInfoDao;
import com.smartfarm.base.farm.core.dao.SettingInfoDao;
import com.smartfarm.base.farm.core.dao.WorkingLogDao;
import com.smartfarm.base.farm.core.entity.FarmBazaarOrder;
import com.smartfarm.base.farm.core.entity.FarmBazaarProduct;
import com.smartfarm.base.farm.core.entity.FarmDelegateExpress;
import com.smartfarm.base.farm.core.entity.FarmDelegateOrder;
import com.smartfarm.base.farm.core.entity.FarmDelegatePlant;
import com.smartfarm.base.farm.core.entity.FarmMemberCrops;
import com.smartfarm.base.farm.core.entity.FarmRentLandInfo;
import com.smartfarm.base.farm.core.entity.SettingInfo;
import com.smartfarm.base.farm.core.entity.WorkingLog;
import com.smartfarm.base.util.GenerateOrderNoUtil;
import com.smartfarm.base.util.NumberUtil;
import com.smartfarm.base.shop.core.dao.BaseOrderDao;
import com.smartfarm.base.shop.core.dao.MemberInfoDao;
import com.smartfarm.base.shop.core.entity.BaseOrder;
import com.smartfarm.base.shop.core.entity.MemberInfo;
import com.smartfarm.base.shop.core.service.OrderService;
import org.springframework.stereotype.Service;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.farm.core.entity.vo.FarmBazaarOrderVo;
import com.smartfarm.base.farm.core.entity.vo.FarmMemberCropsVo;
import com.smartfarm.base.farm.core.manager.FarmMemberCropsManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月09日 14:58:56
 * @version 1.0
 */
@Service("farmMemberCropsManager")
public class FarmMemberCropsManagerImpl implements FarmMemberCropsManager {

	@Resource
	private FarmMemberCropsDao memberCropsDao;
	@Resource
	private FarmBazaarProductDao farmBazaarProductDao;
	@Resource
	private FarmBazaarOrderDao farmBazaarOrderDao;
	@Resource
	private WorkingLogDao workingLogDao;
	@Resource
	private FarmDelegateExpressDao farmDelegateExpressDao;
	@Resource
	private BaseOrderDao baseOrderDao;
	@Resource
	private OrderService orderService;
	@Resource
	private FarmDelegateOrderDao farmDelegateOrderDao;
	@Resource
	private MemberInfoDao memberInfoDao;
	@Resource
	private SettingInfoDao settingInfoDao;
	@Resource
	private FarmRentLandInfoDao rentLandInfoDao;

	@Override
	public int addCrops(FarmDelegateOrder delegateOrder,FarmDelegatePlant delegatePlant) {
		FarmMemberCrops crops = new FarmMemberCrops();
		crops.setMemberLandId(delegateOrder.getMemberLandId());
		crops.setCropsId(delegatePlant.getCropsId());
		crops.setStatus((short) 1);
		crops.setArea(delegatePlant.getPlantArea());
		return memberCropsDao.insert(crops);
	}

	@Override
	public FarmMemberCrops queryCropsByLandId(Long landId) {
		return memberCropsDao.getCropsByLandId(landId);
	}

	@Override
	public List<FarmMemberCropsVo> queryMyCrops(Long landId) {
		return memberCropsDao.getCropsByLandIdAndStatus(landId);
	}

	@Override
	public List<FarmMemberCropsVo> queryMyMatureCrops(Long landId) {
		return memberCropsDao.getMatureCropsByLandId(landId);
	}

	@Override
	public List<FarmMemberCropsVo> queryLandPlantingSituation(Long landId) {
		return memberCropsDao.getMyCropsInfoByLandId(landId);
	}

	@Override
	public int deleteMyCropsById(Long id) {
		return memberCropsDao.deleteById(id);
	}

	@Override
	public List<FarmMemberCropsVo> getAllMemberCrops(long memberLandId) {
		return memberCropsDao.getAllMemberCrops(memberLandId);
	}

	@Override
	public int addFarmBazaarProduct(FarmBazaarProduct farmBazaarProduct) {
		//上架
		farmBazaarProductDao.insertSelective(farmBazaarProduct);
		//库存处理
		FarmMemberCrops farmMemberCrops=memberCropsDao.getById(farmBazaarProduct.getMemberCropsId());
		farmBazaarProduct.setStatus(FarmBazaarProduct.STATUS_ABLE);
		farmBazaarProduct.setEndTime(farmMemberCrops.getFreshTime());
		farmMemberCrops.setInventory((short)((int)farmMemberCrops.getInventory()-farmBazaarProduct.getStock()));
		memberCropsDao.updateById(farmMemberCrops);
		//劳动日志
		WorkingLog workingLog=new WorkingLog();
		workingLog.setMemberLandId(farmBazaarProduct.getMemberLandId());
		workingLog.setPlanTime(DateUtil.formatCurrentDateTime());
		workingLog.setExecuteTime(DateUtil.formatCurrentDateTime());
		workingLog.setActualExecuteTime(DateUtil.formatCurrentDateTime());
		workingLog.setStatus((short)2);
		workingLog.setIsShow((short)1);
		workingLog.setContent("在自由交易中出售"+farmBazaarProduct.getStock()+"斤"+'"'+farmBazaarProduct.getName()+'"');
		workingLogDao.insert(workingLog);
		return 0;
	}

	@Override
	public FarmMemberCrops getFarmMemberCropsById(Long id) {
		return memberCropsDao.getById(id);
	}

	@Override
	public FarmMemberCropsVo queryMemberCropsVoById(Long id) {
		return memberCropsDao.getCropById(id);
	}

	@Override
	public FarmRentLandInfo queryRentLandByMemberLandId(Long memberLandId) {
		return rentLandInfoDao.getRentLandByMemberLandId(memberLandId);
	}

	@Override
	public int editFarmMemberCrops(FarmMemberCrops farmMemberCrops) {
		return memberCropsDao.updateById(farmMemberCrops);
	}

	@Override
	public List<FarmBazaarProduct> getMemberLandProduct(Long memberLandId) {
		return farmBazaarProductDao.selectMemberLandProduct(memberLandId);
	}

	@Override
	public int removeFarmBazaarProduct(Long id) {
		//下架
		FarmBazaarProduct farmBazaarProduct=farmBazaarProductDao.selectByPrimaryKey(id);
		farmBazaarProduct.setStatus(FarmBazaarProduct.STATUS_UNABLE);
		//处理库存
		FarmMemberCrops farmMemberCrops=memberCropsDao.getById(farmBazaarProduct.getMemberCropsId());
		farmMemberCrops.setInventory((short)((int)farmMemberCrops.getInventory()+farmBazaarProduct.getStock()));
		//劳动日志
		WorkingLog workingLog=new WorkingLog();
		workingLog.setMemberLandId(farmBazaarProduct.getMemberLandId());
		workingLog.setPlanTime(DateUtil.formatCurrentDateTime());
		workingLog.setActualExecuteTime(DateUtil.formatCurrentDateTime());
		workingLog.setExecuteTime(DateUtil.formatCurrentDateTime());
		workingLog.setStatus((short)2);
		workingLog.setIsShow((short)1);
		workingLog.setContent("在自由交易中下架剩余的"+farmBazaarProduct.getStock()+"kg"+'"'+farmBazaarProduct.getName()+'"');
		workingLogDao.insert(workingLog);

		farmBazaarProductDao.updateByPrimaryKeySelective(farmBazaarProduct);
		memberCropsDao.updateById(farmMemberCrops);

		return 0;
	}

	@Override
	public List<FarmBazaarProduct> getAllBazaarProduct(Long farmId) {
		return farmBazaarProductDao.selectAllBazaarProduct(farmId);
	}

	@Override
	public synchronized int addBazaarOrder(FarmBazaarOrder farmBazaarOrder) throws Exception {
		//库存判断
		FarmBazaarProduct farmBazaarProduct=farmBazaarProductDao.selectByPrimaryKey(farmBazaarOrder.getBazaarProductId());
		if(farmBazaarProduct.getStock()<farmBazaarOrder.getNumber()){
			return -1;
		}
		//减少库存
		farmBazaarProduct.setStock(farmBazaarProduct.getStock()-farmBazaarOrder.getNumber());
		farmBazaarProductDao.updateByPrimaryKeySelective(farmBazaarProduct);
		farmBazaarOrderDao.insertSelective(farmBazaarOrder);
		BaseOrder baseOrder=new BaseOrder();
		baseOrder.setStatus(BaseOrder.BASE_ORDER_STATUS_INIT);
		baseOrder.setType(BaseOrder.BASE_ORDER_TYPE_FARMBAZAAR);
		baseOrder.setRefId(farmBazaarOrder.getId());
		baseOrderDao.insert(baseOrder);
		//加入订单定时器
		orderService.makeOrder(baseOrder.getId());
		return 0;
	}

	@Override
	public FarmBazaarOrder getBazaarOrderByOrderCode(String OderCode) {
		return farmBazaarOrderDao.selectByOrderCode(OderCode);
	}

	@Override
	public int editBazaarOrder(FarmBazaarOrder farmBazaarOrder) {
		return farmBazaarOrderDao.updateByPrimaryKeySelective(farmBazaarOrder);
	}

	@Override
	public FarmBazaarProduct getBazaarProductById(Long id) {
		return farmBazaarProductDao.selectByPrimaryKey(id);
	}

	@Override
	public List<FarmBazaarOrderVo> getAllOrder(Long memberId) {
		return farmBazaarOrderDao.selectAllOrder(memberId);
	}

	@Override
	public int toReceived(Long  farmBazaarOrderId) {
		FarmBazaarOrder farmBazaarOrder=farmBazaarOrderDao.selectByPrimaryKey(farmBazaarOrderId);
		farmBazaarOrder.setOrderStatus(FarmBazaarOrder.ORDERSTATUS_RECEIVED);
		return farmBazaarOrderDao.updateByPrimaryKeySelective(farmBazaarOrder);
	}

	@Override
	public int removeOverdueBazaarProduct() throws ParseException {
		List<FarmBazaarProduct> bazaarProducts=farmBazaarProductDao.selectAllFarmBazaarProduct();
		if(bazaarProducts!=null){
			for(FarmBazaarProduct farmBazaarProduct:bazaarProducts){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String newTimes = sdf.format(new Date());
				Date newTime = sdf.parse(newTimes);
				Date expirationTime = sdf.parse(farmBazaarProduct.getEndTime().substring(0,8));
				if(newTime.getTime()>expirationTime.getTime()){
					removeFarmBazaarProduct(farmBazaarProduct.getId());
				}
			}
		}
		return 0;
	}
	
	@Override
	public List<FarmBazaarOrderVo> getMemberLandOrderList(Long memberLandId) {
		List<FarmBazaarOrderVo> orderVoList = farmBazaarOrderDao.selectMemberLandOrderList(memberLandId);
		if (null != orderVoList) {
			for (FarmBazaarOrderVo farmBazaarOrderVo : orderVoList) {
				farmBazaarOrderVo.setFarmBazaarProduct(farmBazaarProductDao.selectByPrimaryKey(farmBazaarOrderVo.getBazaarProductId()));
				farmBazaarOrderVo.setFarmDelegateExpress(farmDelegateExpressDao.selectByBazaarOrderId(farmBazaarOrderVo.getId()));
			}

		}
		return orderVoList;
	}

	@Override
	public void orderCancel(Long id) {
		FarmBazaarOrder farmBazaarOrder = new FarmBazaarOrder();
		farmBazaarOrder.setId(id);
		farmBazaarOrder.setOrderStatus(FarmBazaarOrder.ORDERSTATUS_CANCEL);
		farmBazaarOrderDao.updateByPrimaryKeySelective(farmBazaarOrder);
		FarmBazaarProduct farmBazaarProduct=farmBazaarProductDao.selectByPrimaryKey(farmBazaarOrder.getBazaarProductId());
		farmBazaarProduct.setStock(farmBazaarProduct.getStock()+farmBazaarOrder.getNumber());
		farmBazaarProductDao.updateByPrimaryKeySelective(farmBazaarProduct);
	}

	@Override
	public void orderOutTime(Long id) {
		FarmBazaarOrder farmBazaarOrder = new FarmBazaarOrder();
		farmBazaarOrder.setId(id);
		farmBazaarOrder.setOrderStatus(FarmBazaarOrder.ORDERSTATUS_OVERTIME);
		farmBazaarOrderDao.updateByPrimaryKeySelective(farmBazaarOrder);
		FarmBazaarProduct farmBazaarProduct=farmBazaarProductDao.selectByPrimaryKey(farmBazaarOrder.getBazaarProductId());
		farmBazaarProduct.setStock(farmBazaarProduct.getStock()+farmBazaarOrder.getNumber());
		farmBazaarProductDao.updateByPrimaryKeySelective(farmBazaarProduct);
	}

	@Override
	public void orderSuccess(FarmBazaarOrder farmBazaarOrder) {
		FarmBazaarProduct farmBazaarProduct=farmBazaarProductDao.selectByPrimaryKey(farmBazaarOrder.getBazaarProductId());
		//更新自由交易订单
		farmBazaarOrderDao.updateByPrimaryKeySelective(farmBazaarOrder);
		//生成委托订单
		FarmDelegateOrder farmDelegateOrder=new FarmDelegateOrder();
		farmDelegateOrder.setApplyNo(GenerateOrderNoUtil.getOrderNo());
		farmDelegateOrder.setApplyDate(DateUtil.formatCurrentDate());
		farmDelegateOrder.setWorkDate(DateUtil.formatCurrentDate());
		farmDelegateOrder.setWorkType("Posting");
		farmDelegateOrder.setStatus((short) 1);
		Long farmId = settingInfoDao.getFarmIdByMemberLandId(farmBazaarProduct.getMemberLandId());
		SettingInfo delegateType = settingInfoDao.getInfoByType(farmId,"Posting");
		farmDelegateOrder.setIntegral(Long.valueOf(delegateType.getParamValue()));
		farmDelegateOrder.setMemberCropsId(farmBazaarProduct.getMemberCropsId());
		farmDelegateOrder.setMemberLandId(farmBazaarProduct.getMemberLandId());
		farmDelegateOrderDao.insert(farmDelegateOrder);
		//生成委托邮寄记录
		FarmDelegateExpress delegateExpress=new FarmDelegateExpress();
		delegateExpress.setDelegateOrderId(farmDelegateOrder.getId());
		delegateExpress.setMemberCropsId(farmDelegateOrder.getMemberCropsId());
		delegateExpress.setWeight((short)(farmBazaarOrder.getNumber()*farmBazaarProduct.getUnit()));
		delegateExpress.setReceiveName(farmBazaarOrder.getReceiveName());
		delegateExpress.setReceiveMobile(farmBazaarOrder.getReceivePhone());
		delegateExpress.setAddress(farmBazaarOrder.getReceiveAddress());
		delegateExpress.setStatus(FarmDelegateExpress.STATUS_UNSHIPPED);
		delegateExpress.setBazaarOrderId(farmBazaarOrder.getId());
		farmDelegateExpressDao.insert(delegateExpress);
		MemberInfo memberInfo=memberInfoDao.queryById(farmBazaarOrder.getMemberSellerId());
		// 扣除邮寄积分
		memberInfo.setPoint(memberInfo.getPoint()-farmDelegateOrder.getIntegral());
		//发放订单金额到卖家账号
		memberInfo.setCash(NumberUtil.add(memberInfo.getCash(),farmBazaarOrder.getAmount()));
		memberInfoDao.updateById(memberInfo);
		//劳动日志
		WorkingLog log = new WorkingLog();
		log.setActualExecuteTime(DateUtil.formatCurrentDateTime());
		log.setOperator(farmBazaarProduct.getMemberSellerId());
		log.setMemberLandId(farmBazaarProduct.getMemberLandId());
		log.setTaskType("Posting");
		log.setStatus((short) 2);
		log.setIsShow((short) 1);
		log.setContent("自由销售订单["+farmBazaarOrder.getOrderCode()+"],售出"+farmBazaarOrder.getNumber()*farmBazaarProduct.getUnit()+"kg"
						+farmBazaarProduct.getName()+"获得"+farmBazaarOrder.getAmount()+ "元，委托农场邮寄消耗"+farmDelegateOrder.getIntegral()+"积分");
		workingLogDao.insert(log);
	}
}
