package com.smartfarm.base.farm.core.dao;

import com.smartfarm.base.farm.core.entity.Tabulation;

public interface TabulationDao {

    int insert(Tabulation tabulation);

    int updateById(Tabulation tabulation);
}