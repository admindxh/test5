package com.rimi.ctibet.web.controller.vo;

import com.rimi.ctibet.domain.Activity;

public class ActivityOrderVO extends Activity {

    // 支付笔数
    int payNum;
    // 订单数
    int orderNum;
    // 总金额
    float totalMoney;
    
    public ActivityOrderVO() {}
    
    @Override
    public String toString() {
        return "ActivityOrderVO [payNum=" + payNum + ", orderNum=" + orderNum
                + ", totalMoney=" + totalMoney + ", toString()="
                + super.toString() + "]";
    }

    /********************************************
     * Setter Getter
     ********************************************/
    public int getOrderNum() {
        return orderNum;
    }
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getPayNum() {
        return payNum;
    }

    public void setPayNum(int payNum) {
        this.payNum = payNum;
    }
    
}
