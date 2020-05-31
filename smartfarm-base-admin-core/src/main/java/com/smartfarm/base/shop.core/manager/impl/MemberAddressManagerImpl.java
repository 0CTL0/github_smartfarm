package com.smartfarm.base.shop.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import com.smartfarm.base.util.DateUtil;
import org.springframework.stereotype.Service;

import com.smartfarm.base.shop.core.dao.MemberAddressDao;
import com.smartfarm.base.shop.core.entity.MemberAddress;
import com.smartfarm.base.shop.core.manager.MemberAddressManager;


@Service("memberAddressManager")
public class MemberAddressManagerImpl implements MemberAddressManager {
	
	@Resource
	private MemberAddressDao memberAddressDao;

	@Override
	public List<MemberAddress> queryAddressByMemberId(Long memberId) {
		return memberAddressDao.queryAddressByMemberId(memberId);
	}

	@Override
	public void insert(MemberAddress memberAddress) {
		memberAddress.setCreateTime(DateUtil.formatCurrentDateTime());
		memberAddress.setStatus((short) 1);
		memberAddressDao.insert(memberAddress);
	}

	@Override
	public void updateById(MemberAddress memberAddress) {
		memberAddressDao.updateById(memberAddress);
	}

	@Override
	public MemberAddress getAddressDetailById(Long id) {
		return memberAddressDao.getAddressDetailById(id);
	}

	@Override
	public List<MemberAddress> queryMemberAddressById(Long memberId) {
		return memberAddressDao.queryMemberAddressById(memberId);
	}
}