package com.rimi.ctibet.domain;

import com.rimi.ctibet.common.domain.BaseDomain;

/**
 * 会员报名详细
 */
public class MemberEnrollDetail extends BaseDomain {
    // 会员code
    private String memberCode;
    // 所属活动code
    private String activityCode;
    // 报名表单字段对应的code
    private String enrollFormCode;
    // 报名表单字段对应的值
    private String propertyValue;
    // 对于文件类型的字段需要保存文件名
    private String fileName;
    // 0默认 1置顶
    private int isTop;
    // 订单号
    private String orderCode;
    
    public MemberEnrollDetail(){}

	@Override
    public String toString() {
        return "MemberEnrollDetail [memberCode=" + memberCode
                + ", activityCode=" + activityCode + ", enrollFormCode="
                + enrollFormCode + ", propertyValue=" + propertyValue
                + ", fileName=" + fileName + ", isTop=" + isTop
                + ", orderCode=" + orderCode + ", toString()="
                + super.toString() + "]";
    }


    /********************************************
	 * Setter Getter
	 ********************************************/
	public String getOrderCode() {
	    return orderCode;
	}
	
	public void setOrderCode(String orderCode) {
	    this.orderCode = orderCode;
	}
	
	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getEnrollFormCode() {
		return enrollFormCode;
	}

	public void setEnrollFormCode(String enrollFormCode) {
		this.enrollFormCode = enrollFormCode;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getIsTop() {
		return isTop;
	}

	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}
    
}
