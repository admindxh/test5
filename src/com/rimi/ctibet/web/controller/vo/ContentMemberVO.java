package com.rimi.ctibet.web.controller.vo;

import java.util.Date;

public class ContentMemberVO {
	
	private String url;
	// 内容code
	private String code;
	// 内容标题
	private String contentTitle;
	// 内容
	private String content;
	// 作者名字
	private String authorName;
	// 审核状态
	private int state;
	// 审核状态名称
	private String stateName;
	// 官方标记
	private int isOfficial;
	// 官方标记名称
	private String isOfficialName;
	// 板块类型、攻略类型
	private String programaName;
	// 会员名
	private String name;
	// 会员邮箱
	private String email;
	// 会员手机
	private String mobile;
	// 内容创建时间
	private Date createTime;
	
	@Override
	public String toString() {
		return "ContentMemberVO [code=" + code + ", contentTile=" + contentTitle
				+ ", content=" + content + ", authorName=" + authorName
				+ ", state=" + state + ", stateName=" + stateName
				+ ", isOfficial=" + isOfficial + ", isOfficialName="
				+ isOfficialName + ", programaName=" + programaName + ", name="
				+ name + ", email=" + email + ", mobile=" + mobile
				+ ", createTime=" + createTime + ", getCode()=" + getCode()
				+ ", getContentTile()=" + getContentTitle() + ", getContent()="
				+ getContent() + ", getAuthorName()=" + getAuthorName()
				+ ", getIsOfficial()=" + getIsOfficial()
				+ ", getIsOfficialName()=" + getIsOfficialName()
				+ ", getProgramaName()=" + getProgramaName() + ", getName()="
				+ getName() + ", getEmail()=" + getEmail() + ", getMobile()="
				+ getMobile() + ", getCreateTime()=" + getCreateTime()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public int getIsOfficial() {
		return isOfficial;
	}

	public void setIsOfficial(int isOfficial) {
		this.isOfficial = isOfficial;
	}

	public String getIsOfficialName() {
		return isOfficialName;
	}

	public void setIsOfficialName(String isOfficialName) {
		this.isOfficialName = isOfficialName;
	}

	public String getProgramaName() {
		return programaName;
	}

	public void setProgramaName(String programaName) {
		this.programaName = programaName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
