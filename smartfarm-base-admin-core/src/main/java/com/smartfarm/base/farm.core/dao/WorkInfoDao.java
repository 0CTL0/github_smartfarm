package com.smartfarm.base.farm.core.dao;

import com.smartfarm.base.farm.core.entity.WorkInfo;

public interface WorkInfoDao {

    int insert(WorkInfo record);

    int updateById(WorkInfo record);
}