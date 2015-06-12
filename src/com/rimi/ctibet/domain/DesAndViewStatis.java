package com.rimi.ctibet.domain;

import java.util.Date;

/**
 * 景点目的地统计
 * @author dengxh
 *
 */
public class DesAndViewStatis {
		
	private Integer  id;
	private String  desCode;
	private String viewCode;
	private Date ctime;
	private String cuserCode;
	private String type;//去过 的类型和 想去的类型  1 表示去过的  ， 0表示想去的
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDesCode() {
		return desCode;
	}
	public void setDesCode(String desCode) {
		this.desCode = desCode;
	}
	public String getViewCode() {
		return viewCode;
	}
	public void setViewCode(String viewCode) {
		this.viewCode = viewCode;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public String getCuserCode() {
		return cuserCode;
	}
	public void setCuserCode(String cuserCode) {
		this.cuserCode = cuserCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
