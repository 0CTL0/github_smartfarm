package com.smartfarm.base.farm.core.manager;

import java.util.List;

import com.smartfarm.base.farm.core.entity.ProducePlanDetail;
import com.smartfarm.base.farm.core.entity.vo.ProducePlanDetailVo;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年11月26日 09:55:58
 * @version 1.0
 */
public interface ProducePlanDetailManager {

	/**
	 * 根据master_id分页查询所有的计划明细
	 * @return
	 */
	public List<ProducePlanDetail> queryPageListByMasterId(Long masterId, Integer start, Integer limit);
	
	/**s
	 * 根据master_id查询单个主计划的计划明细总数
	 * @param masterId
	 * @return
	 */
	public int queryPageTotalByMasterId(Long masterId);
	
	/**
	 * 根据id查找单个计划
	 * @param id
	 * @return
	 */
	public ProducePlanDetail querySingleDetailById(Long id);
	
	/**
	 * 根据id修改计划
	 * @param planDetail
	 * @return
	 */
	public int modifyDetailById(ProducePlanDetail planDetail);
	
	/**
	 * 添加种植计划
	 * @param planDetail
	 * @return
	 */
	public int addPlanDetail(ProducePlanDetail planDetail);
	
	/**
	 * 根据id删除计划
	 * @param id
	 * @return
	 */
	public int deleteDetailById(Long id);
	
	/**
     * 分页查询未来一周内的所有生产计划明细,默认一周
     * @param start
     * @param limit
     * @return
     */
    public List<ProducePlanDetailVo> queryAllDetailsPageList(Long farmId, String batchCode, String name, String startDate, String endDate, Integer start, Integer limit);
    
    /**
     * 查询未来一周的生产计划总数,默认一周
     * @param startDate
     * @param endDate
     * @return
     */
    public int queryAllDetailsTotal(Long farmId, String name, String startDate, String endDate);
}
