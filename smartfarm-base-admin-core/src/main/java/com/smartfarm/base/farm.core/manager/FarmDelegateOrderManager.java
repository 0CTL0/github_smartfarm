package com.smartfarm.base.farm.core.manager;

import java.util.List;

import com.smartfarm.base.farm.core.entity.FarmDelegateExpress;
import com.smartfarm.base.farm.core.entity.FarmDelegateOrder;
import com.smartfarm.base.farm.core.entity.FarmDelegatePlant;
import com.smartfarm.base.farm.core.entity.WorkingLog;
import com.smartfarm.base.farm.core.entity.vo.FarmDelegateOrderVo;
import com.smartfarm.base.shop.core.entity.MemberInfo;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月09日 14:32:22
 * @version 1.0
 */
public interface FarmDelegateOrderManager {

	/**
	 * 添加种植委托任务
	 * @return
	 */
	public int addPlantDelegateRecord(FarmDelegateOrder delegateOrder, FarmDelegatePlant delegatePlant, MemberInfo memberInfo);
	
	/**
	 * 浇水操作
	 * @param landId
	 * @return
	 */
	public String performWatering(Long landId, Long operator, Integer waterDuration);
	
	/**
	 * 添加委托
	 * @param delegateOrder
	 * @param memberInfo
	 * @return
	 */
	public int addDelegateOrder(FarmDelegateOrder delegateOrder, MemberInfo memberInfo, WorkingLog log);
	
	/**
	 * 添加邮寄委托任务
	 * @param delegateOrder
	 * @param delegateExpress
	 * @param memberInfo
	 * @return
	 */
	public int addPostDelegateRecord(FarmDelegateOrder delegateOrder, FarmDelegateExpress delegateExpress, MemberInfo memberInfo);
	
	/**
	 * 按条件分页查询委托信息
	 * @param startDate
	 * @param endDate
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<FarmDelegateOrderVo> queryDelegateOrderPageList(Long farmId, String paramCode, Short status, String startDate, String endDate, Integer start, Integer limit);
	
	/**
	 * 按条件统计委托总数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int queryDelegateOrderTotal(Long farmId, String paramCode, Short status, String startDate, String endDate);


	/**
	 * 添加委托订单
	 * @param farmDelegateOrder
	 * @return
	 */
	int addDelegateOrder(FarmDelegateOrder farmDelegateOrder);

	/**
	 * 添加委托邮寄
	 * @param farmDelegateExpress
	 * @return
	 */
	int addDelegateExpress(FarmDelegateExpress farmDelegateExpress);

	/**
	 * 添加劳作日志
	 * @param workingLog
	 * @return
	 */
	int addWorkingLog(WorkingLog workingLog);

	/**
	 * 拼接委托任务的内容
	 * @param orderId
	 * @param workType
	 * @return
	 */
	public String stitchDelegateContent(Long orderId, String workType, String rentLandName);

	/**
	 * 重新整地委托
	 * @param delegateOrder
	 * @param memberInfo
	 * @param log
	 * @param cropsId
	 * @return
	 */
	public int addReSoilDelegateOrder(FarmDelegateOrder delegateOrder, MemberInfo memberInfo, WorkingLog log, Long cropsId);
}
