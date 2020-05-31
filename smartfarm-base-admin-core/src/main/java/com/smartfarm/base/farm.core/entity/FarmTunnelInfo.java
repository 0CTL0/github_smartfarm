package com.smartfarm.base.farm.core.entity;

public class FarmTunnelInfo {

    /**启用*/
    public static final Short STATUS_ABLE=1;
    /**禁用*/
    public static final Short STATUS_ENALBE=2;

    private Long id;

    private String name;

    private Integer area;

    private Long baseId;

    private Short status;

    private String brief;

    private String mainPic;

    private String detailPic;

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

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Long getBaseId() {
        return baseId;
    }

    public void setBaseId(Long baseId) {
        this.baseId = baseId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getMainPic() {
        return mainPic;
    }

    public void setMainPic(String mainPic) {
        this.mainPic = mainPic;
    }

    public String getDetailPic() {
        return detailPic;
    }

    public void setDetailPic(String detailPic) {
        this.detailPic = detailPic;
    }

    @Override
    public String toString() {
        return "FarmTunnelInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", area=" + area +
                ", baseId=" + baseId +
                ", status=" + status +
                ", brief='" + brief + '\'' +
                ", mainPic='" + mainPic + '\'' +
                ", detailPic='" + detailPic + '\'' +
                '}';
    }
}