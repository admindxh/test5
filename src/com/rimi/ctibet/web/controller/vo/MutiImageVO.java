package com.rimi.ctibet.web.controller.vo;

import java.util.List;

import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.MutiImage;

public class MutiImageVO extends MutiImage {
	
	String code;
	List<Image> listImage;
	String name;
	String programaCode;
	String summary;
	String coverUrl;
	String falseViewcount;
	String falseFavoriteNum;

	/********************************************
	 * Setter Getter
	 ********************************************/
	public List<Image> getListImage() {
		return listImage;
	}
	public void setListImage(List<Image> listImage) {
		this.listImage = listImage;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProgramaCode() {
		return programaCode;
	}
	public void setProgramaCode(String programaCode) {
		this.programaCode = programaCode;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public String getFalseViewcount() {
		return falseViewcount;
	}
	public void setFalseViewcount(String falseViewcount) {
		this.falseViewcount = falseViewcount;
	}
	public String getFalseFavoriteNum() {
		return falseFavoriteNum;
	}
	public void setFalseFavoriteNum(String falseFavoriteNum) {
		this.falseFavoriteNum = falseFavoriteNum;
	}
	
}
