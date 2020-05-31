package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.Crowdfunding;
import com.smartfarm.base.farm.core.entity.vo.CrowdFundingVo;
import com.smartfarm.base.farm.core.entity.vo.CrowfundingGradeVo;

public interface CrowdfundingDao {
	
	/**
	 * 新增
	 * @param crowdfunding
	 * @return
	 */
    public Long insert(Crowdfunding crowdfunding);

    /**
     * 根据id修改
     * @param crowdfunding
     * @return
     */
    public int updateById(Crowdfunding crowdfunding);
    
    /**
     * 分页查询所有众筹项目
     * @param start
     * @param limit
     * @return
     */
    public List<Crowdfunding> quryCrowdFundingList(@Param("start") Integer start, @Param("limit") Integer limit);
    
    /**
     * 查询所有众筹项目数量
     * @return
     */
    public int queryCountCrowdFunding();
    
    /**
     * 根据id查询crowdFunding详细
     * @param id
     * @return
     */
    public CrowdFundingVo queryCrowdFundingById(@Param("id") Long id);
    
    /**
     * 查询全部众筹列表(小程序端)
     * @return
     */
    public List<CrowdFundingVo> getAllCrowdfundingListWithStatus(@Param("farmId") long farmId, @Param("status") short status);
    
    
    public List<CrowdFundingVo> getAllCrowdfundingList(@Param("farmId") long farmId);
    
    /**
     * 根据id查询众筹项目(小程序端)
     * @param id
     * @return
     */
    public CrowdFundingVo getCrowdfundingDetail(@Param("id") Long id);
    
    
    /***
     * 根据众筹等级id查询众筹信息
     * @param gradeId
     * @return
     */
    public CrowfundingGradeVo getCrowdFundingDetailByGradeId(@Param("gradeId") Long gradeId);
}