package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.FarmTunnelInfo;
import com.smartfarm.base.farm.core.entity.vo.FarmTunnelInfoVo;

public interface FarmTunnelInfoDao {
    int deleteByPrimaryKey(Long id);

    int insert(FarmTunnelInfo record);

    FarmTunnelInfo selectByPrimaryKey(Long id);

    int updateById(FarmTunnelInfo record);


    List<FarmTunnelInfoVo> selectPageList(@Param("baseId") Long baseId, @Param("name") String name, @Param("status") Short status, @Param("farmId") Long farmId,
                                          @Param("start") Integer start, @Param("limit") Integer limit);
    int selectPageTotal(@Param("baseId") Long baseId, @Param("name") String name, @Param("status") Short status, @Param("farmId") Long farmId);
    FarmTunnelInfo selectById(Long id);

    FarmTunnelInfoVo selectFarmTunnelVo(Long id);
    
    List<FarmTunnelInfoVo> queryTunnelForAdvance(Long farmId);
    
    FarmTunnelInfoVo queryTunnelForAdvanceId(Long id);
    
    /**
     * 查询可用的地块
     * @param farmId
     * @return
     */
    List<FarmTunnelInfoVo> queryTunnelForAdvanceAdd(Long farmId);
}