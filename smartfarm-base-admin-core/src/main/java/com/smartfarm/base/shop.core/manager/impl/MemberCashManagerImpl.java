package com.smartfarm.base.shop.core.manager.impl;


import com.smartfarm.base.shop.core.dao.MemberCashRecordDao;
import com.smartfarm.base.shop.core.entity.MemberCashRecord;
import com.smartfarm.base.shop.core.entity.vo.MemberCashRecordVo;
import com.smartfarm.base.shop.core.manager.MemberCashRecordManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("memberCashManager")
public class MemberCashManagerImpl implements MemberCashRecordManager {
	@Resource
	private MemberCashRecordDao memberCashManagerDao;

	@Override
	public List<MemberCashRecordVo> queryMemberCashRecordList(
			String memberName, Short status, Integer start, Integer limit) {		
		return memberCashManagerDao.queryMemberCashRecordList(memberName, status, start, limit);
	}

	@Override
	public int countMemberCashRecordList(String memberName, Short status) {
		return memberCashManagerDao.countCashRecordListPage(memberName, status);
	}

	@Override
	public int updateMemberCashRecord(MemberCashRecord memberCashRecord) {
		return memberCashManagerDao.updateById(memberCashRecord);
	}

	@Override
	public int insert(MemberCashRecord cashRecord) {
		return memberCashManagerDao.insert(cashRecord);
	}

}
