package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.SeedDetail;
import com.smartfarm.base.farm.core.entity.vo.SeedDetailInfoVo;

public interface SeedDetailDao {
	
	/**
	 * 新增
	 * @param seedDetail
	 * @return
	 */
    public int insert(SeedDetail seedDetail);

    /**
     * 根据id修改
     * @param seedDetail
     * @return
     */
    public int updateById(SeedDetail seedDetail);
    /**
     * 根据id查询
     * @param seedDetail
     * @return
     */
    public List<SeedDetail> selectByRentOrderId(SeedDetail seedDetail);
    
    /**
     * 查询种植种子详细
     * @param detailId
     * @return
     */
    public SeedDetailInfoVo querySeedDetailById(@Param("detailId") Long detailId);
}