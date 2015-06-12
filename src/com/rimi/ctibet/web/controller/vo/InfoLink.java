package com.rimi.ctibet.web.controller.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class InfoLink implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3099262153426809537L;
	private String link;
	private String code;
	private String type;
	private String id;
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		
		if(StringUtils.isNotBlank(link))
		{
			link=link.trim();
		}
		this.link = link;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		if(StringUtils.isNotBlank(code))
		{
			code=code.trim();
		}
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
