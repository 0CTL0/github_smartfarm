package com.smartfarm.base.farm.core.entity;

public class SourceCode {
    public final static short CODE_STATUS_UN_SELL = 1;
    public final static short CODE_STATUS_SOLD = 2;

    private Long id;
    private Long sourceId;
    private Short status;
    private String code;
    private Integer searchTimes;
    private String firstSearchTime;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSearchTimes() {
        return searchTimes;
    }

    public void setSearchTimes(Integer searchTimes) {
        this.searchTimes = searchTimes;
    }

    public String getFirstSearchTime() {
        return firstSearchTime;
    }

    public void setFirstSearchTime(String firstSearchTime) {
        this.firstSearchTime = firstSearchTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}