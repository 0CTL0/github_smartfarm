package com.smartfarm.base.farm.core.manager;

import java.util.List;

import com.smartfarm.base.farm.core.entity.FarmCropsInfo;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月13日 14:15:33
 * @version 1.0
 */
public interface FarmCropsInfoManager {

	/**
	 * 查询所有的种子数据
	 * @return
	 */
	public List<FarmCropsInfo> queryCropsInfoList(Long farmId);

	/**
	 * 根据委托单id查询种子信息
	 * @param orderId
	 * @return
	 */
	public FarmCropsInfo queryCronInfoByOrderId(Long orderId);
	
}
