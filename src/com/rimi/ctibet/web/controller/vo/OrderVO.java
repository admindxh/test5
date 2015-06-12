package com.rimi.ctibet.web.controller.vo;

import java.util.Date;

import com.rimi.ctibet.common.util.StringUtil;

public class OrderVO {

    // 活动code
    String activityCode;
    // 活动name
    String activityName;
    // 活动详情页地址
    String activityLinkUrl;
    // 活动金额
    float activityMoney;
    // 订单号
    String orderCode;
    // 支付状态
    int payState;
    String payStateName;
    // 订单金额（改价后与活动中的金额不一样）
    float orderMoney;
    // 订单创建时间
    Date createTime;
    // 渠道code
    String orderChannelCode;
    // 渠道name
    String orderChannelName;
    // 会员code
    String memberCode;
    // 会员name
    String memberName;
    // 手机
    String mobile;
    // 邮箱
    String email;
    // 报名渠道code
    String orderChannelSourceCode;
    // 报名渠道name
    String orderChannelSourceName;
    
    public OrderVO(){}

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

    public float getActivityMoney() {
        return activityMoney;
    }

    public void setActivityMoney(float activityMoney) {
        this.activityMoney = activityMoney;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getPayState() {
        return payState;
    }

    public void setPayState(int payState) {
        this.payState = payState;
    }

    public float getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(float orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrderChannelCode() {
        return orderChannelCode;
    }

    public void setOrderChannelCode(String orderChannelCode) {
        this.orderChannelCode = orderChannelCode;
    }

    public String getOrderChannelName() {
        return StringUtil.isNotNull(orderChannelName)?orderChannelName:"官方网站";
    }

    public void setOrderChannelName(String orderChannelName) {
        this.orderChannelName = orderChannelName;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivityLinkUrl() {
        return activityLinkUrl;
    }

    public void setActivityLinkUrl(String activityLinkUrl) {
        this.activityLinkUrl = activityLinkUrl;
    }

    public String getPayStateName() {
        return payState==0?"未付款":"已付款";
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
}
