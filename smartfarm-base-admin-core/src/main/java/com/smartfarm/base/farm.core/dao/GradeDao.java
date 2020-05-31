package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.Grade;

public interface GradeDao {

	/**
	 * 添加
	 * @param grade
	 * @return
	 */
    public int insert(Grade grade);

    /**
     * 根据id修改
     * @param grade
     * @return
     */
    public int updateById(Grade grade);
    
    /**
     * 批量插入等级表
     * @param list
     * @return
     */
    public int insertBatch(List<Grade> list);
    
    /**
     * 根据id删除档位
     * @param id
     * @return
     */
    public int deleteGroupById(@Param("id") Long id);
}