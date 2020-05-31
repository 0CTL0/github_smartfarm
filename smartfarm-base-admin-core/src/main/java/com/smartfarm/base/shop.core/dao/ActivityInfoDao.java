package com.smartfarm.base.shop.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.shop.core.entity.ActivityInfo;
import com.smartfarm.base.shop.core.entity.vo.ActivityInfoVo;

public interface ActivityInfoDao {
	/**
	 * 添加
	 * @param record
	 * @return
	 */
    public int insert(ActivityInfo record);

    /**
     * 根据id修改
     * @param record
     * @return
     */
    public int updateById(ActivityInfo record);
    
    /**
     * 查询活动总数
     */
	public int selectActivityTotal(@Param("name") String name, @Param("businessId") Long businessId);
    
    /**
     * 分页查询活动列表
     * @param name
     * @param start
     * @param limit
     * @return
     */
    public List<ActivityInfo> selectActivityInfoList(@Param("name") String name, @Param("start") Integer start, @Param("limit") Integer limit, @Param("businessId") Long businessId);
	
    /**
     * 根据ID获取活动信息
     * @param id
     * @return
     */
	public ActivityInfo selectActivityById(Long id);
	
	/**
	 * 查询可用的前端活动
	 * @return
	 */
	public List<ActivityInfoVo> queryUseableList(@Param("nowDate") String nowDate, @Param("businessId") Long businessId);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ActivityInfoVo queryVoById(Long id);
	
	/**
	 * 更新活动剩余名额
	 * @param num
	 * @param id
	 * @return
	 */
	public int updateRemain(@Param("num") Integer num, @Param("id") Long id);
	
	/**
	 * 更新活动剩余名额
	 * @param num
	 * @param id
	 * @return
	 */
	public int addRemain(@Param("num") Integer num, @Param("id") Long id);
	
	/**
	 * 查询推荐活动
	 * @param farmId
	 * @return
	 */
	public List<ActivityInfoVo> queryActivityForAdvance(Long farmId);
	
	public ActivityInfoVo queryActivityForAdvanceId(Long id);
	
	/**
	 * 查询可用的活动
	 * @param farmId
	 * @return
	 */
	public List<ActivityInfoVo> queryActivityForAdvanceAdd(Long farmId);
}