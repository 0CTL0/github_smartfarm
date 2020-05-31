package com.smartfarm.base.monitor.core.entity;

public class MonitorControlSetNode {
    private Long id;

    private Long controlSetId;

    private Long controlNodeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getControlSetId() {
        return controlSetId;
    }

    public void setControlSetId(Long controlSetId) {
        this.controlSetId = controlSetId;
    }

    public Long getControlNodeId() {
        return controlNodeId;
    }

    public void setControlNodeId(Long controlNodeId) {
        this.controlNodeId = controlNodeId;
    }
}