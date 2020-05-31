package com.smartfarm.base.shop.core.manager;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.shop.core.entity.ActivityInfo;
import com.smartfarm.base.shop.core.entity.ActivityPrice;
import com.smartfarm.base.shop.core.entity.ActivityRegistration;
import com.smartfarm.base.shop.core.entity.vo.ActivityInfoVo;
import com.smartfarm.base.shop.core.entity.vo.ActivityRegistrationVo;

public interface ActivityManager {
	/**
	 * 添加活动
	 * @param activity
	 * @param priceList
	 * @return
	 */
	public int editActivity(ActivityInfo activity, List<ActivityPrice> priceList);
	/**
	 * 编辑活动
	 * @param activity
	 * @param priceList
	 * @return
	 */
	public int addActivity(ActivityInfo activity, List<ActivityPrice> priceList);
	/**
	 * 分类查询所有的活动
	 * @param LastId
	 * @return
	 */
	public List<ActivityInfo> queryAllActivityInfo(String name, Integer start, Integer limit, Long businessId);
		/**
		 * 	查询活动总条数
		 * @param status
		 * @param businessId
		 * @return
		 */
	public int countActivityList(String name, Long businessId);
	
	/**
	 * 添加活动信息
	 * @param activity
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public int insert(ActivityInfo activity, MultipartFile file, List<ActivityPrice> priceList)throws IOException;
	/**
	 * 根据Id查询活动信息
	 * @param id
	 * @return
	 */
	public Object selectById(Long id);
	
	/**
	 * 查询活动的价格档次列表
	 * @param id
	 * @return
	 */
	public List<ActivityPrice> selectActivityPriceList(Long id);
	
	/**
	 * 删除价格档次
	 * @param id
	 * @return
	 */
	public int deleteActivityPrice(Long id);
	
	/**
	 * 编辑活动信息
	 * @param activity
	 * @param file
	 * @param priceList
	 * @return
	 * @throws IOException
	 */
	public int editActivityInfo(ActivityInfo activity, MultipartFile file, List<ActivityPrice> priceList)throws IOException;
	/**
	 * 改变活动的状态
	 */
	public int changeStatus(Long id);
	
	/**
	 *根据条件获取活动用户列表
	 * @param Id
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ActivityRegistrationVo> queryActivityRegistrationList(ActivityRegistration activityRegistration, Integer start, Integer limit);
	
	/**
	 * 根据条件统计活动用户列表
	 * @param activityId
	 * @return
	 */
	public int countActivityRegistrationList(ActivityRegistration activityRegistration);	
	
	/**
	 * 根据活动ID获取活动
	 * @param activityId
	 * @return
	 */
	public ActivityInfo getActivity(Long activityId);
	
	/**
	 * 根据票根号查询活动用户
	 * @param ticketNo
	 * @return
	 */
	public ActivityRegistrationVo searchActivityRegist(String ticketNo, Long businessId);
	
	/**
	 * 查询可用的前端活动
	 * @return
	 */
	public List<ActivityInfoVo> queryUseableList(String nowDate, Long businessId);
	
	/**
	 * 根据id查询活动详情
	 * @param id
	 * @return
	 */
	public ActivityInfoVo queryDetailActivityById(Long id);
	
	/**
	 * 更新活动剩余名额
	 * @param num
	 * @param id
	 * @return
	 */
	public int updateRemain(Integer num, Long id);
	
	/**
	 * 添加用户报名记录
	 * @param activityId
	 * @param priceId
	 * @param num
	 */
	public Long addRegistration(Long activityId, Long priceId, Integer num, Long memberId) throws Exception;
	
	/**
	 * 根据id查询价格
	 * @param id
	 * @return
	 */
	public ActivityPrice queryActivityPrice(Long id);
	
	/**
	 * 取消订单
	 * @param id
	 */
	public void orderCancel(Long id);
	
	/**
	 * 订单超时
	 * @param id
	 */
	public void orderOutTime(Long id);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ActivityRegistration queryRegistrationById(Long id);
	
	/**
	 * 根据id查询活动
	 * @param id
	 * @return
	 */
	public ActivityInfo queryActivityById(Long id);
	
	/**
	 * 更新农场活动订单
	 * @param activityRegistration
	 */
	public void updateRegistration(ActivityRegistration activityRegistration);
	
	/**
	 * 完成订单
	 * @param orderNo
	 */
	public void successOrder(String orderNo, String traderNo);
	
	/**
	 * 查询用户农场活动信息
	 * @param memberId
	 * @return
	 */
	public List<ActivityRegistrationVo> queryRegistationListByMemberId(Long memberId);
	
	/**
	 * 查询推荐活动
	 * @param farmId
	 * @return
	 */
	public List<ActivityInfoVo> queryActivityForAdvance(Long farmId);
	
	/**
	 * 查询可用的活动
	 * @param farmId
	 * @return
	 */
	public List<ActivityInfoVo> queryActivityForAdvanceAdd(Long farmId);
}
