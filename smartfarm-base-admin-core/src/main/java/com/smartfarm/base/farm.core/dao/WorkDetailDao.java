package com.smartfarm.base.farm.core.dao;

import com.smartfarm.base.farm.core.entity.WorkDetail;

public interface WorkDetailDao {

    int insert(WorkDetail record);

    int updateById(WorkDetail record);
}