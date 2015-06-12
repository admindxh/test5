package com.rimi.ctibet.domain;

import com.rimi.ctibet.common.domain.BaseDomain;

public class MerchantAndView extends BaseDomain{
	
	private int id;
    private String merchantCode;
    private String viewCode;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getViewCode() {
		return viewCode;
	}
	public void setViewCode(String viewCode) {
		this.viewCode = viewCode;
	}
	
    
	
	
}
