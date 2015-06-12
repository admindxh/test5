package com.rimi.ctibet.common.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;

/**
 * 获取错误代码工具类
 * @author yangtong
 * @date 2014-7-29
 * @package com.rimi.medical.common.util
 * @copyright 成都睿峰科技有限公司
 * @version V1.0
 */
public class ErrorMsgUtil {
	
	/**
	 * 根据 状态码 获取错误消息
	 * @param code
	 * @return
	 */
	public static String getErrorMsg(String code){
		try{
			// 读取配置信息
			Configuration config = new PropertiesConfiguration("errorCode.properties");
			return StringUtils.isNotBlank(config.getString(code)) ? config.getString(code) : "未定义错误" ;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "获取系统配置文件错误！";
	}
	
	/**
	 * 根据 状态码 获取错误消息
	 * @param code
	 * @return
	 */
	public static String getErrorMsg(Integer code){
		try{
			// 读取配置信息
			Configuration config = new PropertiesConfiguration("errorCode.properties");
			return StringUtils.isNotBlank(config.getString(code.toString())) ? config.getString(code.toString()) : "未定义错误" ;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "获取系统配置文件错误！";
	}
	
	public static void main(String[] args) {
		//System.out.println(ErrorMsgUtil.getErrorMsg(500));
	}
	
}
