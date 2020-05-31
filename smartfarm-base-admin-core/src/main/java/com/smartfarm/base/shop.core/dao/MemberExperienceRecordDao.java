package com.smartfarm.base.shop.core.dao;

import com.smartfarm.base.shop.core.entity.MemberExperienceRecord;

public interface MemberExperienceRecordDao {
	/**
	 * 添加
	 * @param memberExperienceRecord
	 * @return
	 */
    public int insert(MemberExperienceRecord memberExperienceRecord);
    /**
     * 根据id修改
     * @param memberExperienceRecord
     * @return
     */
    public int updateById(MemberExperienceRecord memberExperienceRecord);

}