package com.rimi.ctibet.domain;

import java.sql.Timestamp;

public class MemberEmail {
	private int id;
	private int avaliable;
	private String code;
	private String memberCode;
	private String email;
	private String validateCode;
	private int isBind;
	private Timestamp bindTime;

	public MemberEmail() {
	}

	public MemberEmail(int id, int avaliable, String code, String memberCode,
			String email, int isBind, Timestamp bindTime , String validateCode) {
		this.id = id;
		this.avaliable = avaliable;
		this.code = code;
		this.memberCode = memberCode;
		this.email = email;
		this.isBind = isBind;
		this.bindTime = bindTime;
		this.validateCode= validateCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAvaliable() {
		return avaliable;
	}

	public void setAvaliable(int avaliable) {
		this.avaliable = avaliable;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIsBind() {
		return isBind;
	}

	public void setIsBind(int isBind) {
		this.isBind = isBind;
	}

	public Timestamp getBindTime() {
		return bindTime;
	}

	public void setBindTime(Timestamp bindTime) {
		this.bindTime = bindTime;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	@Override
	public String toString() {
		return "MemberEmail [id=" + id + ", avaliable=" + avaliable + ", code="
				+ code + ", memberCode=" + memberCode + ", email=" + email
				+ ", validateCode=" + validateCode + ", isBind=" + isBind
				+ ", bindTime=" + bindTime + "]";
	}


	
	

}
