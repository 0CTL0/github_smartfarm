package com.smartfarm.base.farm.core.entity.vo;

import java.util.List;

import com.smartfarm.base.farm.core.entity.FarmTunnelInfo;

public class FarmTunnelInfoVo extends FarmTunnelInfo {
    private String baseName;
    private String farmName;
    private String showName;

    private Double lowestPrice;  //地块的租地信息中最低价格
    private List<FarmRentLandInfoVo> rentLandInfoVoList;

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }


    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }


    public Double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public List<FarmRentLandInfoVo> getRentLandInfoVoList() {
        return rentLandInfoVoList;
    }

    public void setRentLandInfoVoList(List<FarmRentLandInfoVo> rentLandInfoVoList) {
        this.rentLandInfoVoList = rentLandInfoVoList;
    }
}
