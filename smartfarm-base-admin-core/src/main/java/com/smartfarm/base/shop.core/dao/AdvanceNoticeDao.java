package com.smartfarm.base.shop.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.AdvanceNotice;

public interface AdvanceNoticeDao {
	
	/**
	 * 新增
	 * @param advanceNotice
	 * @return
	 */
    public int insert(AdvanceNotice advanceNotice);

    /**
     * 根据id修改
     * @param advanceNotice
     * @return
     */
    public int updateById(AdvanceNotice advanceNotice);
    
    /**
     * 根据type查询
     * @param type
     * @return
     */
    public List<AdvanceNotice> queryUseableAdvanceList(@Param("type") Short type, @Param("farmId") Long farmId);
    
    /**
     * 查询列表
     * @param farmId
     * @return
     */
    public List<AdvanceNotice> queryAdvanceList(Long farmId);
}