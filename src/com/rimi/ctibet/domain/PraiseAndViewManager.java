package com.rimi.ctibet.domain;

import com.rimi.ctibet.common.domain.BaseDomain;

public class PraiseAndViewManager extends BaseDomain{

	private String type;
    private String ip;
    private String contentCode;
    
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getContentCode() {
		return contentCode;
	}
	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}
    
    
}
