package com.smartfarm.base.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 
 * 证书信任管理器 
 * 〈功能详细描述〉
 *
 * @author justin
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MyX509TrustManager implements X509TrustManager{
	@Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
    }  
	@Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
    }  
	@Override
    public X509Certificate[] getAcceptedIssuers() {  
        return null;  
    }  
}