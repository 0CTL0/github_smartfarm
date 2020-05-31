package com.smartfarm.base.shop.core.dao;


import java.util.List;

import com.smartfarm.base.shop.core.entity.ActivityPrice;

public interface ActivityPriceDao {
	/**
	 * 添加
	 * @param record
	 * @return
	 */
    public int insert(ActivityPrice record);
    
    /**
     * 根据id修改
     * @param record
     * @return
     */
    public int updateByPrimaryKeySelective(ActivityPrice record);
    
    /**
     * 根据Id获取价格档次列表
     * @param id
     * @return
     */
    public List<ActivityPrice> selectActivityList(Long id);
    
    /**
     * 删除某价格档次
     * @param id
     * @return
     */
    public int delectById(Long id);
    
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public ActivityPrice queryById(Long id);
    
}