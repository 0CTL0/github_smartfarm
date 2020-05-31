package com.smartfarm.base.farm.core.cotroller.admin;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.farm.core.entity.ProducePlanMaster;
import com.smartfarm.base.farm.core.entity.ProductBatch;
import com.smartfarm.base.farm.core.entity.SeedInfo;
import com.smartfarm.base.farm.core.entity.SourceInfo;
import com.smartfarm.base.farm.core.entity.vo.FarmTunnelInfoVo;
import com.smartfarm.base.farm.core.entity.vo.ProductBatchVo;
import com.smartfarm.base.farm.core.manager.ProductBatchManager;
import com.smartfarm.base.farm.core.manager.SourceInfoManager;
import com.smartfarm.base.shop.core.entity.ProductInfo;


@Controller
@RequestMapping("/productBatch")
public class ProductBatchController {

	private Logger log = Logger.getLogger(ProductBatchController.class);

	@Resource
	private ProductBatchManager productBatchManager;

	@Resource
	private SourceInfoManager sourceInfoManager;


	/**
	 * 分页查询批次信息
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryProductBatchs")
	@ResponseBody
	public Object queryPageList(HttpServletRequest request,Integer pageSize,Integer pageNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProductBatchController:queryPageList] {pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}.");
			Long Long3=(Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
			List<ProductBatchVo> productBatchList = productBatchManager.getPageList(Long3,(pageNumber - 1) * pageSize, pageSize);
			for (ProductBatchVo batch : productBatchList){
				if (batch.getStartDate() !=null && !batch.getStartDate().equals("")){
					batch.setStartDate(DateUtil.formatDate(batch.getStartDate()));
				}
				if (batch.getEndDate() !=null && !batch.getEndDate().equals("")){
					batch.setEndDate(DateUtil.formatDate(batch.getEndDate()));
				}
				if (batch.getCreateTime() !=null && !batch.getCreateTime().equals("")){
					batch.setCreateTime(DateUtil.formatDate(batch.getCreateTime()));
				}

			}
			map.put("productBatchList", productBatchList);
			map.put("total", productBatchManager.getPageTotal(Long3));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProductBatchController:queryPageList] fail to query productBatch list.",e);
		}
		return map;
	}

	/**
	 * 查询地块信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAddInfo")
	@ResponseBody
	public Object queryTunnelList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProductBatchController:queryTunnelList] begin to query tunnel list.");
			List<FarmTunnelInfoVo> farmTunnelList = productBatchManager.getTunnelList((Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID));
			for (FarmTunnelInfoVo ftiv : farmTunnelList) {
				ftiv.setShowName(ftiv.getFarmName()+" - "+ftiv.getBaseName()+" - "+ftiv.getName());
			}
			map.put("farmTunnelList", farmTunnelList);
			List<ProductInfo> productList = productBatchManager.getProductName();
			map.put("productList", productList);
			List<SeedInfo> seedList = productBatchManager.querySeedInfoList();
			map.put("seedList", seedList);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProductBatchController:queryTunnelList] fail to query tunnel list.",e);
		}
		return map;
	}

	//提交添加批次
	@RequestMapping("/addBatchAndMaster")
	@ResponseBody
	public Object addBatchAndMaster(HttpServletRequest request,ProductBatch productBatch,ProducePlanMaster producePlanMaster,Long seedId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProductBatchController:addBatchAndMaster] begin to add batch and master info.");
			log.info("batchCode:"+productBatch.getId());
			log.info("tunnelId:"+producePlanMaster.getTunnelId());
			String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			productBatch.setCreateTime(currentDate);
			productBatch.setSourceStatus((short) 0);
			productBatch.setFarmId((Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID));
			//添加
			String str="";
			Random r = new Random();
			for(int i=0 ; i<4 ;  i++)
			{
				int ran1 = r.nextInt(10);
				str+=String.valueOf(ran1);
			}
            String currentDate2 = new SimpleDateFormat("yyyyMMddHH").format(new Date());
			String str2=currentDate2+str;
			ProductBatch pBatc=productBatchManager.getProductBatchByCode(str2);
			String str1="";
			if (pBatc!=null){
				//批次是否相同
				str2=pcsfxt(currentDate2);
				if (str2=="2"){
					map.put("success", false);
					return map;
				}
			}
			productBatch.setBatchCode(str2);
			productBatchManager.addBatch(productBatch);
			//根据批次号来查询id
			ProductBatch pBatch = productBatchManager.getProductBatchByCode(productBatch.getBatchCode());
			producePlanMaster.setBatchId(pBatch.getId());
			producePlanMaster.setStartDate(producePlanMaster.getStartDate().replaceAll("-", ""));
			producePlanMaster.setCreateTime(currentDate);


			//添加  添加生产主计划
			productBatchManager.addMaster(seedId,pBatch.getProductInfoId(),producePlanMaster);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProductBatchController:addBatchAndMaster] fail to add batch and master info.",e);
		}
		return map;
	}

	//批次是否相同
	public String pcsfxt(String currentDate){

		   String str1="2";
			Random r = new Random();
			for (int j=0 ; j<9999 ;  j++){
				String str="";
				for(int i=0 ; i<4 ;  i++)
				{
					int ran1 = r.nextInt(10);
					str+=String.valueOf(ran1);
				}
				String str2=currentDate+str;
				ProductBatch pBatc=productBatchManager.getProductBatchByCode(str2);
				if (pBatc ==null){
					str1=str2;
					break;
				}
			}
			return str1;
	}


	/**
	 * 删除批次和主计划
	 * @param request
	 * @param id
	 * @param masterId
	 * @return
	 */
	@RequestMapping("/deleteBatchAndMaster")
	@ResponseBody
	public Object deleteBatchAndMaster(HttpServletRequest request,Long id,Long masterId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProductBatchController:deleteBatchAndMaster] begin to delete batch and master.");
			productBatchManager.deleteBatch(id);
			productBatchManager.deleteMaster(masterId);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProductBatchController:deleteBatchAndMaster] fail to delete batch and master.",e);
		}
		return map;
	}

	//添加生产批次
	@RequestMapping("/addSource")
	@ResponseBody
	public Object addSource(HttpServletRequest request,SourceInfo sourceInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProductBatchController:addSource] begin to add source info.");
			ProductBatch productBatch = new ProductBatch();
			productBatch.setId(sourceInfo.getBatchId());
			productBatch.setSourceStatus((short) 1);
			productBatchManager.addSourceAndModifyStatus(sourceInfo, productBatch);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProductBatchController:addSource] fail to add source info.",e);
		}
		return map;
	}

	@RequestMapping("/addBatchData")
	@ResponseBody
	public Object addBatchData(HttpServletRequest request,ProductBatch productBatch,SourceInfo sourceInfo,Long sourceId,String folderName,String format,Integer size,Integer margin) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[ProductBatchController:addBatchData] begin to add batch data.");
			sourceInfo.setId(sourceId);
			log.info("[ProductBatchController:addBatchData] { si.id:"+sourceInfo.getId()+"}.");
			log.info("[ProductBatchController:addBatchData] { pb.id:"+productBatch.getId()+"}.");
			productBatchManager.addBatchDataAndSourceAmount(productBatch, sourceInfo,size,format,folderName,margin);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[ProductBatchController:addBatchData] fail to add batch data.",e);
		}
		return map;
	}
}
