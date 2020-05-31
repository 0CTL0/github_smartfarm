package com.smartfarm.base.farm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.dao.AcreageInfoDao;
import com.smartfarm.base.farm.core.dao.LandInfoDao;
import com.smartfarm.base.farm.core.dao.LandSeedDao;
import com.smartfarm.base.farm.core.dao.LandTypeDao;
import com.smartfarm.base.farm.core.dao.SeedInfoDao;
import com.smartfarm.base.farm.core.dao.ShipStrategyDao;
import com.smartfarm.base.farm.core.entity.AcreageInfo;
import com.smartfarm.base.farm.core.entity.LandInfo;
import com.smartfarm.base.farm.core.entity.LandSeed;
import com.smartfarm.base.farm.core.entity.LandType;
import com.smartfarm.base.farm.core.entity.SeedInfo;
import com.smartfarm.base.farm.core.entity.ShipStrategy;
import com.smartfarm.base.farm.core.entity.vo.LandInfoOrderVo;
import com.smartfarm.base.farm.core.entity.vo.LandInfoTypeVo;
import com.smartfarm.base.farm.core.entity.vo.LandInfoVo;
import com.smartfarm.base.farm.core.entity.vo.SeedInfoVo;
import com.smartfarm.base.farm.core.manager.LandInfoManager;

@Service("landInfoManager")
public class LandInfoManagerImpl implements LandInfoManager {
	@Resource
	private LandInfoDao landInfoDao;
	@Resource
	private LandTypeDao landTypeDao;
	@Resource
	private SeedInfoDao seedInfoDao;
	@Resource
	private AcreageInfoDao acreageInfoDao;
	@Resource
	private ShipStrategyDao shipStrategyDao;
	@Resource
	private LandSeedDao landSeedDao;

	@Override
	public List<LandInfo> getLandInfoPageList(String name, Short status,
			Integer start, Integer limit) {
		return landInfoDao.selectPageList(name, status, start, limit);
	}

	@Override
	public int countLandInfoPageList(String name, Short status) {
		return landInfoDao.selectPageTotal(name, status);
	}

	@Override
	public LandInfo getLandInfo(Long id) {		
		return landInfoDao.selectLandInfoById(id);
	}

	@Override
	public int addLandInfo(LandInfo landInfo,List<SeedInfoVo> seedInfoVoList) {
		landInfoDao.insert(landInfo);
		for (SeedInfoVo seedInfoVo : seedInfoVoList) {
    		if(seedInfoVo.isFlag()){
    			LandSeed landSeed=new LandSeed();
    			landSeed.setLandId(landInfo.getId());
    			landSeed.setSeedId(seedInfoVo.getId());
    			landSeedDao.insert(landSeed);
    		}        		
    	}
		return 0;
	}

	@Override
	public int editLandSeedInfo(LandInfo landInfo, List<SeedInfoVo> seedVoList) {
		landInfoDao.updateById(landInfo);
		Long LandId=landInfo.getId();
		landSeedDao.deleteById(LandId);
		for (SeedInfoVo seedInfoVo : seedVoList) {
    		if(seedInfoVo.isFlag()){
    			LandSeed landSeed=new LandSeed();
    			landSeed.setLandId(landInfo.getId());
    			landSeed.setSeedId(seedInfoVo.getId());
    			landSeedDao.insert(landSeed);
    		}        		
    	}
		return 0;
	}

	
	@Override
	public List<LandType> getAllLandType() {		
		return landTypeDao.selectLandType();
	}

	@Override
	public List<SeedInfoVo> getAllSeedInfo() {		
		return seedInfoDao.selectAll() ;
	}

	@Override
	public int editSeedInfo(SeedInfo seedInfo) {		
		return seedInfoDao.updateById(seedInfo);
	}

	@Override
	public int changeStatus(Long landId) {
		int acreageSum=acreageInfoDao.selectTotalByLandId(landId);
		int shipSum=shipStrategyDao.selectTotalByLandId(landId);
		if(acreageSum==0||shipSum==0){
			return 0;
		}
		LandInfo landInfo=landInfoDao.selectLandInfoById(landId);
		if(landInfo.getStatus()==1){
			landInfo.setStatus(LandInfo.STATUS_SOLDOUT);
		}
		else{
			landInfo.setStatus(LandInfo.STATUS_PUTAWAY);
		}
		landInfoDao.updateById(landInfo);
		return 1;
	}

	@Override
	public List<AcreageInfo> getAcreageList(Long landId) {		
		return acreageInfoDao.selectListById(landId);
	}

	@Override
	public List<ShipStrategy> getShipList(Long landId) {
		return shipStrategyDao.selectListById(landId);
	}

	@Override
	public int removeAcreageInfoById(AcreageInfo acreageInfo) {		
		return acreageInfoDao.deleteById(acreageInfo);
	}

	@Override
	public int removeShipStrategyById(ShipStrategy shipStrategy) {
		return shipStrategyDao.deleteById(shipStrategy);
	}

	@Override
	public int addAcreageInfo(List<AcreageInfo> acreageInfoLIist) {
		for(AcreageInfo acreageInfo:acreageInfoLIist){
			if(acreageInfo.getId()!=null){
				acreageInfoDao.updateById(acreageInfo);
			}
			else{
				acreageInfoDao.insert(acreageInfo);
			}
		}
		return 0;
	}

	@Override
	public int addShipStrategy(List<ShipStrategy> shipStrategyList) {
		for(ShipStrategy shipStrategy:shipStrategyList){
			if(shipStrategy.getId()!=null){
				shipStrategyDao.updateById(shipStrategy);
			}
			else{
				shipStrategyDao.insert(shipStrategy);
			}
		}
		return 0;
	}

	@Override
	public List<LandSeed> getLandSeedList(Long landId) {
		return landSeedDao.selectByLandId(landId);
	}

	@Override
	public List<LandInfoVo> getAllLandInfo() {		
		return landInfoDao.selectAllByStatus();
	}

	@Override
	public List<LandInfo> queryMiNiLandInfoList() {
		return landInfoDao.queryMiNiLandInfoList();
	}

	@Override
	public List<LandInfo> queryMiNiLandInfoDetailListByName(String name) {
		return landInfoDao.queryMiNiLandInfoDetailListByName(name);
	}

	@Override
	public List<LandInfoTypeVo> queryLangInfoWithTypeList(Long typeId) {
		return landInfoDao.queryLangInfoWithTypeList(typeId);
	}

	@Override
	public LandInfoVo queryLangInfoMsgByLandId(Long landId) {
		return landInfoDao.queryLangInfoMsgByLandId(landId);
	}

	@Override
	public LandInfoOrderVo queryLandInfoWithOrder(Long landId, Long areaId) {
		return landInfoDao.queryLandInfoWithOrder(landId, areaId);
	}


	
}
