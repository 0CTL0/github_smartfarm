package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.ProducePlantingTemplate;

public interface ProducePlantingTemplateDao {
	
	/**
	 * 根据种子id查询其对应的所有种植计划模板
	 * @param seedId
	 * @return
	 */
	public List<ProducePlantingTemplate> getTemplatesBySeedId(@Param("seedId") Long seedId);
	
    int deleteByPrimaryKey(Long id);

    int insert2(ProducePlantingTemplate record);

    int insert(ProducePlantingTemplate record);

    ProducePlantingTemplate selectByPrimaryKey(Long id);

    int updateById(ProducePlantingTemplate record);

    int updateByPrimaryKey(ProducePlantingTemplate record);
}