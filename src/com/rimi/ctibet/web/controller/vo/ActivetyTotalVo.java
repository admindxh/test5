package com.rimi.ctibet.web.controller.vo;

public class ActivetyTotalVo {
			
	private  Integer checkNum;//活动点击数
	
	private Integer acvisitCount;//活动参与数
	
	private String totalMoney;//总的净额
	
	private String acname;

	private String acurl;
	
	
	
	public String getAcurl() {
		return acurl;
	}

	public void setAcurl(String acurl) {
		this.acurl = acurl;
	}

	public Integer getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(Integer checkNum) {
		this.checkNum = checkNum;
	}

	public Integer getAcvisitCount() {
		return acvisitCount;
	}

	public void setAcvisitCount(Integer acvisitCount) {
		this.acvisitCount = acvisitCount;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getAcname() {
		return acname;
	}

	public void setAcname(String acname) {
		this.acname = acname;
	}
	
	
}
