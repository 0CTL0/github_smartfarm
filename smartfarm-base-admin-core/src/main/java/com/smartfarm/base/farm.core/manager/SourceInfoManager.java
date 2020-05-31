package com.smartfarm.base.farm.core.manager;

import java.util.List;

import com.smartfarm.base.farm.core.entity.SourceInfo;
import com.smartfarm.base.farm.core.entity.vo.SourceInfoVo;

public interface SourceInfoManager {
	/**
     * 分页查询溯源信息
     * @param start
     * @param limit
     * @return
     */
    public List<SourceInfo> qurySourceInfoList(Long farmId, String name, String prefix, Integer start, Integer limit);
    
    /***
     * 分页查询溯源信息数量Os
     * @return
     */
    public int quryCountSourceInfoList(Long farmId);
    
    /**
     * 新增溯源信息
     * @param sourceInfo
     * @return
     */
    public int addSourceInfo(SourceInfo sourceInfo);
    /**
     * 根据id查询生产过程
     * @param id
     * @return
     */
    public SourceInfoVo queryAttributeDataById(Long id);
}
