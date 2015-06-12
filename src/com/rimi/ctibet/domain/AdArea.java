package com.rimi.ctibet.domain;

import java.util.Date;

public class AdArea {
	  private String  id;
      private String  code;
	  private String  imgUrl;
	  private String  url;
	  private String  type;
	  private Date createTime = new Date();
	  private int avaliable = 1;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	
	public int getAvaliable() {
		return avaliable;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setAvaliable(int avaliable) {
		this.avaliable = avaliable;
	}
	
    @Override
	public String toString() {
		return "AdArea [avaliable=" + avaliable + ", code=" + code
				+ ", createDate=" + createTime + ", id=" + id + ", imgUrl="
				+ imgUrl + ", type=" + type + ", url=" + url + "]";
	}
	  
}
