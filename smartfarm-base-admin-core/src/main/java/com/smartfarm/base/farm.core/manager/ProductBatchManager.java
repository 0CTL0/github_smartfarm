package com.smartfarm.base.farm.core.manager;

import java.util.List;

import com.smartfarm.base.farm.core.entity.ProducePlanMaster;
import com.smartfarm.base.farm.core.entity.ProductBatch;
import com.smartfarm.base.farm.core.entity.SeedInfo;
import com.smartfarm.base.farm.core.entity.SourceInfo;
import com.smartfarm.base.farm.core.entity.vo.FarmTunnelInfoVo;
import com.smartfarm.base.farm.core.entity.vo.ProductBatchVo;
import com.smartfarm.base.shop.core.entity.ProductInfo;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年11月22日 11:21:28
 * @version 1.0
 */
public interface ProductBatchManager {

	/**
	 * 分页查询
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ProductBatchVo> getPageList(Long farmId, Integer start, Integer limit);

	/**
	 * 统计页数
	 * 
	 * @param
	 * @return
	 */
	public int getPageTotal(Long farmId);
	
	/**
	 * 查询所有基地地块信息
	 * @return
	 */
	public List<FarmTunnelInfoVo> getTunnelList(Long farmId);
	
	/**
	 * 查询农作物名称
	 * @return
	 */
	public List<ProductInfo> getProductName();
	
	/**
	 * 查询种子信息
	 * @return
	 */
	public List<SeedInfo> querySeedInfoList();
	
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
	public ProductBatch getProductBatchByCode(String batchCode);
	
	/**
	 * 添加生产主计划
	 * @param producePlanMaster
	 * @return
	 */
	public int addMaster(Long seedId, Long productId, ProducePlanMaster producePlanMaster);
	
	/**
	 * 删除批次
	 * @param id
	 * @return
	 */
	public int deleteBatch(Long id);
	
	/**
	 * 删除主计划
	 * @param id
	 * @return
	 */
	public int deleteMaster(Long id);
	
	/**
	 * 修改溯源信息添加状态
	 * @param
	 * @return
	 */
	public int modifySourceStatus(ProductBatch productBatch);
	
	/**
	 * 添加溯源信息，并修改batch表的source_status
	 * @param sourceInfo
	 * @param productBatch
	 * @return
	 */
	public int addSourceAndModifyStatus(SourceInfo sourceInfo, ProductBatch productBatch);
	
	/**
	 * 添加批次产量、规格，设置溯源码数量
	 * @param productBatch
	 * @param sourceInfo
	 * @return
	 */
	public int addBatchDataAndSourceAmount(ProductBatch productBatch, SourceInfo sourceInfo, int size, String format, String folderName, int margin);
}
