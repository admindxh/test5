package com.rimi.ctibet.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class ImgUrlUtil {
	 /* 获取内容中的第一个图片
	 * @param reg
	 * @param content
	 * @return
	 */
	public static String  getContentImgSrc(String content){
		String  a  =  content;
		if (!StringUtils.isNotBlank(content)) {
			return "";
		}
		 Pattern  pattern   = Pattern.compile("<img.*?>");
		 Matcher  m = pattern.matcher(a);
		 Pattern pSrc  = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)");
		// Matcher mSrc  = pSrc.matcher(input)
		 String  src  = "";
		 while(m.find()){
			 //System.out.println(m.group());
			    String img  = m.group();
				Matcher mScr  = pSrc.matcher(img);
				if (mScr.find()) {
					src  = mScr.group().replaceAll("src=\"", "").replaceAll("\"", "");
					 //获取地址
				}
				break;
		 }
		 return   src;
	}
}
