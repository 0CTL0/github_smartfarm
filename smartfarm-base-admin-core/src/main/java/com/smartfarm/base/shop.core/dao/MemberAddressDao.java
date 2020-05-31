package com.smartfarm.base.shop.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.MemberAddress;

public interface MemberAddressDao {
	
	/**
	 * 添加
	 * @param memberAddress
	 * @return
	 */
    public int insert(MemberAddress memberAddress);
    
    /**
     * 根据id修改
     * @param memberAddress
     * @return
     */
    public int updateById(MemberAddress memberAddress);
    
    /**
     * 查询用户收货地址
     * @param memberId
     * @return
     */
    public List<MemberAddress> queryAddressByMemberId(Long memberId);
    
    /**
     * 根据id得到地址信息
     * @param id
     * @return
     */
    MemberAddress getAddressDetailById(@Param("id") Long id);
    
    /**
     * 根据用户id查看收货地址
     * @param memberId
     * @return
     */
    List<MemberAddress> queryMemberAddressById(@Param("memberId") Long memberId);
}