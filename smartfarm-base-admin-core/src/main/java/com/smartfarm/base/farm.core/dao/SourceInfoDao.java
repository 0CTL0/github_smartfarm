package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.SourceInfo;
import com.smartfarm.base.farm.core.entity.vo.SourceInfoVo;

public interface SourceInfoDao {

    int insert(SourceInfo sourceInfo);

    int updateById(SourceInfo sourceInfo);
    
    /**
     * 分页查询溯源信息
     * @param start
     * @param limit
     * @return
     */
    public List<SourceInfo> qurySourceInfoList(@Param("farmId") Long farmId, @Param("name") String name, @Param("prefix") String prefix, @Param("start") Integer start, @Param("limit") Integer limit);
    
    /***
     * 分页查询溯源信息数量
     * @return
     */
    public int quryCountSourceInfoList(@Param("farmId") Long farmId);
    
    /**
     * 根据id查询生产过程
     * @param id
     * @return
     */
    public SourceInfoVo queryAttributeDataById(@Param("id") Long id);
    
    /**
     * 添加溯源码数量
     * @param sourceInfo
     * @return
     */
    public int addSourceAmount(SourceInfo sourceInfo);
    
    /**
     * 设置溯源码生成状态
     * @param sourceInfo
     * @return
     */
    public int addCodeStatus(SourceInfo sourceInfo);
    
    /**
     * 更新批次溯源码的文件夹名称
     * @param id
     * @param folderName
     * @return
     */
    public int updateFolderNameById(@Param("id") Long id, @Param("folderName") String folderName);
}