package com.rimi.ctibet.domain;

import java.util.Date;


/**
 * @author xiaozhen
 * 栏目对象
 */
public class Programa {
       private Integer id;
	   private String code;
	   private String programaName;
	   private String enName;
	   private String programaSummary;
	   private String pCode;
	   private String programaUrl;
	   private String imageUrl;
	   private String rank;
	   private String remark;
	   private String createUserCode;
	   private Date createTime = new Date();
	   private Date lastEditTime;
	   private Integer available=1;
	   private String typeCode;
	   
	public Programa(){}

	public Programa(Integer id, String code, String programaName,
			String enName, String programaSummary, String pCode,
			String programaUrl, String imageUrl, String rank, String remark,
			String createUserCode, Date createTime, Date lastEditTime,
			Integer available,String typeCode) {
		super();
		this.id = id;
		this.code = code;
		this.programaName = programaName;
		this.enName = enName;
		this.programaSummary = programaSummary;
		this.pCode = pCode;
		this.programaUrl = programaUrl;
		this.imageUrl = imageUrl;
		this.rank = rank;
		this.remark = remark;
		this.createUserCode = createUserCode;
		this.createTime = createTime;
		this.lastEditTime = lastEditTime;
		this.available = available;
		this.typeCode=typeCode;
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

	public String getProgramaName() {
		return programaName;
	}

	public void setProgramaName(String programaName) {
		this.programaName = programaName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getProgramaSummary() {
		return programaSummary;
	}

	public void setProgramaSummary(String programaSummary) {
		this.programaSummary = programaSummary;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public String getProgramaUrl() {
		return programaUrl;
	}

	public void setProgramaUrl(String programaUrl) {
		this.programaUrl = programaUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
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

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "Programa [available=" + available + ", code=" + code
				+ ", createTime=" + createTime + ", createUserCode="
				+ createUserCode + ", enName=" + enName + ", id=" + id
				+ ", imageUrl=" + imageUrl + ", lastEditTime=" + lastEditTime
				+ ", pCode=" + pCode + ", programaName=" + programaName
				+ ", programaSummary=" + programaSummary + ", programaUrl="
				+ programaUrl + ", rank=" + rank + ", remark=" + remark + "]";
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeCode() {
		return typeCode;
	}
	
}
