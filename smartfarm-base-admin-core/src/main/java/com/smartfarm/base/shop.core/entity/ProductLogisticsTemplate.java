package com.smartfarm.base.shop.core.entity;

public class ProductLogisticsTemplate {
    /**按重计算*/
    public static final Short TYPE_WERIGHT=1;
    /**按件计算*/
    public static final Short TYPE_NUMBER=2;

    private Long id;

    private String name;

    private Short type;

    private Long farmId;

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

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }
}