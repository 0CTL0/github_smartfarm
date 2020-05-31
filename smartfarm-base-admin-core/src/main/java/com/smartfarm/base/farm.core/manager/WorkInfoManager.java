package com.smartfarm.base.farm.core.manager;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.farm.core.entity.WorkInfo;

public interface WorkInfoManager {
	/**
	 * 增加WorkInfo
	 * @param workInfo
	 * @param files
	 * @param attributeListJson
	 * @return
	 * @throws IOException 
	 */
	public int addWorkInfoWithDetail(WorkInfo workInfo, MultipartFile[] files, String attributeListJson) throws IOException;
}
