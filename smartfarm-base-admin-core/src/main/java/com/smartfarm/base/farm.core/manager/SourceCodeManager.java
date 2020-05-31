package com.smartfarm.base.farm.core.manager;

import java.util.List;

import com.smartfarm.base.farm.core.entity.SourceCode;
import com.smartfarm.base.farm.core.entity.SourceInfo;
import com.smartfarm.base.farm.core.entity.vo.SourceCodeVo;

public interface SourceCodeManager {
	/**
     * 查询溯源码列表
     * @param name
     * @param code
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<SourceCodeVo> querySourceCodeList(Long farmId, String name, String code, Short status,
                                                  Integer start, Integer limit);
    
    /**
     * 查询溯源码数量
     * @param name
     * @param code
     * @param status
     * @return
     */
    public int queryCountSourceCodeList(Long farmId, String name, String code, Short status);
    /**
     * 批量插入溯源码
     * @param list
     * @return
     */
    public int insertBatchInfoList(List<SourceCode> list, SourceInfo sourceInfo);
    
    /**
     * 小程序溯源码查询
     * @param code
     * @return
     */
    public SourceCodeVo querySourceCodeWe(String code);
    
    /**
     * 更新溯源码查询次数
     * @param sourceCode
     * @return
     */
    public int modifyCodeSearchTimeWe(SourceCode sourceCode);
    
    /*
     * 根据sourceId查询其所有的溯源码
     */
    public List<SourceCode> queryCodeBySourceId(Long sourceId);
}
