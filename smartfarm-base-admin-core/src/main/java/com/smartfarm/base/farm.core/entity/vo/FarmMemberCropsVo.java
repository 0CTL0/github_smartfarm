package com.smartfarm.base.farm.core.entity.vo;

import com.smartfarm.base.farm.core.entity.FarmMemberCrops;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月16日 15:03:16
 * @version 1.0
 */
public class FarmMemberCropsVo extends FarmMemberCrops {

	private String name;
	private String cover;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}
	
}
