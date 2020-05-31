package com.smartfarm.base.shop.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.MemberInfo;
import com.smartfarm.base.shop.core.entity.vo.MemberInfoVo;

public interface MemberInfoDao {
    /**
     * 查询用户的粉丝
     * @param memberId
     * @return
     */
    List<MemberInfo> selectMemberFans(@Param("memberId") Long memberId);

    /**
     * 统计用户的粉丝
     * @param memberId
     * @return
     */
    int selectMemberFansTotal(@Param("memberId") Long memberId);

    /**
     * 查询农场用户数据
     * @param farmId
     * @param nickName
     * @param start
     * @param limit
     * @return
     */
    List<MemberInfo> selectPageList(@Param("farmId") Long farmId, @Param("nickName") String nickName, @Param("start") Integer start, @Param("limit") Integer limit);
    /**
     * 统计农场用户
     * @param farmId
     * @param nickName
     * @return
     */
    int selectPageTotal(@Param("farmId") Long farmId, @Param("nickName") String nickName);
	/**
	 * 添加
	 * @param memberInfo
	 * @return
	 */
    public int insert(MemberInfo memberInfo);

    /**
     * 根据id修改
     * @param memberInfo
     * @return
     */
    public int updateById(MemberInfo memberInfo);
    
    /**
     * 根据openid查询
     * @param openid
     * @return
     */
    public MemberInfo queryByOpenId(@Param("openid") String openid, @Param("businessId") Long businessId);
    
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public MemberInfo queryById(Long id);
    
    /**
     * 根据用户名查询
     * @param nickName
     * @return
     */
    public MemberInfo queryByNickName(@Param("nickName") String nickName, @Param("businessId") Long businessId);
    
    /**
     * 查询用户信息
     * @param id
     * @return
     */
    public MemberInfoVo queryMemberVoById(Long id);
    
    /**
     * 更新用户的积分
     * @param memberInfo
     * @return
     */
    public int updateIntegralById(MemberInfo memberInfo);
    
    /**
     * 查询用户
     * @param farmId
     * @return
     */
    public List<MemberInfo> queryByFarmId(Long farmId);
}