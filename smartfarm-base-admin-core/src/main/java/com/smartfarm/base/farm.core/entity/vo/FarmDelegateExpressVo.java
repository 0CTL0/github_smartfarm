package com.smartfarm.base.farm.core.entity.vo;

import com.smartfarm.base.farm.core.entity.FarmDelegateExpress;

public class FarmDelegateExpressVo extends FarmDelegateExpress {

    private String cropsName;
    private Integer area;

    public String getCropsName() {
        return cropsName;
    }

    public void setCropsName(String cropsName) {
        this.cropsName = cropsName;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }
}
