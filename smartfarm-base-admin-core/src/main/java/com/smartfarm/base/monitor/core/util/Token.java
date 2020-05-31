package com.smartfarm.base.monitor.core.util;

public class Token {
	private String code;
	private String msg;
	private TokenData data;
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
	public TokenData getData() {
		return data;
	}
	public void setData(TokenData data) {
		this.data = data;
	}
}
