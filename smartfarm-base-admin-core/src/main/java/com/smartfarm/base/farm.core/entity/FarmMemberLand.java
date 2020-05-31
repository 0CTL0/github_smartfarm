package com.smartfarm.base.farm.core.entity;

public class FarmMemberLand {
    /**未过期*/
    public  static final Short STATUS_UNINVALID=1;
    /**过期*/
    public  static final Short STATUS_INVALID=0;

    private Long id;

    private Long rentLandId;

    private String expiration;

    private String alias;

    private Long rentOrderId;

    private Long memberId;

    private Short status;

    private Integer ares;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRentLandId() {
        return rentLandId;
    }

    public void setRentLandId(Long rentLandId) {
        this.rentLandId = rentLandId;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getRentOrderId() {
        return rentOrderId;
    }

    public void setRentOrderId(Long rentOrderId) {
        this.rentOrderId = rentOrderId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getAres() {
        return ares;
    }

    public void setAres(Integer ares) {
        this.ares = ares;
    }
}