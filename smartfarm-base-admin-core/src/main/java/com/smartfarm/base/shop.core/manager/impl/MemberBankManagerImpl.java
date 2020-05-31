package com.smartfarm.base.shop.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.shop.core.dao.MemberBankDao;
import com.smartfarm.base.shop.core.entity.MemberBank;
import com.smartfarm.base.shop.core.manager.MemberBankManager;


@Service("memberBankManager")
public class MemberBankManagerImpl implements MemberBankManager {
	
	@Resource
	private MemberBankDao memberBankDao;

	@Override
	public int insert(MemberBank memberBank) {
		return memberBankDao.insert(memberBank);
	}

	@Override
	public List<MemberBank> queryBankByMemberId(Long memberId) {
		return memberBankDao.queryBankByMemberId(memberId);
	}


}