package com.smartfarm.base.farm.core.entity;

public class FarmDelegateType {
    private Long id;

    private Short workType;

    private String workName;

    private Long integral;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getWorkType() {
        return workType;
    }

    public void setWorkType(Short workType) {
        this.workType = workType;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }
}