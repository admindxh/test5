package com.rimi.ctibet.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串匹配工具类
 * @author sunzy
 * @date Mar 6, 2014
 * @package com.rimi.medical.common.util
 * @copyright 成都睿峰科技有限公司
 * @version V1.0
 */
public class MatchHolder {
	/**
	 * 是否是年龄
	 * @param s
	 * @return
	 */
	public static boolean isAge(String s){
		String regEx="^[1-9][0-9]{0,1}||1[0-9]{2}$";
		Pattern pat=Pattern.compile(regEx);
		Matcher mch=pat.matcher(s);
		return mch.matches();
	}
	/**
	 * 是否是中文
	 * @param s
	 * @return
	 */
	public static boolean isChinese(String s){
		String regEx="^[\u4e00-\u9fa5]+$";
		Pattern pat=Pattern.compile(regEx);
		Matcher mch=pat.matcher(s);
		return mch.matches();
	}
	/**
	 * 是否是英文
	 * @param s
	 * @return
	 */
	public static boolean isEnglish(String s){
		String regEx="^[a-z|A-Z]+$"; 
		Pattern pat=Pattern.compile(regEx);
		Matcher mch=pat.matcher(s);
		return mch.matches();
	}
	/**
	 * 是否是数字
	 * @param s
	 * @return
	 */
	public static boolean isNumber(String s){
		String regEx="^[0-9]+$"; 
		Pattern pat=Pattern.compile(regEx);
		Matcher mch=pat.matcher(s);
		return mch.matches();
	}
	public static boolean isMatched(String regEx,String s){ 
		Pattern pat=Pattern.compile(regEx);
		Matcher mch=pat.matcher(s);
		return mch.matches();
	}/**
	 * 验证手机号
	 * @param phone
	 * @return
	 */
	public static boolean isEmpTelphone(String phone){
//		String regExp = "^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0]))\\d{8}$";
//		String regExp = "^(13[0-9]|15[012356789]|18[02356789]|14[57])[0-9]{8}$";
		String regExp = "^(13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(phone);
		return m.matches();
	}
	

	
	public static boolean isEmail(String email){
		String regExp = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(email);
		return m.matches();
	}
}
