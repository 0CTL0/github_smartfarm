package com.smartfarm.base.monitor.core.util;

import java.util.List;


public class Live {
	private List<LiveData> data;
	private String code;
	private String msg;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<LiveData> getData() {
		return data;
	}
	public void setData(List<LiveData> data) {
		this.data = data;
	}
}
