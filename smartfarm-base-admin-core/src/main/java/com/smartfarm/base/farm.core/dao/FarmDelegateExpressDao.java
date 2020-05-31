package com.smartfarm.base.farm.core.dao;

import com.smartfarm.base.farm.core.entity.FarmDelegateExpress;
import com.smartfarm.base.farm.core.entity.vo.FarmDelegateExpressVo;

public interface FarmDelegateExpressDao {
	
	/**
	 * 添加邮寄委托
	 * @return
	 */
	public int insert(FarmDelegateExpress delegateExpress);

	/**
	 * 根据任务id查询邮递单信息
	 * @param logId
	 * @return
	 */
	public Long getExpressIdByLogId(Long logId);

	/**
	 * 更新邮递单记录，回填数据
	 * @param delegateExpress
	 * @return
	 */
	public int updateById(FarmDelegateExpress delegateExpress);

	/**
	 * 根据自由销售订单的id查询
	 * @param orderId
	 * @return
	 */
	FarmDelegateExpress selectByBazaarOrderId(Long orderId);

	/**
	 * 拼接邮寄委托内容
	 * @param orderId
	 * @return
	 */
	public FarmDelegateExpressVo getExpressDelegateInfo(Long orderId);





    int deleteByPrimaryKey(Long id);

    int insert2(FarmDelegateExpress record);

    FarmDelegateExpress selectByPrimaryKey(Long id);



    int updateById1(FarmDelegateExpress record);
}