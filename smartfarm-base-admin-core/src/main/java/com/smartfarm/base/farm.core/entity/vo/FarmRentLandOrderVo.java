package com.smartfarm.base.farm.core.entity.vo;

import com.smartfarm.base.farm.core.entity.FarmRentLandOrder;

public class FarmRentLandOrderVo extends FarmRentLandOrder {
    private String farmName;
    private String baseName;
    private String tunnelName;
    private String rentLandName;
    private Integer area;   //租地信息面积
    private String mainPic; //地块主图

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getTunnelName() {
        return tunnelName;
    }

    public void setTunnelName(String tunnelName) {
        this.tunnelName = tunnelName;
    }

    public String getRentLandName() {
        return rentLandName;
    }

    public void setRentLandName(String rentLandName) {
        this.rentLandName = rentLandName;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getMainPic() {
        return mainPic;
    }

    public void setMainPic(String mainPic) {
        this.mainPic = mainPic;
    }
}
