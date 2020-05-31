package com.smartfarm.base.admin.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.admin.core.dao.FarmInfoDao;
import com.smartfarm.base.admin.core.entity.FarmInfo;
import com.smartfarm.base.admin.core.manager.FarmManager;
import com.smartfarm.base.farm.core.dao.FarmBaseInfoDao;
import com.smartfarm.base.farm.core.dao.FarmCropsInfoDao;
import com.smartfarm.base.farm.core.dao.FarmRentLandInfoDao;
import com.smartfarm.base.farm.core.dao.FarmTunnelInfoDao;
import com.smartfarm.base.farm.core.entity.FarmBaseInfo;
import com.smartfarm.base.farm.core.entity.FarmCropsInfo;
import com.smartfarm.base.farm.core.entity.FarmRentLandInfo;
import com.smartfarm.base.farm.core.entity.FarmTunnelInfo;
import com.smartfarm.base.farm.core.entity.vo.FarmBaseInfoVo;
import com.smartfarm.base.farm.core.entity.vo.FarmRentLandInfoVo;
import com.smartfarm.base.farm.core.entity.vo.FarmTunnelInfoVo;

@Service("/farmManager")
public class FarmManagerImpl implements FarmManager {
    @Resource
    private FarmInfoDao farmInfoDao;
    @Resource
    private FarmBaseInfoDao farmBaseInfoDao;
    @Resource
    private FarmTunnelInfoDao farmTunnelInfoDao;
    @Resource
    private FarmRentLandInfoDao farmRentLandInfoDao;
    @Resource
    private FarmCropsInfoDao farmCropsInfoDao;


    public List<FarmInfo> getFarmInfoList(String name, Short status, Integer start, Integer limit) {
        return farmInfoDao.selectPageList(name,status,start,limit);
    }
    public int countFarmTotal(String name, Short status) {
        return farmInfoDao.selectPageTotal(name,status);
    }
    public int addFarmInfo(FarmInfo farmInfo) {
        return farmInfoDao.insert(farmInfo);
    }
    public FarmInfo getFarmInfo(Long farmId) {
        return farmInfoDao.selectById(farmId);
    }
    public int editFarmInfo(FarmInfo farmInfo) {
        return farmInfoDao.updateById(farmInfo);
    }
    public int changeFarmStatus(Long id) {
        FarmInfo farmInfo=farmInfoDao.selectById(id);
        if(farmInfo.getStatus()==FarmInfo.STATUS_ABLE){
            farmInfo.setStatus(FarmInfo.STATUS_ENALBE);
        }
        else {
            farmInfo.setStatus(FarmInfo.STATUS_ABLE);
        }
        return farmInfoDao.updateById(farmInfo);
    }
    public List<FarmInfo> getAllFarm() {
        return farmInfoDao.selectAll();
    }



    public List<FarmBaseInfoVo> getFarmBaseList(Long farmId, String name, Short status, Integer start, Integer limit) {
        return farmBaseInfoDao.selectPageList(name,status,farmId,start,limit);
    }
    public int countBaseTotal(Long farmId, String name, Short status) {
        return farmBaseInfoDao.selectPageTotal(farmId,name,status);
    }
    public int addFarmBaseInfo(FarmBaseInfo farmBaseInfo) {
        return farmBaseInfoDao.insert(farmBaseInfo);
    }
    public FarmBaseInfo getFarmBaseInfo(Long id) {
        return farmBaseInfoDao.selectById(id);
    }
    public int editFarmBaseInfo(FarmBaseInfo farmBaseInfo) {
        return farmBaseInfoDao.updateById(farmBaseInfo);
    }
    public int changeFarmBaseStatus(Long id) {
        FarmBaseInfo farmBaseInfo=farmBaseInfoDao.selectById(id);
        if(farmBaseInfo.getStatus()==FarmBaseInfo.STATUS_ABLE){
            farmBaseInfo.setStatus(FarmBaseInfo.STATUS_ENALBE);
        }
        else {
            farmBaseInfo.setStatus(FarmBaseInfo.STATUS_ABLE);
        }
        return farmBaseInfoDao.updateById(farmBaseInfo);
    }
    public List<FarmBaseInfo> getALLFarmBase(Long farmId) {
        return farmBaseInfoDao.selectAll(farmId);
    }


    public List<FarmTunnelInfoVo> getFarmTunnelList(Long baseId,Long farmId, String name, Short status, Integer start, Integer limit) {
        return farmTunnelInfoDao.selectPageList(baseId,name,status,farmId,start,limit);
    }
    public int countTunnelTotal(Long baseId,Long farmId, String name, Short status) {
        return farmTunnelInfoDao.selectPageTotal(baseId,name,status,farmId);
    }
    public int addFarmTunnelInfo(FarmTunnelInfo farmTunnelInfo) {
        return farmTunnelInfoDao.insert(farmTunnelInfo);
    }
    public FarmTunnelInfo getFarmTunnelInfo(Long id) {
        return farmTunnelInfoDao.selectById(id);
    }
    public int editFarmTunnelInfo(FarmTunnelInfo farmTunnelInfo) {
        return farmTunnelInfoDao.updateById(farmTunnelInfo);
    }
    public int changeFarmTunnelStatus(Long id) {
        FarmTunnelInfo farmTunnelInfo=farmTunnelInfoDao.selectById(id);
        if(farmTunnelInfo.getStatus()==FarmTunnelInfo.STATUS_ABLE){
            farmTunnelInfo.setStatus(FarmTunnelInfo.STATUS_ENALBE);
        }
        else {
            farmTunnelInfo.setStatus(FarmInfo.STATUS_ABLE);
        }
        return farmTunnelInfoDao.updateById(farmTunnelInfo);
    }

    public List<FarmRentLandInfoVo> getFarmRentLandList(Long tunnelId, String name, Short status, Integer start, Integer limit) {
        return farmRentLandInfoDao.selectPageList(name,status,tunnelId,start,limit);
    }
    public int countRentLandTotal(Long rentLandId, String name, Short status) {
        return farmRentLandInfoDao.selectPageTotal(name,status,rentLandId);
    }
    public int addFarmRentLandInfo(FarmRentLandInfo farmRentLandInfo) {
        return farmRentLandInfoDao.insertSelective(farmRentLandInfo);
    }
    public FarmRentLandInfo getFarmRentLandInfo(Long id) {
        return farmRentLandInfoDao.selectByPrimaryKey(id);
    }
    public int editFarmRentLandInfo(FarmRentLandInfo FarmRentLandInfo) {
        return farmRentLandInfoDao.updateByPrimaryKeySelective(FarmRentLandInfo);
    }
    public int changeFarmRentLandStatus(Long id) {
        FarmRentLandInfo farmRentLandInfo=farmRentLandInfoDao.selectByPrimaryKey(id);
        if(farmRentLandInfo.getStatus()==FarmRentLandInfo.STATUS_ABLE){
            farmRentLandInfo.setStatus(FarmRentLandInfo.STATUS_ENALBE);
        }
        else {
            farmRentLandInfo.setStatus(FarmRentLandInfo.STATUS_ABLE);
        }
        return farmRentLandInfoDao.updateByPrimaryKeySelective(farmRentLandInfo);
    }


    public List<FarmBaseInfoVo> getFarmBaseVoList(Long farmId) {
        return farmBaseInfoDao.selectBaseVoList(farmId);
    }

    public FarmBaseInfoVo getFarmBaseVo(Long id) {
        return farmBaseInfoDao.selectBaseVo(id);
    }

    public List<FarmCropsInfo> getFarmCropsList(Long farmId) {
        return farmCropsInfoDao.selectAllRecordByFarmId(farmId);
    }

    public FarmCropsInfo getFarmCropsInfo(Long id) {
        return farmCropsInfoDao.getCropById(id);
    }

    public FarmTunnelInfoVo getFarmTunnelVo(Long id) {
        FarmTunnelInfoVo farmTunnelInfoVo=farmTunnelInfoDao.selectFarmTunnelVo(id);
        farmTunnelInfoVo.setRentLandInfoVoList(farmRentLandInfoDao.selectRentLandVoList(id));
        return farmTunnelInfoVo;
    }

    public FarmRentLandInfo getRentLandInfo(Long id) {
        return farmRentLandInfoDao.selectByPrimaryKey(id);
    }
    
    public List<FarmCropsInfo> getCropsInfoPageList(Long farmId,String name, Short status, Integer start, Integer limit) {
        return farmCropsInfoDao.selectPageList(farmId,name,status,start,limit);
    }

    public int countCropsInfoPageList(Long farmId,String name, Short status) {
        return farmCropsInfoDao.selectPageTotal(farmId,name,status);
    }

    public int editCropsInfo(FarmCropsInfo farmCropsInfo) {
        return farmCropsInfoDao.updateByPrimaryKeySelective(farmCropsInfo);
    }

    public FarmCropsInfo getCropsInfo(Long id) {
        return farmCropsInfoDao.selectByPrimaryKey(id);
    }

    public int addCropsInfo(FarmCropsInfo farmCropsInfo) {
        return farmCropsInfoDao.insertSelective(farmCropsInfo);
    }
	public List<FarmTunnelInfoVo> queryTunnelForAdvance(Long farmId) {
		return farmTunnelInfoDao.queryTunnelForAdvance(farmId);
	}
	@Override
	public List<FarmTunnelInfoVo> queryTunnelForAdvanceAdd(Long farmId) {
		return farmTunnelInfoDao.queryTunnelForAdvanceAdd(farmId);
	}
}
