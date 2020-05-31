package com.smartfarm.base.monitor.core.entity.vo;

import java.io.Serializable;

/**
 * 微信小程序-控制器VO,同时包括了其定时/策略数量
 *
 * @author HongSF
 * @version v1.0.0
 * @date 2020/4/2
 **/
public class WeChatControlNodeVo implements Serializable {
    private static final long serialVersionUID = 868973908061849780L;
    /**
     * 控制器ID
     */
    private Long id;
    /**
     * 控制器名称
     */
    private String name;
    /**
     * 产品编码（ID-位数）
     */
    private String productCode;
    /**
     * 操作状态（1、开 2、关 3、暂停）
     */
    private Short operateStatus;
    /**
     * 控制设备类型（1、电磁阀）
     */
    private Short controlType;
    /**
     * 定时个数:在monitor_control_set表中字段type=1为定时类型
     */
    private int timingCount;
    /**
     * 策略个数:在monitor_control_set表中字段type=2为策略类型
     */
    private int strategyCount;

    public Short getControlType() {
        return controlType;
    }

    public void setControlType(Short controlType) {
        this.controlType = controlType;
    }

    public int getTimingCount() {
        return timingCount;
    }

    public void setTimingCount(int timingCount) {
        this.timingCount = timingCount;
    }

    public int getStrategyCount() {
        return strategyCount;
    }

    public void setStrategyCount(int strategyCount) {
        this.strategyCount = strategyCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Short getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(Short operateStatus) {
        this.operateStatus = operateStatus;
    }

    public Short getType() {
        return controlType;
    }

    public void setType(Short type) {
        this.controlType = type;
    }

}
