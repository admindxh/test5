package com.rimi.ctibet.common.util;

import java.util.List;

public class ListUtil {

	/**
	 * 判断list是否不为空，返回true表示不为空false表示为空
	 * @param list
	 * @return
	 */
	public static boolean isNotNull(List list){
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 返回集合的单个对象
	 * @param list
	 * @return
	 */
	public static <X> X returnSingle(List<X> list){
		return (list!=null && list.size()>0)?(list.get(0)):(null);
	}
	
	/**
	 * 拼接字符串
	 */
	public static String join(List<String> list, String regx){
		return join(list.toArray(new String[list.size()]), regx);
	}
	public static String join(String[] str, String regx){
		String res = "";
		if(str!=null && str.length>0){
			for (int i = 0; i < str.length; i++) {
				String s = str[i];
				res += s;
				if(i<str.length-1){
					res += ",";
				}
			}
		}
		return res;
	}
	
}
