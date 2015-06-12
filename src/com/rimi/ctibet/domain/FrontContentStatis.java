package com.rimi.ctibet.domain;

import java.util.Date;

/**
 * 
 * @author dengxh
 *
 */
public class FrontContentStatis {
	
	private Integer id;
	private String progtype;//所属类型
	private String actiontype;//操作类型
	private String tablename;//表明
	private String code;//内容code
	private Date ctime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProgtype() {
		return progtype;
	}
	public void setProgtype(String progtype) {
		this.progtype = progtype;
	}
	public String getActiontype() {
		return actiontype;
	}
	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	
	
	
}
