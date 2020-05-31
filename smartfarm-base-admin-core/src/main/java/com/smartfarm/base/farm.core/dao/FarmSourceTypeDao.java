package com.smartfarm.base.farm.core.dao;

import com.smartfarm.base.farm.core.entity.FarmSourceType;

import java.util.List;

public interface FarmSourceTypeDao {

    /**
     * 查询所有溯源任务类型
     * @return
     */
    public List<FarmSourceType> getSourceTypes();



    int deleteByPrimaryKey(Long id);

    int insert(FarmSourceType record);

    int insertSelective(FarmSourceType record);

    FarmSourceType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FarmSourceType record);

    int updateByPrimaryKey(FarmSourceType record);
}