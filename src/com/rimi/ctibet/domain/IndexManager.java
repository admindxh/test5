package com.rimi.ctibet.domain;

import com.rimi.ctibet.common.domain.BaseDomain;

/**
 * 首页数据管理
 * @author dengxh
 *
 */
public class IndexManager  extends BaseDomain{
	
	private  String  pramaCode ;
	private  String  url;
	private  String  number;
	private  String  replyname;
	private  String  praise;
	private  String  replycontent;
	
	public String getPramaCode() {
		return pramaCode;
	}
	public void setPramaCode(String pramaCode) {
		this.pramaCode = pramaCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getReplyname() {
		return replyname;
	}
	public void setReplyname(String replyname) {
		this.replyname = replyname;
	}
	public String getPraise() {
		return praise;
	}
	public void setPraise(String praise) {
		this.praise = praise;
	}
	public String getReplycontent() {
		return replycontent;
	}
	public void setReplycontent(String replycontent) {
		this.replycontent = replycontent;
	}
	
	
}
