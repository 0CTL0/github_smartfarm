package com.smartfarm.base.shop.core.dao;

import com.smartfarm.base.shop.core.entity.MemberOrderCommission;
import com.smartfarm.base.shop.core.entity.vo.MemberOrderCommissionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberOrderCommissionDao {

    /**
     * 查询类表页面数据
     * @param commissionMemberName
     * @param orderMemberName
     * @param orderNo
     * @param start
     * @param limit
     * @return
     */
    List<MemberOrderCommissionVo> selectPageList(@Param("farmId") Long farmId, @Param("commissionMemberName") String commissionMemberName,
                                                 @Param("orderMemberName") String orderMemberName, @Param("orderNo") String orderNo,
                                                 @Param("start") Integer start, @Param("limit") Integer limit);

    /**
     * 统计列表页面数据
     * @param commissionMemberName
     * @param orderMemberName
     * @param orderNo
     * @return
     */
    int selectPageTotal(@Param("farmId") Long farmId, @Param("commissionMemberName") String commissionMemberName,
                        @Param("orderMemberName") String orderMemberName, @Param("orderNo") String orderNo);

    /**
     * 查询用户的分销订单记录
     * @param memberId
     * @return
     */
    List<MemberOrderCommissionVo> selectMemberOrderCommissions(@Param("memberId") Long memberId);

    int deleteByPrimaryKey(Long id);

    int insertSelective(MemberOrderCommission record);

    MemberOrderCommission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberOrderCommission record);


}