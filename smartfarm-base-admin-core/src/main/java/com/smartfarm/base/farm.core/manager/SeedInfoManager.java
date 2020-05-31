package com.smartfarm.base.farm.core.manager;

import java.util.List;

import com.smartfarm.base.farm.core.entity.SeedInfo;

public interface SeedInfoManager {
	/**
	 * 查询种子列表
	 * @param name
	 * @param status
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<SeedInfo> getSeedInfoPageList(String name, Short status, Integer start, Integer limit);
	/**
	 * 统计种子列表
	 * @param name
	 * @param status
	 * @return
	 */
	public int countSeedInfoPageList(String name, Short status);
	/**
	 * 编辑种子信息
	 * @param seedInfo
	 * @return
	 */
	public int editSeedInfo(SeedInfo seedInfo);
	/**
	 * 根据ID查询种子消息
	 * @param id
	 * @return
	 */
	public SeedInfo getSeedInfo(Long id);
	/**
	 * 新增种子
	 * @param seedInfo
	 * @return
	 */
	public int addSeedInfo(SeedInfo seedInfo);
	
	 /**
     * 根据土地id查询种子信息
     * @param landId
     * @return
     */
    public List<SeedInfo> querySeedInfoByLandId(Long landId);
	
}
