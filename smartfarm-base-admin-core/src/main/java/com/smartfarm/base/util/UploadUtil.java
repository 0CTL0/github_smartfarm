package com.smartfarm.base.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;


public class UploadUtil {
	 public static final String imageType = "IMG";
	 public static final String musicType = "MUSIC";
	 public static final String unknown = "UN_KNOWN";
	 
	public static String uploadFile(MultipartFile file,String savePath,String fileName) throws IOException{
		ResourceBundle resource = ResourceBundle.getBundle("projectSet");
		String saveUrl = resource.getString("saveUrl");
		
		String name = file.getOriginalFilename();//上传文件真实名称
        String suffixName = name.substring(name.lastIndexOf("."));
        fileName += suffixName;
		File tempFile = new File(saveUrl + savePath,fileName);
		if (!tempFile.getParentFile().exists()) {
			tempFile.getParentFile().mkdir();
		}
		if (tempFile.exists()) {
			tempFile.delete();
		}
		try {
            file.transferTo(tempFile);
        } catch (Exception e) {
        	throw new IOException(e.getMessage());
        }
		savePath+=fileName;
		return savePath;
	}
	
	
	public static String getFileType(MultipartFile file){
		String img[] = { "bmp", "jpg", "jpeg", "png", "tiff", "gif", "pcx", "tga", "exif", "fpx", "svg", "psd",
				"cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "wmf" };
		String music[] = { "mp3", "wma", "wav", "mod", "ra", "cd", "md", "asf", "aac", "vqf", "ape", "mid", "ogg",
				"m4a", "vqf" };
		String fileName = file.getOriginalFilename();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
		for (int i = 0; i < img.length; i++) {
			if (img[i].equals(fileType)) {
				return imageType;
			}
		}
		for (int j = 0; j < music.length; j++) {
			if (music[j].equals(fileType)) {
				return musicType;
			}
		}
		return unknown;
	}

}
