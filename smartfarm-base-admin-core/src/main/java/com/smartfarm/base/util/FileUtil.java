package com.smartfarm.base.util;

import java.io.File;
import java.util.List;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月06日 09:53:59
 * @version 1.0
 */
public class FileUtil {

	// 递归删除文件
	public static void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				// 如果是文件夹的话，则继续调用deleteFolder方法，不是的话调用delete()方法，删除文件
				if (file.isDirectory()) {
					deleteFolder(file);
				} else {
					file.delete();
				}
			}
		}
		folder.delete();
	}

	//删除指定文件
	public static void deleteFiles(List<String> filePath) {
		for (String string : filePath) {
			File file = new File(string);
			file.delete();
		}
	}
}
