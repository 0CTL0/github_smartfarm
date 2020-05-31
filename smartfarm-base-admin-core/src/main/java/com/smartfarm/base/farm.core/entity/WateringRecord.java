package com.smartfarm.base.farm.core.entity;

public class WateringRecord {
    private Long id;

    private Long memberLandId;

    private String wateringDate;

    private String wateringTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberLandId() {
        return memberLandId;
    }

    public void setMemberLandId(Long memberLandId) {
        this.memberLandId = memberLandId;
    }

    public String getWateringDate() {
        return wateringDate;
    }

    public void setWateringDate(String wateringDate) {
        this.wateringDate = wateringDate;
    }

    public String getWateringTime() {
        return wateringTime;
    }

    public void setWateringTime(String wateringTime) {
        this.wateringTime = wateringTime;
    }
}