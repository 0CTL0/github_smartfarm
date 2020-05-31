package com.smartfarm.base.farm.core.manager;

import java.util.List;

import com.smartfarm.base.farm.core.entity.WorkAttribute;

public interface WorkAttributeManager {
	/**
     * 根据id查询Attribute
     * @param id
     * @return
     */
    public List<WorkAttribute> queryAttributeById(Long id);
}
