package com.smartfarm.base.monitor.core.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import com.smartfarm.base.util.DateUtil;

public class FileInputStreamUtil {
	public static void downLoadPic(String picUrl){
		 HttpURLConnection conn = null;
         InputStream inputStream = null;
         BufferedInputStream bis = null;
         FileOutputStream out = null;
         try
         {
        	 ResourceBundle resource = ResourceBundle.getBundle("projectSet");
     		String saveUrl = resource.getString("saveUrl");
//             File file0=new File("D:\\test");
             File file0=new File(saveUrl);
             if(!file0.isDirectory()&&!file0.exists()){
                 file0.mkdirs();
             }
             out = new FileOutputStream(file0+"\\snap\\"+DateUtil.formatCurrentDateTime()+"snap.jpg");
             // 建立链接
             URL httpUrl=new URL(picUrl);
             conn=(HttpURLConnection) httpUrl.openConnection();
             //以Post方式提交表单，默认get方式
             conn.setRequestMethod("GET");
             conn.setDoInput(true);  
             conn.setDoOutput(true);
             // post方式不能使用缓存 
             conn.setUseCaches(false);
             //连接指定的资源 
             conn.connect();
             //获取网络输入流
             inputStream=conn.getInputStream();
             bis = new BufferedInputStream(inputStream);
             byte b [] = new byte[1024];
             int len = 0;
             while((len=bis.read(b))!=-1){
                 out.write(b, 0, len);
             }
             System.out.println("下载完成...");
         } catch (Exception e) {
             e.printStackTrace();
         }finally{
             try {
                 if(out!=null){
                     out.close();
                 }
                 if(bis!=null){
                     bis.close();
                 }
                 if(inputStream!=null){
                     inputStream.close();
                 }
             } catch (Exception e2) {
                 e2.printStackTrace();
             }
         }
	}
}
