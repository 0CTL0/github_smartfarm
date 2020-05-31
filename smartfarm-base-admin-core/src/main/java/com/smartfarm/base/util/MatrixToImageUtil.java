package com.smartfarm.base.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;



/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月05日 23:25:30
 * @version 1.0
 */
public class MatrixToImageUtil {

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;
	private static final String PATH = ResourceBundle.getBundle("projectSet").getString("saveUrl");
	
	private MatrixToImageUtil() {
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	// 输出为文件
	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}

	// 输出为流
	public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}

	/**
	 * 根据内容，生成指定宽高、指定格式的二维码图片
	 *
	 * @param text 内容
	 * @param width 宽
	 * @param height 高
	 * @param format 图片格式
	 * @return 生成的二维码图片路径
	 * @throws Exception
	 */
	public static void generateQRCode(List<String> textList, int width, int height, String format,String path,String folderName,int margin) throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, margin);//外边距
		int i = 1;
		for (String text : textList) {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
			String pathName = path+"/"+folderName+"/"+ (i++) +"."+format;
			File outputFile = new File(pathName);
			if (!outputFile.exists()) {
				outputFile.mkdirs();
			}
			MatrixToImageUtil.writeToFile(bitMatrix, format, outputFile);
		}
	}
	
	public static void exportTraceCode(List<String> textList,int height,int width,String format,String folderName,int margin) {
		try {
            //生成二维码图片，并返回图片路径
        	generateQRCode(textList, width, height,format,PATH,folderName,margin);
        	FileOutputStream fos1 = new FileOutputStream(new File(PATH+"/file/"+folderName+".zip"));
    		ZipUtil.toZip(PATH+"/"+folderName, fos1, true);
    		File folder = new File(PATH+"/"+folderName);
    		FileUtil.deleteFolder(folder);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
