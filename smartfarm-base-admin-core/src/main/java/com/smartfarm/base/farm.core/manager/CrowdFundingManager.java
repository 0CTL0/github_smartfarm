package com.smartfarm.base.farm.core.manager;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.farm.core.entity.Crowdfunding;
import com.smartfarm.base.farm.core.entity.vo.CrowdFundingVo;
import com.smartfarm.base.farm.core.entity.vo.CrowfundingGradeVo;

public interface CrowdFundingManager {
	
	 public Long insert(Crowdfunding crowdfunding, MultipartFile cover, MultipartFile[] desc, String gearListJson)  throws IOException;

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
    public List<Crowdfunding> quryCrowdFundingList(Integer start, Integer limit);
    
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
    public CrowdFundingVo queryCrowdFundingById(Long id);

    /**
     * 根据id修改众筹信息
     * @param crowdfunding
     * @param cover
     * @param files
     * @param gearListJson
     * @return
     * @throws IOException 
     */
	public int editCrowdFundingById(Crowdfunding crowdfunding,
                                    MultipartFile cover, MultipartFile[] files, String gearListJson) throws IOException;
	
	 /**
     * 查询全部众筹列表(小程序端)
     * @return
     */
    public List<CrowdFundingVo> getAllCrowdfundingListWithStatus(long farmId, short status);

    /**
     * 查询全部众筹列表
     * @param farmId
     * @return
     */
    public List<CrowdFundingVo> getAllCrowdfundingList(long farmId);
    
    /**
     * 根据id查询众筹项目(小程序端s)
     * @param id
     * @return
     */
    public CrowdFundingVo getCrowdfundingDetail(Long id);
    
    /***
     * 根据众筹等级id查询众筹信息
     * @param gradeId
     * @return
     */
    public CrowfundingGradeVo getCrowdFundingDetailByGradeId(Long gradeId);
}
