package com.smartfarm.base.farm.core.entity;

public class FarmBaseInfo {
	
	private Long id;// 流水号
	private Long farmId;// 农场id
	private String name;// 基地名称
	private String pic;// 图片
	private String address;// 地址
	private Integer area;// 面积
    private Short status;//状态

    /**启用*/
    public static final Short STATUS_ABLE=1;
    /**禁用*/
    public static final Short STATUS_ENALBE=2;

    public Short getStatus() {
        return status;
    }

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FarmBaseInfo{" +
                "id=" + id +
                ", farmId=" + farmId +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", address='" + address + '\'' +
                ", area=" + area +
                ", status=" + status +
                '}';
    }
}