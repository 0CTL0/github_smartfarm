package com.smartfarm.base.shop.core.entity;


import java.io.Serializable;

public class AccessToken implements Serializable {

	private static final long serialVersionUID = -2923452472511224831L;
	
	private Long id;
	
	private String accessToken;
	
	private String jsapiTicket;
	
	private Short accountNo;
	
	private String appid;
	
	private String secret;
	
	private Long businessId;
	
	private String mchid;
	
	private String payKey;

	private String certUrl;
	
	public String getMchid() {
		return mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid;
	}

	public String getPayKey() {
		return payKey;
	}

	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getJsapiTicket() {
		return jsapiTicket;
	}

	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}

	public Short getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(Short accountNo) {
		this.accountNo = accountNo;
	}

	public String getCertUrl() {
		return certUrl;
	}

	public void setCertUrl(String certUrl) {
		this.certUrl = certUrl;
	}
}
