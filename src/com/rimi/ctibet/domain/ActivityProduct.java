package com.rimi.ctibet.domain;

import com.rimi.ctibet.common.domain.BaseDomain;

/**
 * 活动作品
 */
public class ActivityProduct extends BaseDomain {
    // 活动code
    private String activityCode;
    // 会员code
    private String memberCode;
    // 作品名
    private String name;
    // 作品地址
    private String url;
    // 作品文件名
    private String fileName;
    // 赞
    private int likeNum;
    // 伪赞
    private int fakeLikeNum;
    //0默认 1置顶
    private int isTop;
    //审核状态-1未通过，0待审核，1已通过
    private int state;
    
    public ActivityProduct(){}
    
	@Override
	public String toString() {
		return "ActivityProduct [activityCode=" + activityCode
				+ ", memberCode=" + memberCode + ", name=" + name + ", url="
				+ url + ", fileName=" + fileName + ", likeNum=" + likeNum
				+ ", fakeLikeNum=" + fakeLikeNum + ", isTop=" + isTop
				+ ", state=" + state + "]";
	}

	/********************************************
	 * Setter Getter
	 ********************************************/

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
	}

	public int getFakeLikeNum() {
		return fakeLikeNum;
	}

	public void setFakeLikeNum(int fakeLikeNum) {
		this.fakeLikeNum = fakeLikeNum;
	}

	public int getIsTop() {
		return isTop;
	}

	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
    
}
