package com.rimi.ctibet.common.util;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 网站搜索
 * @author chengsl
 *
 */
public class WebSearch implements Serializable {
	
	private static final long serialVersionUID = 2615434251621116684L;
	public static final String KEY_CODE=    "1000";
	public static final String KEY_URL=     "1001";
	public static final String KEY_IMAGEURL="1002";
	public static final String KEY_TITLE=   "1003";
	public static final String KEY_CONTENT= "1004";
	public static final String KEY_TYPE=    "1005";
	public static final String KEY_DATE=    "1006";
	private String code;
	private String url;
	private String imageUrl;
	private String title;
	private String content;
	private String type="0001";
	private Long date = new Date().getTime();
	private String keywords;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		if(content!=null)
		{
			String msg=	content.replaceAll("<[^>]*>","");
			  Pattern p = Pattern.compile("\\s*|\t|\r|\n");
	          Matcher m = p.matcher(msg);
	          content = m.replaceAll("");
		}
		this.content = content;
	}
	public void setRcon(String s)
	{
		this.content=s;
	}
	
	public String getType() {
		return type;
	}
	/**
	 * 参考 content 的type 进行设置
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	public Long getDate() {
		return date;
	}
	/**
	 * 使用 date.getTime()为其赋值！
	 * @param date
	 */
	public void setDate(Long date) {
		this.date = date;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	@Override
	public String toString() {
		return "WebSearch [code=" + code + ", url=" + url + ", imageUrl="
				+ imageUrl + ", title=" + title + ", content=" + content
				+ ", type=" + type + ", date=" + date + ", keywords="
				+ keywords + "]";
	}
	
}
