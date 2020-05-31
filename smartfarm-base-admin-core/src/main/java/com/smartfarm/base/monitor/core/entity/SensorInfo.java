package com.smartfarm.base.monitor.core.entity;

public class SensorInfo {
    private Long id;

    private String code;

    private String name;

    private String unit;

    private Float resolutionRatio;

    private Integer upperLimit;

    private Integer lowerLimit;

    private String precision;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getResolutionRatio() {
        return resolutionRatio;
    }

    public void setResolutionRatio(Float resolutionRatio) {
        this.resolutionRatio = resolutionRatio;
    }

    public Integer getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }

    public Integer getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Integer lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }
}