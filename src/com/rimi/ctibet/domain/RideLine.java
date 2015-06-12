	package com.rimi.ctibet.domain;

import com.rimi.ctibet.common.domain.BaseDomain;

public class RideLine extends BaseDomain {
    private static final long serialVersionUID = 1L;
    
    public static final String DEFUALT_LINK = "ride/line/list?code=";
    
    // 名称
    private String name;
    // 副标题
    private String subTitle;
    // banner图
    private String bannerImg;
    // 简介
    private String introduce;
    // 说明
    private String summary;
    // 注意事项
    private String notice;
    // 链接
    private String link;
    
    private String achref;//活动地址
    
    
    /********************************************
     * Setter Getter
     ********************************************/
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBannerImg() {
        return bannerImg;
    }
    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }
    public String getIntroduce() {
        return introduce;
    }
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getNotice() {
        return notice;
    }
    public void setNotice(String notice) {
        this.notice = notice;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getSubTitle() {
        return subTitle;
    }
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
	/**
	 * @return the achref
	 */
	public String getAchref() {
		return achref;
	}
	/**
	 * @param achref the achref to set
	 */
	public void setAchref(String achref) {
		this.achref = achref;
	}
    
}
