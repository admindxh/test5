package com.rimi.ctibet.domain;

import java.util.Date;

public class MemberInfo {
	private int id;
	private int avaliable;
	private String code;
	private String memberCode;
	private String name;
	private String description;
	private String phone;
	private String pic;
    private Integer sex = 1;
    private String email;
    private Integer score = 0;
    private Date birthday;
    private String province;
    private String city;
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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public MemberInfo() {
	}

	public MemberInfo(int id, int avaliable, String code, String memberCode,
			String name, String description, String phone, String pic,
			Integer sex, String email, Integer score) {
		super();
		this.id = id;
		this.avaliable = avaliable;
		this.code = code;
		this.memberCode = memberCode;
		this.name = name;
		this.description = description;
		this.phone = phone;
		this.pic = pic;
		this.sex = sex;
		this.email = email;
		this.score = score;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAvaliable() {
		return avaliable;
	}

	public void setAvaliable(int avaliable) {
		this.avaliable = avaliable;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "MemberInfo [id=" + id + ", avaliable=" + avaliable + ", code="
				+ code + ", memberCode=" + memberCode + ", name=" + name
				+ ", description=" + description + ", phone=" + phone
				+ ", pic=" + pic + ", sex=" + sex + ", email=" + email
				+ ", score=" + score + ", birthday=" + birthday + ", province="
				+ province + ", city=" + city + "]";
	}
	
	
	
	
}