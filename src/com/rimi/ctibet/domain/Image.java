package com.rimi.ctibet.domain;

import java.sql.Timestamp;

public class Image {
    //官方图集
    public final static String ATLAS_OFFICAL = "atlas_offical";
    //图集
    public final static String ATLAS_USER = "atlas_user";
    
	private int id;
	private int avaliable=1;
	private String code;
	private String mutiCode;
	private String name;
	private String summary;
	private String url;
	private Timestamp createTime;
	private String createUserCode;
	private Timestamp lastEditTime;	
	private String editUserCode;
	private String hyperlink;
	private int isPreview;
	private int goCount;
	private int wantCount;
	private String destinationCode;
	private String viewCode;
	private String isshow;
	
	
	private String smimg;//缩略图
	
	//帮助属性
	private String createUserName;

	public Image() {
	}

	public Image(int avaliable, String code, String mutiCode, String name, String summary) {
		this.avaliable = avaliable;
		this.code = code;
		this.mutiCode = mutiCode;
		this.name = name;
		this.summary = summary;
		this.createTime = new Timestamp(System.currentTimeMillis());
	}

	public Image(int id, int avaliable, String code, String mutiCode,
			String name, String summary, String url, Timestamp createTime,
			String createUserCode, Timestamp lastEditTime, String editUserCode,
			String hyperlink,int isPreview,int goCount,int wantCount) {
		super();
		this.id = id;
		this.avaliable = avaliable;
		this.code = code;
		this.mutiCode = mutiCode;
		this.name = name;
		this.summary = summary;
		this.url = url;
		this.createTime = createTime;
		this.createUserCode = createUserCode;
		this.lastEditTime = lastEditTime;
		this.editUserCode = editUserCode;
		this.hyperlink = hyperlink;
		this.isPreview=isPreview;
		this.goCount = goCount;
		this.wantCount = wantCount;
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

	public String getMutiCode() {
		return mutiCode;
	}

	public void setMutiCode(String mutiCode) {
		this.mutiCode = mutiCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public Timestamp getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Timestamp lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public String getEditUserCode() {
		return editUserCode;
	}

	public void setEditUserCode(String editUserCode) {
		this.editUserCode = editUserCode;
	}

	public String getHyperlink() {
		return hyperlink;
	}

	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink;
	}

	public void setIsPreview(int isPreview) {
		this.isPreview = isPreview;
	}
	public int getIsPreview() {
		return isPreview;
	}
	
	public int getGoCount() {
		return goCount;
	}

	public void setGoCount(int goCount) {
		this.goCount = goCount;
	}

	public int getWantCount() {
		return wantCount;
	}

	public void setWantCount(int wantCount) {
		this.wantCount = wantCount;
	}

	public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public String getViewCode() {
        return viewCode;
    }

    public void setViewCode(String viewCode) {
        this.viewCode = viewCode;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
 
    
    public String getIsshow() {
		return isshow;
	}

	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", avaliable=" + avaliable + ", code="
				+ code + ", mutiCode=" + mutiCode + ", name=" + name
				+ ", summary=" + summary + ", url=" + url + ", createTime="
				+ createTime + ", createUserCode=" + createUserCode
				+ ", lastEditTime=" + lastEditTime + ", editUserCode="
				+ editUserCode + ", hyperlink=" + hyperlink + ", isPreview="+isPreview +", goCount="+goCount+" , wantCount ="+wantCount+"]";
	}

	public String getSmimg() {
		return smimg;
	}

	public void setSmimg(String smimg) {
		this.smimg = smimg;
	}
	
}
