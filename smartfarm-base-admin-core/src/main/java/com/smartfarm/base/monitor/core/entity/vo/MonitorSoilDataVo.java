package com.smartfarm.base.monitor.core.entity.vo;

import java.util.List;

public class MonitorSoilDataVo {
	private List<MonitorSoilRealDataVo> soilDataList;
	private Integer maxThreshold;//最大值

    private Integer minThreshold;//最小值
    private Short status;//sersor_node状态
    
    private String nodeName;//节点名
    private Long sersorType;

	public List<MonitorSoilRealDataVo> getSoilDataList() {
		return soilDataList;
	}

	public void setSoilDataList(List<MonitorSoilRealDataVo> soilDataList) {
		this.soilDataList = soilDataList;
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

	public Long getSersorType() {
		return sersorType;
	}

	public void setSersorType(Long sersorType) {
		this.sersorType = sersorType;
	}

	
}
