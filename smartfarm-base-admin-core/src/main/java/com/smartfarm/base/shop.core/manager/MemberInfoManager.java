package com.smartfarm.base.shop.core.manager;

import java.util.List;

import com.smartfarm.base.shop.core.entity.MemberCashRecord;
import com.smartfarm.base.shop.core.entity.MemberInfo;
import com.smartfarm.base.shop.core.entity.MemberShareRecord;
import com.smartfarm.base.shop.core.entity.vo.MemberInfoVo;



public interface MemberInfoManager {

    /**
     * 获取用户的粉丝
     * @param memberId
     * @return
     */
    List<MemberInfo> getMemberFans(Long memberId);

    /**
     * 统计用户的粉丝
     * @param memberId
     * @return
     */
    int getMemberFansTotal(Long memberId);

    /**
     * 查询用户后台页面列表
     * @param farmId
     * @param nickName
     * @param start
     * @param limit
     * @return
     */
    List<MemberInfo> getPageList(Long farmId, String nickName, Integer start, Integer limit);

    /**
     * 统计用户页面
     * @param farmId
     * @param nickName
     * @return
     */
    int getPageTotal(Long farmId, String nickName);
	
	/**
     * 根据openid查询
     * @param openid
     * @return
     */
    public MemberInfo queryByOpenId(String openid, Long businessId);
    
    /**
     * 添加用户
     * @param memberInfo
     */
    public void insert(MemberInfo memberInfo);
    
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
    public MemberInfo queryByNickName(String nickName, Long businessId);
    
    /**
     * 修改用户
     * @param memberInfo
     */
    public void updateMemberInfo(MemberInfo memberInfo);
    
    /**
     * 添加分享记录
     * @param memberShareRecord
     */
    public void addShareRecord(MemberShareRecord memberShareRecord);
    
    /**
     * 添加访问记录
     * @param code
     * @param memberId
     */
    public void addShareVisit(String code, Long memberId);
    
    /**
     * 查询用户信息
     * @param id
     * @return
     */
    public MemberInfoVo queryMemberVoById(Long id);
    
    /**
     * 按类型查询会员金额记录
     * @param status
     * @param memberId
     * @param cashType
     * @return
     */
    public List<MemberCashRecord> queryCashRecord(Short status, Long memberId, Short cashType);
    
    /**
     * 提现申请
     */
    public void cashRecordApply(Long memberId, Double money, Long bankId);
    
    /**
     * 获得当前会员的类型（MemberType）
     * @return
     */
    public Short getMemberTypeById(Long memberId);
    
    /**
     * 查询用户
     * @param farmId
     * @return
     */
    public List<MemberInfo> queryByFarmId(Long farmId);
    
}
