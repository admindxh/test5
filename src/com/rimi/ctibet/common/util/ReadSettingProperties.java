package com.rimi.ctibet.common.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ReadSettingProperties {
	
	/**
	 * 获取通用设置的配置信息
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		try {
			// 读取配置信息
			Configuration config = new PropertiesConfiguration("commonSetting.properties");
			//获取值
			return config.getString(key);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获取通用设置的配置信息
	 * @param key
	 * @return
	 */
	public static String getValue(String propertiesName, String key){
		try {
			// 读取配置信息
			Configuration config = new PropertiesConfiguration(propertiesName);
			//获取值
			return config.getString(key);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args) {
		//System.out.println(getValue("max_cookies_store_time"));
		//System.out.println(Integer.parseInt(getValue("max_cookies_store_time")));
	}
	
}
