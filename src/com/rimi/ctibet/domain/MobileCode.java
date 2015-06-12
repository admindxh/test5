package com.rimi.ctibet.domain;

import java.sql.Timestamp;

public class MobileCode {
       private int id;
	   private String mobile;
	   private String validateCode;
	   private Timestamp createTime;
	   
	   public MobileCode(){}
	   
	   public MobileCode(int id,String mobile,String validateCode,Timestamp createTime){
		   this.id=id;
		   this.mobile=mobile;
		   this.validateCode=validateCode;
		   this.createTime=createTime;
	   }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	   

}
