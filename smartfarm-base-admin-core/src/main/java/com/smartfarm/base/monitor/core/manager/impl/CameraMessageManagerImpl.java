package com.smartfarm.base.monitor.core.manager.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.monitor.core.dao.CameraMessageDao;
import com.smartfarm.base.monitor.core.entity.CameraMessage;
import com.smartfarm.base.monitor.core.manager.CameraMessageManager;
import com.smartfarm.base.monitor.core.util.DensityFreeUtil;
import com.smartfarm.base.monitor.core.util.FileInputStreamUtil;
import com.smartfarm.base.monitor.core.util.GetTokenUtil;
import com.smartfarm.base.monitor.core.util.GetVedioUtil;
import com.smartfarm.base.monitor.core.util.Live;
import com.smartfarm.base.monitor.core.util.SnapUtil;
import com.smartfarm.base.monitor.core.util.Token;

@Service("cameraMessageManager")
public class CameraMessageManagerImpl implements CameraMessageManager{

	@Resource
	private CameraMessageDao cameraMessageDao;

	@Override
	public List<CameraMessage> queryCamerasByFarmId(Long farmId,Integer start,Integer limit) {
		return cameraMessageDao.getFarmCamerasPageList(farmId,start,limit);
	}

	@Override
	public int countCamerasTotal(Long farmId) {
		return cameraMessageDao.getFarmCamerasTotal(farmId);
	}

	@Override
	public int addFarmCamera(CameraMessage cameraMessage) {
		cameraMessage.setAppKey(CameraMessage.CAMERA_APP_KEY);
		cameraMessage.setAppSecret(CameraMessage.CAMERA_APP_SECRET);
		cameraMessage.setAccessToken(CameraMessage.CAMERA_ACCESS_TOKEN);
		return cameraMessageDao.insert(cameraMessage);
	}

	@Override
	public CameraMessage queryCameraById(Long id) {
		return cameraMessageDao.getSingleCamera(id);
	}

	@Override
	public int modifyCameraInfoById(CameraMessage cameraMessage) {
		return cameraMessageDao.updateById(cameraMessage);
	}

	@Override
	public int removeCameraById(Long id) {
		return cameraMessageDao.deleteById(id);
	}

	@Override
	public String queryCameraLiveAddressById(Long id) {
		CameraMessage cameraMessage = checkCamera(id);
		//获取摄像头直播地址
		Live live = GetVedioUtil.getVedioAddress(cameraMessage.getAccessToken(), cameraMessage.getDeviceSerial()+":"+cameraMessage.getChannelNo());
		//返回m3u8的直播地址类型
		String hls = live.getData().get(0).getHls();
		//如果返回直播地址为空，就开通免密
		if(hls == null || "".equals(hls)){
			DensityFreeUtil.densityFree(cameraMessage.getAccessToken(), cameraMessage.getDeviceSerial(), cameraMessage.getValidateCode());
			return live.getData().get(0).getHls();
		}
		return hls;
	}

	@Override
	public void snapPhoto(Long id) {
		CameraMessage cameraMessage = checkCamera(id);
		String picUrl = SnapUtil.getSnapUrl(cameraMessage.getAccessToken(), cameraMessage.getDeviceSerial(), cameraMessage.getChannelNo());
		FileInputStreamUtil.downLoadPic(picUrl);
	}

	@Override
	public CameraMessage checkCamera(Long id) {
		CameraMessage cameraMessage = null;
		cameraMessage = cameraMessageDao.queryCameraMessageById(id);
		//判断AccessToken有没有过期
		Date date = new Date();
		if(date.getTime() > cameraMessage.getExpireTime()){
			CameraMessage camera = new CameraMessage();
			Token token = GetTokenUtil.getSnapUrl(cameraMessage.getAppKey(), cameraMessage.getAppSecret());
			camera.setAccessToken(token.getData().getAccessToken());
			camera.setExpireTime(Long.parseLong(token.getData().getExpireTime()));
			camera.setId(id);
			cameraMessageDao.updateById(camera);
			cameraMessage = cameraMessageDao.queryCameraMessageById(id);
		}
		return cameraMessage;
	}

	@Override
	public List<CameraMessage> queryCameraMessageList() {
		return cameraMessageDao.queryCameraMessageList();
	}

	@Override
	public List<CameraMessage> queryFarmCameras(Long farmId) {
		return cameraMessageDao.getFarmCameras(farmId);
	}

}
