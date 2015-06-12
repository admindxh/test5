package com.rimi.ctibet.domain;

import java.util.Date;

import com.rimi.ctibet.common.domain.BaseDomain;
import com.rimi.ctibet.common.util.CodeFactory;

/**
 * 订单
 */
public class Order extends BaseDomain {
    
    // 活动code
    private String activityCode;
    // 会员code
    private String memberCode;
    // 渠道code()
    private String orderChannelCode;
    // 来源渠道code(-1表示官方渠道)
    private String orderChannelSourceCode;
    // 支付状态 0待付 1已付
    private int payState=0;
    // 付款金额
    private float money;
    // 支付宝交易号
    private String alipayOrderCode;
    // 成交时间
    private Date dealTime;
    // 付款人支付宝邮箱
    private String alipayEmail;
    
    public Order() {}

    @Override
    public String toString() {
        return "Order [activityCode=" + activityCode + ", memberCode="
                + memberCode + ", orderChannelCode=" + orderChannelCode
                + ", orderChannelSourceCode=" + orderChannelSourceCode
                + ", payState=" + payState + ", money=" + money
                + ", alipayOrderCode=" + alipayOrderCode + ", dealTime="
                + dealTime + ", alipayEmail=" + alipayEmail + ", toString()="
                + super.toString() + "]";
    }
    
    public final static String createCode(){
        return CodeFactory.createCode("OD");
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

    public String getOrderChannelCode() {
        return orderChannelCode;
    }

    public void setOrderChannelCode(String orderChannelCode) {
        this.orderChannelCode = orderChannelCode;
    }

    public int getPayState() {
        return payState;
    }

    public void setPayState(int payState) {
        this.payState = payState;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getAlipayOrderCode() {
        return alipayOrderCode;
    }

    public void setAlipayOrderCode(String alipayOrderCode) {
        this.alipayOrderCode = alipayOrderCode;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getAlipayEmail() {
        return alipayEmail;
    }

    public void setAlipayEmail(String alipayEmail) {
        this.alipayEmail = alipayEmail;
    }

    public String getOrderChannelSourceCode() {
        return orderChannelSourceCode;
    }

    public void setOrderChannelSourceCode(String orderChannelSourceCode) {
        this.orderChannelSourceCode = orderChannelSourceCode;
    }
    
    
}
