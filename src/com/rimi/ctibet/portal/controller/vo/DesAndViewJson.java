package com.rimi.ctibet.portal.controller.vo;

import java.util.List;

/**
 * 上传攻略页面帮助类
 * @author dengxh
 *
 */
public class DesAndViewJson {
	
	private String name;
	private String id;
	private boolean checked;
	private List<ViewJson> items;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<ViewJson> getItems() {
		return items;
	}
	public void setItems(List<ViewJson> items) {
		this.items = items;
	}
	
	
			
}
