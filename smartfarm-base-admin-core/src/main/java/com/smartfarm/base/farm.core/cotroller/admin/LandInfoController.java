package com.smartfarm.base.farm.core.cotroller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.util.UploadUtil;
import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.farm.core.entity.AcreageInfo;
import com.smartfarm.base.farm.core.entity.LandInfo;
import com.smartfarm.base.farm.core.entity.LandSeed;
import com.smartfarm.base.farm.core.entity.ShipStrategy;
import com.smartfarm.base.farm.core.entity.vo.LandInfoVo;
import com.smartfarm.base.farm.core.entity.vo.SeedInfoVo;
import com.smartfarm.base.farm.core.manager.LandInfoManager;

@RequestMapping("landInfo")
@Controller
public class LandInfoController {
	private Logger log = Logger.getLogger(LandInfoController.class);
	@Resource
	private  LandInfoManager landInfoManager;

	
	/**
	 * 获得土地列表及查询
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("queryLandInfoPageList")
	@ResponseBody
	public Object queryLandInfoPageList( String name, Short searchStatus, Integer pageSize, Integer pageNumber) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[LandInfoController:queryLandInfoPageList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}.");
			List<LandInfo> landInfoList = landInfoManager.getLandInfoPageList(name, searchStatus, (pageNumber - 1) * pageSize, pageSize);
			map.put("landInfoList", landInfoList);
			
			map.put("total", landInfoManager.countLandInfoPageList(name, searchStatus));
			
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[LandInfoController:queryLandInfoPageListt]false", e);
		}
		
		return map;
	}
	
	/**
	 * 获得土地分类和种子信息
	 * @param request
	 * @return
	 */
	@RequestMapping("queryLandTypesAndSeedInfos")
	@ResponseBody
	public Object queryLandTypesAndSeedInfos(HttpServletRequest request) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			map.put("landTypes", landInfoManager.getAllLandType());
			map.put("seedInfos", landInfoManager.getAllSeedInfo());
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[LandInfoController:queryLandTypesAndSeedInfos]false", e);
		}
		
		return map;
	}
	
	/**
	 * 新增土地信息
	 * @param request
	 * @param file
	 * @param files
	 * @return
	 */
	@RequestMapping("/addLandInfo")
	@ResponseBody
	public Object addLandInfo(HttpServletRequest request,LandInfo landInfo,String seedInfosJson,
			@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam(value = "files", required = false) MultipartFile[] files) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[LandInfoController:addLandInfo]{ seedInfo:" + landInfo);			
			log.info("[LandInfoController:addLandInfo]{ MultipartFile:" + file);
			log.info("[LandInfoController:addLandInfo]{MultipartFiles[]:" + files.length );					
			
			String coverStr= UploadUtil.uploadFile(file, "/upload/",RandomUtil.generateNumber(10));
			String descPathStr="";
			for(MultipartFile f :files){				
				String path = UploadUtil.uploadFile(f, "/upload/", RandomUtil.generateNumber(20));
				descPathStr=descPathStr+path+";";
			}			
			descPathStr=descPathStr.substring(0, descPathStr.length()-1);			
			log.info("[LandInfoController:addLandInfo]{descPathStr:" + descPathStr);
			
			//将可种植种子的id拼接成字符串
			
			JSONArray ja = JSONArray.fromObject(seedInfosJson);				
        	@SuppressWarnings("unchecked")
        	List<SeedInfoVo> seedInfoVoList = (List<SeedInfoVo>) JSONArray.toCollection(ja,SeedInfoVo.class); 
        	//log.info("[LandInfoController:addLandInfo]{seedInfoList.get(1).isFlag:" + seedInfoVoList.get(1).isFlag() );
        	/*
        	String idsString="";
        	for (SeedInfoVo seedInfoVo : seedInfoVoList) {
        		if(seedInfoVo.isFlag()){
        			Long id=seedInfoVo.getId();
        			String idStr=String.valueOf(id);       			        			
        			idsString=idsString+idStr+";";
        		}        		
        	}
			idsString=idsString.substring(0,idsString.length()-1);
			*/					
        	landInfo.setStatus(LandInfo.STATUS_SOLDOUT);
			landInfo.setCover(coverStr);
			landInfo.setDescription(descPathStr);;
			//landInfo.setSeedIds(idsString);			
			landInfoManager.addLandInfo(landInfo,seedInfoVoList);
			map.put("success", true);			
		}catch(Exception e){
			map.put("success", false);
			log.error("[LandInfoController:addLandInfo]false ", e);
		}		
		return map;
	}
	
	
	/**
	 * 获得编辑回显的土地信息
	 * @param request
	 * @return
	 */
	@RequestMapping("getLandInfoEdit")
	@ResponseBody
	public Object getLandInfoEdit(HttpServletRequest request,Long id) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[LandInfoController:getLandInfoEdit]{ id:" + id);	
			map.put("landTypes", landInfoManager.getAllLandType());
			LandInfo landInfo=landInfoManager.getLandInfo(id);
			map.put("landInfo", landInfo);
						
			/*
			String idsStr=landInfo.getSeedIds();
			String idsString[]=idsStr.split(";");
			List<Long> idsStrList=new ArrayList<Long>();
			for(int i=0;i<idsString.length;i++){
				idsStrList.add(Long.valueOf(idsString[i]));
			}
			log.info("[LandInfoController:addLandInfo]{idsStrList.get(1):" + idsStrList.get(1));
			*/
			//将选中的可种植种子设为true，表示已勾选
			List<SeedInfoVo> seedInfos=landInfoManager.getAllSeedInfo();
			List<LandSeed> landSeedList=landInfoManager.getLandSeedList(id);
			for(SeedInfoVo seedInfoVo:seedInfos){
				for(LandSeed landSeed:landSeedList){
					if(landSeed.getSeedId()==seedInfoVo.getId()){
						seedInfoVo.setFlag(true);
					}
				}
			}
												
			map.put("seedInfos", seedInfos);
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			log.error("[LandInfoController:queryLandTypesAndSeedInfos]false", e);
		}
		
		return map;
	}

	
	/**
	 * 编辑土地信息
	 * @param request
	 * @param file
	 * @param files
	 * @return
	 */
	@RequestMapping("/editLandInfo")
	@ResponseBody
	public Object editLandInfo(HttpServletRequest request,LandInfo landInfo,String seedInfosJson,
			@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam(value = "files", required = false) MultipartFile[] files) {
		Map<String, Object>map = new HashMap<String, Object>();
		try{
			log.info("[LandInfoController:addLandInfo]{ seedInfo:" + landInfo);			
			log.info("[LandInfoController:addLandInfo]{ MultipartFile:" + file);
			log.info("[LandInfoController:addLandInfo]{MultipartFiles[]:" + files.length );	
			
			if(file!=null){
				String coverStr=UploadUtil.uploadFile(file, "/upload/",RandomUtil.generateNumber(10));
				landInfo.setCover(coverStr);
			}			
			if(files.length>0){
				String descPathStr="";
				for(MultipartFile f :files){				
					String path = UploadUtil.uploadFile(f, "/upload/",RandomUtil.generateNumber(20));
					descPathStr=descPathStr+path+";";
				}			
				descPathStr=descPathStr.substring(0, descPathStr.length()-1);	
				landInfo.setDescription(descPathStr);
				log.info("[LandInfoController:addLandInfo]{descPathStr:" + descPathStr);
			}							
			
			//将可种植种子的id拼接成字符串
			JSONArray ja = JSONArray.fromObject(seedInfosJson);				
        	@SuppressWarnings("unchecked")
        	List<SeedInfoVo> seedInfoVoList = (List<SeedInfoVo>) JSONArray.toCollection(ja,SeedInfoVo.class); 
        	/*log.info("[LandInfoController:addLandInfo]{seedInfoList.get(1).isFlag:" + seedInfoVoList.get(1).isFlag() );
        	String idsString="";
        	for (SeedInfoVo seedInfoVo : seedInfoVoList) {
        		if(seedInfoVo.isFlag()){
        			Long id=seedInfoVo.getId();
        			String idStr=String.valueOf(id);       			        			
        			idsString=idsString+idStr+";";
        		}        		
        	}
			idsString=idsString.substring(0,idsString.length()-1);									
			landInfo.setSeedIds(idsString);
			*/
			
        	landInfoManager.editLandSeedInfo(landInfo, seedInfoVoList);
			map.put("success", true);			
		}catch(Exception e){
			map.put("success", false);
			log.error("[LandInfoController:addLandInfo]false ", e);
		}		
		return map;
	}
		
	/**
	 * 改变状态
	 * @return
	 */
	@RequestMapping("/changeStatus")
	@ResponseBody
	public Object changeStatus(@RequestParam("landId")Long landId){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[LandInfoController:changeStatus]{landId:" + landId + "}");
			if(landInfoManager.changeStatus(landId)==0)	{
				map.put("success", false);
			}
			else{
				map.put("success", true);
			}		
		}
		catch(Exception e){
			map.put("success", false);
			log.error("[EasyTaskInfoController:changeExecutionStatus]false!",e);
		}
		return map;
	}
	
	/**
	 * 根据土地Id获取土块列表
	 * @return
	 */
	@RequestMapping("/getAcreageInfoList")
	@ResponseBody
	public Object getAcreageInfoList(@RequestParam("landId")Long landId){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[LandInfoController:changeStatus]{landId:" + landId + "}");
		
			map.put("acreageInfoList", landInfoManager.getAcreageList(landId));
			map.put("success", true);
					
		}
		catch(Exception e){
			map.put("success", false);
			log.error("[EasyTaskInfoController:changeExecutionStatus]false!",e);
		}
		return map;
	}
	
	/**
	 * 根据土地Id获取配送周期列表
	 * @return
	 */
	@RequestMapping("/getshipList")
	@ResponseBody
	public Object getshipList(@RequestParam("landId")Long landId){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[LandInfoController:changeStatus]{landId:" + landId + "}");
		
			map.put("shipList", landInfoManager.getShipList(landId));
			map.put("success", true);
					
		}
		catch(Exception e){
			map.put("success", false);
			log.error("[LandInfoController:getshipList]false!",e);
		}
		return map;
	}
	
	/**
	 * 删除土块信息
	 * @param acreageInfo
	 * @return
	 */
	@RequestMapping("/removeAcreageInfo")
	@ResponseBody
	public Object removeAcreageInfo(AcreageInfo acreageInfo){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[LandInfoController:removeAcreageInfo]{acreageInfo.getId():" + acreageInfo.getId() + "}");
		
			landInfoManager.removeAcreageInfoById(acreageInfo);
			map.put("success", true);					
		}
		catch(Exception e){
			map.put("success", false);
			log.error("[LandInfoController:removeAcreageInfo]false!",e);
		}
		return map;
	}
	
	
	/**
	 * 删除配送周期信息
	 * @return
	 */
	@RequestMapping("/removeShipStrategy")
	@ResponseBody
	public Object removeShipStrategy(ShipStrategy shipStrategy){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			log.info("[LandInfoController:removeAcreageInfo]{acreageInfo.getId():" + shipStrategy.getId() + "}");
		
			landInfoManager.removeShipStrategyById(shipStrategy);
			map.put("success", true);					
		}
		catch(Exception e){
			map.put("success", false);
			log.error("[LandInfoController:removeAcreageInfo]false!",e);
		}
		return map;
	}
	
	/**
	 * 保存土块信息
	 * @return
	 */
	@RequestMapping("/saveAcreage")
	@ResponseBody
	public Object addAcreageInfo(HttpServletRequest request,String acreageInfoListJson){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			JSONArray ja = JSONArray.fromObject(acreageInfoListJson);				
        	@SuppressWarnings("unchecked")
        	List<AcreageInfo> acreageInfoList = (List<AcreageInfo>) JSONArray.toCollection(ja,AcreageInfo.class); 
        	//log.info("[LandInfoController:addAcreageInfo]{acreageInfoList.get(3).id:" + acreageInfoList.get(2).getId());
        	landInfoManager.addAcreageInfo(acreageInfoList);
        	
			map.put("success", true);					
		}
		catch(Exception e){
			map.put("success", false);
			log.error("[LandInfoController:addAcreageInfo]false!",e);
		}
		return map;
	}
	
	/**
	 * 保存配送周期信息
	 * @return
	 */
	@RequestMapping("/saveShipStrategy")
	@ResponseBody
	public Object saveShipStrategy(HttpServletRequest request,String shipStrategyJson){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			JSONArray ja = JSONArray.fromObject(shipStrategyJson);				
        	@SuppressWarnings("unchecked")
        	List<ShipStrategy> shipStrategyList = (List<ShipStrategy>) JSONArray.toCollection(ja,ShipStrategy.class); 
        	//log.info("[LandInfoController:saveShipStrategy]{shipStrategyList.get(0).id:" + shipStrategyList.get(0).getId());
        	landInfoManager.addShipStrategy(shipStrategyList);
        	
			map.put("success", true);					
		}
		catch(Exception e){
			map.put("success", false);
			log.error("[LandInfoController:addShipStrategy]false!",e);
		}
		return map;
	}
	
	
	/**
	 * 接口：获得可以租用土地的信息（状态为上架）
	 * @return
	 */
	@RequestMapping("/getAllLandInfoList")
	@ResponseBody
	public Object getAllLandInfoList(){
		Map<String,Object> map=new HashMap<String,Object>();
		try{			
			List<LandInfoVo> landInfoVoList=landInfoManager.getAllLandInfo();
			//以typeId为条件，划分为几个list,存入map中
			Map<Long,List<LandInfoVo>> landInfoMap=new HashMap<Long,List<LandInfoVo>>();			
			for(LandInfoVo landInfoVo : landInfoVoList){
				Long typeId=landInfoVo.getTypeId();
				if(map.containsKey(typeId)){
					landInfoMap.get(typeId).add(landInfoVo);
				}
				else{
					List<LandInfoVo> landList=new ArrayList<LandInfoVo>();
					landList.add(landInfoVo);
					landInfoMap.put(typeId, landList);
				}
			}
			map.put("landInfoList", landInfoMap);
			
			map.put("landTypeList", landInfoManager.getAllLandType());
			map.put("success", true);					
		}
		catch(Exception e){
			map.put("success", false);
			log.error("[LandInfoController:getLandInfoList]false!",e);
		}
		return map;
	}
	
	
	
	
}
