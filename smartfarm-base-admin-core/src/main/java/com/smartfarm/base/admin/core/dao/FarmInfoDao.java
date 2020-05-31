package com.smartfarm.base.admin.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.admin.core.entity.FarmInfo;

public interface FarmInfoDao {
    int insert(FarmInfo record);
    
    int updateById(FarmInfo record);

    List<FarmInfo> selectPageList(@Param("name") String name, @Param("status") Short status, @Param("start") Integer start, @Param("limit") Integer limit);
    
    int selectPageTotal(@Param("name") String name, @Param("status") Short status);
    
    FarmInfo selectById(Long id);
    
    List<FarmInfo> selectAll();
    
    /**
     * 查询农场名称，用作查询员工条件
     * @return
     */
    public List<FarmInfo> getFarmName();
}