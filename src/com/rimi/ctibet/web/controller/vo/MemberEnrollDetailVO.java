package com.rimi.ctibet.web.controller.vo;

import java.util.Date;

import com.rimi.ctibet.domain.MemberEnrollDetail;

public class MemberEnrollDetailVO extends MemberEnrollDetail {

	// 活动code
	private String activityCode;
	// 活动名
	private String activityName;
	// 会员name
	private String memberName;
	// sex
	private int sex;
	// 头像
	private String pic;
	// 参加时间
	private Date enrollTime;
	// 是否报名支付 1是 0否
    private int isEnrollPay;
    private String isEnrollPayName;
    // 支付状态
    private int payState;
    private String payStateName;
	//属性名
	private String property;
	//属性类型
	private String propertyType;
	//来源渠道code
	private String orderChannelSourceCode;
	//来源渠道name
	private String orderChannelSourceName;
	
	
	private String detail;
	/********************************************
	 * Setter Getter
	 ********************************************/
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
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
	public Date getEnrollTime() {
		return enrollTime;
	}
	public void setEnrollTime(Date enrollTime) {
		this.enrollTime = enrollTime;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
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
    public int getIsEnrollPay() {
        return isEnrollPay;
    }
    public void setIsEnrollPay(int isEnrollPay) {
        this.isEnrollPay = isEnrollPay;
    }
    public String getIsEnrollPayName() {
        return isEnrollPayName;
    }
    public void setIsEnrollPayName(String isEnrollPayName) {
        this.isEnrollPayName = isEnrollPayName;
    }
    public int getPayState() {
        return payState;
    }
    public void setPayState(int payState) {
        this.payState = payState;
    }
    public String getPayStateName() {
        return payStateName;
    }
    public void setPayStateName(String payStateName) {
        this.payStateName = payStateName;
    }
    public String getOrderChannelSourceCode() {
        return orderChannelSourceCode;
    }
    public void setOrderChannelSourceCode(String orderChannelSourceCode) {
        this.orderChannelSourceCode = orderChannelSourceCode;
    }
    public String getOrderChannelSourceName() {
        return orderChannelSourceName;
    }
    public void setOrderChannelSourceName(String orderChannelSourceName) {
        this.orderChannelSourceName = orderChannelSourceName;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
	
}
