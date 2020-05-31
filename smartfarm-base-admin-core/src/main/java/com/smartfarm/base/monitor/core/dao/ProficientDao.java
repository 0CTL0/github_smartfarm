package com.smartfarm.base.monitor.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.monitor.core.entity.Proficient;

public interface ProficientDao {

	/**
	 * 分页查询
	 * @param mobile
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Proficient> getPageList(@Param("mobile") String mobile, @Param("start") Integer start, @Param("limit") Integer limit);
	
	/**
	 * 统计页数
	 * @param mobile
	 * @return
	 */
	public int getPageTotal(@Param("mobile") String mobile);
	
	/**
	 * 新增
	 * @param profiClient
	 * @return
	 */
    public int insert(Proficient proficient);

    /**
     * 根据id查找
     * @param id
     * @return
     */
    public Proficient selectById(@Param("id") Long id);
    
    /**
     * 根据id更新
     * @param proficient
     * @return
     */
    public int updateById(Proficient proficient);
    
    /**
     * 根据id删除专家
     * @param id
     * @return
     */
    public int deleteById(@Param("id") Long id);
    
    /**
     * 查询全部专家
     * @return
     */
    public List<Proficient> queryProficientList();
    
}