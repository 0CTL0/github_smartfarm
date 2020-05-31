package com.smartfarm.base.farm.core.manager;

import java.util.List;

import com.smartfarm.base.farm.core.entity.Grade;

public interface GradeManager {
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
    public int deleteGroupById(Long id);
}
