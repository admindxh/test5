package com.rimi.ctibet.web.controller.vo;

import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.GroupTravel;

public class GroupTravelVO extends GroupTravel {

    String viewCode;
    String viewName;
    int falseFavoriteNum;
    String detailCleanHtml;
    
    
    
    public String getViewCode() {
        return viewCode;
    }
    public void setViewCode(String viewCode) {
        this.viewCode = viewCode;
    }
    public String getViewName() {
        return viewName;
    }
    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
    public int getFalseFavoriteNum() {
        return falseFavoriteNum;
    }
    public void setFalseFavoriteNum(int falseFavoriteNum) {
        this.falseFavoriteNum = falseFavoriteNum;
    }
    public String getDetailCleanHtml() {
        this.detailCleanHtml = StringUtil.cleanHTML(super.getDetail());
        return detailCleanHtml;
    }
    public void setDetailCleanHtml(String detailCleanHtml) {
        this.detailCleanHtml = detailCleanHtml;
    }
    
    
    
}
