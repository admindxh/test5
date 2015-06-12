package com.rimi.ctibet.domain;

import java.util.Date;

public class LogUser {
   private String id;
   private String username;
   private String password;
   private String email;
   private String phone;
   private String code;
   private String memberCode;
   private Integer isBind;
   private String mobile;
   private Integer isVerified ;
   private String pic;
   private Integer sex;
   private Integer score;
   private Date birthday;
   private String province;
   private String city;
   private Integer status;
   private Integer memberType;
   private Date createTime;
public Date getCreateTime() {
	return createTime;
}
public void setCreateTime(Date createTime) {
	this.createTime = createTime;
}
public Integer getStatus() {
	return status;
}
public void setStatus(Integer status) {
	this.status = status;
}
public Integer getMemberType() {
	return memberType;
}
public void setMemberType(Integer memberType) {
	this.memberType = memberType;
}
public Date getBirthday() {
	return birthday;
}
public void setBirthday(Date birthday) {
	this.birthday = birthday;
}
public String getProvince() {
	return province;
}
public void setProvince(String province) {
	this.province = province;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getPic() {
	return pic;
}
public void setPic(String pic) {
	this.pic = pic;
}
public Integer getSex() {
	return sex;
}
public void setSex(Integer sex) {
	this.sex = sex;
}
public Integer getScore() {
	return score;
}
public void setScore(Integer score) {
	this.score = score;
}
public String getMobile() {
	return mobile;
}
public void setMobile(String mobile) {
	this.mobile = mobile;
}
public Integer getIsVerified() {
	return isVerified;
}
public void setIsVerified(Integer isVerified) {
	this.isVerified = isVerified;
}
public Integer getIsBind() {
	return isBind;
}
public void setIsBind(Integer isBind) {
	this.isBind = isBind;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public String getMemberCode() {
	return memberCode;
}
public void setMemberCode(String memberCode) {
	this.memberCode = memberCode;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
   
}
