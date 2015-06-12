package com.rimi.ctibet.web.controller.vo;

import java.util.Date;

//专题
public class SpecialVO {
	//code
	private String code;
	// 专题名称
	private String name;
	// 专题简介
	private String summary;
	// url
	private String url;
	// 缩略图url
	private String tag;
	// 图片url
	private String title;
	// 
	private String keywords;
	// 创建时间
	private Date createTime;
	// 
	private int sortNum;

	// 查看数
    private int viewcount;
	// 假查看数
    private int falseViewcount;
	// 收藏数
    private int favoriteNum;
	// 假收藏数
    private int falseFavoriteNum;
	// 评论数
    private int replyNum;
    
    public SpecialVO(){}
    
    /********************************************
	 * Setter Getter
	 ********************************************/
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getViewcount() {
		return viewcount;
	}
	public void setViewcount(int viewcount) {
		this.viewcount = viewcount;
	}
	public int getFalseViewcount() {
		return falseViewcount;
	}
	public void setFalseViewcount(int falseViewcount) {
		this.falseViewcount = falseViewcount;
	}
	public int getFavoriteNum() {
		return favoriteNum;
	}
	public void setFavoriteNum(int favoriteNum) {
		this.favoriteNum = favoriteNum;
	}
	public int getFalseFavoriteNum() {
		return falseFavoriteNum;
	}
	public void setFalseFavoriteNum(int falseFavoriteNum) {
		this.falseFavoriteNum = falseFavoriteNum;
	}
	public int getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
	
}
