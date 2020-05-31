package com.smartfarm.base.farm.core.entity;

public class FarmMemberCrops {
    /**在种植*/
    public static final Short STATUS_PLANTING=1;
    /**已成熟*/
    public static final Short STATUS_MATURE=2;
    /**已收获*/
    public static final Short STATUS_REAPED=3;
    /**已整地*/
    public static final Short STATUS_RESOIL=4;

    private Long id;
    private Long memberLandId;
    private Long cropsId;
    private Short status;
    private Integer area;
    private Short weight;
    private Short inventory;
    private String pickTime;
    private String freshTime;

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

    public Long getCropsId() {
        return cropsId;
    }

    public void setCropsId(Long cropsId) {
        this.cropsId = cropsId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

	public Short getWeight() {
		return weight;
	}

	public void setWeight(Short weight) {
		this.weight = weight;
	}

	public Short getInventory() {
		return inventory;
	}

	public void setInventor(Short inventory) {
		this.inventory = inventory;
	}

    public void setInventory(Short inventory) {
        this.inventory = inventory;
    }

    public String getPickTime() {
        return pickTime;
    }

    public void setPickTime(String pickTime) {
        this.pickTime = pickTime;
    }

    public String getFreshTime() {
        return freshTime;
    }

    public void setFreshTime(String freshTime) {
        this.freshTime = freshTime;
    }

    @Override
    public String toString() {
        return "FarmMemberCrops{" +
                "id=" + id +
                ", memberLandId=" + memberLandId +
                ", cropsId=" + cropsId +
                ", status=" + status +
                ", area=" + area +
                ", weight=" + weight +
                ", inventory=" + inventory +
                ", pickTime='" + pickTime + '\'' +
                ", freshTime='" + freshTime + '\'' +
                '}';
    }
}