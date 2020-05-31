package com.smartfarm.base.monitor.core.entity;

public class MonitorWarningInfo {
    private Long id;

    private Long sersorNodeId;

    private Double maxThreshold;

    private Double minThreshold;

    private double realValue;

    private String warningTime;

    private Short status;

    private String handleAction;

    private int warningStatus;

    public int getWarningStatus() {
        return warningStatus;
    }

    public void setWarningStatus(int warningStatus) {
        this.warningStatus = warningStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSersorNodeId() {
		return sersorNodeId;
	}

	public void setSersorNodeId(Long sersorNodeId) {
		this.sersorNodeId = sersorNodeId;
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

   
    public double getRealValue() {
		return realValue;
	}

	public void setRealValue(double realValue) {
		this.realValue = realValue;
	}

	public String getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(String warningTime) {
        this.warningTime = warningTime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getHandleAction() {
        return handleAction;
    }

    public void setHandleAction(String handleAction) {
        this.handleAction = handleAction;
    }
}