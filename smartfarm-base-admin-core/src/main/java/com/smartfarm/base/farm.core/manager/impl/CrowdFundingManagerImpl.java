package com.smartfarm.base.farm.core.manager.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.util.UploadUtil;
import com.smartfarm.base.farm.core.dao.CrowdfundingDao;
import com.smartfarm.base.farm.core.dao.GradeDao;
import com.smartfarm.base.farm.core.entity.Crowdfunding;
import com.smartfarm.base.farm.core.entity.Grade;
import com.smartfarm.base.farm.core.entity.vo.CrowdFundingVo;
import com.smartfarm.base.farm.core.entity.vo.CrowfundingGradeVo;
import com.smartfarm.base.farm.core.manager.CrowdFundingManager;

@Service("/crowdFundingManager")
public class CrowdFundingManagerImpl implements CrowdFundingManager {

	private Logger log = Logger.getLogger(CrowdFundingManager.class);
	@Resource
	private CrowdfundingDao crowdfundingDao;

	@Resource
	private GradeDao gradeDao;

	@Override
	public int updateById(Crowdfunding crowdfunding) {
		return crowdfundingDao.updateById(crowdfunding);
	}

	@Override
	public List<Crowdfunding> quryCrowdFundingList(Integer start, Integer limit) {
		return crowdfundingDao.quryCrowdFundingList(start, limit);
	}

	@Override
	public int queryCountCrowdFunding() {
		return crowdfundingDao.queryCountCrowdFunding();
	}

	@Override
	public Long insert(Crowdfunding crowdfunding, MultipartFile cover,
			MultipartFile[] desc, String gearListJson) throws IOException {
		// 插入描述信息图片
		String pathListStr = "";
		for (MultipartFile file : desc) {
			String path = UploadUtil.uploadFile(file, "/upload/",
					"crowdfunding" + RandomUtil.generateNumber(10));
			// String path = UploadUtil.uploadFile(file,
			// "/upload/",DateUtil.formatCurrentDateTime());
			pathListStr = pathListStr + path + ";";
		}
		pathListStr = pathListStr.substring(0, pathListStr.length() - 1);
		crowdfunding.setDescription(pathListStr);
		// 插入封面图片
		String coverPath = UploadUtil.uploadFile(cover, "/upload/",
				"crowdfunding" + RandomUtil.generateNumber(10));
		crowdfunding.setCover(coverPath);
		//设置项目状态为初稿
		crowdfunding.setStatus((short)-1);
		//设置项目热筹状态
		crowdfunding.setIsHot(false);

		Long id = crowdfundingDao.insert(crowdfunding);

		// 解析gearListJson
		JSONArray ja = JSONArray.fromObject(gearListJson);
		@SuppressWarnings("unchecked")
		List<Grade> gearList = (List<Grade>) JSONArray.toCollection(ja,
				Grade.class);
		for (Grade grade : gearList) {
			grade.setCrowfundingId(crowdfunding.getId());
			gradeDao.insert(grade);
		}
		// gradeDao.insertBatch(gearList);
		return id;
	}

	@Override
	public CrowdFundingVo queryCrowdFundingById(Long id) {
		return crowdfundingDao.queryCrowdFundingById(id);
	}

	@Override
	public int editCrowdFundingById(Crowdfunding crowdfunding,
			MultipartFile cover, MultipartFile[] files, String gearListJson)
			throws IOException {
		if (files != null && files.length > 0) {
			// 更新描述信息图片
			String pathListStr = "";
			for (MultipartFile file : files) {
				String path = UploadUtil.uploadFile(file, "/upload/",
						"crowdfunding" + RandomUtil.generateNumber(10));
				// String path = UploadUtil.uploadFile(file,
				// "/upload/",DateUtil.formatCurrentDateTime());
				pathListStr = pathListStr + path + ";";
			}
			pathListStr = pathListStr.substring(0, pathListStr.length() - 1);
			crowdfunding.setDescription(pathListStr);
		}
		if (cover != null) {
			// 更新封面图片
			String coverPath = UploadUtil.uploadFile(cover, "/upload/",
					"crowdfunding" + RandomUtil.generateNumber(10));
			crowdfunding.setCover(coverPath);
		}

		int count = crowdfundingDao.updateById(crowdfunding);

		if (gearListJson != null) {
			// 解析gearListJson
			JSONArray ja = JSONArray.fromObject(gearListJson);
			@SuppressWarnings("unchecked")
			List<Grade> gearList = (List<Grade>) JSONArray.toCollection(ja,
					Grade.class);
			for (Grade grade : gearList) {
				if (grade.getId() == null) {
					grade.setCrowfundingId(crowdfunding.getId());
					gradeDao.insert(grade);
				} else {
					gradeDao.updateById(grade);
				}
			}
		}
		return count;
	}

	@Override
	public CrowdFundingVo getCrowdfundingDetail(Long id) {
		return crowdfundingDao.getCrowdfundingDetail(id);
	}

	@Override
	public List<CrowdFundingVo> getAllCrowdfundingListWithStatus(long farmId, short status) {
		return crowdfundingDao.getAllCrowdfundingListWithStatus(farmId, status);
	}

	@Override
	public List<CrowdFundingVo> getAllCrowdfundingList(long farmId) {
		return crowdfundingDao.getAllCrowdfundingList(farmId);
	}

	@Override
	public CrowfundingGradeVo getCrowdFundingDetailByGradeId(Long gradeId) {
		return crowdfundingDao.getCrowdFundingDetailByGradeId(gradeId);
	}

}
