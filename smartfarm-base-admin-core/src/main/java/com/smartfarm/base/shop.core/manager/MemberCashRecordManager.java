package com.smartfarm.base.shop.core.manager;

import com.smartfarm.base.shop.core.entity.MemberCashRecord;
import com.smartfarm.base.shop.core.entity.vo.MemberCashRecordVo;

import java.util.List;

public interface MemberCashRecordManager {
	/**
	 * 根据条件获取用户财务列表
	 * @param memberName
	 * @param status
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<MemberCashRecordVo> queryMemberCashRecordList(String memberName, Short status, Integer start, Integer limit);
	/**
	 * 统计会员金额列表
	 * @param taskName
	 * @param status
	 * @return
	 */
	public int countMemberCashRecordList(String taskName, Short status);
	/**
	 * 更新会员金额信息
	 * @param memberCashRecord
	 * @return
	 */
	public int updateMemberCashRecord(MemberCashRecord memberCashRecord);
	/**
	 * 添加记录
	 * @param cashRecord
	 * @return
	 */
	public int insert(MemberCashRecord cashRecord);
}
