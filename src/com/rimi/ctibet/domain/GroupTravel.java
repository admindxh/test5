package com.rimi.ctibet.domain;

import java.util.Date;

/**
 * 団游信息管理
 * @author xiaozhen
 *
 */
public class GroupTravel {

	 //排序规则
	public  final static String RULE_PRAISE = "praise";
	public  final static String RULE_VIEW = "view";
	public  final static String RULE_COLLECT = "collect";
	
	 private int id;
	 private String code;
	 private String name;
	 private String price = "0";
	 private String detail;
	 private String img;
	 private String ctripUrl;
	 private String url;
	 private String keyWord;
	 private int avaliable = 1;
	 private int isRecommend;
	 private int praiseNum = 0;
	 private int viewNum = 0;
	 private int collectNum = 0;
	 private Date createTime = new Date();
	 private Date joinTime;
	 /**
      * tdk
      */
     private String tdkTitle;
     private String tdkDescription;
     private String tdkKeywords;
	 public Date getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}
	public Date getCreateTime() {
		return createTime;
	  }
	 public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	 }
	 public int getId() {
	 	return id;
	 }
	 public void setId(int id) {
	 	this.id = id;
	 }
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
     
	 public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDetail() {
	 	return detail;
	 }
	 public void setDetail(String detail) {
	 	this.detail = detail;
	 }
	 public String getImg() {
	 	return img;
	 }
	 public void setImg(String img) {
	 	this.img = img;
	 }
	 public String getCtripUrl() {
	 	return ctripUrl;
	 }
	 public void setCtripUrl(String ctripUrl) {
	 	this.ctripUrl = ctripUrl;
	 }
	 public String getUrl() {
	 	return url;
	 }
	 public void setUrl(String url) {
	 	this.url = url;
	 }
	 public String getKeyWord() {
	 	return keyWord;
	 }
	 public void setKeyWord(String keyWord) {
	 	this.keyWord = keyWord;
	 }
	 
	public int getAvaliable() {
		return avaliable;
	}
	public void setAvaliable(int avaliable) {
		this.avaliable = avaliable;
	}
	public int getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(int isRecommend) {
		this.isRecommend = isRecommend;
	}
	public int getPraiseNum() {
		return praiseNum;
	}
	public void setPraiseNum(int praiseNum) {
		this.praiseNum = praiseNum;
	}
	public int getViewNum() {
		return viewNum;
	}
	public void setViewNum(int viewNum) {
		this.viewNum = viewNum;
	}
	
	public int getCollectNum() {
		return collectNum;
	}
	public void setCollectNum(int collectNum) {
		this.collectNum = collectNum;
	}
	
	public String getTdkTitle() {
        return tdkTitle;
    }
    public void setTdkTitle(String tdkTitle) {
        this.tdkTitle = tdkTitle;
    }
    public String getTdkDescription() {
        return tdkDescription;
    }
    public void setTdkDescription(String tdkDescription) {
        this.tdkDescription = tdkDescription;
    }
    public String getTdkKeywords() {
        return tdkKeywords;
    }
    public void setTdkKeywords(String tdkKeywords) {
        this.tdkKeywords = tdkKeywords;
    }
    @Override
	public String toString() {
		return "GroupTravel [avaliable=" + avaliable + ", code=" + code
				+ ", createTime=" + createTime + ", ctripUrl=" + ctripUrl
				+ ", detail=" + detail
				+ ", id=" + id + ", img=" + img + ", isRecommend="
				+ isRecommend + ", keyWord=" + keyWord + ", name=" + name
				+ ", praiseNum=" + praiseNum + ", price=" + price + ", url="
				+ url + ", viewNum=" + viewNum + "]";
	}
}
