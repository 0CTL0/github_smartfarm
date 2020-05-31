package com.smartfarm.base.farm.core.entity;

public class WorkingLog {

	/**未完成*/
	public static final Short STATUS_UNABLE=1;
	/**已完成*/
	public static final Short STATUS_ABLED=2;
	/**展示*/
	public static final Short SHOW_ABLED=1;
	/**不展示*/
	public static final Short SHOW_UNABL=2;

	private Long id;
	private String planTime;
	private String executeTime;
	private String actualExecuteTime;
	private Long operator;
	private String content;
	private Short status;
	private String pic;
	private String taskType;
	private Short isShow;
	private Long planDetailId;
	private Long memberLandId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlanTime() {
		return planTime;
	}

	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}

	public String getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}

	public String getActualExecuteTime() {
		return actualExecuteTime;
	}

	public void setActualExecuteTime(String actualExecuteTime) {
		this.actualExecuteTime = actualExecuteTime;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public Short getIsShow() {
		return isShow;
	}

	public void setIsShow(Short isShow) {
		this.isShow = isShow;
	}

	public Long getPlanDetailId() {
		return planDetailId;
	}

	public void setPlanDetailId(Long planDetailId) {
		this.planDetailId = planDetailId;
	}

	public Long getMemberLandId() {
		return memberLandId;
	}

	public void setMemberLandId(Long memberLandId) {
		this.memberLandId = memberLandId;
	}

}