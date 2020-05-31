package com.smartfarm.base.farm.core.entity;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年11月20日 18:14:41
 * @version 1.0
 */
public class ProductBatch {

	private Long id;// 流水号id
	private Long productInfoId;// 所属产品id
	private String batchCode;// 批次号
	private String createTime;// 创建时间
	private Short sourceStatus;// 溯源添加状态，0：未添加；1：已添加
	private Integer yield;// 批次产量
	private Integer packageSpecs;// 产品包装规格
	private Long farmId;//关联农场id


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductInfoId() {
		return productInfoId;
	}

	public void setProductInfoId(Long productInfoId) {
		this.productInfoId = productInfoId;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Short getSourceStatus() {
		return sourceStatus;
	}

	public void setSourceStatus(Short sourceStatus) {
		this.sourceStatus = sourceStatus;
	}

	public Integer getYield() {
		return yield;
	}

	public void setYield(Integer yield) {
		this.yield = yield;
	}

	public Integer getPackageSpecs() {
		return packageSpecs;
	}

	public void setPackageSpecs(Integer packageSpecs) {
		this.packageSpecs = packageSpecs;
	}

	public Long getFarmId() {
		return farmId;
	}

	public void setFarmId(Long farmId) {
		this.farmId = farmId;
	}
}
