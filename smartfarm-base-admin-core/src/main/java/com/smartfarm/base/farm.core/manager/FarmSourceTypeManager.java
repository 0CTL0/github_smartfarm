package com.smartfarm.base.farm.core.manager;

import com.smartfarm.base.farm.core.entity.FarmSourceType;

import java.util.List;

public interface FarmSourceTypeManager {

    /**
     * 查询所有溯源类型
     * @return
     */
    public List<FarmSourceType> querySourceTypeList();
}
