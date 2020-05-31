package com.smartfarm.base.farm.core.manager.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.MatrixToImageUtil;
import com.smartfarm.base.farm.core.dao.ProducePlanDetailDao;
import com.smartfarm.base.farm.core.dao.ProducePlanMasterDao;
import com.smartfarm.base.farm.core.dao.ProducePlantingTemplateDao;
import com.smartfarm.base.farm.core.dao.ProductBatchDao;
import com.smartfarm.base.farm.core.dao.SeedInfoDao;
import com.smartfarm.base.farm.core.dao.SeedProductInfoDao;
import com.smartfarm.base.farm.core.dao.SourceCodeDao;
import com.smartfarm.base.farm.core.dao.SourceInfoDao;
import com.smartfarm.base.farm.core.entity.ProducePlanDetail;
import com.smartfarm.base.farm.core.entity.ProducePlanMaster;
import com.smartfarm.base.farm.core.entity.ProducePlantingTemplate;
import com.smartfarm.base.farm.core.entity.ProductBatch;
import com.smartfarm.base.farm.core.entity.SeedInfo;
import com.smartfarm.base.farm.core.entity.SeedProductInfo;
import com.smartfarm.base.farm.core.entity.SourceCode;
import com.smartfarm.base.farm.core.entity.SourceInfo;
import com.smartfarm.base.farm.core.entity.vo.FarmTunnelInfoVo;
import com.smartfarm.base.farm.core.entity.vo.ProductBatchVo;
import com.smartfarm.base.farm.core.manager.ProductBatchManager;
import com.smartfarm.base.shop.core.entity.ProductInfo;

@Service("productBatchManager")
public class ProductBatchManagerImpl implements ProductBatchManager {
	
	@Resource
	private ProductBatchDao productBatchDao;
	
	@Resource
	private SourceInfoDao sourceInfoDao;
	
	@Resource
	private SeedInfoDao seedInfoDao;
	
	@Resource
	private ProducePlanMasterDao producePlanMasterDao;
	
	@Resource
	private ProducePlantingTemplateDao plantingTemplateDao;
	
	@Resource
	private ProducePlanDetailDao planDetailDao;
	
	@Resource
	private SeedProductInfoDao seedProductInfoDao;
	
	@Resource
	private SourceCodeDao sourceCodeDao;

	@Override
	public List<ProductBatchVo> getPageList(Long farmId,Integer start, Integer limit) {
		return productBatchDao.getPageList(farmId,start, limit);
	}

	@Override
	public int getPageTotal(Long farmId) {
		return productBatchDao.getPageTotal(farmId);
	}
	
	@Override
	public List<FarmTunnelInfoVo> getTunnelList(Long farmId) {
		return productBatchDao.getTunnelList(farmId);
	}

	@Override
	public List<ProductInfo> getProductName() {
		return productBatchDao.getProductName();
	}
	
	@Override
	public List<SeedInfo> querySeedInfoList() {
		return seedInfoDao.getAllSeedInfo();
	}

	@Override
	public ProductBatch getProductBatchByCode(String batchCode) {
		return productBatchDao.getProductBatchByCode(batchCode);
	}

	@Override
	public int addBatch(ProductBatch productBatch) {
		return productBatchDao.addBatch(productBatch);
	}

	@Override
	public int addMaster(Long seedId,Long productId,ProducePlanMaster producePlanMaster) {
		//<!-- 根据种子id查询其对应的所有种植计划模板 -->
		List<ProducePlantingTemplate> templates = plantingTemplateDao.getTemplatesBySeedId(seedId);
		if (templates.size()!=0){
			int day = templates.get(templates.size()-1).getDays();
			try {
				producePlanMaster.setEndDate(DateUtil.formatAddDate(producePlanMaster.getStartDate(),day));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}


		//添加 流水号id
		int insert =productBatchDao.addMaster(producePlanMaster);
//        //根据batch_id查找master的id
//		Long masterId = producePlanMasterDao.getMasterIdByBatchId(producePlanMaster.getBatchId());
//		//根据种子id和产品id查找匹配信息是否存在
//		SeedProductInfo seedProductInfo = seedProductInfoDao.getBySidAndPid(seedId, productId);
//		if (seedProductInfo == null) {
//			SeedProductInfo sInfo = new SeedProductInfo();
//			sInfo.setSeedId(seedId);
//			sInfo.setProductId(productId);
//			seedProductInfoDao.insert(sInfo);
//		}
//
//		List<ProducePlanDetail> planDetailList = new ArrayList<ProducePlanDetail>();
//		String startDate = producePlanMaster.getStartDate();
//		for (ProducePlantingTemplate template : templates) {
//			ProducePlanDetail planDetail = new ProducePlanDetail();
//			planDetail.setName(template.getName());
//			planDetail.setBrief(template.getBrief());
//			planDetail.setStage(template.getStage());
//			try {
//				planDetail.setTaskDate(DateUtil.formatAddDate(startDate, template.getDays()));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			planDetail.setPlantId(masterId);
//			planDetail.setLogStatus((short) 0);
//			planDetailList.add(planDetail);
//		}
//		int insert = planDetailDao.insertDetailList(planDetailList);
		return insert;
	}

	@Override
	public int deleteBatch(Long id) {
		return productBatchDao.deleteBatch(id);
	}

	@Override
	public int deleteMaster(Long id) {
		return productBatchDao.deleteMaster(id);
	}

	@Override
	public int modifySourceStatus(ProductBatch productBatch) {
		return productBatchDao.updateSourceStatus(productBatch);
	}

	@Override
	public int addSourceAndModifyStatus(SourceInfo sourceInfo, ProductBatch productBatch) {
		sourceInfo.setCodeStatus(SourceInfo.SOURCE_CODE_UNGENERATED);
		int add = sourceInfoDao.insert(sourceInfo);
		int update = productBatchDao.updateSourceStatus(productBatch);
		return add*update;
	}

	@Override
	public int addBatchDataAndSourceAmount(ProductBatch productBatch, SourceInfo sourceInfo,int size,String format,String folderName,int margin) {
		int pbAdd = productBatchDao.addYieldAndSpecs(productBatch);
		int amount = productBatch.getYield()/productBatch.getPackageSpecs();
		sourceInfo.setAmount(amount);
		int siAdd = sourceInfoDao.addSourceAmount(sourceInfo);
		
		sourceInfo.setCodeStatus(SourceInfo.SOURCE_CODE_GENERATED);
		
		List<SourceCode> list = new ArrayList<SourceCode>();
		for(int i=0;i<amount;i++){
			//批量生成溯源码
			SourceCode code = new SourceCode();
			code.setCreateTime(DateUtil.formatCurrentDateTime());
			code.setSourceId(sourceInfo.getId());
			code.setCode(sourceInfo.getPrefix()+UUID.randomUUID().toString().replaceAll("-", ""));
			code.setFirstSearchTime(null);
			code.setStatus(SourceCode.CODE_STATUS_UN_SELL);
			code.setSearchTimes(0);
			list.add(code);
		}
		
		int siModify = sourceInfoDao.addCodeStatus(sourceInfo);
		int scInsert = sourceCodeDao.insertBatchInfoList(list);
		
		List<SourceCode> sourceCodeList = sourceCodeDao.getCodeBySourceId(sourceInfo.getId());
		List<String> textList = new ArrayList<String>();
		for (SourceCode sourceCode : sourceCodeList) {
			textList.add("{\"code\":\""+ sourceCode.getCode()+"\"}");
		}
		MatrixToImageUtil.exportTraceCode(textList, size, size, format, folderName, margin);
		
		sourceInfoDao.updateFolderNameById(sourceInfo.getId(), folderName);
		
		return pbAdd*siAdd*siModify*scInsert;
	}
	

}
