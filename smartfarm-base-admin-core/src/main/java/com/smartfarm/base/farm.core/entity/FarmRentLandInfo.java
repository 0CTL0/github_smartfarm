package com.smartfarm.base.farm.core.entity;

public class FarmRentLandInfo {
    private Long id;

    private String name;

    private Long tunnelId;

    private Integer area;

    private Short status;

    private Double price;


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

    public Long getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(Long tunnelId) {
        this.tunnelId = tunnelId;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    /**启用*/
    public static final Short STATUS_ABLE=1;
    /**禁用*/
    public static final Short STATUS_ENALBE=2;

    @Override
    public String toString() {
        return "FarmRentLandInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tunnelId=" + tunnelId +
                ", area=" + area +
                ", status=" + status +
                ", price=" + price +
                '}';
    }
}