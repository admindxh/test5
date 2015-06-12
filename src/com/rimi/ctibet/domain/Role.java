package com.rimi.ctibet.domain;

import java.util.Date;

public class Role {
	 private Integer id;
	 private String code;
	 private String name;
	 private String summary;
	 private String remark;
	 private String access;
	 private String accessCode;
	 private String createUserCode;
	 private String available = "1";
	 private Date createTime = new Date();
	 private Date lastEditTime;
	 
	public Role(){} 
	 
	public Role(Integer id, String code, String name, String summary,
			String remark, String access, String accessCode,
			String createUserCode, String available, Date createTime,
			Date lastEditTime) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.summary = summary;
		this.remark = remark;
		this.access = access;
		this.accessCode = accessCode;
		this.createUserCode = createUserCode;
		this.available = available;
		this.createTime = createTime;
		this.lastEditTime = lastEditTime;
	}

	public String getAccess() {
		return access;
	}
	
	public void setAccess(String access) {
		this.access = access;
	}
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAccessCode() {
		return accessCode;
	}
	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	 
	 

}
