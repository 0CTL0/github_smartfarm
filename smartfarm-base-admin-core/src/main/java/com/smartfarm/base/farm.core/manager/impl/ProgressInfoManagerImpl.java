package com.smartfarm.base.farm.core.manager.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.util.UploadUtil;
import com.smartfarm.base.farm.core.dao.ProgressInfoDao;
import com.smartfarm.base.farm.core.entity.ProgressInfo;
import com.smartfarm.base.farm.core.entity.vo.ProgressInfoVo;
import com.smartfarm.base.farm.core.manager.ProgressInfoManager;

@Service("progressInfoManager")
public class ProgressInfoManagerImpl implements ProgressInfoManager{

	@Resource
	private ProgressInfoDao progressInfoDao;
	
	@Override
	public List<ProgressInfo> queryAllOrderProgress(Long id) {
		return progressInfoDao.queryAllOrderProgress(id);
	}

	@Override
	public int insert(ProgressInfo progressInfo, MultipartFile[] files) throws IOException {
		// 插入描述信息图片
		String pathListStr = "";
		for (MultipartFile file : files) {
			String path = UploadUtil.uploadFile(file, "/upload/",
					"crowdfunding" + RandomUtil.generateNumber(10));
			pathListStr = pathListStr + path + ";";
		}
		pathListStr = pathListStr.substring(0, pathListStr.length() - 1);
		progressInfo.setPics(pathListStr);
		
		progressInfo.setProjectType(ProgressInfo.TYPE_CROWDFUNDING);
		progressInfo.setReportTime(DateUtil.formatCurrentDateTime());
		return progressInfoDao.insert(progressInfo);
	}

	@Override
	public int deleteProgressInfoById(Long id) {
		return progressInfoDao.deleteProgressInfoById(id);
	}

	@Override
	public List<ProgressInfoVo> getCrowdfundingProgressInfoList(Long id) {
		return progressInfoDao.getCrowdfundingProgressInfoList(id);
	}

}
