package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.ProducePlanMaster;
import com.smartfarm.base.farm.core.entity.ProductBatch;
import com.smartfarm.base.farm.core.entity.vo.FarmTunnelInfoVo;
import com.smartfarm.base.farm.core.entity.vo.ProductBatchVo;
import com.smartfarm.base.shop.core.entity.ProductInfo;

public interface ProductBatchDao {

	/**
	 * 分页查询
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ProductBatchVo> getPageList(@Param("farmId") Long farmId, @Param("start") Integer start, @Param("limit") Integer limit);
	
	/**
	 * 统计页数
	 * @param
	 * @return
	 */
	public int getPageTotal(@Param("farmId") Long farmId);
	
	/**
	 * 查询所有基地地块信息
	 * @return
	 */
	public List<FarmTunnelInfoVo> getTunnelList(@Param("farmId") Long farmId);
	
	/**
	 * 查询农作物名称
	 * @return
	 */
	public List<ProductInfo> getProductName();
	
	/**
	 * 添加批次
	 * @param productBatch
	 * @return
	 */
	public int addBatch(ProductBatch productBatch);
	
	/**
	 * 根据批次号查询批次信息
	 * @param batchCode
	 * @return
	 */
	public ProductBatch getProductBatchByCode(@Param("batchCode") String batchCode);
	
	/**
	 * 添加生产主计划
	 * @param producePlanMaster
	 * @return
	 */
	public int addMaster(ProducePlanMaster producePlanMaster);
	
	/**
	 * 删除批次
	 * @param id
	 * @return
	 */
	public int deleteBatch(@Param("id") Long id);
	
	/**
	 * 删除主计划
	 * @param id
	 * @return
	 */
	public int deleteMaster(@Param("id") Long id);
	
	/**
	 * 修改溯源信息添加状态
	 * @param
	 * @return
	 */
	public int updateSourceStatus(ProductBatch productBatch);
	
	/**
	 * 添加批次产量和规格
	 * @param productBatch
	 * @return
	 */
	public int addYieldAndSpecs(ProductBatch productBatch);
}
