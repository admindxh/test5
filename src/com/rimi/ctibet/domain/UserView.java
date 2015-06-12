package com.rimi.ctibet.domain;

public class UserView {
   public static final String User_View_Wanna="wanna";  //想去
   public static final String User_View_Gone="gone";    //去过
   private Integer id;        //流水ID
   private Integer avaliable; //状态码
   private String memberCode; //用户CODE
   private String viewCode;   //景点CODE
   private String areaCode;   //目的地CODE（如拉萨地区）
   private String type;       //类型（想去/去过）
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getAvaliable() {
	return avaliable;
}
public void setAvaliable(Integer avaliable) {
	this.avaliable = avaliable;
}
public String getMemberCode() {
	return memberCode;
}
public void setMemberCode(String memberCode) {
	this.memberCode = memberCode;
}
public String getViewCode() {
	return viewCode;
}
public void setViewCode(String viewCode) {
	this.viewCode = viewCode;
}
public String getAreaCode() {
	return areaCode;
}
public void setAreaCode(String areaCode) {
	this.areaCode = areaCode;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
   
   
}
