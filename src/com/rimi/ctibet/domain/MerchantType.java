
package com.rimi.ctibet.domain;

import java.util.Date;

public class MerchantType {
    private Integer id;
    private String name;
    private String code;
    //首页推荐的排序
    private Integer rank;
    //一个 分类的url
    private String url;
    private Integer avaliable =1;
	private Date createTime = new Date();
   

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public MerchantType(){}
    
    public MerchantType(Integer id, String name, String code) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	public Integer getAvaliable() {
		return avaliable;
	}

	public void setAvaliable(Integer avaliable) {
		this.avaliable = avaliable;
	}

	@Override
	public String toString() {
		return "MerchantType [code=" + code + ", id=" + id + ", name=" + name
				+ "]";
	}
    
    
}
