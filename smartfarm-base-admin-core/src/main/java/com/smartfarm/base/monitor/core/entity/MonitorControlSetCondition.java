package com.smartfarm.base.monitor.core.entity;

public class MonitorControlSetCondition {
    private Long id;

    private Long controlSetId;

    private Long controlNodeId;

    private Short type;

    private Double dataValue;
    
    private Short kinds;

    public Short getKinds() {
		return kinds;
	}

	public void setKinds(Short kinds) {
		this.kinds = kinds;
	}

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

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Double getDataValue() {
        return dataValue;
    }

    public void setDataValue(Double dataValue) {
        this.dataValue = dataValue;
    }
}