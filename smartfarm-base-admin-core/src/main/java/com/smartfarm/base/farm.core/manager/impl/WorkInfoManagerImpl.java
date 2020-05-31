package com.smartfarm.base.farm.core.manager.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.util.UploadUtil;
import com.smartfarm.base.farm.core.dao.WorkDetailDao;
import com.smartfarm.base.farm.core.dao.WorkInfoDao;
import com.smartfarm.base.farm.core.entity.WorkDetail;
import com.smartfarm.base.farm.core.entity.WorkInfo;
import com.smartfarm.base.farm.core.manager.WorkInfoManager;

@Service("workInfoManager")
public class WorkInfoManagerImpl implements WorkInfoManager{

	@Resource
	private WorkInfoDao workInfoDao;
	
	@Resource
	private WorkDetailDao workDetailDao;
	
	@Override
	public int addWorkInfoWithDetail(WorkInfo workInfo, MultipartFile[] files,
			String attributeListJson) throws IOException {
		// 插入描述信息图片
		String pathListStr = "";
		for (MultipartFile file : files) {
			String path = UploadUtil.uploadFile(file, "/upload/",
					"crowdfunding" + RandomUtil.generateNumber(10));
			// String path = UploadUtil.uploadFile(file,
			// "/upload/",DateUtil.formatCurrentDateTime());
			pathListStr = pathListStr + path + ";";
		}
		pathListStr = pathListStr.substring(0, pathListStr.length() - 1);
		workInfo.setPics(pathListStr);
		int count = workInfoDao.insert(workInfo);
		
		//解析attributeListJson
		JSONArray ja = JSONArray.fromObject(attributeListJson);
		@SuppressWarnings("unchecked")
		List<WorkDetail> details = (List<WorkDetail>) JSONArray.toCollection(ja, WorkDetail.class);
		for (WorkDetail workDetail : details) {
			workDetail.setWorkId(workInfo.getId());
			workDetailDao.insert(workDetail);
		}
		return count;
	}

}
