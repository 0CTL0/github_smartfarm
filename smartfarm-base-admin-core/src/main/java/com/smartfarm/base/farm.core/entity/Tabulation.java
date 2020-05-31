package com.smartfarm.base.farm.core.entity;

public class Tabulation {
    private Long id;

    private Long sourceId;

    private Short status;

    private String sourceCode;

    private Integer lookAmount;

    private String createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Integer getLookAmount() {
        return lookAmount;
    }

    public void setLookAmount(Integer lookAmount) {
        this.lookAmount = lookAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}