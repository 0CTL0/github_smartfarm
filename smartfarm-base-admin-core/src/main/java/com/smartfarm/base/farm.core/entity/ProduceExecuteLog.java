package com.smartfarm.base.farm.core.entity;

public class ProduceExecuteLog {
    
	private Long id;
    private String planTime;//任务发布时间
    private String executeTime;
    private Long operator;
    private String content;
    private Short status;//任务状态，0：未完成；1：已完成
    private String pic;
    private Short taskType;//溯源区分，1：种植；2：环境；3：加工；4：质检
    private Short isShow;//是否展示，0：否；1：是
    private Long planDetailId;

    private  String[] picArray;//图片数组

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

    public Short getTaskType() {
        return taskType;
    }

    public void setTaskType(Short taskType) {
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

    public String[] getPicArray() {
        return picArray;
    }

    public void setPicArray(String[] picArray) {
        this.picArray = picArray;
    }
}