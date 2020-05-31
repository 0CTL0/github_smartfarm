package com.smartfarm.base.monitor.core.entity;

public class CameraMessage {
	public final static String CAMERA_APP_KEY = "6ee5c6137f764374b1f4daab5b18e1b5";//萤石云账号app_key
	public final static String CAMERA_APP_SECRET = "75600b89605bb9a928f20d571086d70f";//萤石云账号app_secret
	public final static String CAMERA_ACCESS_TOKEN = "at.0h1iusv77655jdum22eqmcge4rsjffl1-92o78k5uku-11nk490-ka75egvne";//萤石云账号access_token

	private Long id;
	private String appKey;
	private String appSecret;
	private String createTime;
	private String accessToken;
	private Long expireTime;
	private String deviceSerial;
	private Integer channelNo;
	private String validateCode;
	private String cameraName;
	private Long farmId;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Long getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}
	public String getDeviceSerial() {
		return deviceSerial;
	}
	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}
	public Integer getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(Integer channelNo) {
		this.channelNo = channelNo;
	}
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	public String getCameraName() {
		return cameraName;
	}
	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}

	public Long getFarmId() {
		return farmId;
	}

	public void setFarmId(Long farmId) {
		this.farmId = farmId;
	}
}
