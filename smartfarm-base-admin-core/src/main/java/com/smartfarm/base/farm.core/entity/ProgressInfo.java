package com.smartfarm.base.farm.core.entity;

public class ProgressInfo {
		
    /** projectType = 租地种植*/
	public final static Short TYPE_PLANT = 1; 
    /** projectType = 农场众筹 */
	public final static Short TYPE_CROWDFUNDING = 2; 
    /** projectType = 畜牧认养 */
	public final static Short TYPE_BREED = 3;
	
    private Long id;

    private Long projectId;

    private Short projectType;

    private String reportTime;

    private Long adminUserId;

    private String content;

    private String pics;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Short getProjectType() {
        return projectType;
    }

    public void setProjectType(Short projectType) {
        this.projectType = projectType;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public Long getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }
}