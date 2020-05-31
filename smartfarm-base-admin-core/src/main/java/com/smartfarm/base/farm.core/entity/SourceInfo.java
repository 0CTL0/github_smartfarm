package com.smartfarm.base.farm.core.entity;

public class SourceInfo {
	public final static short SOURCE_CODE_UNGENERATED = 0;
	public final static short SOURCE_CODE_GENERATED = 1;

	private Long id;
	private String name;
	private String prefix;
	private Integer maxSearchTimes;
	private Integer amount;
	private String remarks;
	private Long batchId;
	private Short codeStatus;// 溯源码生成状态，0：未生成；1：已生成
	private String folderName;//二维码文件夹名称
	
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

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Integer getMaxSearchTimes() {
		return maxSearchTimes;
	}

	public void setMaxSearchTimes(Integer maxSearchTimes) {
		this.maxSearchTimes = maxSearchTimes;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public Short getCodeStatus() {
		return codeStatus;
	}

	public void setCodeStatus(Short codeStatus) {
		this.codeStatus = codeStatus;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

}