package com.rimi.ctibet.domain;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Transient;

public class Member {
	private int id;
	private int avaliable;
	private String code;
	private String password;
	private int status;
	private Timestamp createTime;
	private String createIp;
	private Integer memberType;

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Member() {
	}

	public Member(int id, int avaliable, String code, String password,
			int status, Timestamp createTime, String createIp) {
		this.id=id;
		this.avaliable=avaliable;
		this.code=code;
		this.password=password;
		this.status=status;
		this.createTime=createTime;
		this.createIp=createIp;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}
	
    @Transient
    public Date getLastActivateTime() {
        Calendar cl = Calendar.getInstance();
        cl.setTime(createTime);
        cl.add(Calendar.DATE , 2);

        return cl.getTime();
    }

	@Override
	public String toString() {
		return "Member [id=" + id + ", avaliable=" + avaliable + ", code="
				+ code + ", password=" + password + ", status=" + status
				+ ", createTime=" + createTime + ", createIp=" + createIp
				+ ", memberType=" + memberType + "]";
	}
	
}
