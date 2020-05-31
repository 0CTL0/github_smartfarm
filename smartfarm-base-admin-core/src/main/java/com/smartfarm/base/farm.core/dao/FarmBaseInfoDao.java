package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.FarmBaseInfo;
import com.smartfarm.base.farm.core.entity.vo.FarmBaseInfoVo;

public interface FarmBaseInfoDao {
    int insert(FarmBaseInfo record);
    int updateById(FarmBaseInfo record);

    List<FarmBaseInfoVo> selectPageList(@Param("name") String name, @Param("status") Short status, @Param("farmId") Long farmId,
                                        @Param("start") Integer start, @Param("limit") Integer limit);
    int selectPageTotal(@Param("farmId") Long farmId, @Param("name") String name, @Param("status") Short status);
    FarmBaseInfo selectById(Long id);
    List<FarmBaseInfo> selectAll(@Param("farmId") Long farmId);

    List<FarmBaseInfoVo> selectBaseVoList(@Param("farmId") Long farmId);

    FarmBaseInfoVo selectBaseVo(@Param("id") Long id);
}