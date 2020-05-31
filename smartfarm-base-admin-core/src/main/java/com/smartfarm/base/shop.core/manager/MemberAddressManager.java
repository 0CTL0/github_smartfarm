package com.smartfarm.base.shop.core.manager;

import java.util.List;

import com.smartfarm.base.shop.core.entity.MemberAddress;



public interface MemberAddressManager {
	/**
     * 查询用户收货地址
     * @param memberId
     * @return
     */
    public List<MemberAddress> queryAddressByMemberId(Long memberId);
    
    /**
     * 新增
     * @param memberAddress
     */
    public void insert(MemberAddress memberAddress);
    
    /**
     * 修改
     * @param memberAddress
     */
    public void updateById(MemberAddress memberAddress);
    
    /**
     * 根据id得到地址信息
     * @param id
     * @return
     */
    MemberAddress getAddressDetailById(Long id);
    
    /**
     * 根据用户id查看收货地址
     * @param memberId
     * @return
     */
    List<MemberAddress> queryMemberAddressById(Long memberId);
}
