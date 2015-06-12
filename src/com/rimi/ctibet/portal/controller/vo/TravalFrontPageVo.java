package com.rimi.ctibet.portal.controller.vo;

import java.util.Date;
import java.util.List;

import com.rimi.ctibet.domain.View;

public class TravalFrontPageVo {
	
	private String code;
	private String travelImgUrl;
	private String travelTitle;
	private String  userName;
	private Date createTime;
	private String  travelContent;
	private Integer  viewCount;
	private Integer faveteCount;
	private Integer praiseCount;
	private Integer replyCount;
	
	private String programaCode;
	
	private String url;
	
	private String isOfficial;
	
	private List<View>  viewList  ;
	
	private String pic;
	
	private Integer sex;
	
	private String  memberCode;
	
	public String getTravelImgUrl() {
		return travelImgUrl;
	}
	public void setTravelImgUrl(String travelImgUrl) {
		this.travelImgUrl = travelImgUrl;
	}
	public String getTravelTitle() {
		return travelTitle;
	}
	public void setTravelTitle(String travelTitle) {
		this.travelTitle = travelTitle;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTravelContent() {
		return travelContent;
	}
	public void setTravelContent(String travelContent) {
		this.travelContent = travelContent;
	}
	public Integer getViewCount() {
		return viewCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	public Integer getFaveteCount() {
		return faveteCount;
	}
	public void setFaveteCount(Integer faveteCount) {
		this.faveteCount = faveteCount;
	}
	public Integer getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}
	public Integer getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getIsOfficial() {
		return isOfficial;
	}
	public void setIsOfficial(String isOfficial) {
		this.isOfficial = isOfficial;
	}
	public List<View> getViewList() {
		return viewList;
	}
	public void setViewList(List<View> viewList) {
		this.viewList = viewList;
	}
	public String getProgramaCode() {
		return programaCode;
	}
	public void setProgramaCode(String programaCode) {
		this.programaCode = programaCode;
	}	
	
	
	
}

