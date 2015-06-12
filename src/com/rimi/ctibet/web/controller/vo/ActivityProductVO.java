package com.rimi.ctibet.web.controller.vo;

import com.rimi.ctibet.domain.ActivityProduct;

public class ActivityProductVO extends ActivityProduct {

	//活动名称
	String activityName;
	//会员名
	String memberName;
	//审核状态
	String stateName;
	// sex
    int sex;
    // 头像
    String pic;
	
	/********************************************
	 * Setter Getter
	 ********************************************/
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
    public int getSex() {
        return sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public String getPic() {
        return pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    
}
