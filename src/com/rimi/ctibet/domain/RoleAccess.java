package com.rimi.ctibet.domain;

import java.util.Date;

public class RoleAccess {
	
	 private int id;
	 private String roleCode;
	 private String accessName ;
	 private String accessUrl;
	 private int avaliable =1;
	 private Date createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getAccessName() {
		return accessName;
	}
	public void setAccessName(String accessName) {
		this.accessName = accessName;
	}
	public String getAccessUrl() {
		return accessUrl;
	}
	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}
	public int getAvaliable() {
		return avaliable;
	}
	public void setAvaliable(int avaliable) {
		this.avaliable = avaliable;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "RoleAccess [accessName=" + accessName + ", accessUrl="
				+ accessUrl + ", avaliable=" + avaliable + ", createTime="
				+ createTime + ", id=" + id + ", roleCode=" + roleCode + "]";
	}
}
