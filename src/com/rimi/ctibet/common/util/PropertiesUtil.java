package com.rimi.ctibet.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


/**
 * 配置文件工具类
 * 
 * @author 刘洪兵
 * 
 * @时间 2014-11-17
 * 
 */
public class PropertiesUtil {
	/**
	 * 获取配置文件流
	 * 
	 * @param fileUrl
	 *            文件路径
	 * @return getInputStream
	 */
	private static InputStream getInputStream(String fileUrl) {
		File file = new File(fileUrl);
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			new Exception("指 定 文 件 没 有 找 到。。");
			return null;
		}
	}
	/**
	 * 获取配置文件
	 * @param fileUrl 文件路径
	 * @return Properties
	 */
	public static Properties getProperties(String fileUrl) {
		InputStream inputStream = getInputStream(fileUrl);
		if (null != inputStream) {
			Properties properties = new Properties();
			try {
				properties.load(inputStream);
				return properties;
			} catch (IOException e) {
				new Exception("系 统 I/O 错 误。。。。");
				return null;
			}
		}else{
			return null;
		}
	}
	/**
	 * 保存配置文件
	 * @param properties Properties
	 * @param fileUrl 保存地址
	 * @return 是否成功
	 */
	public static Boolean saveProperties(Properties properties,String fileUrl){
		try {
			properties.store(new FileOutputStream(new File(fileUrl)), null);
			return true;
		} catch (IOException e) {
			new Exception("系 统 I/O 错 误。。。。");
			return false;
		}
	}
}
