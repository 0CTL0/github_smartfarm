package com.smartfarm.base.monitor.core.entity.vo;

import java.io.Serializable;

/**
 * 微信小程序-Gather/Temperature页面，折线图所需数据(根据传感器ID获取某天的实时数据)
 *
 * @author HongSF
 * @version v1.0.0
 * @date 2020/4/3
 **/
public class WeChatTemperatureVo implements Serializable {
    private static final long serialVersionUID = 3101849349425843758L;
    /**
     * 传感器名称
     */
    private String sensorNodeName;
    /**
     * 最大阀值
     */
    private Double maxThreshold;
    /**
     * 最小阀值
     */
    private Double minThreshold;
    /**
     * 某小时
     */
    private String hour;
    /**
     * 某小时内实时平均数据(平均是按每小时分组取平均)
     */
    private Double avgRealValue;
    /**
     * 传感器单位
     */
    private String unit;

    public String getSensorNodeName() {
        return sensorNodeName;
    }

    public Double getAvgRealValue() {
        return avgRealValue;
    }

    public void setAvgRealValue(Double avgRealValue) {
        this.avgRealValue = avgRealValue;
    }

    public void setSensorNodeName(String sensorNodeName) {
        this.sensorNodeName = sensorNodeName;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

}
