package com.rimi.ctibet.domain;

import java.util.Date;

import javax.persistence.Transient;

public class Surguestion {
 private Integer id;
 private String memberCode;
 private String content;
 private Date createDate;
 private String code;
 private Integer avaliable;
 @Transient
 private String username;
 
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getMemberCode() {
	return memberCode;
}
public void setMemberCode(String memberCode) {
	this.memberCode = memberCode;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public Date getCreateDate() {
	return createDate;
}
public void setCreateDate(Date createDate) {
	this.createDate = createDate;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
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
}
