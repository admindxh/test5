package com.rimi.ctibet.domain;

import com.rimi.ctibet.common.domain.BaseDomain;

/**
 * 订单渠道
 */
public class OrderChannelSource extends BaseDomain {
    
    public final static String PAY_LINK = "alipay/channelPay?chcd=";

    // 活动code
    private String activityCode;
    // 渠道name
    private String name;
    // 报名支付链接
    private String payLink;
    
    public OrderChannelSource() {}

    @Override
    public String toString() {
        return "OrderChannel [activityCode=" + activityCode + ", name=" + name
                + ", payLink=" + payLink + ", toString()=" + super.toString()
                + "]";
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayLink() {
        return payLink;
    }

    public void setPayLink(String payLink) {
        this.payLink = payLink;
    }
    
    
}
