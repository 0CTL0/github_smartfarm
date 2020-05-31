package com.smartfarm.base.shop.core.manager;

import java.util.List;

import com.smartfarm.base.shop.core.entity.MemberBank;




public interface MemberBankManager {
	
	/**
	 * 添加
	 * @param memberBank
	 * @return
	 */
    public int insert(MemberBank memberBank);
    
    /**
     * 查询会员绑定银行信息
     * @param memberId
     * @return
     */
    public List<MemberBank> queryBankByMemberId(Long memberId);
}
