package com.rimi.ctibet.common.domain;

import java.io.Serializable;
import java.util.Date;

public class BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String code;
	private Date createTime = new Date();
	private Date lastEditTime;
	private int avaliable=1;
	
	/**
	 * tdk
	 */
	private String tdkTitle;
	private String tdkDescription;
	private String tdkKeywords;
	
	
	@Override
	public String toString() {
		return "BaseDomain [id=" + id + ", code=" + code + ", createTime="
				+ createTime + ", lastEditTime=" + lastEditTime
				+ ", avaliable=" + avaliable + "]";
	}
	
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
	public int getAvaliable() {
		return avaliable;
	}
	public void setAvaliable(int avaliable) {
		this.avaliable = avaliable;
	}

    public String getTdkTitle() {
        return tdkTitle;
    }

    public void setTdkTitle(String tdkTitle) {
        this.tdkTitle = tdkTitle;
    }

    public String getTdkDescription() {
        return tdkDescription;
    }

    public void setTdkDescription(String tdkDescription) {
        this.tdkDescription = tdkDescription;
    }

    public String getTdkKeywords() {
        return tdkKeywords;
    }

    public void setTdkKeywords(String tdkKeywords) {
        this.tdkKeywords = tdkKeywords;
    }
	
}
