package com.smartfarm.base.util;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.ResourceBundle;

@SuppressWarnings("deprecation")
public class WechatConfig {

    private static SSLConnectionSocketFactory sslcsf;

    public static SSLConnectionSocketFactory getSslcsf(String mchId,String certUrl) {
        if (null == sslcsf) {
            setSsslcsf(mchId,certUrl);
        }
        return sslcsf;
    }

    private static void setSsslcsf(String mchId,String certUrl) {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            Thread.currentThread().getContextClassLoader();
           //加载本地的证书进行https加密传输
            ResourceBundle resource = ResourceBundle.getBundle("projectSet");
            String saveUrl = resource.getString("saveUrl");
            String path=saveUrl+certUrl;
            FileInputStream instream = new FileInputStream(new File(path));
            try {
                keyStore.load(instream, mchId.toCharArray());
            } finally {
                instream.close();
            }
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
            sslcsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
