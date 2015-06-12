package com.rimi.ctibet.web.controller.vo;

import java.util.Date;

/**
 * 帖子通过帮助 vo
 * 
 * @author dengxh
 *
 */
public class PostPassVo {

	private String code;
	private String userName;
	private String pname;
	private String ctitle;
	private Date ctime;
	private String content;
	private String contenttitle;
	private Integer falseViewcount;
	private Integer falsepraise;
	private Integer falsereplynum;
	private Integer isTop;
	private String url;

	private String mcp;

	private String replyinfo;
	private String replyName;
	private String replysex;
	public String getReplysex() {
		return replysex;
	}

	public void setReplysex(String replysex) {
		this.replysex = replysex;
	}

	private String replyTime;
	private String replyPic;

	private String postuserpic;
	private String usercode;
	private String usersex;

	public String getUsersex() {
		return usersex;
	}

	public void setUsersex(String usersex) {
		this.usersex = usersex;
	}

	public Integer getFalsereplynum() {
		return falsereplynum;
	}

	public void setFalsereplynum(Integer falsereplynum) {
		this.falsereplynum = falsereplynum;
	}

	public String getReplyinfo() {
		return replyinfo;
	}

	public void setReplyinfo(String replyInfo) {
		this.replyinfo = replyInfo;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getCtitle() {
		return ctitle;
	}

	public void setCtitle(String ctitle) {
		this.ctitle = ctitle;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getFalseViewcount() {
		return falseViewcount;
	}

	public void setFalseViewcount(Integer falseViewcount) {
		this.falseViewcount = falseViewcount;
	}

	public Integer getFalsepraise() {
		return falsepraise;
	}

	public void setFalsepraise(Integer falsepraise) {
		this.falsepraise = falsepraise;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContenttitle() {
		return contenttitle;
	}

	public void setContenttitle(String contenttitle) {
		this.contenttitle = contenttitle;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public String getPostuserpic() {
		return postuserpic;
	}

	public void setPostuserpic(String postuserpic) {
		this.postuserpic = postuserpic;
	}

	public String getReplyName() {
		return replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public String getReplyPic() {
		return replyPic;
	}

	public void setReplyPic(String replyPic) {
		this.replyPic = replyPic;
	}

	public String getMcp() {
		return mcp;
	}

	public void setMcp(String mcp) {
		this.mcp = mcp;
	}

}
