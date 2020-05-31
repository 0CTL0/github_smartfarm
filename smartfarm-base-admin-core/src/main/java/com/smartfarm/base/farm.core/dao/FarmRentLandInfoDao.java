package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.FarmRentLandInfo;
import com.smartfarm.base.farm.core.entity.vo.FarmRentLandInfoVo;

public interface FarmRentLandInfoDao {
    int deleteByPrimaryKey(Long id);
    int insertSelective(FarmRentLandInfo record);
    FarmRentLandInfo selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(FarmRentLandInfo record);

    /**
     * 通过rentLandId查询FarmId
     * @param rentLandId
     * @return
     */
    Long selectFarmId(@Param("rentLandId") Long rentLandId);

    /**
     * 列表数据查询
     * @param name
     * @param status
     * @param tunnelId
     * @param start
     * @param limit
     * @return
     */
    List<FarmRentLandInfoVo> selectPageList(@Param("name") String name, @Param("status") Short status, @Param("tunnelId") Long tunnelId,

                                            @Param("start") Integer start, @Param("limit") Integer limit);

    /**
     * 统计列表数据
     * @param name
     * @param status
     * @param tunnelId
     * @return
     */
    int selectPageTotal(@Param("name") String name, @Param("status") Short status, @Param("tunnelId") Long tunnelId);

    /**
     * 查询土块的租地信息列表
     * @param tunnelId
     * @return
     */
    List<FarmRentLandInfoVo> selectRentLandVoList(@Param("tunnelId") Long tunnelId);

    /**
     * 根据用户租地id查询租地信息
     * @param memberLandId
     * @return
     */
    public FarmRentLandInfo getRentLandByMemberLandId(@Param("memberLandId") Long memberLandId);
}