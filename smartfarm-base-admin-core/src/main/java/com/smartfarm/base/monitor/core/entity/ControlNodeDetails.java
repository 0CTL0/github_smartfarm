package com.smartfarm.base.monitor.core.entity;

public class ControlNodeDetails {
	private Long id;
	
	private String name;
	
	private String productCode;
	
	private Short nodeStatus;
	
	private Short operateStatus;
	
	private Short type;
	
	private Long gatewayId;

	private Long sersorType;

	private String sersorTypeName;

	public String getSersorTypeName() {
		return sersorTypeName;
	}

	public void setSersorTypeName(String sersorTypeName) {
		this.sersorTypeName = sersorTypeName;
	}

	public Long getSersorType() {
		return sersorType;
	}

	public void setSersorType(Long sersorType) {
		this.sersorType = sersorType;
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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Short getNodeStatus() {
		return nodeStatus;
	}

	public void setNodeStatus(Short nodeStatus) {
		this.nodeStatus = nodeStatus;
	}

	public Short getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(Short operateStatus) {
		this.operateStatus = operateStatus;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Long getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(Long gatewayId) {
		this.gatewayId = gatewayId;
	}
	
}
