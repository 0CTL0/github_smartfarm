package com.smartfarm.base.shop.core.manager;

import java.util.List;

import com.smartfarm.base.shop.core.entity.AdvanceNotice;


public interface AdvanceNoticeManager {
	
	/**
     * 根据type查询
     * @param type
     * @return
     */
    public List<AdvanceNotice> queryUseableAdvanceList(Short type, Long farmId);
    
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
     * 查询列表
     * @param farmId
     * @return
     */
    public List<AdvanceNotice> queryAdvanceList(Long farmId);
}
