package com.rimi.ctibet.domain;

import java.util.Date;

public class UserFavorite {
	private int id;
	private String memberCode;
	private String code;
	private String type;
	private String state;
	private Date joinTime;
	/****** 收藏类型 Begin*******/
	
	public static final String  User_Fav_Post="post";      //帖子
	public static final String  User_Fav_Stra="stratege";  //攻略
	public static final String  User_Fav_Read="readTibet"; //读西藏
	public static final String  User_Fav_See="seeTibet";   //看西藏
	public static final String  User_Fav_Merc="merchant";  //商户
	public static final String  User_Fav_Group="tourGroup";//团游
	public static final String  User_Fav_Image="seeTibet";    //图片看西藏
	public static final String  User_Fav_Activity="activity";//活动 
	
	/****** 收藏类型 End*******/
	
	public UserFavorite(int id, String memberCode, String code,
			String type, String state) {
		super();
		this.id = id;
		this.memberCode = memberCode;
		this.code = code;
		this.type = type;
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public UserFavorite() {
	}

	public UserFavorite(int id, String memberCode,
			String postCode) {
		this.id = id;
		
		this.memberCode = memberCode;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	

	
}