package com.rimi.ctibet.web.controller.vo;

import java.util.List;

/**
 * 目的地和景点的结合封装
 * @author dengxh
 *
 */
public class DesAndViewVo {
	
	
	private String desName;
	
	private String ischecked;
	
	private  	List<String[]>  views  ;
	
	public String getDesName() {
		return desName;
	}
	public void setDesName(String desName) {
		this.desName = desName;
	}
	public List<String[]> getViews() {
		return views;
	}
	public void setViews(List<String[]> views) {
		this.views = views;
	}
	public String getIschecked() {
		return ischecked;
	}
	public void setIschecked(String ischecked) {
		this.ischecked = ischecked;
	}
	
	
	
}
