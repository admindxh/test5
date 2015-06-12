package com.rimi.ctibet.domain;

import java.util.Date;

public class Reply {
	private int id;
    private String code;
    // 回复、评论code
    private String postCode;
    // 内容code
    private String contentCode;
    // 序号
    private int number=0;
    
    private Date ctime =  new Date();
    
    public Reply(){}
    
    public Reply(int id, String code, String postCode, String contentCode,
    		int number) {
		super();
		this.id = id;
		this.code = code;
		this.postCode = postCode;
		this.contentCode = contentCode;
		this.number = number;
		this.ctime  = new Date();
	}

	@Override
	public String toString() {
		return "Reply [id=" + id + ", code=" + code + ", postCode=" + postCode
				+ ", contentCode=" + contentCode + ", number=" + number + "]";
	}
	/********************************************
	 * Setter Getter
	 ********************************************/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getContentCode() {
		return contentCode;
	}
	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
    
}
