package com.smartfarm.base.farm.core.manager;

import java.util.List;

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

public interface LandInfoManager {
	/**
	 * 查询土地列表
	 * @param name
	 * @param status
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<LandInfo> getLandInfoPageList(String name, Short status, Integer start, Integer limit);
	/**
	 * 统计土地列表
	 * @param name
	 * @param status
	 * @return
	 */
	public int countLandInfoPageList(String name, Short status);
	/**
	 * 编辑土地信息
	 * @param seedInfo
	 * @return
	 */
	public int editLandSeedInfo(LandInfo landInfo, List<SeedInfoVo> seedVoList);
	/**
	 * 根据ID查询土地消息
	 * @param id
	 * @return
	 */
	public LandInfo getLandInfo(Long id);
	/**
	 * 新增种子
	 * @param seedInfo
	 * @return
	 */
	public int addLandInfo(LandInfo landInfo, List<SeedInfoVo> seedVoList);
	/**
	 * 获得土地分类
	 * @return
	 */
	public List<LandType> getAllLandType();
	/**
	 * 获得全部种子信息
	 * @return
	 */
	public List<SeedInfoVo> getAllSeedInfo();
	/**
	 * 编辑种子信息
	 * @param seedInfo
	 * @return
	 */
	public int editSeedInfo(SeedInfo seedInfo);
	/**
	 *改变土地的状态 
	 * @param landId
	 * @return
	 */
	public int changeStatus(Long landId);
	/**
	 * 获得土地的土块列表
	 * @param landId
	 * @return
	 */
	public List<AcreageInfo> getAcreageList(Long landId);
	/**
	 * 获得土地的配送周期列表
	 * @param landId
	 * @return
	 */
	public List<ShipStrategy> getShipList(Long landId);
	/**
	 * 获得土地可种植种子信息
	 * @param landId
	 * @return
	 */
	public List<LandSeed> getLandSeedList(Long landId);
	/**
	 * 根据土块id删除acreageInfo
	 * @param acreageInfo
	 * @return
	 */
	public int removeAcreageInfoById(AcreageInfo acreageInfo);
	/**
	 * 根据土块删除ShipStrategy
	 * @param shipStrategy
	 * @return
	 */
	public int removeShipStrategyById(ShipStrategy shipStrategy);
	/**
	 * 土块列表中有id值的更新，没id值的插入
	 * @return
	 */
	public int addAcreageInfo(List<AcreageInfo> acreageInfoLIist);
	/**
	 * 周期列表中有id值的更新，没id值的插入
	 * @return
	 */
	public int addShipStrategy(List<ShipStrategy> shipStrategyList);
	/**
	 * 获得全部已上架的土地信息
	 * @return
	 */
	public List<LandInfoVo> getAllLandInfo();
	
	/**
     * 小程序查询土地列表
     * @return
     */
    public List<LandInfo> queryMiNiLandInfoList();
    
    /**
     * 根据土地名称查找土地列表
     * @param name
     * @return
     */
    public List<LandInfo> queryMiNiLandInfoDetailListByName(String name);
    
    /**
     * 根据类型查询土地信息列表
     * @param typeId
     * @return
     */
    public List<LandInfoTypeVo> queryLangInfoWithTypeList(Long typeId);
    
    /**
     * 根据土地id查询土地的所有信息
     * @param landId
     * @return
     */
    public LandInfoVo queryLangInfoMsgByLandId(Long landId);
    
    /**
     * 根据土地id和地块信息查询
     * @param landId
     * @param areaId
     * @return
     */
    public LandInfoOrderVo queryLandInfoWithOrder(Long landId, Long areaId);
	
}
