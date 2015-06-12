package com.rimi.ctibet.domain;

import java.sql.Timestamp;
import java.util.List;

public class MutiImage {

	private int id;
	private int avaliable;
	private String code;
	private String programaCode;
	private String name;
	private String summary;
	private String detail;
	private String coverUrl;
	private Timestamp createTime;
	private String directory;
	private String createUserCode;
	private Timestamp lastEditTime;
	private String editUserCode;
	private String keywords;
	private String description;
	private String title;
	private String isOfficial;
	private Integer seeCount;
	private String hyperlink;
	private Praise praise;
	private List<Image> images;

	/**
     * tdk
     */
    private String tdkTitle;
    private String tdkDescription;
    private String tdkKeywords;
    
	public MutiImage() {
	}

	public MutiImage(int id, int avaliable, String code, String programaCode,
			String name, String summary, String detail, String coverUrl,
			Timestamp createTime, String directory, String createUserCode,
			Timestamp lastEditTime, String editUserCode, String keywords,
			String description, String title,String isOfficial,Integer seeCount,String hyperlink) {
		this.id = id;
		this.avaliable = avaliable;
		this.code = code;
		this.programaCode = programaCode;
		this.name = name;
		this.summary = summary;
		this.detail = detail;
		this.coverUrl = coverUrl;
		this.createTime = createTime;
		this.directory = directory;
		this.createUserCode = createUserCode;
		this.lastEditTime = lastEditTime;
		this.editUserCode = editUserCode;
		this.keywords = keywords;
		this.description = description;
		this.title = title;
		this.isOfficial=isOfficial;
		this.seeCount = seeCount;
		this.hyperlink=hyperlink;
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

	public String getProgramaCode() {
		return programaCode;
	}

	public void setProgramaCode(String programaCode) {
		this.programaCode = programaCode;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
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

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

	public String getIsOfficial() {
		return isOfficial;
	}

	public void setIsOfficial(String isOfficial) {
		this.isOfficial = isOfficial;
	}

	public Integer getSeeCount() {
		return seeCount;
	}

	public void setSeeCount(Integer seeCount) {
		this.seeCount = seeCount;
	}
	

	public String getHyperlink() {
        return hyperlink;
    }


    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

    @Override
    public String toString() {
        return "MutiImage [id=" + id + ", avaliable=" + avaliable + ", code="
                + code + ", programaCode=" + programaCode + ", name=" + name
                + ", summary=" + summary + ", detail=" + detail + ", coverUrl="
                + coverUrl + ", createTime=" + createTime + ", directory="
                + directory + ", createUserCode=" + createUserCode
                + ", lastEditTime=" + lastEditTime + ", editUserCode="
                + editUserCode + ", keywords=" + keywords + ", description="
                + description + ", title=" + title + ", isOfficial="
                + isOfficial + ", seeCount=" + seeCount + ", hyperlink="
                + hyperlink + ", praise=" + praise + ", images=" + images
                + ", tdkTitle=" + tdkTitle + ", tdkDescription="
                + tdkDescription + ", tdkKeywords=" + tdkKeywords + "]";
    }

	public Praise getPraise() {
		return praise;
	}

	public void setPraise(Praise praise) {
		this.praise = praise;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
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
