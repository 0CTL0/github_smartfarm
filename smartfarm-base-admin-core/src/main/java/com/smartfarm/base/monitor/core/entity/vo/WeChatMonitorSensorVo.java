package com.smartfarm.base.monitor.core.entity.vo;

import java.io.Serializable;

/**
 * 微信小程序-传感器VO,同时包括了其实时数据
 *
 * @author HongSF
 * @version v1.0.0
 * @date 2020/4/1 15:35
 * @see com.smartfarm.base.monitor.core.entity.vo
 **/
public class WeChatMonitorSensorVo implements Serializable {
    private static final long serialVersionUID = 6792401489553876679L;

    private Long id;

    private String nodeName;

    private Long sersorType;

    private Double maxThreshold;

    private Double minThreshold;

    private String transferDate;

    private String transferTime;

    private String unit;

    private double realValue;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Long getSersorType() {
        return sersorType;
    }

    public void setSersorType(Long sersorType) {
        this.sersorType = sersorType;
    }

    public Double getMaxThreshold() {
        return maxThreshold;
    }

    public void setMaxThreshold(Double maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public Double getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(Double minThreshold) {
        this.minThreshold = minThreshold;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(String transferTime) {
        this.transferTime = transferTime;
    }

    public double getRealValue() {
        return realValue;
    }

    public void setRealValue(double realValue) {
        this.realValue = realValue;
    }
}
