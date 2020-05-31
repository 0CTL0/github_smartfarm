package com.smartfarm.base.farm.core.manager;

import java.util.List;

import com.smartfarm.base.farm.core.entity.WorkType;

public interface WorkTypeManager {
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
    public WorkType queryWorkTypeById(Long id);
}
