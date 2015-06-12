package com.rimi.ctibet.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public final class CodeProduceUtil {

	private static final String CHARSET = "utf-8";
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	/**
	 * 禁止生成实例，生成实例也没有意义。
	 */
	private CodeProduceUtil() {
	}

	/**
	 * 将字符串编成一维条码的矩阵
	 * 
	 * @param str
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage toBarCodeMatrix(String str, Integer width, Integer height) {
		if (width == null || width < 200 || width > 400) {
			width = 300;
		}
		if (height == null || height < 50 || height > 150) {
			height = 100;
		}
		try {
			// 文字编码
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
			BitMatrix bitMatrix = new MultiFormatWriter().encode(str, BarcodeFormat.CODE_128, width, height, hints);
			return toBufferedImage(bitMatrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成矩阵，是一个简单的函数，参数固定，更多的是使用示范。
	 * 
	 * @param text
	 * @return
	 */
	public static BufferedImage toQRCodeMatrix(String text, Integer width, Integer height) {
		if (width == null || width < 200 || width > 400) {
			width = 300;
		}
		if (height == null || height < 200 || height > 400) {
			height = 300;
		}
		// 二维码的图片格式
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		// 内容所使用编码
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		// 生成二维码
		// File outputFile = new File("d:"+File.separator+"new.gif");
		// MatrixUtil.writeToFile(bitMatrix, format, outputFile);
		return toBufferedImage(bitMatrix);
	}

	/**
	 * 解码，需要javase包。
	 * 
	 * @param file
	 * @return
	 */
	public static String decode(File file) {
		BufferedImage image;
		try {
			if (file == null || file.exists() == false) {
				throw new Exception(" File not found:" + file.getPath());
			}
			image = ImageIO.read(file);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			Result result;
			// 解码设置编码方式为：utf-8，
			Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
			hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
			result = new MultiFormatReader().decode(bitmap, hints);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据点矩阵生成黑白图
	 */
	private static BufferedImage toBufferedImage(BitMatrix matrix) {
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

	public static void main(String[] args) throws Exception {
		String text = "http://www.baidu.com";
		String text2 = "458568456245684";
		String result;
		String format = "gif";
		// 生成二维码
		File outputFile = new File("d:" + File.separator + "rqcode.gif");
		if (!ImageIO.write(CodeProduceUtil.toQRCodeMatrix(text, null, null), format, outputFile)) {
			throw new IOException("Could not write an image of format " + format + " to " + outputFile);
		}
		result = CodeProduceUtil.decode(outputFile);
		//System.out.println(result);

		outputFile = new File("d:" + File.separator + "barcode.gif");
		if (!ImageIO.write(CodeProduceUtil.toBarCodeMatrix(text2, null, null), format, outputFile)) {
			throw new IOException("Could not write an image of format " + format + " to " + outputFile);
		}
		result = CodeProduceUtil.decode(outputFile);
		//System.out.println(result);
	}

}