package com.smartfarm.base.admin.core.manager;

import java.util.List;

import com.smartfarm.base.admin.core.entity.FarmInfo;
import com.smartfarm.base.farm.core.entity.FarmBaseInfo;
import com.smartfarm.base.farm.core.entity.FarmCropsInfo;
import com.smartfarm.base.farm.core.entity.FarmRentLandInfo;
import com.smartfarm.base.farm.core.entity.FarmTunnelInfo;
import com.smartfarm.base.farm.core.entity.vo.FarmBaseInfoVo;
import com.smartfarm.base.farm.core.entity.vo.FarmRentLandInfoVo;
import com.smartfarm.base.farm.core.entity.vo.FarmTunnelInfoVo;


public interface FarmManager {
    public List<FarmInfo> getFarmInfoList(String name, Short status, Integer start, Integer limit);
    public int countFarmTotal(String name, Short status);
    public int addFarmInfo(FarmInfo farmInfo);
    public FarmInfo getFarmInfo(Long farmId);
    public int editFarmInfo(FarmInfo farmInfo);
    public int changeFarmStatus(Long id);
    public List<FarmInfo> getAllFarm();

    public List<FarmBaseInfoVo> getFarmBaseList(Long farmId, String name, Short status, Integer start, Integer limit);
    public int countBaseTotal(Long farmId, String name, Short status);
    public int addFarmBaseInfo(FarmBaseInfo farmBaseInfo);
    public FarmBaseInfo getFarmBaseInfo(Long id);
    public int editFarmBaseInfo(FarmBaseInfo farmBaseInfo);
    public int changeFarmBaseStatus(Long id);
    public List<FarmBaseInfo> getALLFarmBase(Long farmId);

    public List<FarmTunnelInfoVo> getFarmTunnelList(Long baseId, Long farmId, String name, Short status, Integer start, Integer limit);
    public int countTunnelTotal(Long baseId, Long farmId, String name, Short status);
    public int addFarmTunnelInfo(FarmTunnelInfo farmTunnelInfo);
    public FarmTunnelInfo getFarmTunnelInfo(Long id);
    public int editFarmTunnelInfo(FarmTunnelInfo farmTunnelInfo);
    public int changeFarmTunnelStatus(Long id);
    

    public List<FarmRentLandInfoVo> getFarmRentLandList(Long tunnelId, String name, Short status, Integer start, Integer limit);
    public int countRentLandTotal(Long rentLandId, String name, Short status);
    public int addFarmRentLandInfo(FarmRentLandInfo farmRentLandInfo);
    public FarmRentLandInfo getFarmRentLandInfo(Long id);
    public int editFarmRentLandInfo(FarmRentLandInfo FarmRentLandInfo);
    public int changeFarmRentLandStatus(Long id);

    public List<FarmCropsInfo> getCropsInfoPageList(Long farmId, String name, Short status, Integer start, Integer limit);
    public int countCropsInfoPageList(Long farmId, String name, Short status);
    public int editCropsInfo(FarmCropsInfo farmCropsInfo);
    public FarmCropsInfo getCropsInfo(Long id);
    public int addCropsInfo(FarmCropsInfo farmCropsInfo);


    /**
     * 获取全部的基地、基地的地块、地块最低价格
     * @return
     */
    public List<FarmBaseInfoVo> getFarmBaseVoList(Long farmId);

    /**
     * 根据id获取基地信息
     * @param id
     * @return
     */
    public FarmBaseInfoVo getFarmBaseVo(Long id);

    /**
     * 获得全部的农作物
     * @return
     */
    public List<FarmCropsInfo> getFarmCropsList(Long farmId);

    /**
     * 根据id获取农作物信息
     * @param id
     * @return
     */
    public FarmCropsInfo getFarmCropsInfo(Long id);

    /**
     * 根据id获取地块信息
     * @param id
     * @return
     */
    public FarmTunnelInfoVo getFarmTunnelVo(Long id);

    /**
     * 根据id获取租地信息
     * @param id
     * @return
     */
    public FarmRentLandInfo getRentLandInfo(Long id);
    
    List<FarmTunnelInfoVo> queryTunnelForAdvance(Long farmId);

    /**
     * 查询可用的地块
     * @param farmId
     * @return
     */
    List<FarmTunnelInfoVo> queryTunnelForAdvanceAdd(Long farmId);
}
