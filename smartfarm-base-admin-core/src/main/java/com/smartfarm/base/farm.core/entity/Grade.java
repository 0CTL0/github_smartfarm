package com.smartfarm.base.farm.core.entity;

import java.math.BigDecimal;

/** 众筹档次 */
public class Grade {
    /**流水号*/
    private Long id;
    /**价格*/
    private BigDecimal price;
    /**描述*/
    private String description;
    /**众筹流水号*/
    private Long crowfundingId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCrowfundingId() {
        return crowfundingId;
    }

    public void setCrowfundingId(Long crowfundingId) {
        this.crowfundingId = crowfundingId;
    }
}