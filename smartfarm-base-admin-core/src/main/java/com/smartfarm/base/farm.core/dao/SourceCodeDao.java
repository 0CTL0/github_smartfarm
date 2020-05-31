package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.SourceCode;
import com.smartfarm.base.farm.core.entity.vo.SourceCodeVo;

public interface SourceCodeDao {

    int insert(SourceCode sourceCode);

    /**
     * 修改溯源码查询次数信息
     * @param sourceCode
     * @return
     */
    public int updateById(SourceCode sourceCode);
    
    /**
     * 批量插入溯源码
     * @param list
     * @return
     */
    public int insertBatchInfoList(List<SourceCode> list);
    
    /**
     * 查询溯源码列表
     * @param name
     * @param code
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<SourceCodeVo> querySourceCodeList(@Param("farmId") Long farmId, @Param("name") String name, @Param("code") String code, @Param("status") Short status,
                                                  @Param("start") Integer start, @Param("limit") Integer limit);
    
    /**
     * 查询溯源码数量
     * @param name
     * @param code
     * @param status
     * @return
     */
    public int queryCountSourceCodeList(@Param("farmId") Long farmId, @Param("name") String name, @Param("code") String code, @Param("status") Short status);
    
    /**
     * 微信小程序溯源码查询
     * @param code
     * @return
     */
    public SourceCodeVo getSourceInquireWe(@Param("code") String code);
    
    /**
     * 根据source_id对应的code
     * @param sourceId
     * @return
     */
    public List<SourceCode> getCodeBySourceId(@Param("sourceId") Long sourceId);
    
}