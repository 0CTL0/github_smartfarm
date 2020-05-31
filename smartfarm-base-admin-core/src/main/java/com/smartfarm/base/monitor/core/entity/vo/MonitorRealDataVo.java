package com.smartfarm.base.monitor.core.entity.vo;

import java.util.List;

public class MonitorRealDataVo {
	private String name;//传感器信息名称
	private List<MonitorSoilRealDataVo> realDataList;
	private Integer maxThreshold;//最大值

    private Integer minThreshold;//最小值
    
    private Long sersorNodeId;
    
    private String unit;//单位
    
    private Short status;//sersor_node状态
    
    private String nodeName;//节点名
    
    private String transferDate;
    
    private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MonitorSoilRealDataVo> getRealDataList() {
		return realDataList;
	}

	public void setRealDataList(List<MonitorSoilRealDataVo> realDataList) {
		this.realDataList = realDataList;
	}

	public Integer getMaxThreshold() {
		return maxThreshold;
	}

	public void setMaxThreshold(Integer maxThreshold) {
		this.maxThreshold = maxThreshold;
	}

	public Integer getMinThreshold() {
		return minThreshold;
	}

	public void setMinThreshold(Integer minThreshold) {
		this.minThreshold = minThreshold;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Long getSersorNodeId() {
		return sersorNodeId;
	}

	public void setSersorNodeId(Long sersorNodeId) {
		this.sersorNodeId = sersorNodeId;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
