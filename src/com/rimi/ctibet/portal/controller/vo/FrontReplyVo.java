package com.rimi.ctibet.portal.controller.vo;

import java.util.Date;

/**
 * 回复信息帮助vo
 * @author dengxh
 *
 */
public class FrontReplyVo {
	private String  userImgUrl;
	private String userName;
	private Date  createTime;
	private String  replyContent;
	private String  replyUserCode;
	private String  replYUserSex;
	public String getUserImgUrl() {
		return userImgUrl;
	}
	public void setUserImgUrl(String userImgUrl) {
		this.userImgUrl = userImgUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public String getReplyUserCode() {
		return replyUserCode;
	}
	public void setReplyUserCode(String replyUserCode) {
		this.replyUserCode = replyUserCode;
	}
	public String getReplYUserSex() {
		return replYUserSex;
	}
	public void setReplYUserSex(String replYUserSex) {
		this.replYUserSex = replYUserSex;
	} 
	
	
	
	
	
	
}
