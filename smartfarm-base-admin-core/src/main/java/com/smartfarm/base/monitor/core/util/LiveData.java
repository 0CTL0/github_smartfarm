package com.smartfarm.base.monitor.core.util;

public class LiveData {
	private String deviceSerial;
	private int channelNo;
	private String deviceName;
	private String hls;
	private String hlsHd;
	private String rtmp;
	private String rtmpHd;
	private String flvAddress;
	private String hdFlvAddress;
	private int status;
	private int exception;
	private String ret;
	private String desc;
	public String getDeviceSerial() {
		return deviceSerial;
	}
	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}
	public int getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(int channelNo) {
		this.channelNo = channelNo;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getHls() {
		return hls;
	}
	public void setHls(String hls) {
		this.hls = hls;
	}
	public String getHlsHd() {
		return hlsHd;
	}
	public void setHlsHd(String hlsHd) {
		this.hlsHd = hlsHd;
	}
	public String getRtmp() {
		return rtmp;
	}
	public void setRtmp(String rtmp) {
		this.rtmp = rtmp;
	}
	public String getRtmpHd() {
		return rtmpHd;
	}
	public void setRtmpHd(String rtmpHd) {
		this.rtmpHd = rtmpHd;
	}
	public String getFlvAddress() {
		return flvAddress;
	}
	public void setFlvAddress(String flvAddress) {
		this.flvAddress = flvAddress;
	}
	public String getHdFlvAddress() {
		return hdFlvAddress;
	}
	public void setHdFlvAddress(String hdFlvAddress) {
		this.hdFlvAddress = hdFlvAddress;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getException() {
		return exception;
	}
	public void setException(int exception) {
		this.exception = exception;
	}
	public String getRet() {
		return ret;
	}
	public void setRet(String ret) {
		this.ret = ret;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
