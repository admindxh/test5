package com.rimi.ctibet.domain;

import java.util.Date;

public class BadWords {
	private Integer id;
    private String code;
    private String badword;
    private Date createDate;
	private Integer  avaliable;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBadword() {
		return badword;
	}
	public void setBadword(String badword) {
		this.badword = badword;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAvaliable() {
		return avaliable;
	}
	public void setAvaliable(Integer avaliable) {
		this.avaliable = avaliable;
	} 
	
}
