package com.smartfarm.base.farm.core.entity;

public class WorkAttribute {
    private Long id;

    private Long workTypeId;

    private Integer serail;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(Long workTypeId) {
        this.workTypeId = workTypeId;
    }

    public Integer getSerail() {
        return serail;
    }

    public void setSerail(Integer serail) {
        this.serail = serail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}