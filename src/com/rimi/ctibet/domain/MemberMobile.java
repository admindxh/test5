package com.rimi.ctibet.domain;

import java.sql.Timestamp;

public class MemberMobile {
	@Override
	public String toString() {
		return "MemberMobile [id=" + id + ", avaliable=" + avaliable
				+ ", code=" + code + ", memberCode=" + memberCode + ", mobile="
				+ mobile + ", isVerified=" + isVerified + ", verifyTime="
				+ verifyTime + "]";
	}

	private int id;
	private int avaliable;
	private String code;
	private String memberCode;
	private String mobile;
	private int isVerified;
	private Timestamp verifyTime;
	
	public MemberMobile(){}
	
	public MemberMobile(int id, int avaliable, String code, String memberCode,
			String mobile, int isVerified, Timestamp verifyTime){
		this.id = id;
		this.avaliable = avaliable;
		this.code = code;
		this.memberCode = memberCode;
		this.mobile = mobile;
		this.isVerified= isVerified;
		this.verifyTime = verifyTime;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(int isVerified) {
		this.isVerified = isVerified;
	}

	public Timestamp getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Timestamp verifyTime) {
		this.verifyTime = verifyTime;
	}
	
	
}
