package com.smartfarm.base.farm.core.entity;

public class MemberVideo {

    public static final short STATUS_DELETED = 0;
    public static final short STATUS_TO_BE_PUBLISHED = 1;
    public static final short STATUS_TO_BE_AUDIT = 2;
    public static final short STATUS_AUDIT_PASSED = 3;
    public static final short STATUS_AUDIT_UNPASS = 4;

    private Long id;
    private Long memberId;
    private String name;
    private String cover;
    private String videoUrl;
    private String createTime;
    private Short status;
    private Integer playTimes;
    private Long farmId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(Integer playTimes) {
        this.playTimes = playTimes;
    }

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }
}