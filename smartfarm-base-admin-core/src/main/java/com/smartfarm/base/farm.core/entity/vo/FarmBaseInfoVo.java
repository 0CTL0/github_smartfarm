package com.smartfarm.base.farm.core.entity.vo;

import java.util.List;

import com.smartfarm.base.farm.core.entity.FarmBaseInfo;

public class FarmBaseInfoVo extends FarmBaseInfo {
    private String farmName;

    private List<FarmTunnelInfoVo> farmTunnelVoList;

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public List<FarmTunnelInfoVo> getFarmTunnelVoList() {
        return farmTunnelVoList;
    }

    public void setFarmTunnelVoList(List<FarmTunnelInfoVo> farmTunnelVoList) {
        this.farmTunnelVoList = farmTunnelVoList;
    }

    @Override
    public String toString() {
        return "FarmBaseInfoVo{" +
                "farmName='" + farmName + '\'' +
                ", farmTunnelVoList=" + farmTunnelVoList +
                '}';
    }
}
