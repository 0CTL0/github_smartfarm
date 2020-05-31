package com.smartfarm.base.shop.core.dao;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.MemberShareRecord;

public interface MemberShareRecordDao {
	/**
	 * 新增
	 * @param memberShareRecord
	 * @return
	 */
    public int insert(MemberShareRecord memberShareRecord);
    
    /**
     * 根据id修改
     * @param memberShareRecord
     * @return
     */
    int updateById(MemberShareRecord memberShareRecord);
    
    /**
     * 根据编码查询
     * @param code
     * @return
     */
    public MemberShareRecord queryShareRecordByCode(@Param("code") String code);

}