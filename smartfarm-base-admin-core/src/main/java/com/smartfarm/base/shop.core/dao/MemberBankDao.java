package com.smartfarm.base.shop.core.dao;

import java.util.List;

import com.smartfarm.base.shop.core.entity.MemberBank;

public interface MemberBankDao {
	
	/**
	 * 添加
	 * @param memberBank
	 * @return
	 */
    public int insert(MemberBank memberBank);

    /**
     * 根据id修改
     * @param memberBank
     * @return
     */
    public int updateById(MemberBank memberBank);
    
    /**
     * 查询会员绑定银行信息
     * @param memberId
     * @return
     */
    public List<MemberBank> queryBankByMemberId(Long memberId);
    
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public MemberBank queryById(Long id);
}