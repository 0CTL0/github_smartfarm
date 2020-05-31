package com.smartfarm.base.shop.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import com.smartfarm.base.farm.core.dao.EmployeeInfoDao;
import com.smartfarm.base.farm.core.dao.SettingInfoDao;
import com.smartfarm.base.farm.core.entity.SettingInfo;
import org.springframework.stereotype.Service;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.shop.core.dao.MemberBankDao;
import com.smartfarm.base.shop.core.dao.MemberCashRecordDao;
import com.smartfarm.base.shop.core.dao.MemberInfoDao;
import com.smartfarm.base.shop.core.dao.MemberShareRecordDao;
import com.smartfarm.base.shop.core.dao.MemberVisitRecordDao;
import com.smartfarm.base.shop.core.entity.MemberBank;
import com.smartfarm.base.shop.core.entity.MemberCashRecord;
import com.smartfarm.base.shop.core.entity.MemberInfo;
import com.smartfarm.base.shop.core.entity.MemberShareRecord;
import com.smartfarm.base.shop.core.entity.MemberVisitRecord;
import com.smartfarm.base.shop.core.entity.vo.MemberInfoVo;
import com.smartfarm.base.shop.core.manager.MemberInfoManager;


@Service("memberInfoManager")
public class MemberInfoManagerImpl implements MemberInfoManager {
	
	@Resource
	private MemberInfoDao memberInfoDao;
	@Resource
	private MemberShareRecordDao memberShareRecordDao;
	@Resource
	private MemberVisitRecordDao memberVisitRecordDao;
	@Resource
	private MemberCashRecordDao memberCashRecordDao;
	@Resource
	private MemberBankDao memberBankDao;
//	@Resource
//	private MemberRelationDao memberRelationDao;
	@Resource
	private SettingInfoDao settingInfoDao;
	@Resource
	private EmployeeInfoDao employeeInfoDao;


	@Override
	public List<MemberInfo> getMemberFans(Long memberId) {
		return memberInfoDao.selectMemberFans(memberId);
	}

	@Override
	public int getMemberFansTotal(Long memberId) {
		return 0;
	}

	@Override
	public List<MemberInfo> getPageList(Long farmId, String nickName, Integer start, Integer limit) {
		return memberInfoDao.selectPageList(farmId,nickName,start,limit);
	}

	@Override
	public int getPageTotal(Long farmId, String nickName) {
		return memberInfoDao.selectPageTotal(farmId,nickName);
	}

	@Override
	public MemberInfo queryByOpenId(String openid,Long businessId) {
		return memberInfoDao.queryByOpenId(openid,businessId);
	}

	@Override
	public void insert(MemberInfo memberInfo) {
		memberInfoDao.insert(memberInfo);
	}

	@Override
	public MemberInfo queryById(Long id) {
		return memberInfoDao.queryById(id);
	}

	@Override
	public void updateMemberInfo(MemberInfo memberInfo) {
		memberInfoDao.updateById(memberInfo);
	}

	@Override
	public void addShareRecord(MemberShareRecord memberShareRecord) {
		memberShareRecordDao.insert(memberShareRecord);
	}

	@Override
	public void addShareVisit(String code, Long memberId) {
		MemberShareRecord memberShareRecord = memberShareRecordDao.queryShareRecordByCode(code);
		MemberVisitRecord memberVisitRecord = new MemberVisitRecord();
		memberVisitRecord.setCreateTime(DateUtil.formatCurrentDateTime());
		memberVisitRecord.setMemberId(memberId);
		memberVisitRecord.setShareRecordId(memberShareRecord.getId());
		memberVisitRecordDao.insert(memberVisitRecord);

		MemberInfo memberInfo = memberInfoDao.queryById(memberId);
		Long businessId=memberInfo.getBusinessId();
		//获取绑定规则
		SettingInfo settingInfo = settingInfoDao.getInfoByType(businessId,"commission_user");
		//全部人绑定分享关系
		if(SettingInfo.VALUE_COMMISSION_USER_ALL.equals(settingInfo.getParamValue())){
			if(memberInfo.getMemberId() == null && memberShareRecord.getMemberId().longValue() != memberId.longValue()) {
				memberInfo.setMemberId(memberShareRecord.getMemberId());
				memberInfoDao.updateById(memberInfo);
			}
		}
		//员工绑定分享关系
		if(SettingInfo.VALUE_COMMISSION_EMPLOYEE_.equals(settingInfo.getParamValue())){
			String mobile=memberInfo.getMobile();
			if(mobile != null){
				if(employeeInfoDao.queryEmployeeInfoByMobile(mobile, businessId)!=null){
					if(memberInfo.getMemberId() == null && memberShareRecord.getMemberId().longValue() != memberId.longValue()) {
						memberInfo.setMemberId(memberShareRecord.getMemberId());
						memberInfoDao.updateById(memberInfo);
					}
				}
			}
		}

		//TODO
//		int count = memberRelationDao.countMemberRelation(memberShareRecord.getMemberId(), memberId);
//		if(count == 0 && memberShareRecord.getMemberId().longValue() != memberId.longValue()) {
//			MemberRelation memberRelation = new MemberRelation();
//			memberRelation.setMemberId(memberShareRecord.getMemberId());
//			memberRelation.setLinkMemberId(memberId);
//			memberRelationDao.insert(memberRelation);
//		}


	}

	@Override
	public MemberInfoVo queryMemberVoById(Long id) {
		return memberInfoDao.queryMemberVoById(id);
	}

	@Override
	public List<MemberCashRecord> queryCashRecord(Short status, Long memberId, Short cashType) {
		return memberCashRecordDao.queryCashRecord(status, memberId, cashType);
	}

	@Override
	public void cashRecordApply(Long memberId, Double money, Long bankId) {
		MemberInfo memberInfo = memberInfoDao.queryById(memberId);
		memberInfo.setCash(memberInfo.getCash() - money);
		memberInfoDao.updateById(memberInfo);
		MemberBank memberBank = memberBankDao.queryById(bankId);
		MemberCashRecord memberCashRecord = new MemberCashRecord();
		memberCashRecord.setMemberId(memberId);
		memberCashRecord.setBankName(memberBank.getBankName());
		memberCashRecord.setBankNo(memberBank.getBankNo());
		memberCashRecord.setAccount(memberBank.getAccount());
		memberCashRecord.setCashType((short)2);
		memberCashRecord.setStatus((short)2);
		memberCashRecord.setCreateTime(DateUtil.formatCurrentDateTime());
		memberCashRecord.setMoney(money);
		memberCashRecordDao.insert(memberCashRecord);
		
	}

	@Override
	public MemberInfo queryByNickName(String nickName, Long businessId) {
		MemberInfo info =memberInfoDao.queryByNickName(nickName,businessId);
		if(info==null){
			return null;
		}
		return info;
	}

	@Override
	public Short getMemberTypeById(Long memberId) {

		Short memberType = MemberInfo.MEMBER_TYPE_OLD; 
		
		MemberInfo memberinfo = memberInfoDao.queryById(memberId);
		
		if (memberinfo.getCreateTime().substring(0, 7).equals(DateUtil.formatCurrentDate())){
			memberType = MemberInfo.MEMBER_TYPE_NEW;
		}

		return memberType;
	}

	@Override
	public List<MemberInfo> queryByFarmId(Long farmId) {
		return memberInfoDao.queryByFarmId(farmId);
	}

}