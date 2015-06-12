package com.rimi.ctibet.domain;

/**
 * 活动报名须知
 */
public class EnrollNotice {

    private int id;
    private String code;
    // 所属活动code
    private String activityCode;
    // 名称
    private String name1;
    // 简介
    private String summary1;
    private String name2;
    private String summary2;
    private String name3;
    private String summary3;
	
    public EnrollNotice(){}

	@Override
	public String toString() {
		return "EnrollNotice [id=" + id + ", code=" + code + ", activityCode="
				+ activityCode + ", name1=" + name1 + ", summary1=" + summary1
				+ ", name2=" + name2 + ", summary2=" + summary2 + ", name3="
				+ name3 + ", summary3=" + summary3 + "]";
	}

	/********************************************
	 * Setter Getter
	 ********************************************/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getSummary1() {
		return summary1;
	}

	public void setSummary1(String summary1) {
		this.summary1 = summary1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getSummary2() {
		return summary2;
	}

	public void setSummary2(String summary2) {
		this.summary2 = summary2;
	}

	public String getName3() {
		return name3;
	}

	public void setName3(String name3) {
		this.name3 = name3;
	}

	public String getSummary3() {
		return summary3;
	}

	public void setSummary3(String summary3) {
		this.summary3 = summary3;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
    
    
}
