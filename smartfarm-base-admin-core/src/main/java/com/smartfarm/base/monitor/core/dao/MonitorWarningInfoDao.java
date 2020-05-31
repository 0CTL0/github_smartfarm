package com.smartfarm.base.monitor.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.monitor.core.entity.MonitorWarningInfo;
import com.smartfarm.base.monitor.core.entity.vo.MonitorWarningInfoVo;

public interface MonitorWarningInfoDao {
	
    int deleteById(Long id);

    int insert(MonitorWarningInfo record);

    MonitorWarningInfo selectById(Long id);

    int updateById(MonitorWarningInfo record);
    
    /**
     * 查询土壤列表警告信息
     * @param codes
     * @return
     */
    List<MonitorWarningInfoVo> querySoilWarningList(@Param("ids") List<Long> ids);
    
    /**
     * 
     * @param businessId
     * @param start
     * @param limit
     * @return
     */
    List<MonitorWarningInfoVo> queryPageWarningList(@Param("businessId") Long businessId, @Param("start") Integer start, @Param("limit") Integer limit);
    
    /**
     * 统计
     * @param businessId
     * @return
     */
    int countPageWarningList(Long businessId);
}