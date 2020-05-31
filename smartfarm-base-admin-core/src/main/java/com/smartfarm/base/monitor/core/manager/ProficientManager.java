package com.smartfarm.base.monitor.core.manager;

import java.util.List;

import com.smartfarm.base.monitor.core.entity.Proficient;

public interface ProficientManager {
	
	/**
	 * 分页查询
	 * @param mobile
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Proficient> getPageList(String mobile, Integer start, Integer limit);
	
	/**
	 * 统计页数
	 * @param mobile
	 * @return
	 */
	public int countProficlientPageList(String mobile);
	
	/**
	 * 新增专家
	 * @param seedInfo
	 * @return
	 */
	public int addProficlient(Proficient proficient);
	
	/**
	 * 根据id查找专家
	 * @param id
	 * @return
	 */
	public Proficient selectProficientById(Long id);
	
	/**
	 * 更新专家信息
	 * @param proficient
	 * @return
	 */
	public int updateProficient(Proficient proficient);
	
	/**
	 * 根据id删除专家
	 * @param id
	 * @return
	 */
	public int deleteProficient(Long id);
	
	/**
     * 查询全部专家
     * @return
     */
    public List<Proficient> queryProficientList();
}
