package com.smartfarm.base.farm.core.entity.vo;

import com.smartfarm.base.farm.core.entity.FarmRentLandInfo;

public class FarmRentLandInfoVo extends FarmRentLandInfo {
    private String tunnelName;
    private Boolean flag=false;  //表示地块信息是否被选中

    public String getTunnelName() {
        return tunnelName;
    }

    public void setTunnelName(String tunnelName) {
        this.tunnelName = tunnelName;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
