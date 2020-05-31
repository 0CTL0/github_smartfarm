package com.smartfarm.base.farm.core.entity.vo;

import com.smartfarm.base.farm.core.entity.EmployeeInfo;

public class EmployeeInfoVo extends EmployeeInfo {
    private String farmName;

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }
}
