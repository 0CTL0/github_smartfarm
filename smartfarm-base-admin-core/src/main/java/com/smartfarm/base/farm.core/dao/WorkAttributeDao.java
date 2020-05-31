package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.WorkAttribute;

public interface WorkAttributeDao {

    int insert(WorkAttribute workAttribute);

    int updateById(WorkAttribute workAttribute);
    
    /**
     * 根据id查询Attribute
     * @param id
     * @return
     */
    public List<WorkAttribute> queryAttributeById(@Param("id") Long id);
}