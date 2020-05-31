package com.smartfarm.base.farm.core.entity;

public class FarmRentLandDevice {
    public final static short DEVICE_CAMERA = 1;
    public final static short DEVICE_CONTROL_NODE = 2;
    public final static short DEVICE_SENSOR_NODE = 3;

    private Long id;
    private Long rentLandId;
    private Short deviceType;
    private Long deviceId;
    private Long farmId;

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

    public Short getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Short deviceType) {
        this.deviceType = deviceType;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

    @Override
    public String toString() {
        return "FarmRentLandDevice{" +
                "id=" + id +
                ", rentLandId=" + rentLandId +
                ", deviceType=" + deviceType +
                ", deviceId=" + deviceId +
                ", farmId=" + farmId +
                '}';
    }
}