package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.WorkType;

public interface WorkTypeDao {

    /**
     * 查询所有workType
     * @return
     */
    public List<WorkType> queryWorkTypeList();

    /**
     * 根据id查询WorkType
     * @param id
     * @return
     */
    public WorkType queryWorkTypeById(@Param("id") Long id);



    int insert(WorkType workType);
    
    int updateById(WorkType workType);
    
}