package com.smartfarm.base.farm.core.entity.vo;

import com.smartfarm.base.farm.core.entity.FarmMemberLand;


/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月18日 16:12:50
 * @version 1.0
 */
public class FarmMemberLandVo extends FarmMemberLand {

	private Long tunnelId;
	private String mainPic;
	private String brief;


	public Long getTunnelId() {
		return tunnelId;
	}

	public void setTunnelId(Long tunnelId) {
		this.tunnelId = tunnelId;
	}

	public String getMainPic() {
		return mainPic;
	}

	public void setMainPic(String mainPic) {
		this.mainPic = mainPic;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}
}
