package com.rimi.ctibet.domain;

public class UserSysMessage {
     private Integer id;
     private String memberCode; //用户ID
	 private String msgCode;//信息ID
	 private String state;//状态
	 private String type;//通知类型（sysmsg:系统通知；post_reply:帖子回复； strategy_judge：攻略审核； post_delete:删帖通知）
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public UserSysMessage(){}
	public UserSysMessage(Integer id, String memberCode, String msgCode,
			String state, String type) {
		super();
		this.id = id;
		this.memberCode = memberCode;
		this.msgCode = msgCode;
		this.state = state;
		this.type = type;
	}
	 
	 
	


}
