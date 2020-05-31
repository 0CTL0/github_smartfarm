package com.smartfarm.base.farm.core.manager;

import com.smartfarm.base.farm.core.entity.vo.SeedDetailInfoVo;

public interface SeedDetailManager {
	 /**
     * 查询种植种子详细
     * @param detailId
     * @return
     */
    public SeedDetailInfoVo querySeedDetailById(Long detailId);
}
