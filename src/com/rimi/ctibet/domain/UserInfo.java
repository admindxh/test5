package com.rimi.ctibet.domain;

import java.util.Date;

public class UserInfo {
	  private Integer id ;
	  private String  code;
	  private String  name;
	  private String pwd;
	  private String roleCode;
	  private Date createTime = new Date();
	  private Date lastEditTime = new Date();;
	  private String available = "1";

	  public final static String USERINFO  = "userInfo";
      public UserInfo(){}

	  public UserInfo(Integer id, String code, String name, String pwd,
			String roleCode, Date createTime, Date lastEditTime,
			String available) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.pwd = pwd;
		this.roleCode = roleCode;
		this.createTime = createTime;
		this.lastEditTime = lastEditTime;
		this.available = available;
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
	
		public String getPwd() {
			return pwd;
		}
	
		public void setPwd(String pwd) {
			this.pwd = pwd;
		}
	
		public String getRoleCode() {
			return roleCode;
		}
	
		public void setRoleCode(String roleCode) {
			this.roleCode = roleCode;
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
	
		public String getAvailable() {
			return available;
		}
	
		public void setAvailable(String available) {
			this.available = available;
		}

		@Override
		public String toString() {
			return "UserInfo [id=" + id + ", code=" + code + ", name=" + name
					+ ", pwd=" + pwd + ", roleCode=" + roleCode
					+ ", createTime=" + createTime + ", lastEditTime="
					+ lastEditTime + ", available=" + available + "]";
		}
	  
	  
      

}
