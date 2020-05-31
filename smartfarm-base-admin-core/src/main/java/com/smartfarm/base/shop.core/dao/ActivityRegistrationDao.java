package com.smartfarm.base.shop.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.ActivityRegistration;
import com.smartfarm.base.shop.core.entity.vo.ActivityRegistrationVo;

public interface ActivityRegistrationDao {

	/**
	 * 添加
	 * @param record
	 * @return
	 */
    public int insert(ActivityRegistration activityRegistration);

    /**
     * 根据id修改
     * @param record
     * @return
     */
    public int updateById(ActivityRegistration activityRegistration);
    
  
    /**
     * 统计活动列表数
     * @param activityId
     * @return
     */
    public int  countActivityRegistList(@Param("activityRegistration") ActivityRegistration activityRegistration);

    /**
     * 查询活动全部用户信息
     * @param activityRegistration
     * @param start
     * @param limit
     * @return
     */
	public List<ActivityRegistrationVo> selectActivityRegistList(
            @Param("activityRegistration") ActivityRegistration activityRegistration, @Param("start") Integer start, @Param("limit") Integer limit);
    
	
	/**
	 * 根据表根号查询用户信息
	 * @param ticketNo
	 * @return
	 */
	public ActivityRegistrationVo selectActivityRegistration(@Param("ticketNo") String ticketNo, @Param("businessId") Long businessId);
	
	/**
	 * 查询活动参与人信息
	 * @param activityId
	 * @return
	 */
	public List<ActivityRegistrationVo> querySuccessListByActivityId(Long activityId);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ActivityRegistration queryById(Long id);
	
	/**
	 * 根据订单号查询
	 * @param orderNo
	 * @return
	 */
	public ActivityRegistration queryByOrderNo(@Param("orderNo") String orderNo);
	
	/**
	 * 查询用户农场活动信息
	 * @param memberId
	 * @return
	 */
	public List<ActivityRegistrationVo> queryListByMemberId(Long memberId);
	
}