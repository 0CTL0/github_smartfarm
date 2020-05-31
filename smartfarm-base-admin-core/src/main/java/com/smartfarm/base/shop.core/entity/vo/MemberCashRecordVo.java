package com.smartfarm.base.shop.core.entity.vo;

import com.smartfarm.base.shop.core.entity.MemberCashRecord;

public class MemberCashRecordVo extends MemberCashRecord {
	private String memberName;
	private String operatorName;
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
}
