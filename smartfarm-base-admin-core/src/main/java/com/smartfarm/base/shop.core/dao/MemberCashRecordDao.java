package com.smartfarm.base.shop.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.MemberCashRecord;
import com.smartfarm.base.shop.core.entity.vo.MemberCashRecordVo;

public interface MemberCashRecordDao {
	/**
	 * 新增
	 * @param memberCashRecord
	 * @return
	 */
    public int insert(MemberCashRecord memberCashRecord);
    
    /**
     * 根据id修改
     * @param memberCashRecord
     * @return
     */
    public int updateById(MemberCashRecord memberCashRecord);   
    /**
     * 按类型查询会员金额记录
     * @param status
     * @param memberId
     * @param cashType
     * @return
     */
    public List<MemberCashRecord> queryCashRecord(@Param("status") Short status, @Param("memberId") Long memberId, @Param("cashType") Short cashType);
  /**
     * 按用户名、类型查询会员金额列表
     * @param taskName
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<MemberCashRecordVo> queryMemberCashRecordList(@Param("mName") String mName, @Param("status") Short status, @Param("start") Integer start, @Param("limit") Integer limit);
    /**
     * 统计会员金额列表
     * @param memberName
     * @param status
     * @return
     */
    public int countCashRecordListPage(@Param("mName") String mName, @Param("status") Short status);
  
}