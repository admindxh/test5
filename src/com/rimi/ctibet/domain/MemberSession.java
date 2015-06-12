package com.rimi.ctibet.domain;

public class MemberSession {
	private int id;
	private int avaliable;
	private String code;
	private String memberCode;
	private String sessionId;
	private String exppire;
	
	public MemberSession(){}
	
	public MemberSession(int id, int avaliable, String code, String memberCode,
			String sessionId, String exppire){
		this.id = id;
		this.avaliable = avaliable;
		this.code = code;
		this.memberCode = memberCode;
		this.sessionId = sessionId;
		this.exppire = exppire;
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

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getExppire() {
		return exppire;
	}

	public void setExppire(String exppire) {
		this.exppire = exppire;
	}
	
	
}
