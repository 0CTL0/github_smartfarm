package com.smartfarm.base.shop.core.dao;

import com.smartfarm.base.shop.core.entity.MemberVisitRecord;

public interface MemberVisitRecordDao {
	/**
	 * 新增
	 * @param memberVisitRecord
	 * @return
	 */
    public int insert(MemberVisitRecord memberVisitRecord);
    
    /**
     * 根据id修改
     * @param memberVisitRecord
     * @return
     */
    public int updateById(MemberVisitRecord memberVisitRecord);

}