package com.smartfarm.base.shop.core.entity;

public class MemberVisitRecord {
    private Long id;

    private Long shareRecordId;

    private Long memberId;

    private String createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShareRecordId() {
        return shareRecordId;
    }

    public void setShareRecordId(Long shareRecordId) {
        this.shareRecordId = shareRecordId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}