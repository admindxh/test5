package com.rimi.ctibet.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


public class HtmlRegexpUtil {
	private final static String REGXPFORHTML = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签
	public static String filterHtml(String str) {
		Pattern pattern = Pattern.compile(REGXPFORHTML);
		if (!StringUtils.isNotBlank(str)) {
			return "";
		}
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
	    String resultStr  = sb.toString();
	      resultStr = resultStr.replaceAll("&nbsp;", "");
		return resultStr;
	}
	public String replaceTag(String input) {
		if (!hasSpecialChars(input)) {
			return input;
		}
		StringBuffer filtered = new StringBuffer(input.length());
		char c;
		for (int i = 0; i <= input.length() - 1; i++) {
			c = input.charAt(i);
			switch (c) {
			case '<':
				filtered.append("&lt;");
				break;
			case '>':
				filtered.append("&gt;");
				break;
			case '"':
				filtered.append("&quot;");
				break;
			case '&':
				filtered.append("&amp;");
				break;
			default:
				filtered.append(c);
			}

		}
		return (filtered.toString());
	}
	public boolean hasSpecialChars(String input) {
		boolean flag = false;
		if ((input != null) && (input.length() > 0)) {
			char c;
			for (int i = 0; i <= input.length() - 1; i++) {
				c = input.charAt(i);
				switch (c) {
				case '>':
					flag = true;
					break;
				case '<':
					flag = true;
					break;
				case '"':
					flag = true;
					break;
				case '&':
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	public static void main(String[] args) {
		  String    a=  "<p><em><strong>sdfsdfsdfs</strong></em></p><p><em><strong>sdfsldjflsjdfla<img src=\"//ctibet//upload//img//post//20141223//1419300916663064793.jpg/\" title=\"1419300916663064793.jpg\" alt=\"Tulips.jpg\"/></strong></em></p><p> 44rty   <br/></p>"; 
		  HtmlRegexpUtil   h  = new   HtmlRegexpUtil();
		  ////System.out.println(h.replaceTag(a));
		  //System.out.println(HtmlRegexpUtil.filterHtml(a));
		  String regex  = "\\s+";
		  String b  = "sdfs lllll&nbsp;&nbsp;sdeeeeee&nbsp;&nbsp;";
	}
	
}
