	package com.rimi.ctibet.web.controller.vo;

import com.rimi.ctibet.domain.Activity;

public class ActivityCountVO extends Activity {
    
    //总产看数
    int totalCheck;
    //参加人员
    int joinNum;
    //总参加人员
    int totalJoinNum;
    //总金额
    int totalMoney;

    /********************************************
     * Setter Getter
     ********************************************/
    public int getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(int joinNum) {
        this.joinNum = joinNum;
    }

    public int getTotalCheck() {
        return totalCheck;
    }

    public void setTotalCheck(int totalCheck) {
        this.totalCheck = totalCheck;
    }

    public int getTotalJoinNum() {
        return totalJoinNum;
    }

    public void setTotalJoinNum(int totalJoinNum) {
        this.totalJoinNum = totalJoinNum;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }
}
