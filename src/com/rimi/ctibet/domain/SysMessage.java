package com.rimi.ctibet.domain;

import java.util.Date;

import javax.persistence.Transient;

public class SysMessage {
     private Integer id;
     private String code;
     private String title; //通知标题
	 private String content;//内容
	 private String msgTo;//接收人
	 private Integer score;//赠送的积分
	 private Date createDate;//创建时间
	 private String type;//通知类型
	 private Integer avaliable;//删除状态码
	 private String contentCode;
	 @Transient
	 private String contentTitle;
	 @Transient
	 private String url;
	 @Transient
	 private String state;
	 public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getContentCode() {
		return contentCode;
	}

	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}

	public Integer getAvaliable() {
		return avaliable;
	}

	public void setAvaliable(Integer avaliable) {
		this.avaliable = avaliable;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public SysMessage(){}
	
	

	public SysMessage(Integer id, String code, String title, String content,
			String msgTo, Integer score, Date createDate, String type,
			Integer avaliable) {
		super();
		this.id = id;
		this.code = code;
		this.title = title;
		this.content = content;
		this.msgTo = msgTo;
		this.score = score;
		this.createDate = createDate;
		this.type = type;
		this.avaliable = avaliable;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		this.content = content;
	}

	public String getMsgTo() {
		return msgTo;
	}

	public void setMsgTo(String msgTo) {
		this.msgTo = msgTo;
	}

	

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
