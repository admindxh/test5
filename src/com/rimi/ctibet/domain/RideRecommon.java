package com.rimi.ctibet.domain;

import java.util.Date;


/**
 * 骑行专区首页推荐
 * @author dengxh
 *
 */
public class RideRecommon {
	
	private Integer id;
	private String code;
	private Integer avaliable;
	private Date createTime;
	private String ctorname;//code　或者 名称 
	private String contentType;//内容类型
	private String url;//访问地址
	private String imgurl;//图片地址
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public Integer getAvaliable() {
		return avaliable;
	}
	public void setAvaliable(Integer avaliable) {
		this.avaliable = avaliable;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCtorname() {
		return ctorname;
	}
	public void setCtorname(String ctorname) {
		this.ctorname = ctorname;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
